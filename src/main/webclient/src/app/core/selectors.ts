import {createSelector} from "reselect";

import {BuildzStore} from "./store/buildz-store";
import {IBuildSearch, IBuildSearchRequestParams, IBuildSearchResult, IBuildStats, ISearchLabel} from "./domain";

export const buildStats = (store: BuildzStore): IBuildStats => store.stats;
export const buildSearch = (store: BuildzStore): IBuildSearch => store.buildSearch;
export const buildSearchRequestParameters = createSelector(buildSearch, (buildSearch: IBuildSearch): IBuildSearchRequestParams => {
  return {
    project: buildSearch.project,
    branch: buildSearch.branch,
    minBuildNumber: buildSearch.minBuildNumber,
    maxBuildNumber: buildSearch.maxBuildNumber,
    labels: labelsToMap(buildSearch.labels),
    pageSize: buildSearch.pageSize,
    page: buildSearch.page,
    sortAttribute: buildSearch.sortAttribute,
    sortDirection: buildSearch.sortDirection,
  }
});
export const buildSearchResult = createSelector(buildSearch, (buildSearch: IBuildSearch): IBuildSearchResult => {
  return {
    builds: buildSearch.builds,
    totalElements: buildSearch.totalElements,
    totalPages: buildSearch.totalPages,
    hasNext: buildSearch.hasNext,
    hasPrevious: buildSearch.hasPrevious
  }
});

export const labelsToMap = (labels: ISearchLabel[]): { [key: string]: string } => {
  let toReturn = {};
  labels.forEach(label => {
    toReturn[label.key] = label.value
  });
  return toReturn;
};
