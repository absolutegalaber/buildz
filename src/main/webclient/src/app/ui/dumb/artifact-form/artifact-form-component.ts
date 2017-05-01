import {ChangeDetectionStrategy, Component, Input, OnInit} from "@angular/core";
import {IArtifact} from "../../../core/domain";
import {FormArray, FormControl, FormGroup} from "@angular/forms";
@Component({
  selector: 'bz-artifact-form',
  templateUrl: './artifact-form-component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ArtifactForm implements OnInit {
  @Input()
  artifact: IArtifact;
  theForm: FormGroup;

  test() {
    let rawValue = this.theForm.getRawValue();
    let newArtifact: IArtifact = {
      project: rawValue.project,
      branch: rawValue.branch,
      labels: {}
    };
    for (let val of rawValue.labels) {
      newArtifact.labels[val.key] = val.value;
    }
    console.log(newArtifact);
  }

  ngOnInit(): void {
    console.log(this.artifact);
    let labelControls = [];
    for (let key in this.artifact.labels) {
      labelControls.push(new FormGroup({
        key: new FormControl(key),
        value: new FormControl(this.artifact.labels[key])
      }));
    }
    this.theForm = new FormGroup({
      project: new FormControl(this.artifact.project),
      branch: new FormControl(this.artifact.branch),
      labels: new FormArray(labelControls),
    });
  }
}