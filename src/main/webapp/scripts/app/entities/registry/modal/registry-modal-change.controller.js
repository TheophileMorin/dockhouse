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
        .controller('RegistryModalChangeController', RegistryModalChangeController);

    RegistryModalChangeController.$inject = ['$modalInstance', '$filter', 'registryEdited', 'Logger', 'Registry', 'RegistryTypes'];

    /* @ngInject */
    function RegistryModalChangeController($modalInstance, $filter, registryEdited, Logger, Registry, RegistryTypes ){
        /* jshint validthis: true */
        var vm = this;
        var logger = Logger.getInstance('RegistryModalChangeController');

        vm.modalHtmlURL = "scripts/app/entities/registry/modal/registry-modal-change.html";

        vm.registryEdited = {};
        vm.registryTypes = [];

        /*
         TODO
         You can actually edit a registry with a wrong apiVersion
         --> If Docker(v2) is changed into Rocket, v2 is still apiVersion and is not reset.
         So we have to put a listener on the apiVersion or the registryType ?
         -->  by putting ng-model-options="{ getterSetter: true }" in the HTML select field
         --> by adding the following code (it's a template)
         var _name = 'Brian';
         $scope.user = {
         name: function(newName) {
         if (angular.isDefined(newName)) {
         _name = newName;
         }
         return _name;
         }
         };
         */
        vm.vHttpsRegistry = false;

        vm.save = save;
        vm.cancel = cancel;
        vm.loadTypes = loadTypes;

        activate();

        function activate() {
            /* We clone the registryEdited to isolate reference from the registryController (in case of a dismissed modal). */
            cloneInitialReference();
            setProtocolStateInView();
            vm.loadTypes();
            logger.debug("activated");
        }

        function save() {
            logger.debug('Choice --> Save');
            setProtocol();
            setPlaceHolders();
            $modalInstance.close(vm.registryEdited);
        }

        function cancel() {
            $modalInstance.dismiss('cancel');
        }


        function loadTypes() {
            RegistryTypes.getAll()
                .then(function(data){
                    vm.registryTypes = data;
                    doneLoading();
                })
                .catch(function(error) {
                    logger.error('Enabled to get the list of registry types.');
                });
        }

        function setProtocol() {
            if(vm.vHttpsRegistry) {
                vm.registryEdited.protocol = "HTTPS";
            } else {
                vm.registryEdited.protocol = "HTTP";
            }
        }

        function setPlaceHolders() {
            if(vm.registryEdited.host == "" || vm.registryEdited.host == null) {
                vm.registryEdited.host = vm.registryEdited.registryType.defaultHost;
            }
            if(vm.registryEdited.port == null) {
                vm.registryEdited.port = vm.registryEdited.registryType.defaultPort;
            }
        }

        function setProtocolStateInView() {
            if(vm.registryEdited.protocol == null) {
                vm.vHttpsRegistry = false;
                return;
            }
            if(vm.registryEdited.protocol ===   "HTTPS") {
                vm.vHttpsRegistry = true;
                return;
            }
            vm.vHttpsRegistry = false;
        }

        function cloneInitialReference() {
            try {
                //If the modal is opened in edition mode, the parameter is a RestAngular object.
                vm.registryEdited = registryEdited.clone();
            } catch (e) {
                //Otherwise it's a common JS object
                vm.registryEdited = registryEdited;
            }

        }

        /**
         * Allow angular to bind correctly objects in the select fields.
         * Replace the object.
         */
        function doneLoading() {
            //update the edited registry in the modal.
            if(vm.registryEdited.registryType != null) {
                vm.registryEdited.registryType = getTypeCorrectReference();
            }
        }

        function getTypeCorrectReference() {
            try {
                for (var i = 0, max = vm.registryTypes.length ; i < max ; ++i) {
                    if (vm.registryTypes[i].id == vm.registryEdited.registryType.id) {
                        return vm.registryTypes[i];
                    }
                }
            } catch(e) {
                return null;
            }
            return null;
        }
    }
})();
