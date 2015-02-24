(function (){
    "use strict";

    angular
        .module('docklandApp')
        .controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['Author', '$stateParams'];

    /* @ngInject */
    function AuthorDetailController(Author, $stateParams){
        /* jshint validthis: true */
        var vm = this;

        vm.author = {};

        activate($stateParams.id);

        ////////////////

        function activate(id) {
            Author.get(id)
                .then(function(data){
                    vm.author = data;
                })
                .catch(function(error) {
                    //logger.error('Enabled to get the list of registries.');
                });
        }

    }

})();
