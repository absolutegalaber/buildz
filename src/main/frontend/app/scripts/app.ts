/// <reference path="refs.ts" />

'use strict';
module buildz {
    import IHttpProvider = angular.IHttpProvider;
    var theApp:angular.IModule = angular.module('buildzApp', [
        'ngAnimate',
        'ngResource',
        'ngSanitize',
        'ui.router',
        'ngMaterial'
    ]);

    theApp.config(['$resourceProvider', ($resourceProvider)=> {
        $resourceProvider.defaults.stripTrailingSlashes = false;
    }]);

    theApp.config(['$stateProvider', ($stateProvider)=> {
        $stateProvider.state('home', {
            url: '/',
            templateUrl: 'views/home.html',
            controller: 'HomeController',
            controllerAs: 'HomeController',
            resolve: {
                stats: ['BuildResource', (resource:IBuildResource)=> {
                    return resource.stats().$promise;
                }]
            }
        });
    }]);
    theApp.config(['$stateProvider', ($stateProvider)=> {
        $stateProvider.state('buildList', {
            url: '/list',
            templateUrl: 'views/buildList.html',
            controller: 'BuildListController',
            controllerAs: 'BuildListController'
        });
    }]);

    theApp.config(['$stateProvider', ($stateProvider)=> {
        $stateProvider.state('build', {
            url: '/build/:id',
            templateUrl: 'views/build.html',
            controller: 'BuildController',
            controllerAs: 'BuildController',
            resolve: {
                build: ['$stateParams', 'BuildResource', ($stateParams, resource:IBuildResource)=> {
                    return resource.get({
                        id: $stateParams['id']
                    }).$promise
                }]
            }
        });
    }]);


    theApp.config(['$stateProvider', ($stateProvider)=> {
        $stateProvider.state('environment', {
            url: '/environment/:name',
            templateUrl: 'views/environment.html',
            controller: 'EnvironmentController',
            controllerAs: 'EnvironmentController',
            resolve: {
                environment: ['$stateParams', 'EnvironmentResource', ($stateParams, resource:IEnvironmentResource)=> {
                    return resource.get({
                        name: $stateParams['name']
                    }).$promise
                }]
            }
        });
    }]);


    theApp.config(['$urlRouterProvider', ($urlRouterProvider)=> {
        $urlRouterProvider.otherwise('/')
    }]);
}


