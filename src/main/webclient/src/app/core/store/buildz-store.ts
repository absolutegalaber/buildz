import {IBuildState, IBuildStats, IEnvironmentState} from "../domain";
import {statsReducer} from "./stats-reducer";
import {compose} from "@ngrx/core/compose";
import {environment} from "../../../environments/environment";
import {combineReducers} from "@ngrx/store";
import {storeFreeze} from "ngrx-store-freeze";
import {storeLogger} from "ngrx-store-logger";
import {buildStateReducer} from "./build-state-reducer";
import {environmentStateReducer} from "./environment-state-reducer";

export interface BuildzStore {
  stats: IBuildStats,
  buildState: IBuildState,
  environmentState: IEnvironmentState
}

export const buildzReducer = {
  stats: statsReducer,
  buildState: buildStateReducer,
  environmentState: environmentStateReducer
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
