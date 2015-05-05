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
        .controller('RegistryModalRemoveController', RegistryModalRemoveController);

    RegistryModalRemoveController.$inject = ['$modalInstance', 'registryName', 'Logger'];

    /* @ngInject */
    function RegistryModalRemoveController($modalInstance, registryName, Logger){
        /* jshint validthis: true */
        var vm = this;
        var logger = Logger.getInstance('RegistryModalRemoveController');

        vm.modalHtmlURL = "scripts/app/entities/registry/modal/registry-modal-delete.html";

        vm.registryName = registryName;
        vm.confirmRemove = confirmRemove;
        vm.cancel = cancel;

        ////////////////
        activate();

        function activate() {
            logger.debug("acivated");
        }

        function confirmRemove() {
            logger.debug('Choice -> confirm delete');
            $modalInstance.close(true);
        };

        function cancel() {
            logger.debug('Choice -> Cancel');
            $modalInstance.dismiss('cancel');
        }


    }

})();
