import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {IBuildSearchRequestParams} from "../../../core/domain";
import {FormControl, FormGroup} from "@angular/forms";
@Component({
  selector: 'bz-build-search-form',
  templateUrl: './build-search-form.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BuildSearchFormComponent implements OnInit {
  @Input()
  buildSearchParams: IBuildSearchRequestParams;
  @Output()
  onSearchChanged = new EventEmitter<IBuildSearchRequestParams>();
  theForm: FormGroup;

  ngOnInit(): void {
    this.theForm = new FormGroup({
      project: new FormControl(this.buildSearchParams.project),
      branch: new FormControl(this.buildSearchParams.branch),
      minBuildNumber: new FormControl(this.buildSearchParams.minBuildNumber),
      maxBuildNumber: new FormControl(this.buildSearchParams.maxBuildNumber),
      sortAttribute: new FormControl(this.buildSearchParams.sortAttribute),
      sortDirection: new FormControl(this.buildSearchParams.sortDirection),
      pageSize: new FormControl(this.buildSearchParams.pageSize)
    });
  }
}
