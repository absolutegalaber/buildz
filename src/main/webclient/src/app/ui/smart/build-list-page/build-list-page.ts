import {Component} from "@angular/core";
import {Selector} from "../../../core/services/selector-service";
import {Dispatcher} from "../../../core/services/dispatcher-service";
import {Observable} from "rxjs/Observable";
import {IBuildSearchRequestParams, IBuildSearchResult} from "../../../core/domain";
@Component({
  templateUrl: './build-list-page.html'
})
export class BuildListPage {
  buildSearchParameters: Observable<IBuildSearchRequestParams>;
  buildSearchResult: Observable<IBuildSearchResult>;

  constructor(selector: Selector, private dispatcher: Dispatcher) {
    this.buildSearchParameters = selector.buildSearchParams();
    this.buildSearchResult = selector.buildSearchResult();
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
}
