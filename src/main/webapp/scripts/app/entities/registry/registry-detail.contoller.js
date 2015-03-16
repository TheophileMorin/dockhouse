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

    RegistryDetailController.$inject = ['Registry', '$stateParams', 'Logger', '$timeout'];

    /* @ngInject */
    function RegistryDetailController(Registry, $stateParams, Logger, $timeout){
        /* jshint validthis: true */
        var logger = Logger.getInstance('RegistryDetailController');
        var vm = this;


        vm.registry = {};

        vm.loadRegistry = loadRegistry;

        activate($stateParams.id);

        ////////////////

        function activate(id){
            console.log(this); //TODO --> undefined ???...
            vm.loadRegistry(id);
            $timeout(function(){vm.loadRegistry(id); logger.debug("Timeout used... Resolve this bug damn bug !")},1);
            logger.debug("activated");
        }

        function loadRegistry(id) {
            Registry.get(id)
                .then(function(data){
                    vm.registry = data;
                })
                .catch(function(error) {
                    logger.error('Enabled to get the given registry.');
                });
        }
    }

})();

