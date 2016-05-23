/// <reference path="../refs.ts" />
'use strict';
module buildz {
    import IResource = angular.resource.IResource;
    import IResourceClass = angular.resource.IResourceClass;

    export class Artifact {
        project:string = null;
        branch:string = null;
        labels:{[key:string]:string} = {}
    }

    export interface IArtifact {
        project:string;
        branch:string;
        labels:{[key:string]:string}
    }

    export interface IEnvironment extends IResource<IEnvironment> {
        name:string;
        artifacts:Array<IArtifact>;
    }


    export interface IEnvironmentResource extends IResourceClass<IEnvironment> {
        verify(artifacts:Array<Artifact>):Array<IBuild>
    }

    angular.module('buildzApp').factory('EnvironmentResource', ['$resource', ($resource)=> {
        return <IEnvironmentResource> $resource('/v1/environments/:name', {}, {
            verify: {
                url: '/v1/environments/verify',
                method: 'POST',
                isArray: true
            }
        });
    }])
}