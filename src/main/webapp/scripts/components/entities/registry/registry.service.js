/*
 *  Copyright (C) 2015  Dockhouse project org. ( http://dockhouse.github.io/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
(function (){
    "use strict";

    angular
        .module('dockhouseApp')
        .factory('Registry', Registry);

    Registry.$inject = ['Restangular'];

    /* @ngInject */
    function Registry(Restangular){
        var service = Restangular.service('registries');
        var registriesMock = [{
            id:"aqwzsxedc",
            name:"registry Docker One",
            registryType:{
                "id": "azertyuiop",
                "name": "Docker",
                "logo": "assets/images/logos/docker.png",
                "host": "127.0.0.1",
                "port": "1478",
                "public": "false"
            },
            host:"192.168.25.6",
            port:"8585",
            protocol:"HTTP"
        },{
            id:"zsxedcrfv",
            name:"test",
            registryType:{
                "id": "azertyuiop",
                "name": "Docker",
                "logo": "assets/images/logos/docker.png",
                "host": "127.0.0.1",
                "port": "1478",
                "public": "false"
            },
            host:"192.168.0.250",
            port:"3030",
            protocol:"HTTPS"
        }];

        return {
            get: get,
            getAll: getAll,
            create: create,
            update: update,
            remove: remove
        };

        ////////////////

        /**
         * Get a registry from an id
         * @returns the registry corresponding (promise){*}
         */
        function get(id) {/*
            return service.one(id)
                .get()
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    //logger.error('registries/:id',"Error lors de l'appel du service REST registries",error);
                    throw error;
                })*/
            //TODO MOCK
            var promise=  new Promise(function(resolve, reject) {
                if (true)
                    resolve(registriesMock[0]);
                else
                    reject(null);
            });
            return promise
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    throw error;
                });
        }

        /**
         * Get the list of all the registries
         * @returns the complete array of registries (promise){*}
         */
        function getAll() {
            /*return service
                .getList()
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    //logger.error('registries',"Error lors de l'appel du service REST registries",error);
                    throw error;
                })*/
            //TODO MOCK
            var promise=  new Promise(function(resolve, reject) {
                if (true)
                    resolve(registriesMock);
                else
                    reject(null);
            });
            return promise
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    throw error;
                });
        }

        /**
         * Create a new registry
         * @param registry to create
         * @returns the registry inserted
         */
        function create(registry) {
            //logger.debug('call the /registries service');
           /* return service
                .post(registry)
                .then(function(data) {
                    return data;
                })
                .catch(function (error){
                    //logger.error('registries',"Error lors de l'appel du service REST registries",error);
                    throw error;
                })*/
            //TODO MOCK
            var promise=  new Promise(function(resolve, reject) {
                if (true)
                    resolve(registriesMock);
                else
                    reject(null);
            });
            return promise
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    throw error;
                });
        }

        /**
         * Update registry
         * @param registry to update
         * @returns the registry updated
         */
        function update(registry) {/*
            return registry.put()
                .then(function(data) {
                    return data;
                })
                .catch(function (error) {
                    throw error;
                })
                */
            //TODO MOCK
            var promise=  new Promise(function(resolve, reject) {
                if (true)
                    resolve(registriesMock[0]);
                else
                    reject(null);
            });
            return promise
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    throw error;
                });
        }

        /**
         * Remove registry
         * @param registry to remove
         * @returns the registry removed
         */
        function remove(registry) {/*
            return registry.remove()
                .then(function(data) {
                    return data;
                })
                .catch(function (error) {
                    throw error;
                })*/
            //TODO MOCK
            var promise=  new Promise(function(resolve, reject) {
                if (true)
                    resolve(registriesMock[0]);
                else
                    reject(null);
            });
            return promise
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    throw error;
                });
        }

        function testRegisty(editedRegistry) { //TODO MOCK
            var promise=  new Promise(function(resolve, reject) {
                if (true)
                    resolve(true);
                else
                    reject(false);
            });
            return promise
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    throw error;
                });
        }
    }


})();

