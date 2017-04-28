export interface IBuildLabel {
  id: number;
  key: string;
  value: string;
}

export interface IBuild {
  id: number;
  project: string;
  branch: string;
  buildNumber: number;
  labels: IBuildLabel[];
}

export interface IBuildStats {
  projects: string[];
  environments: string[];
  numberOfBuilds: number;
  numberOfLabels: number;
}

export interface ISearchLabel {
  key: string;
  value: string;
}

export interface IBuildSearch {
  project: string;
  branch: string;
  minBuildNumber: number;
  maxBuildNumber: number;
  labels: ISearchLabel[];
  pageSize: number;
  page: number;
  sortAttribute: string;
  sortDirection: string;
  builds: IBuild[],
  totalElements: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
}

export interface IBuildSearchRequestParams {
  project?: string;
  branch?: string;
  minBuildNumber?: number;
  maxBuildNumber?: number;
  labels?: { [key: string]: string };
  pageSize?: number;
  page?: number;
  sortAttribute?: string;
  sortDirection?: string;
}

export interface IBuildSearchResult {
  builds: IBuild[],
  totalElements: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
}
