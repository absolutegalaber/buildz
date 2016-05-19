'use strict';
module buildz {
    export class HomeController {
        static $inject = ['stats', 'BuildSearch', '$state'];

        constructor(public stats:IBuildStatsResponse, private search:BuildSearch, private state) {
        }

        selectProject(projectName:string) {
            this.search.ofProject(projectName).then(()=> {
                this.state.go('buildList');
            });
        }
    }

    angular.module('buildzApp').controller('HomeController', HomeController)
}