import {Routes} from "@angular/router";
import {HomePage} from "./ui/smart/home-page/home-page";
import {BuildListPage} from "./ui/smart/build-list-page/build-list-page";
export const buildzRoutes: Routes = [
  {path: '', component: HomePage},
  {path: 'build-list', component: BuildListPage},
];
