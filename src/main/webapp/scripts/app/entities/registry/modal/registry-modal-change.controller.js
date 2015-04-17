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
                $modalInstance.close(vm.registryEdited);
        }

        function cancel() {
            $modalInstance.dismiss('cancel');
        }


        function loadTypes() {
            RegistryTypes.getAll()
                .then(function(data){
                    vm.registryTypes = data;
                    console.log(data);
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
    }
})();
