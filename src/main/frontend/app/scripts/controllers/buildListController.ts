'use strict';
module buildz {
    export class BuildListController {
        static $inject = ['BuildSearch'];

        constructor(public search:BuildSearch) {
            if (!this.search.loaded) {
                this.search.load();
            }
        }
    }

    angular.module('buildzApp').controller('BuildListController', BuildListController)
}