import {NgModule} from "@angular/core";
import {CoreModule} from "../core/core-module";
import {HomePage} from "./smart/home-page/home-page";
import {Dispatcher} from "../core/services/dispatcher-service";
import {ProjectNamesListComponent} from "./dumb/project-names-list/project-names-list-component";
import {EnvironmentNamesListComponent} from "./dumb/environment-names-list/environment-names-list-component";
import {BuildPage} from "./smart/build-page/build-page";
import {BuildSearchFormComponent} from "./dumb/build-search-form/build-search-form.component";
import {BuildListTableComponent} from "./dumb/build-list-table/build-list-table.component";
import {ReactiveFormsModule} from "@angular/forms";
import {BuildInfoComponent} from "./dumb/build-info/build-info.component";
@NgModule({
  imports: [
    CoreModule,
    ReactiveFormsModule
  ],
  declarations: [
    HomePage,
    BuildPage,
    ProjectNamesListComponent,
    EnvironmentNamesListComponent,
    BuildSearchFormComponent,
    BuildListTableComponent,
    BuildInfoComponent
  ],
  providers: []
})
export class UiModule {
  constructor(dispatcher: Dispatcher) {
    dispatcher.loadStats();
  }
}
