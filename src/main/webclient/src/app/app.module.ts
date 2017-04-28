import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {CoreModule} from "./core/core-module";
import {UiModule} from "./ui/ui.module";
import {RouterModule} from "@angular/router";
import {buildzRoutes} from "./app.routes";
import {StoreModule} from "@ngrx/store";
import {buildzRootReducer} from "./core/store/builds-store";
import {EffectsModule} from "@ngrx/effects";
import {BuildzEffects} from "./core/effects/buildz-effects";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {RouterStoreModule} from "@ngrx/router-store";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpModule,
    CoreModule.forRoot(),
    UiModule,
    RouterModule.forRoot(buildzRoutes),
    RouterStoreModule.connectRouter(),
    StoreModule.provideStore(buildzRootReducer),
    EffectsModule.run(BuildzEffects),
    StoreDevtoolsModule.instrumentOnlyWithExtension()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
