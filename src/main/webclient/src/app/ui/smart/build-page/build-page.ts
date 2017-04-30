import {Component} from "@angular/core";
import {Selector} from "../../../core/services/selector-service";
import {Dispatcher} from "../../../core/services/dispatcher-service";
import {Observable} from "rxjs/Observable";
import {IBuild, IBuildSearchRequestParams, IBuildSearchResult} from "../../../core/domain";
@Component({
  templateUrl: './build-page.html'
})
export class BuildPage {
  buildSearchParameters: Observable<IBuildSearchRequestParams>;
  buildSearchResult: Observable<IBuildSearchResult>;
  selectedBuild: Observable<IBuild>;
  buildInfoVisible: Observable<Boolean>;

  constructor(selector: Selector, private dispatcher: Dispatcher) {
    this.buildSearchParameters = selector.buildSearchParams();
    this.buildSearchResult = selector.buildSearchResult();
    this.selectedBuild = selector.selectedBuild();
    this.buildInfoVisible = selector.buildInfoVisible();
  }

  searchChanged(params: IBuildSearchRequestParams) {
    this.dispatcher.buildSearchChanged(params)
  }

  prevPage() {
    this.dispatcher.prevBuildListPage();
  }

  nextPage() {
    this.dispatcher.nextBuildListPage();
  }

  buildSelected(id: number) {
    this.dispatcher.singleBuildSelected(id);
  }

  hideBuildInfo() {
    this.dispatcher.hideBuildInfo();
  }
}
