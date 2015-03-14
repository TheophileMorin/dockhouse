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
        .controller('RegistryController', RegistryController);

    RegistryController.$inject = ['Registry', '$modal', 'Logger'];

    /* @ngInject */
    function RegistryController(Registry, $modal, Logger){
        /* jshint validthis: true */
        var logger = Logger.getInstance('RegistryController');
        var vm = this;


        vm.registries = [];
        vm.registryEdited = {};
        vm.editionMode = false;
        vm.loadAll = loadAll;
        vm.openCreate = openCreate;
        vm.openEdit = openEdit;
        vm.openRemove = openRemove;
        vm.clear = clear;


        activate();

        ////////////////

        function activate(){ //TODO Bug lors de l'affichage de la liste. Il semble que le dataBinding ne se fait pas correctement...
            vm.loadAll();
            logger.debug("activate")
        }

        function loadAll() {
            Registry.getAll()
                .then(function(data){
                    vm.registries = data;
                })
                .catch(function(error) {
                    logger.error('Enabled to get the list of registries.');
                });
        }

        function applyChanges(registry) {

            if (vm.editionMode){
                update(registry);
            } else {
                create(registry);
            }
        }

        function create(registry) {

            Registry.create(registry)
                .then(function(data) {
                    vm.loadAll();
                    vm.clear();
                });
        }

        function update(registry) {

            Registry.update(registry)
                .then(function(data) {
                    vm.loadAll();
                    vm.clear();
                });
        }

        function openCreate() {
            vm.clear();

            // Open the modal Edition window
            logger.debug('Opening the creation window ...');
            var modalInstance = $modal.open({
                templateUrl: 'RegistryChangeModal',
                controller: 'RegistryModalChangeController as modal',
                backdrop: 'static',
                resolve: {
                    registryEdited: function() {
                        return vm.registryEdited;
                    }
                }
            });

            modalInstance.result.then(function (registryModified) {
                logger.debug('Trying to create the Registry ...');
                applyChanges(registryModified);
            }, function () {
                logger.debug('Modal dismissed at: ' + new Date());
                vm.clear();
            });

        }

        function openEdit(registry) {
            vm.registryEdited = registry;
            vm.editionMode = true;

            // Open the modal Edition window
            var modalInstance = $modal.open({
                templateUrl: 'RegistryChangeModal',
                controller: 'RegistryModalChangeController as modal',
                backdrop: 'static',
                resolve: {
                    registryEdited: function() {
                        return vm.registryEdited;
                    }
                }
            });

            modalInstance.result.then(function (registryModified) {
                applyChanges(registryModified);
            }, function () {
                logger.debug('Modal dismissed at: ' + new Date());
                vm.clear();
            });

        }

        function openRemove(registry) {
            logger.debug('Opening the delete confirmation modal window ...');
            vm.authorEdited = registry;

            var modalInstance = $modal.open({
                templateUrl: 'RegistryRemoveConfirmationModal',
                controller: 'RegistryModalRemoveController as modalDelete',
                backdrop: 'static',
                resolve: {
                    registryName: function() {
                        return registry.name;
                    }
                }
            });

            modalInstance.result.then(function (toDelete) {
                if (toDelete){
                    remove(registry);
                }
            }, function () {
                logger.debug('Modal delete dismissed at: ' + new Date());
                vm.clear();
            });
        }

        function remove(registry) {
            Registry.remove(registry)
                .then(function(data) {
                    vm.loadAll();
                    vm.clear();
                });
        }

        function clear() {
            vm.registryEdited = {
                id:null,
                name:null,
                registryTypeId:null,
                host:null,
                port:null,
                protocol:null };
            vm.editionMode = false;
        }


    }

})();

