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
        .factory('RegistryTypes', RegistryTypes);

    RegistryTypes.$inject = ['Restangular', 'Logger'];

    /* @ngInject */
    function RegistryTypes(Restangular, Logger){
        var logger = Logger.getInstance('RegistryTypesService');

        var service = Restangular.service('registry_types');

        return {
            get: get,
            getAll: getAll,
            create: create,
            update: update,
            remove: remove
        };

        ////////////////

        /**
         * Get a registry type from an id
         * @returns the registry type corresponding (promise){*}
         */
        function get(id) {
            logger.debug('GET /registry_types/'+id);
            return service.one(id)
                .get()
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    logger.error('/registry_types/:id',"Error lors de l'appel du service REST registry_types",error);
                    throw error;
                });
        }

        /**
         * Get the list of all the registry types
         * @returns the complete array of registry types (promise){*}
         */
        function getAll() {
            logger.debug('GET /registry_types');
            return service
                .getList()
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    logger.error('registry_types',"Error lors de l'appel du service REST registry_types",error);
                    throw error;
                });
        }


        /**
         * Create a new registry type
         * @param registry_type type to create
         * @returns the registry type inserted
         */
        function create(registry_type) {
            logger.debug('POST /registry_types');
            return service
                .post(registry_type)
                .then(function(data) {
                    return data;
                })
                .catch(function (error){
                    logger.error('registry_types',"Error lors de l'appel du service REST registry_types",error);
                    throw error;
                });
        }

        /**
         * Update registry type
         * @param registry_type type to update
         * @returns the registry type updated
         */
        function update(registry_type) {
            logger.debug('PUT /registry_types');
            return registry_type
                .put()
                .then(function(data) {
                    return data;
                })
                .catch(function (error) {
                    logger.error('registry_types',"Error lors de l'appel du service REST registry_types",error);
                    throw error;
                });
        }

        /**
         * Remove registry type
         * @param registry_type type to remove
         * @returns the registry type removed
         */
        function remove(registry_type) {
            logger.debug('DELETE /registry_types');
            return registry_type
                .remove()
                .then(function(data) {
                    return data;
                })
                .catch(function (error) {
                    logger.error('registry_types',"Error lors de l'appel du service REST registry_types",error);
                    throw error;
                });
        }
    }

})();

