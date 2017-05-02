import {ChangeDetectionStrategy, Component, Input} from "@angular/core";
import {IBuild} from "../../../core/domain";
@Component({
  selector: 'bz-artifact-list',
  templateUrl: './artifact-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ArtifactListComponent {
  @Input()
  builds: IBuild[];
}
