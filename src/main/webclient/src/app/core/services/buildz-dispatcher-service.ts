import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";
import {BuildzStore} from "../store/buildz-store";
import {StatsRequired} from "../store/stats-reducer";
import {BuildSearchModified, BuildSelected, HideBuildInfo, NextBuildsPage, PrevBuildsPage, ProjectSelected} from "../store/build-state-reducer";
import {IArtifactData, IBuildSearchRequestParams, IEnvironment} from "../domain";
import {AddArtifact, ArtifactChanged, DeleteArtifact, EnvironmentLoaded, SaveEnvironment, VerifyEnvironment} from "../store/environment-state-reducer";
@Injectable()
export class BuildzDispatcher {

  constructor(private store: Store<BuildzStore>) {
  }

  loadStats(): void {
    this.store.dispatch(new StatsRequired());
  }

  projectSelected(projectName: string): void {
    this.store.dispatch(new ProjectSelected(projectName));
  }

  buildSearchChanged(params: IBuildSearchRequestParams): void {
    this.store.dispatch(new BuildSearchModified(params));
  }

  prevBuildListPage(): void {
    this.store.dispatch(new PrevBuildsPage());
  }

  nextBuildListPage(): void {
    this.store.dispatch(new NextBuildsPage());
  }

  singleBuildSelected(id: number): void {
    this.store.dispatch(new BuildSelected(id));
  }

  hideBuildInfo() {
    this.store.dispatch(new HideBuildInfo());
  }

  environmentLoaded(env: IEnvironment): void {
    this.store.dispatch(new EnvironmentLoaded(env));
  }

  saveEnvironment(): void {
    this.store.dispatch(new SaveEnvironment());
  }

  verifyEnvironment(): void {
    this.store.dispatch(new VerifyEnvironment());
  }

  artifactChanged(artifactData: IArtifactData): void {
    this.store.dispatch(new ArtifactChanged(artifactData));
  }

  addArtifact() {
    this.store.dispatch(new AddArtifact());
  }

  deleteArtifact(index: number) {
    this.store.dispatch(new DeleteArtifact(index));
  }
}
