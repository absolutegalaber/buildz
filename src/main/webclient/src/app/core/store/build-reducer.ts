import {IBuildStats} from "../domain";
import {Action} from "@ngrx/store";

export const STATS_REQUIRED = '[BuildStats] Required';
export const STATS_LOADED = '[BuildStats] Loaded';

export class StatsRequired implements Action {
  readonly type = STATS_REQUIRED;
  readonly payload = null;
}
export class StatsLoaded implements Action {
  readonly type = STATS_LOADED;

  constructor(public payload: IBuildStats) {
  }
}

export type StatsAction =
  StatsRequired |
  StatsLoaded;

export const initial_IBuildStats: IBuildStats = {
  projects: [],
  environments: [],
  numberOfBuilds: 0,
  numberOfLabels: 0
};

export function statsReducer(stats: IBuildStats = initial_IBuildStats, action: StatsAction): IBuildStats {
  switch (action.type) {
    case STATS_LOADED: {
      return action.payload;
    }
    default:
      return stats;
  }
}
