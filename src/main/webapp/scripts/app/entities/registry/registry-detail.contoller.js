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

    RegistryDetailController.$inject = ['Registry', '$stateParams', 'Logger'];

    /* @ngInject */
    function RegistryDetailController(Registry, $stateParams, Logger){
        /* jshint validthis: true */
        var logger = Logger.getInstance('RegistryDetailController');
        var vm = this;

        vm.registry = {};
        vm.showRegistryDetails = false;
        vm.onlineRegistry = "pending";
        vm.registryDetail = "";
        vm.registryImages = [];

        vm.loadRegistry = loadRegistry;
        vm.editRegistry = editRegistry;

        activate($stateParams.id);

        ////////////////

        function activate(id){
            vm.loadRegistry(id);
            logger.debug("activated");
        }

        function loadRegistry(id) {
            Registry.get(id)
                .then(function(data){
                    vm.registry = data;
                    testRegistry();
                })
                .catch(function(error) {
                    logger.error('Enabled to get the given registry.' + error);
                });
        }

        function testRegistry() {
            Registry.testRegistry(vm.registry.id)
                .then(function(data){
                    if(data.status == "ok") { //TODO mock, change to "online" or "offline"
                        vm.onlineRegistry = "online";
                        vm.registryDetail = JSON.stringify(data, null, 10); //TODO mock
                        mockRegistryImages();
                    } else {
                        vm.onlineRegistry = "offline";
                    }
                })
                .catch(function(error) {
                    logger.error('Enabled to test the given registry.' + error);
                });
        }

        function mockRegistryImages() {
            vm.registryImages = [
                {
                    "name" : "Dockhouse/Dockhouse",
                    "version" : "v1.0.6"
                },
                {
                    "name" : "Dockhouse/Dockhouse-pic-tool",
                    "version" : "v2.3.7"
                }
            ];
        }

        function editRegistry() {
            logger.log("MOCK"); //TODO mock
            if(vm.onlineRegistry == "pending") {
                vm.onlineRegistry = "online";
            }
            else if(vm.onlineRegistry == "online") {
                vm.onlineRegistry = "offline";
            }
            else if(vm.onlineRegistry == "offline") {
                vm.onlineRegistry = "pending";
            }
            logger.log(vm.onlineRegistry);
        }
    }

})();

