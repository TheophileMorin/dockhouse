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
        .module('docklandApp')
        .factory('Author', Author);

    Author.$inject = ['Restangular', 'DateUtils'];

    /* @ngInject */
    function Author(Restangular, DateUtils){
        var service = Restangular.service('authors');

        return {
            get: get,
            getAll: getAll,
            create: create,
            update: update
        };

        ////////////////

        /**
         * Get a registry from an id
         * @returns the registry corresponding (promise){*}
         */
        function get(id) {
            return service.one(id)
                .get()
                .then(function(data) {
                    data.birthDate = DateUtils.formatDateForUI(data.birthDate);
                    return data;
                })
                .catch(function(error) {
                    //logger.error('authors/:id',"Error lors de l'appel du service REST authors",error);
                    throw error;
                })
        }

        /**
         * Get the list of all the registries
         * @returns the complete array of registries (promise){*}
         */
        function getAll() {
            return service
                .getList()
                .then(function(data) {
                    return data;
                })
                .catch(function(error) {
                    //logger.error('authors',"Error lors de l'appel du service REST authors",error);
                    throw error;
                })
        }

        /**
         * Create a new author
         * @param author to create
         * @returns the author inserted
         */
        function create(author) {
            //logger.debug('call the /registries service');
            return service
                .post(author)
                .then(function(data) {
                    return data;
                })
                .catch(function (error){
                    //logger.error('registries',"Error lors de l'appel du service REST Registry",error);
                    throw error;
                })
        }

        /**
         * Update author
         * @param author to update
         * @returns the author updated
         */
        function update(author) {
            return author.put()
                .then(function(data) {
                    return data;
                })
                .catch(function (error) {
                    throw error;
                })
        }
    }


})();
//
//'use strict';
//
//angular.module('docklandApp')
//    .factory('Author', function ($resource) {
//        return $resource('api/authors/:id', {}, {
//            'query': { method: 'GET', isArray: true},
//            'get': {
//                method: 'GET',
//                transformResponse: function (data) {
//                    data = angular.fromJson(data);
//                    var birthDateFrom = data.birthDate.split("-");
//                    data.birthDate = new Date(new Date(birthDateFrom[0], birthDateFrom[1] - 1, birthDateFrom[2]));
//                    return data;
//                }
//            }
//        });
//    });
