/// <reference path="refs.ts" />

'use strict';
module buildz {
    var theApp:angular.IModule = angular.module('buildzApp', [
        'ngAnimate',
        'ngResource',
        'ngSanitize',
        'ui.router',
        'ngMaterial'
    ]);

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


    theApp.config(['$urlRouterProvider', ($urlRouterProvider)=> {
        $urlRouterProvider.otherwise('/')
    }]);
}


