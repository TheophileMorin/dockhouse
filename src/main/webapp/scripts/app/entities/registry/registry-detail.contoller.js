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
        .controller('RegistryDetailController', RegistryDetailController);

    RegistryDetailController.$inject = ['Registry', '$stateParams', '$state', 'Logger', '$modal'];

    /* @ngInject */
    function RegistryDetailController(Registry, $stateParams, $state, Logger, $modal){
        /* jshint validthis: true */
        var logger = Logger.getInstance('RegistryDetailController');
        var vm = this;

        vm.registry = {};
        vm.showRegistryDetails = false;
        vm.onlineRegistry = "pending";
        vm.registryDetail = "/";
        vm.registryImages = [];

        vm.loadRegistry = loadRegistry;
        vm.openEditionModal = openEditionModal;
        vm.openRemoveImageModal = openRemoveImageModal;

        activate($stateParams.id);

        ////////////////

        function activate(id){
            vm.loadRegistry(id);
            logger.debug("activated");
        }

        function loadRegistry(id) {
            if(id == "") {
                $state.go('registry');
            }
            clear();
            Registry.get(id)
                .then(function(data){
                    vm.registry = data;
                    testRegistry();
                })
                .catch(function(error) {
                    logger.error('Unable to get the given registry.' + error);
                    $state.go('error');
                });
        }

        function testRegistry() {
            Registry.testRegistry(vm.registry.id)
                .then(function(data){
                    vm.onlineRegistry = data;
                    if(data == "online") {
                        getRegistryDetail();
                        getRegistryImages();
                    }
                })
                .catch(function() {
                    vm.onlineRegistry = "offline"; //force offline mode when error occurs.
                });
        }

        function getRegistryDetail() {
            Registry.getDetail(vm.registry.id).
                then(function(data) {
                    vm.registryDetail = JSON.stringify(JSON.parse(data), null, 5);
                }).
                catch(function() {
                    vm.registryDetail = "/";
                });
        }

        function getRegistryImages() {
            Registry.getAllImages(vm.registry.id).
                then(function(data) {
                    vm.registryImages = data;
                }).
                catch(function() {
                    vm.registryImages = [];
                });
        }

        function deleteRegistryImage(imageID) {
            Registry.deleteImage(vm.registry.id, imageID).
                then(function(data) {
                    console.log("Image correctly deleted : " + data);
                }).
                catch(function() {
                    console.log("Image deletion failed...")
                });
        }

        function openEditionModal() {
            // Open the modal Edition window
            var modalInstance = $modal.open({
                templateUrl: 'RegistryChangeModal',
                controller: 'RegistryModalChangeController as modal',
                backdrop: 'static',
                resolve: {
                    registryEdited: function() {
                        return vm.registry;
                    }
                }
            });
            modalInstance.result.then(function (registryModified) {
                Registry.update(registryModified).then(function(data) {
                    vm.loadRegistry(data.id);
                });
            }, function () {
                logger.debug('Modal dismissed at: ' + new Date());
            });
        }

        function openRemoveImageModal(image) {
            var modalInstance = $modal.open({
                templateUrl: 'ImageRemoveConfirmationModal',
                controller: 'ImageModalRemoveController as modalDelete',
                backdrop: 'static',
                resolve: {
                    imageName: function() {
                        return image.name;
                    }
                }
            });

            modalInstance.result.then(function (toDelete) {
                if (toDelete){
                    deleteRegistryImage(image.name);
                }
            }, function () {
                logger.debug('Modal delete dismissed at: ' + new Date());
            });
        }

        function clear() {
            vm.registry = {};
            vm.showRegistryDetails = false;
            vm.onlineRegistry = "pending";
            vm.registryDetail = "/";
            vm.registryImages = [];
        }

    }
})();

