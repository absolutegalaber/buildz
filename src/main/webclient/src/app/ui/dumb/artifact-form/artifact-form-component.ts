import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {IArtifact} from "../../../core/domain";
import {FormArray, FormControl, FormGroup} from "@angular/forms";
@Component({
  selector: 'bz-artifact-form',
  templateUrl: './artifact-form-component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ArtifactFormComponent implements OnInit {
  @Input()
  artifact: IArtifact;
  @Output()
  onArtifactChanged = new EventEmitter<IArtifact>();
  theForm: FormGroup;
  labelsForm: FormArray;


  artifactChanged() {
    this.onArtifactChanged.emit(this.fromFormModel());
  }

  addLabel() {
    this.artifact.labels['Please Define Label Key'] = 'Please Define Label Value';
    this.toFormModel();
  }

  deleteLabel(key: string): void {
    delete this.artifact.labels[key];
    this.toFormModel();
  }

  ngOnInit(): void {
    this.toFormModel();
  }

  toFormModel(): void {
    let labelControls = [];
    for (let key in this.artifact.labels) {
      labelControls.push(new FormGroup({
        key: new FormControl(key),
        value: new FormControl(this.artifact.labels[key])
      }));
    }
    this.labelsForm = new FormArray(labelControls);
    this.theForm = new FormGroup({
      project: new FormControl(this.artifact.project),
      branch: new FormControl(this.artifact.branch),
    });
  }

  fromFormModel(): IArtifact {
    let rawValue = this.theForm.getRawValue();
    let newArtifact: IArtifact = {
      project: rawValue.project,
      branch: rawValue.branch,
      labels: {}
    };
    for (let obj of this.labelsForm.getRawValue()) {
      newArtifact.labels[obj.key] = obj.value;
    }
    return newArtifact;
  }
}
