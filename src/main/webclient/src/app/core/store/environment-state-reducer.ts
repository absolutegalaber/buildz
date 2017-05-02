import {Action} from "@ngrx/store";
import {IArtifactData, IBuild, IEnvironment, IEnvironmentState} from "../domain";
import {cloneIt} from "./clone";

export const ENV_LOADED = '[EnvironmentState] Loaded';
export const ENV_ARTIFACT_CHANGED = '[EnvironmentState] Artifact Changed';
export const ENV_ADD_ARTIFACT = '[EnvironmentState] Add Artifact';
export const ENV_DELETE_ARTIFACT = '[EnvironmentState] Delete Artifact';
export const VERFIY_ENV_BUILDS = '[EnvironmentState] Verify Environment Builds';
export const ENV_BUILDS_VERIFIED = '[EnvironmentState] Environment Builds Loaded';
export const SAVE_ENV = '[EnvironmentState] Save Environment ';

export class EnvironmentLoaded implements Action {
  readonly type = ENV_LOADED;

  constructor(public payload: IEnvironment) {
  }
}
export class AddArtifact implements Action {
  readonly type = ENV_ADD_ARTIFACT;
}
export class DeleteArtifact implements Action {
  readonly type = ENV_DELETE_ARTIFACT;

  constructor(public payload: number) {
  }
}
export class ArtifactChanged implements Action {
  readonly type = ENV_ARTIFACT_CHANGED;

  constructor(public payload: IArtifactData) {
  }
}
export class VerifyEnvironment implements Action {
  readonly type = VERFIY_ENV_BUILDS;
}
export class EnvironmentBuildsVerified implements Action {
  readonly type = ENV_BUILDS_VERIFIED;

  constructor(public payload: IBuild[]) {
  }
}
export class SaveEnvironment implements Action {
  readonly type = SAVE_ENV;
}

export type IEnvironmentAction =
  EnvironmentLoaded |
  ArtifactChanged |
  VerifyEnvironment |
  EnvironmentBuildsVerified |
  AddArtifact |
  DeleteArtifact;

export const initial_IBuildSearch: IEnvironmentState = {
  environment: null,
  builds: [],
  buildsLoaded: false,
  artifactsForVerification: [],
};

export function environmentStateReducer(state: IEnvironmentState = initial_IBuildSearch, action: IEnvironmentAction): IEnvironmentState {
  switch (action.type) {
    case ENV_LOADED: {
      let newState: IEnvironmentState = cloneIt(state);
      newState.environment = action.payload;
      newState.artifactsForVerification = cloneIt(newState.environment.artifacts);
      newState.buildsLoaded = false;
      newState.builds = [];
      return newState;
    }
    case ENV_ARTIFACT_CHANGED: {
      let newState: IEnvironmentState = cloneIt(state);
      newState.artifactsForVerification[action.payload.artifactIndex] = action.payload.newArtifact;
      newState.buildsLoaded = false;
      newState.builds = [];
      return newState;
    }
    case ENV_BUILDS_VERIFIED: {
      let newState: IEnvironmentState = cloneIt(state);
      newState.builds = action.payload;
      newState.buildsLoaded = true;
      return newState;
    }
    case ENV_ADD_ARTIFACT: {
      let newState: IEnvironmentState = cloneIt(state);
      newState.artifactsForVerification.push({
        project: null,
        branch: null,
        labels: {}
      });
      return newState;
    }
    case ENV_DELETE_ARTIFACT: {
      let newState: IEnvironmentState = cloneIt(state);
      newState.artifactsForVerification.splice(action.payload, 1);
      return newState;
    }
    default:
      return state;
  }
}
