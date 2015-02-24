(function (){
    "use strict";

    angular
        .module('docklandApp')
        .controller('AuthorController', AuthorController);

    AuthorController.$inject = ['Author', 'DateUtils'];

    /* @ngInject */
    function AuthorController(Author, DateUtils){
        /* jshint validthis: true */
        var vm = this;

        vm.authors = [];
        vm.authorEdited = {};
        vm.loadAll = loadAll;
        vm.create = create;
        vm.edit = edit;
        vm.delete = remove;
        vm.confirmRemove = confirmRemove;
        vm.clear = clear;

        activate();

        ////////////////

        function activate(){
            vm.loadAll();
        }

        function loadAll() {
            Author.getAll()
                .then(function(data){
                    vm.authors = data;
                })
                .catch(function(error) {
                    //logger.error('Enabled to get the list of authors.');
                });
        }

        function create(author) {
            Author.create(author)
                .then(function(data) {
                    vm.loadAll();
                    $('#saveAuthorModal').modal('hide');
                    vm.clear();
                });
        }

        function edit(author) {
            vm.authorEdited = author;
            vm.authorEdited.birthDate = DateUtils.formatDateForUI(vm.authorEdited.birthDate);
            $('#saveAuthorModal').modal('show');
        }

        function remove(id) {

        }

        function confirmRemove(id) {

        }

        function clear() {
            vm.authorEdited = {name: null, birthDate: null, id: null};
        }


    }

})();
//
//'use strict';
//
//angular.module('docklandApp')
//    .controller('AuthorController', function ($scope, Author) {
//        $scope.authors = [];
//        $scope.loadAll = function() {
//            Author.query(function(result) {
//               $scope.authors = result;
//            });
//        };
//        $scope.loadAll();
//
//        $scope.create = function () {
//            Author.save($scope.author,
//                function () {
//                    $scope.loadAll();
//                    $('#saveAuthorModal').modal('hide');
//                    $scope.clear();
//                });
//        };
//
//        $scope.update = function (id) {
//            Author.get({id: id}, function(result) {
//                $scope.author = result;
//                $('#saveAuthorModal').modal('show');
//            });
//        };
//
//        $scope.delete = function (id) {
//            Author.get({id: id}, function(result) {
//                $scope.author = result;
//                $('#deleteAuthorConfirmation').modal('show');
//            });
//        };
//
//        $scope.confirmDelete = function (id) {
//            Author.delete({id: id},
//                function () {
//                    $scope.loadAll();
//                    $('#deleteAuthorConfirmation').modal('hide');
//                    $scope.clear();
//                });
//        };
//
//        $scope.clear = function () {
//            $scope.author = {name: null, birthDate: null, id: null};
//        };
//    });
