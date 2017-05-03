import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {CommonModule} from "@angular/common";
import {StoreModule} from "@ngrx/store";
import {BuildzSelector} from "./services/buildz-selector-service";
import {BuildzDispatcher} from "./services/buildz-dispatcher-service";
import {RouterModule} from "@angular/router";
import {CloningPipe} from "./services/cloning.pipe";
@NgModule({
  imports: [
    HttpModule
  ],
  declarations: [
    CloningPipe
  ],
  exports: [
    CommonModule,
    HttpModule,
    RouterModule,
    StoreModule,
    CloningPipe
  ]
})
export class CoreModule {
  static forRoot() {
    return {
      ngModule: CoreModule,
      providers: [
        BuildzSelector,
        BuildzDispatcher
      ]
    }
  }
}
