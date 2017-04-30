import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from "@angular/core";
import {IBuild} from "../../../core/domain";
@Component({
  selector: 'bz-build-info',
  templateUrl: './build-info.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BuildInfoComponent {
  @Input()
  build: IBuild;
  @Input()
  visible: boolean;
  @Output()
  onClose = new EventEmitter<void>();

  get isActive(): string {
    return this.visible ? 'is-active' : '';
  }
}
