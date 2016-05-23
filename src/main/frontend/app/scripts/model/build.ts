/// <reference path="../refs.ts" />

'use strict';
module buildz {

    import IResource = angular.resource.IResource;
    import IResourceClass = angular.resource.IResourceClass;
    export interface IBuildLabel {
        id:number;
        key:string;
        value:string;
    }

    export interface IBuild extends IResource<IBuild> {
        id:number;
        project:string;
        branch:string;
        buildNumber:number;
        labels:Array<IBuildLabel>
    }

    export interface IBuildSearchResponse {
        builds:Array<IBuild>;
        page:number;
        totalElements:number;
        totalPages:number;
        hasNext:boolean;
        hasPrevious:boolean;
    }
    export interface IBuildStatsResponse {
        builds:Array<string>;
        environments:Array<string>;
        numberOfBuilds:number;
        numberOfLabels:number;
    }

    export interface IBuildResource extends IResourceClass<IBuild> {
        search(search:any):angular.resource.IResource<IBuildSearchResponse>
        stats():angular.resource.IResource<IBuildStatsResponse>
    }

    angular.module('buildzApp').factory('BuildResource', ['$resource', ($resource)=> {
        return <IBuildResource> $resource('/v1/builds/:id', {}, {
            search: {
                url: '/v1/builds/search',
                method: 'POST',
                isArray: false
            },
            stats: {
                url: '/v1/builds/stats',
                method: 'GET',
                isArray: false
            }
        })
    }]);

    export class SearchLabel {
        public key:string = '';
        public value:string = '';
    }

    export class BuildSearch {
        static $inject = ['BuildResource'];
        public project:string = null;
        public branch:string = null;
        public minBuildNumber:number = null;
        public maxBuildNumber:number = null;
        public labels:Array<SearchLabel> = [];
        public pageSize:number = 10;
        public page:number = 0;
        public sortAttribute:string = 'buildNumber';
        public sortDirection:string = 'desc';
        public builds:Array<IBuild>;
        public totalElements:number;
        public totalPages:number;
        public hasNext:boolean;
        public hasPrevious:boolean;
        public loaded = false;

        constructor(private resource:IBuildResource) {
        }

        public addLabel():void {
            this.labels.push(new SearchLabel());
        }

        public deleteLabel(label:SearchLabel):void {
            var index = this.labels.indexOf(label);
            this.labels.splice(index, 1)
        }

        public ofProject(project:string) {
            this.project = project;
            this.branch = null;
            this.minBuildNumber = null;
            this.maxBuildNumber = null;
            this.labels.length = 0;
            this.page = 0;
            return this.load();
        }

        public ofProjectAndBranch(project:string, branch:string) {
            this.project = project;
            this.branch = branch;
            this.minBuildNumber = null;
            this.maxBuildNumber = null;
            this.labels.length = 0;
            this.page = 0;
            return this.load();
        }

        public ofLabel(key:string, value:string) {
            this.project = null;
            this.branch = null;
            this.minBuildNumber = null;
            this.maxBuildNumber = null;
            this.labels.length = 0;
            this.page = 0;
            var searchLabel = new SearchLabel();
            searchLabel.key = key;
            searchLabel.value = value;
            this.labels.push(searchLabel);
            return this.load();
        }

        public load() {
            this.loaded = true;
            var labelsToSearch = {};
            angular.forEach(this.labels, (searchLabel)=> {
                labelsToSearch[searchLabel.key] = searchLabel.value;
            });

            return this.resource.search({
                project: this.project,
                branch: this.branch,
                minBuildNumber: this.minBuildNumber,
                maxBuildNumber: this.maxBuildNumber,
                labels: labelsToSearch,
                page: this.page,
                pageSize: this.pageSize,
                sortAttribute: this.sortAttribute,
                sortDirection: this.sortDirection,
            }).$promise.then((data)=> {
                this.builds = data.builds;
                this.totalElements = data.totalElements;
                this.totalPages = data.totalPages;
                this.hasNext = data.hasNext;
                this.hasPrevious = data.hasPrevious;
            })
        }

        clear():void {
            this.project = null;
            this.branch = null;
            this.minBuildNumber = null;
            this.maxBuildNumber = null;
            this.labels.length = 0;
            this.page = 0;
            this.load();
        }

        public nextPage():void {
            this.page++;
            this.load();
        }

        public previousPage():void {
            this.page--;
            this.load();
        }
    }
    angular.module('buildzApp').service('BuildSearch', BuildSearch)
}