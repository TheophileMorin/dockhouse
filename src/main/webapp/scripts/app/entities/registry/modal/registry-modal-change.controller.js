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
        vm.vTestBtn = {
            style : "btn btn-default",
            status : "default",
            icon : "glyphicon glyphicon-question-sign"
        };
        vm.vAlertDisconnectedRegistry = false;

        vm.save = save;
        vm.cancel = cancel;
        vm.loadTypes = loadTypes;
        vm.testRegistry = testRegistry;

        ////////////////

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
            if(!vm.vAlertDisconnectedRegistry) {
                vm.vAlertDisconnectedRegistry = true;
            } else {
                $modalInstance.close(vm.registryEdited);
            }
        }

        function cancel() {
            $modalInstance.dismiss('cancel');
        }


        function loadTypes() {
            RegistryTypes.getAll()
                .then(function(data){
                    vm.registryTypes = data;
                    convertToPostVersion();
                })
                .catch(function(error) {
                    logger.error('Enabled to get the list of registry types.');
                });
        }


        function testRegistry() {
            logger.debug("test registry");
            setProtocol();

            Registry.testRegistry(vm.registryEdited).then(function(data) {
                if(data) {
                    vm.vTestBtn.style = "btn btn-success";
                    vm.vTestBtn.status = "online";
                    vm.vTestBtn.icon = "glyphicon glyphicon-ok-circle";
                } else {
                    vm.vTestBtn.style = "btn btn-danger";
                    vm.vTestBtn.status = "offline";
                    vm.vTestBtn.icon = "glyphicon glyphicon-remove-circle";
                }
            }).catch(function(error) {
                logger.error('Unable to test registry status.');
            });
        }

        function convertToPostVersion(){
            //If the modal is opened in edition mode.
            if(vm.registryEdited.registryType != null) {
                vm.registryEdited.registryTypeId = vm.registryEdited.registryType.id;
                delete vm.registryEdited.registryType;
            } else { //To prevent blank choice in the select field.
                if(vm.registryTypes.length) {
                    vm.registryEdited.registryTypeId = vm.registryTypes[0].id;
                }
            }
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
