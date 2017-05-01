import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from "@angular/core";
@Component({
  selector: 'bz-environment-names',
  templateUrl: './environment-names-list-component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EnvironmentNamesListComponent {
  @Input()
  environmentNames: string[];
  @Output()
  onSelectEnvironment = new EventEmitter<string>();
}
