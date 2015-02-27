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

    AuthorController.$inject = ['Author', '$modal', 'Logger'];

    /* @ngInject */
    function AuthorController(Author, $modal, Logger){
        /* jshint validthis: true */
        var logger = Logger.getInstance('AuthorController');
        var vm = this;

        vm.authors = [];
        vm.authorEdited = {};
        vm.editionMode = false;
        vm.loadAll = loadAll;
        vm.openCreate = openCreate;
        vm.openEdit = openEdit;
        vm.openRemove = openRemove;
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
                    vm.clear();
                });
        }

        function update(author) {
            Author.update(author)
                .then(function(data) {
                    vm.loadAll();
                    vm.clear();
                });
        }

        function openCreate() {
            vm.clear();

            // Open the modal Edition window
            logger.debug('Opening the creation window ...');
            var modalInstance = $modal.open({
                templateUrl: 'AuthorChangeModal',
                controller: 'AuthorModalChangeController as modal',
                backdrop: 'static',
                resolve: {
                    authorEdited: function() {
                        return vm.authorEdited;
                    }
                }
            });

            modalInstance.result.then(function (authorModified) {
                logger.debug('Trying to create the author ...');
                applyChanges(authorModified);
            }, function () {
                logger.debug('Modal dismissed at: ' + new Date());
                vm.clear();
            });

        }

        function openEdit(author) {
            vm.authorEdited = author;
            vm.editionMode = true;

            // Open the modal Edition window
            var modalInstance = $modal.open({
                templateUrl: 'AuthorChangeModal',
                controller: 'AuthorModalChangeController as modal',
                backdrop: 'static',
                resolve: {
                    authorEdited: function() {
                        return vm.authorEdited;
                    }
                }
            });

            modalInstance.result.then(function (authorModified) {
                applyChanges(authorModified);
            }, function () {
                logger.debug('Modal dismissed at: ' + new Date());
                vm.clear();
            });

        }

        function openRemove(author) {
            logger.debug('Opening the delete confirmation modal window ...');
            vm.authorEdited = author;

            var modalInstance = $modal.open({
                templateUrl: 'AuthorRemoveConfirmationModal',
                controller: 'AuthorModalRemoveController as modalDelete',
                backdrop: 'static',
                resolve: {
                    authorName: function() {
                        return vm.authorEdited.name;
                    }
                }
            });

            modalInstance.result.then(function (toDelete) {
                if (toDelete){
                    remove(vm.authorEdited);
                }
            }, function () {
                logger.debug('Modal delete dismissed at: ' + new Date());
                vm.clear();
            });
        }

        function remove(author) {
            Author.remove(author)
                .then(function(data) {
                    vm.loadAll();
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
