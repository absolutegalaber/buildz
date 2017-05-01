import {Action} from "@ngrx/store";
import {IEnvironment, IEnvironmentState} from "../domain";
import {cloneIt} from "./clone";

export const ENV_SELECTED = '[EnvironmentState] Selected';
export const ENV_LOADED = '[EnvironmentState] Loaded';

export class EnvironmentSelected implements Action {
  readonly type = ENV_SELECTED;

  constructor(public payload: string) {
  }
}
export class EnvironmentLoaded implements Action {
  readonly type = ENV_LOADED;

  constructor(public payload: IEnvironment) {
  }
}

export type IEnvironmentAction = EnvironmentLoaded;

export const initial_IBuildSearch: IEnvironmentState = {
  environment: null
};

export function environmentStateReducer(state: IEnvironmentState = initial_IBuildSearch, action: IEnvironmentAction): IEnvironmentState {
  switch (action.type) {
    case ENV_LOADED: {
      let newState: IEnvironmentState = cloneIt(state);
      newState.environment = action.payload;
      return newState;
    }
    default:
      return state;
  }
}
