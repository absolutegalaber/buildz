import "rxjs/add/operator/map";
import "rxjs/add/operator/mergeMap";
import "rxjs/add/operator/switchMapTo";
import "rxjs/add/operator/switchMap";
import "rxjs/add/operator/withLatestFrom";
import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Action, Store} from "@ngrx/store";
import {Actions, Effect, toPayload} from "@ngrx/effects";
import {STATS_REQUIRED, StatsLoaded} from "../store/build-reducer";
import {IBuild, IBuildState, IBuildStats} from "../domain";
import {BUILD_SEARCH_MODIFIED, BuildLoaded, BuildSearchLoaded, NEXT_BUILDS_PAGE, PREV_BUILDS_PAGE, PROJECT_SELECTED, SEARCH_BUILDS, SINGLE_BUILD_SELECTED} from "../store/build-state-reducer";
import {BuildzStore} from "../store/buildz-store";
import {artifactsForVerification, buildSearchRequestParameters} from "../selectors";
import {go} from "@ngrx/router-store";
import {ENV_ARTIFACT_CHANGED, EnvironmentBuildsVerified, VERFIY_ENV_BUILDS} from "../store/environment-state-reducer";
@Injectable()
export class BuildzEffects {

  constructor(private store: Store<BuildzStore>, private actions$: Actions, private http: Http) {
  }

  @Effect()
  statsRequired$: Observable<Action> = this.actions$
    .ofType(STATS_REQUIRED)
    .switchMapTo(
      this.http.get('/v1/builds/stats/')
        .map((res: Response) =>
          new StatsLoaded(res.json() as IBuildStats)
        )
    );

  @Effect()
  searchBuilds$: Observable<Action> = this.actions$
    .ofType(
      SEARCH_BUILDS,
      BUILD_SEARCH_MODIFIED,
      PROJECT_SELECTED,
      PREV_BUILDS_PAGE,
      NEXT_BUILDS_PAGE
    )
    .withLatestFrom(this.store.select(buildSearchRequestParameters))
    .switchMap(([action, params]) =>
      this.http.post('/v1/builds/search', params)
        .mergeMap((res: Response) => [
          new BuildSearchLoaded(res.json() as IBuildState),
          go(['/builds'])
        ])
    );

  @Effect()
  singleBuildSelected$: Observable<Action> = this.actions$
    .ofType(
      SINGLE_BUILD_SELECTED
    )
    .map(toPayload)
    .switchMap((id: number) =>
      this.http.get(`/v1/builds/${id}`)
        .map((res: Response) =>
          new BuildLoaded(res.json() as IBuild),
        )
    );

  @Effect()
  verifyEnvironment: Observable<Action> = this.actions$
    .ofType(
      ENV_ARTIFACT_CHANGED,
      VERFIY_ENV_BUILDS
    )
    .withLatestFrom(this.store.select(artifactsForVerification))
    .switchMap(([action, artifacts]) =>
      this.http.post(`/v1/environments/verify`, artifacts)
        .map((res: Response) =>
          new EnvironmentBuildsVerified(res.json() as IBuild[])
        )
    );
}
