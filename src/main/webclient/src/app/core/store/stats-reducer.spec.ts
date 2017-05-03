import {StatsLoaded, statsReducer} from "./stats-reducer";
import {IBuildStats} from "../domain";
describe('statsReducer', () => {
  it('StatsLoaded pushes new stats data into state', () => {
    let newStats: IBuildStats = <IBuildStats>{};
    let newState = statsReducer(null, new StatsLoaded(newStats));
    expect(newState).toBe(newStats);
  });
});
