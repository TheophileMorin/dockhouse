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
        vm.registryDetail = [];
        vm.registryImages = [];

        vm.loadRegistry = loadRegistry;

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
                    logger.error('Enabled to get the given registry.');
                });
        }

        function testRegistry() {
            Registry.testRegistry(vm.registry)
                .then(function(data){
                    if(data) {
                        vm.onlineRegistry = "online";
                        // the return of the ping.
                        mockRegistryDetail();
                        mockRegistryImages();
                    } else {
                        vm.onlineRegistry = "offline";
                    }
                })
                .catch(function(error) {
                    logger.error('Enabled to test the given registry.');
                });
        }

        function mockRegistryDetail() {
            vm.registryDetail = [
                {
                    "key" : "State",
                    "value" : "online"
                },
                {
                    "key" : "Timestamp",
                    "value" : "131065412310"
                },
                {
                    "key" : "NbImages",
                    "value" : "2"
                },
                {
                    "key" : "Value",
                    "value" : "150"
                }
            ];
        }

        function mockRegistryImages() {
            vm.registryImages = [
                {
                    "name" : "Image 1",
                    "version" : "v1.0.6"
                },
                {
                    "name" : "Image 2",
                    "version" : "v2.3.7"
                }
            ];
        }
    }

})();

