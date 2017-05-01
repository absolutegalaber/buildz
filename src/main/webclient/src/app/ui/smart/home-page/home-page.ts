import {Component} from "@angular/core";
import {Selector} from "../../../core/services/selector-service";
import {Observable} from "rxjs/Observable";
import {IBuildStats} from "../../../core/domain";
import {Dispatcher} from "../../../core/services/dispatcher-service";
@Component({
  templateUrl: './home-page.html'
})
export class HomePage {
  buildStats: Observable<IBuildStats>;

  constructor(selector: Selector, private dispatcher: Dispatcher) {
    this.buildStats = selector.buildStats();
  }

  selectProject(projectName: string) {
    this.dispatcher.projectSelected(projectName);
  }

  selectEnvironment(environmentName: string) {
    this.dispatcher.environmentSelected(environmentName);
  }
}
