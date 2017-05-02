import {Component} from "@angular/core";
import {BuildzSelector} from "../../../core/services/selector-service";
import {Observable} from "rxjs/Observable";
import {IBuildStats} from "../../../core/domain";
import {BuildzDispatcher} from "../../../core/services/dispatcher-service";
@Component({
  templateUrl: './home-page.html'
})
export class HomePage {
  buildStats: Observable<IBuildStats>;

  constructor(selector: BuildzSelector, private dispatcher: BuildzDispatcher) {
    this.buildStats = selector.buildStats();
  }

  selectProject(projectName: string) {
    this.dispatcher.projectSelected(projectName);
  }
}
