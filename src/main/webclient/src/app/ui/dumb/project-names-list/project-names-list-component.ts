import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from "@angular/core";
@Component({
  selector: 'bz-project-names',
  templateUrl: './project-names-list-component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProjectNamesListComponent {
  @Input()
  projectNames: string[];
  @Output()
  onSelectProject = new EventEmitter<string>();
}
