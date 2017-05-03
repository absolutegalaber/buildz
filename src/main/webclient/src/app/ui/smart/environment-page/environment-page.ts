import {Component} from "@angular/core";
import {BuildzSelector} from "../../../core/services/buildz-selector-service";
import {IArtifact, IBuild} from "../../../core/domain";
import {Observable} from "rxjs/Observable";
import {BuildzDispatcher} from "../../../core/services/buildz-dispatcher-service";
@Component({
  templateUrl: './environment-page.html'
})
export class EnvironmentPage {
  environmentName: Observable<string>;
  artifacts: Observable<IArtifact[]>;
  builds: Observable<IBuild[]>;
  buildsLoaded: Observable<boolean>;

  constructor(selector: BuildzSelector, private dispatcher: BuildzDispatcher) {
    this.environmentName = selector.selectedEnvironmentName();
    this.artifacts = selector.selectedEnvironmentArtifacts();
    this.builds = selector.environmentBuilds();
    this.buildsLoaded = selector.environmentBuildsLoaded();
  }

  addArtifact() {
    this.dispatcher.addArtifact();
  }

  deleteArtifact(index: number) {
    this.dispatcher.deleteArtifact(index);
  }

  artifactChanged(newArtifact: IArtifact, artifactIndex: number) {
    this.dispatcher.artifactChanged({
      newArtifact: newArtifact,
      artifactIndex: artifactIndex
    });
  }

  saveEnv() {
    this.dispatcher.saveEnvironment();
  }
}
