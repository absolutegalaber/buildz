import {IBuildSearch, IBuildSearchRequestParams} from "../domain";
import {Action} from "@ngrx/store";
import {cloneIt} from "./clone";

export const SEARCH_BUILDS = '[BuildSearch] Do Search';
export const BUILD_SEARCH_MODIFIED = '[BuildSearch] Modified';
export const PROJECT_SELECTED = '[BuildSearch] Project Selected';
export const NEXT_BUILDS_PAGE = '[BuildSearch] Next Page';
export const PREV_BUILDS_PAGE = '[BuildSearch] Prev Page';
export const SEARCH_LOADED = '[BuildSearch] Loaded';

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

  constructor(public payload: IBuildSearch) {
  }
}

export type BuildSearchAction =
  DoSearch |
  BuildSearchModified |
  NextBuildsPage |
  PrevBuildsPage |
  ProjectSelected |
  BuildSearchLoaded;

export const initial_IBuildSearch: IBuildSearch = {
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
  }
;

export function buildSearchReducer(state: IBuildSearch = initial_IBuildSearch, action: BuildSearchAction): IBuildSearch {
  switch (action.type) {
    case PROJECT_SELECTED: {
      let newState: IBuildSearch = cloneIt(state);
      newState.project = action.payload;
      return newState;
    }
    case NEXT_BUILDS_PAGE: {
      let newState: IBuildSearch = cloneIt(state);
      newState.page++;
      return newState;
    }
    case PREV_BUILDS_PAGE: {
      let newState: IBuildSearch = cloneIt(state);
      newState.page--;
      return newState;
    }
    case BUILD_SEARCH_MODIFIED: {
      let newState: IBuildSearch = cloneIt(state);
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
      let newState: IBuildSearch = cloneIt(state);
      newState.builds = action.payload.builds;
      newState.totalElements = action.payload.totalElements;
      newState.totalPages = action.payload.totalPages;
      newState.hasNext = action.payload.hasNext;
      newState.hasPrevious = action.payload.hasPrevious;
      return newState;
    }
    default:
      return state;
  }
}
