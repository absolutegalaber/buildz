import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";
import {BuildzStore} from "../store/buildz-store";
import {StatsRequired} from "../store/build-reducer";
import {BuildSearchModified, NextBuildsPage, PrevBuildsPage, ProjectSelected} from "../store/build-search-reducer";
import {IBuildSearchRequestParams} from "../domain";
@Injectable()
export class Dispatcher {

  constructor(private store: Store<BuildzStore>) {
  }

  loadStats() {
    this.store.dispatch(new StatsRequired());
  }

  projectSelected(projectName: string) {
    this.store.dispatch(new ProjectSelected(projectName));
  }

  buildSearchChanged(params: IBuildSearchRequestParams) {
    this.store.dispatch(new BuildSearchModified(params));
  }

  prevBuildListPage() {
    this.store.dispatch(new PrevBuildsPage());
  }

  nextBuildListPage() {
    this.store.dispatch(new NextBuildsPage());
  }
}
