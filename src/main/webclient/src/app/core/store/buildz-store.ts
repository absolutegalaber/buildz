import {IBuild, IBuildState, IBuildStats} from "../domain";
import {statsReducer} from "./build-reducer";
import {compose} from "@ngrx/core/compose";
import {environment} from "../../../environments/environment";
import {combineReducers} from "@ngrx/store";
import {storeFreeze} from "ngrx-store-freeze";
import {storeLogger} from "ngrx-store-logger";
import {buildStateReducer} from "./build-state-reducer";

export interface BuildzStore {
  stats: IBuildStats,
  buildState: IBuildState,
}

export const buildzReducer = {
  stats: statsReducer,
  buildState: buildStateReducer
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
