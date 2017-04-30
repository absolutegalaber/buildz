import {Routes} from "@angular/router";
import {HomePage} from "./ui/smart/home-page/home-page";
import {BuildPage} from "./ui/smart/build-page/build-page";
export const buildzRoutes: Routes = [
  {path: '', component: HomePage},
  {path: 'builds', component: BuildPage}
];
