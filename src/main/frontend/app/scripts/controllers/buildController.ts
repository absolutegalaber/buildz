'use strict';
module buildz {
    export class BuildController {
        static $inject = ['build', 'BuildSearch', '$state'];

        constructor(public build:IBuild, private search:BuildSearch, private state) {
        }

        public selectProject() {
            this.search.ofProject(this.build.project).then(()=> {
                this.state.go('buildList');
            });
        }

        public selectProjectAndBranch() {
            this.search.ofProjectAndBranch(this.build.project, this.build.branch).then(()=> {
                this.state.go('buildList');
            });
        }

        public selectLabel(key:string, value:string) {
            this.search.ofLabel(key, value).then(()=> {
                this.state.go('buildList');
            });
        }
    }

    angular.module('buildzApp').controller('BuildController', BuildController)
}