import {Routes} from "@angular/router";
import {HomePage} from "./ui/smart/home-page/home-page";
import {BuildPage} from "./ui/smart/build-page/build-page";
import {EnvironmentPage} from "./ui/smart/environment-page/environment-page";
import {LoadEnvironmentGuard} from "./ui/guards/load-environment.guard";
export const buildzRoutes: Routes = [
  {path: '', component: HomePage},
  {path: 'builds', component: BuildPage},
  {path: 'environment/:environmentName', component: EnvironmentPage, canActivate: [LoadEnvironmentGuard]}
];
