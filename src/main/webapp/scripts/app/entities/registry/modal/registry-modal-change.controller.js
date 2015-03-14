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

    RegistryModalChangeController.$inject = ['$modalInstance', '$filter', 'registryEdited', 'Logger', 'Registry'];

    /* @ngInject */
    function RegistryModalChangeController($modalInstance, $filter, registryEdited, Logger, Registry){
        /* jshint validthis: true */
        var vm = this;
        var logger = Logger.getInstance('RegistryModalChangeController');
        var forceSave = false;

        vm.registryEdited = registryEdited;
        vm.registryTypes = [];
        vm.httpsRegistry = false;
        vm.alert = null;


        vm.save = save;
        vm.cancel = cancel;
        vm.loadTypes = loadTypes;
        vm.test = test;

        ////////////////

        activate();

        function activate() {
            vm.loadTypes();
            convertToPostVersion();
        }

        function save() {
            logger.debug('Choice --> Save');
            setProtocol();
            if(!forceSave && !testRegistry()) {
                vm.alert = "Le registre que vous souhaitez ajouter est déconnecté. Rappuyez sur le bouton de sauvegarde pour l'ajouter quand même."; //TODO translate ?
                forceSave = true;
            } else {
                $modalInstance.close(vm.registryEdited);
            }
        }

        function cancel() {
            $modalInstance.dismiss('cancel');
        }


        function loadTypes() {
            /*RegistryTypes.getAll()
             .then(function(data){
             vm.registryTypes = data;
             })
             .catch(function(error) {
             //logger.error('Enabled to get the list of authors.');
             });*/
            vm.registryTypes = [{//TODO remove mock
                "id": "azertyuiop",
                "name": "Docker",
                "logo": "assets/images/logos/docker.png",
                "host": "127.0.0.1",
                "port": "1478",
                "public": "false"
            },{
                "id": "qsdfghjklm",
                "name": "Rocket",
                "logo": "assets/images/logos/rocket.png",
                "host": "127.0.0.1",
                "port": "9999",
                "public": "true"
            }];
            logger.debug("registry types loaded.");
        }

        function test() {
            logger.debug("test registry");

            var field = $('#testResultField');
            if(testRegistry()) {
                field.attr('class', 'btn btn-success');
                $('#testResultTextField').attr('translate','dockhouseApp.registry.status.online');
                field.text("online"); //TODO ajouter la translation
            } else {
                field.attr('class', 'btn btn-danger');
                $('#testResultTextField').attr('translate','dockhouseApp.registry.status.offline');
                field.text("offline"); //TODO ajouter la translation
            }
        }

        function testRegistry() {
            /* Registry.testRegistry(vm.registryEdited).then(function(data) {
             return true;
             }).catch(function(error) {
             return false;
             });
             */
            setProtocol();
            return (vm.registryEdited.protocol === "HTTPS");//TODO enlever le mock.
        }

        function convertToPostVersion(){
            //If the modal is opened in edition mode.
            if(vm.registryEdited.registryType != null) {
                vm.registryEdited.registryTypeId = vm.registryEdited.registryType.id;
                delete vm.registryEdited.registryType;
            } else { //To prevent blank choice in the select field.
                vm.registryEdited.registryTypeId = vm.registryTypes[0].id;
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
