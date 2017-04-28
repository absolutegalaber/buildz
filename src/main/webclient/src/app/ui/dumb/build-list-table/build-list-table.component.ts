import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from "@angular/core";
import {IBuildSearchResult} from "../../../core/domain";
@Component({
  selector: 'bz-build-list-table',
  templateUrl: './build-list-table.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BuildListTableComponent {
  @Input()
  buildSearchResult: IBuildSearchResult;
  @Output()
  onPrevPage = new EventEmitter<void>();
  @Output()
  onNextPage = new EventEmitter<void>();
}
