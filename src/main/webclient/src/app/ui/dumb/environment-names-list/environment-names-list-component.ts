import {ChangeDetectionStrategy, Component, Input} from "@angular/core";
@Component({
  selector: 'bz-environment-names',
  templateUrl: './environment-names-list-component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EnvironmentNamesListComponent {
  @Input()
  environmentNames: string[];
}
