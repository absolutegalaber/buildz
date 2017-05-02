import {createSelector} from "reselect";
import {BuildzStore} from "./store/buildz-store";
import {IArtifact, IBuild, IBuildSearchRequestParams, IBuildSearchResult, IBuildState, IBuildStats, IEnvironment, ISearchLabel} from "./domain";

export const buildStats = (store: BuildzStore): IBuildStats => store.stats;
export const buildSearch = (store: BuildzStore): IBuildState => store.buildState;
export const buildSearchRequestParameters = createSelector(buildSearch, (buildSearch: IBuildState): IBuildSearchRequestParams => {
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
export const buildSearchResult = createSelector(buildSearch, (buildSearch: IBuildState): IBuildSearchResult => {
  return {
    builds: buildSearch.builds,
    totalElements: buildSearch.totalElements,
    totalPages: buildSearch.totalPages,
    hasNext: buildSearch.hasNext,
    hasPrevious: buildSearch.hasPrevious
  }
});
export const selectedBuild = (store: BuildzStore): IBuild => store.buildState.selectedBuild;
export const buildInfoVisible = (store: BuildzStore): boolean => store.buildState.buildInfoVisible;
export const selectedEnvironment = (store: BuildzStore): IEnvironment => store.environmentState.environment;
export const selectedEnvironmentName = createSelector(selectedEnvironment, (env: IEnvironment): string => env != null ? env.name : null);
export const selectedEnvironmentArtifacts = createSelector(selectedEnvironment, (env: IEnvironment): IArtifact[] => env != null ? env.artifacts : []);
export const artifactsForVerification = (store: BuildzStore): IArtifact[] => store.environmentState.artifactsForVerification;
export const environmentBuilds = (store: BuildzStore): IBuild[] => store.environmentState.builds;
export const environmentBuildsLoaded = (store: BuildzStore): boolean => store.environmentState.buildsLoaded;
export const environmentToSave = createSelector(selectedEnvironment, artifactsForVerification, (env: IEnvironment, artifacts: IArtifact[]): IEnvironment => {
  return {
    id: env != null ? env.id : null,
    name: env != null ? env.name : null,
    artifacts: artifacts
  }
});

export const labelsToMap = (labels: ISearchLabel[]): { [key: string]: string } => {
  let toReturn = {};
  labels.forEach(label => {
    toReturn[label.key] = label.value
  });
  return toReturn;
};
