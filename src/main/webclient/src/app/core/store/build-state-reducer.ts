import {IBuild, IBuildSearchRequestParams, IBuildState} from "../domain";
import {Action} from "@ngrx/store";
import {cloneIt} from "./clone";

export const SEARCH_BUILDS = '[BuildState] Do Search';
export const BUILD_SEARCH_MODIFIED = '[BuildState] Modified';
export const PROJECT_SELECTED = '[BuildState] Project Selected';
export const NEXT_BUILDS_PAGE = '[BuildState] Next Page';
export const PREV_BUILDS_PAGE = '[BuildState] Prev Page';
export const SEARCH_LOADED = '[BuildState] Search Loaded';
export const SINGLE_BUILD_SELECTED = '[BuildState] Build Selected';
export const SINGLE_BUILD_LOADED = '[BuildState] Build Loaded';
export const HIDE_BUILD_INFO = '[BuildState] Hide Build Modal';

export class DoSearch implements Action {
  readonly type = SEARCH_BUILDS;
  readonly payload = null;
}
export class BuildSearchModified implements Action {
  readonly type = BUILD_SEARCH_MODIFIED;

  constructor(public payload: IBuildSearchRequestParams) {
  }
}
export class NextBuildsPage implements Action {
  readonly type = NEXT_BUILDS_PAGE;
  readonly payload = null;
}
export class PrevBuildsPage implements Action {
  readonly type = PREV_BUILDS_PAGE;
  readonly payload = null;
}
export class ProjectSelected implements Action {
  readonly type = PROJECT_SELECTED;

  constructor(public payload: string) {
  }
}
export class BuildSearchLoaded implements Action {
  readonly type = SEARCH_LOADED;

  constructor(public payload: IBuildState) {
  }
}
export class BuildSelected implements Action {
  readonly type = SINGLE_BUILD_SELECTED;

  constructor(public payload: number) {
  }
}
export class BuildLoaded implements Action {
  readonly type = SINGLE_BUILD_LOADED;

  constructor(public payload: IBuild) {
  }
}
export class HideBuildInfo implements Action {
  readonly type = HIDE_BUILD_INFO;
}

export type BuildSearchAction =
  DoSearch |
  BuildSearchModified |
  NextBuildsPage |
  PrevBuildsPage |
  ProjectSelected |
  BuildSelected |
  BuildLoaded |
  BuildSearchLoaded |
  HideBuildInfo;

export const initial_IBuildSearch: IBuildState = {
  project: null,
  branch: null,
  minBuildNumber: null,
  maxBuildNumber: null,
  labels: [],
  pageSize: 5,
  page: 0,
  sortAttribute: 'buildNumber',
  sortDirection: 'desc',
  builds: [],
  totalElements: 0,
  totalPages: 0,
  hasNext: false,
  hasPrevious: false,
  selectedBuild: null,
  buildInfoVisible: false
};

export function buildStateReducer(state: IBuildState = initial_IBuildSearch, action: BuildSearchAction): IBuildState {
  switch (action.type) {
    case PROJECT_SELECTED: {
      let newState: IBuildState = cloneIt(state);
      newState.project = action.payload;
      return newState;
    }
    case NEXT_BUILDS_PAGE: {
      let newState: IBuildState = cloneIt(state);
      newState.page++;
      return newState;
    }
    case PREV_BUILDS_PAGE: {
      let newState: IBuildState = cloneIt(state);
      newState.page--;
      return newState;
    }
    case BUILD_SEARCH_MODIFIED: {
      let newState: IBuildState = cloneIt(state);
      newState.project = action.payload.project;
      newState.branch = action.payload.branch;
      newState.minBuildNumber = action.payload.minBuildNumber;
      newState.maxBuildNumber = action.payload.maxBuildNumber;
      newState.pageSize = action.payload.pageSize;
      newState.page = action.payload.page;
      newState.sortAttribute = action.payload.sortAttribute;
      newState.sortDirection = action.payload.sortDirection;
      return newState;
    }
    case SEARCH_LOADED: {
      let newState: IBuildState = cloneIt(state);
      newState.builds = action.payload.builds;
      newState.totalElements = action.payload.totalElements;
      newState.totalPages = action.payload.totalPages;
      newState.hasNext = action.payload.hasNext;
      newState.hasPrevious = action.payload.hasPrevious;
      return newState;
    }
    case SINGLE_BUILD_LOADED: {
      let newState: IBuildState = cloneIt(state);
      newState.selectedBuild = action.payload;
      newState.buildInfoVisible = true;
      return newState;
    }
    case HIDE_BUILD_INFO: {
      let newState: IBuildState = cloneIt(state);
      newState.buildInfoVisible = false;
      return newState;
    }
    default:
      return state;
  }
}
