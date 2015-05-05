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
        .controller('BreadcrumbsController', BreadcrumbsController);

    BreadcrumbsController.$inject = ['$state', '$location'];

    function BreadcrumbsController($state, $location) {
        /* jshint validthis: true */
        var vm = this;

        vm.updateBreadcrumbs = updateBreadcrumbs;
        vm.breadcrumbs = [];

        activate();
        ////////////////

        function activate() {
            console.log("breadcrumbs activated");
            console.log($state.current);
            updateBreadcrumbs();
        }

        function updateBreadcrumbs() {
            vm.breadcrumbs = getStates();
        }

        function getStates() {
            var cur = $state.current;
            var parent = $state.get($state.current.parent);
            var tab = [];
            while (parent) {
                tab.push(cur.name);
                cur = parent;
                parent =  $state.get(parent.parent);
            }
            tab.push(cur.name);
            return tab.reverse();
        }


        function hello() {
            console.log("hello");
        }
    }

})();
