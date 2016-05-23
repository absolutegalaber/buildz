'use strict';
module buildz {
    export class EnvironmentController {
        static $inject = ['environment', 'EnvironmentResource', '$state'];
        public newLabelKey:string = null;
        public newLabelVal:string = null;
        public builds:Array<IBuild> = [];

        constructor(public environment:IEnvironment, private resource:IEnvironmentResource, private $state) {
            if (environment.artifacts.length > 0) {
                this.verify();
            }
        }

        addArtifact() {
            this.environment.artifacts.push(new Artifact());
        }

        deleteArtifact(artifact:IArtifact) {
            var index = this.environment.artifacts.indexOf(artifact);
            this.environment.artifacts.splice(index, 1);
            console.log(this.environment.artifacts);
        }

        addArtifactLabel(artifact:IArtifact) {
            artifact.labels[this.newLabelKey] = this.newLabelVal;
            this.newLabelKey = null;
            this.newLabelVal = null;
        }

        removeArtifactLabel(artifact:IArtifact, toRemove:string) {
            delete artifact.labels[toRemove];
        }

        verify() {
            this.resource.verify(this.environment.artifacts).$promise.then((response)=> {
                this.builds = response;
            })
        }

        saveEnvironment():void {
            console.log("Save!");
            this.resource.save(
                this.environment
            ).$promise.then((newEnv)=> {
                this.$state.go('home')
            })
        }

        deleteEnvironment() {
            this.environment.$delete().then((response)=> {
                this.$state.go('home')
            })
        }

    }
    angular.module('buildzApp').controller('EnvironmentController', EnvironmentController)
}