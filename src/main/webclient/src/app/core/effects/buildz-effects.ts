import "rxjs/add/operator/map";
import "rxjs/add/operator/mergeMap";
import "rxjs/add/operator/switchMapTo";
import "rxjs/add/operator/switchMap";
import "rxjs/add/operator/withLatestFrom";
import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Action, Store} from "@ngrx/store";
import {Actions, Effect} from "@ngrx/effects";
import {STATS_REQUIRED, StatsLoaded} from "../store/build-reducer";
import {IBuildSearch, IBuildStats} from "../domain";
import {BUILD_SEARCH_MODIFIED, BuildSearchLoaded, NEXT_BUILDS_PAGE, PREV_BUILDS_PAGE, PROJECT_SELECTED, SEARCH_BUILDS} from "../store/build-search-reducer";
import {BuildzStore} from "../store/builds-store";
import {buildSearchRequestParameters} from "../selectors";
import {go} from "@ngrx/router-store";
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
          new BuildSearchLoaded(res.json() as IBuildSearch),
          go('build-list')
        ])
    );
}
