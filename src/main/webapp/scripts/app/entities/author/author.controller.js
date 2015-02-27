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
        .module('docklandApp')
        .controller('AuthorController', AuthorController);

    AuthorController.$inject = ['Author', 'DateUtils'];

    /* @ngInject */
    function AuthorController(Author, DateUtils){
        /* jshint validthis: true */
        var vm = this;

        vm.authors = [];
        vm.authorEdited = {};
        vm.editionMode = false;
        vm.loadAll = loadAll;
        vm.applyChanges = applyChanges;
        vm.edit = openEdit;
        vm.delete = openRemove;
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

        function applyChanges(author) {
            if (vm.editionMode){
                update(author);
            } else {
                create(author);
            }
        }

        function create(author) {
            Author.create(author)
                .then(function(data) {
                    vm.loadAll();
                    $('#saveAuthorModal').modal('hide');
                    vm.clear();
                });
        }

        function update(author) {
            Author.update(author)
                .then(function(data) {
                    vm.loadAll();
                    $('#saveAuthorModal').modal('hide');
                    vm.clear();
                });
        }

        function openEdit(author) {
            vm.authorEdited = author;
            vm.authorEdited.birthDate = DateUtils.formatDateForUI(vm.authorEdited.birthDate);
            vm.editionMode = true;
            $('#saveAuthorModal').modal('show');
        }

        function openRemove(author) {
            vm.authorEdited = author;
            $('#deleteAuthorConfirmation').modal('show');
        }

        function confirmRemove(id) {
            Author.remove(vm.authorEdited)
                .then(function(data) {
                    vm.loadAll();
                    $('#deleteAuthorConfirmation').modal('hide');
                    vm.clear();
                });
        }

        function clear() {
            vm.authorEdited = {name: null, birthDate: null, id: null};
            vm.editionMode = false;
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