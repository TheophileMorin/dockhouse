/**
 * Created by BWI on 27/02/15.
 */
(function (){
    "use strict";

    angular
        .module('docklandApp')
        .controller('AuthorModalChangeController', AuthorModalChangeController);

    AuthorModalChangeController.$inject = ['$modalInstance', 'authorEdited', 'Logger'];

    /* @ngInject */
    function AuthorModalChangeController($modalInstance, authorEdited, Logger){
        /* jshint validthis: true */
        var vm = this;
        var logger = Logger.getInstance('AuthorModalChangeController');

        vm.authorEdited = authorEdited;
        vm.save = save;
        vm.cancel = cancel;

        console.log("ARRIVEE DANS LE CONTROLLER");
        console.log(vm.authorEdited);

        ////////////////

        function save() {
            logger.debug('Choice --> Save')
            $modalInstance.close(vm.authorEdited);
        };

        function cancel() {
            $modalInstance.dismiss('cancel');
        }


    }

})();
