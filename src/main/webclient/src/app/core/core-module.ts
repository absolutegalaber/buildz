import {NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";
import {CommonModule} from "@angular/common";
import {StoreModule} from "@ngrx/store";
import {Selector} from "./services/selector-service";
import {Dispatcher} from "./services/dispatcher-service";
import {RouterModule} from "@angular/router";
@NgModule({
  imports: [
    HttpModule
  ],
  exports: [
    CommonModule,
    HttpModule,
    RouterModule,
    StoreModule
  ]
})
export class CoreModule {
  static forRoot() {
    return {
      ngModule: CoreModule,
      providers: [
        Selector,
        Dispatcher
      ]
    }
  }
}
