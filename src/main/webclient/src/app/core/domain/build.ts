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
