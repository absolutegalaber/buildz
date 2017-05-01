import {Routes} from "@angular/router";
import {HomePage} from "./ui/smart/home-page/home-page";
import {BuildPage} from "./ui/smart/build-page/build-page";
import {EnvironmentPage} from "./ui/smart/environment-page/environment-page";
export const buildzRoutes: Routes = [
  {path: '', component: HomePage},
  {path: 'builds', component: BuildPage},
  {path: 'environments', component: EnvironmentPage}
];
