import {Component} from "@angular/core";
import {Selector} from "../../../core/services/selector-service";
import {IArtifact} from "../../../core/domain";
import {Observable} from "rxjs/Observable";
@Component({
  templateUrl: './environment-page.html'
})
export class EnvironmentPage {
  environmentName: Observable<string>;
  artifacts: Observable<IArtifact[]>;

  constructor(selector: Selector) {
    this.environmentName = selector.selectedEnvironmentName();
    this.artifacts = selector.selectedEnvironmentArtifacts();
  }
}
