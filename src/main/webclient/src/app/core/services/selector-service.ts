import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";
import {BuildzStore} from "../store/buildz-store";
import {IArtifact, IBuild, IBuildSearchRequestParams, IBuildSearchResult, IBuildStats} from "../domain";
import {Observable} from "rxjs/Observable";
import {buildInfoVisible, buildSearchRequestParameters, buildSearchResult, buildStats, selectedBuild, selectedEnvironmentArtifacts, selectedEnvironmentName} from "../selectors";

@Injectable()
export class Selector {

  constructor(private store: Store<BuildzStore>) {
  }

  buildStats(): Observable<IBuildStats> {
    return this.store.select(buildStats);
  }

  buildSearchParams(): Observable<IBuildSearchRequestParams> {
    return this.store.select(buildSearchRequestParameters);
  }

  buildSearchResult(): Observable<IBuildSearchResult> {
    return this.store.select(buildSearchResult);
  }

  selectedBuild(): Observable<IBuild> {
    return this.store.select(selectedBuild);
  }

  buildInfoVisible(): Observable<boolean> {
    return this.store.select(buildInfoVisible);
  }

  selectedEnvironmentName(): Observable<string> {
    return this.store.select(selectedEnvironmentName);
  }

  selectedEnvironmentArtifacts(): Observable<IArtifact[]> {
    return this.store.select(selectedEnvironmentArtifacts);
  }

}
