import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";
import {BuildzStore} from "../store/buildz-store";
import {IBuildSearchRequestParams, IBuildSearchResult, IBuildStats} from "../domain";
import {Observable} from "rxjs/Observable";
import {buildInfoVisible, buildSearchRequestParameters, buildSearchResult, buildStats, selectedBuild} from "../selectors";

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

  selectedBuild() {
    return this.store.select(selectedBuild);
  }

  buildInfoVisible() {
    return this.store.select(buildInfoVisible);
  }
}
