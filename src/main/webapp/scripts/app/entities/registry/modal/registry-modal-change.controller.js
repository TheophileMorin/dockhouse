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
        var forceSave = false;

        vm.modalHtmlURL = "scripts/app/entities/registry/modal/registry-modal-change.html";

        vm.registryEdited = registryEdited;
        vm.registryTypes = [];
        vm.httpsRegistry = false;
        vm.testBtn = {
            style : "btn btn-default",
            status : "default",
            icon : "glyphicon glyphicon-question-sign"
        };
        vm.alertText = null;

        vm.save = save;
        vm.cancel = cancel;
        vm.loadTypes = loadTypes;
        vm.testRegistry = testRegistry;

        ////////////////

        activate();

        function activate() {
            console.log();
            vm.loadTypes();
            logger.debug("activated");
        }

        function save() { //TODO adapt to asynchronism of promises in testRegistry.
            logger.debug('Choice --> Save');
            setProtocol();
            if(!forceSave && !testRegistry()) {
                vm.alertText = "Le registre que vous souhaitez ajouter est déconnecté. Rappuyez sur le bouton de sauvegarde pour l'ajouter quand même."; //TODO translate ?
                forceSave = true;
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
                    console.log(vm.registryTypes);
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
                    vm.testBtn.style = "btn btn-success";
                    vm.testBtn.status = "online";
                    vm.testBtn.icon = "glyphicon glyphicon-ok-circle";
                } else {
                    //field.attr('class', 'btn btn-danger');
                    vm.testBtn.style = "btn btn-danger";
                    vm.testBtn.status = "offline";
                    vm.testBtn.icon = "glyphicon glyphicon-remove-circle";
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
            if(vm.httpsRegistry) {
                vm.registryEdited.protocol = "HTTPS";
            } else {
                vm.registryEdited.protocol = "HTTP";
            }
        }
    }
})();
