(function (){
    "use strict";

    angular
        .module('docklandApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'CONFIG'];

    function NavbarController($state, Auth, Principal, CONFIG) {
        /* jshint validthis: true */
        var vm = this;

        vm.isAuthenticated = Principal.isAuthenticated;
        vm.isInRole = Principal.isInRole;
        vm.config = CONFIG;
        vm.logout = logout;

        ////////////////
        function logout() {
            Auth.logout();
            $state.go('home');
        };
    }

})();

