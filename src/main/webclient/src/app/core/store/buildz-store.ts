import {IBuild, IBuildSearch, IBuildStats} from "../domain";
import {statsReducer} from "./build-reducer";
import {compose} from "@ngrx/core/compose";
import {environment} from "../../../environments/environment";
import {combineReducers} from "@ngrx/store";
import {storeFreeze} from "ngrx-store-freeze";
import {storeLogger} from "ngrx-store-logger";
import {buildSearchReducer} from "./build-search-reducer";

export interface BuildzStore {
  stats: IBuildStats,
  buildSearch: IBuildSearch,
  selectedBuild: IBuild
}

export const buildzReducer = {
  stats: statsReducer,
  buildSearch: buildSearchReducer
};

const prodReducer = compose(combineReducers)(buildzReducer);
const devReducer = compose(storeLogger({collapsed: true}), storeFreeze, combineReducers)(buildzReducer);

export function buildzRootReducer(state: any, action: any) {
  if (environment.production) {
    return prodReducer(state, action);
  } else {
    return devReducer(state, action);
  }
}
