'use strict';

angular.module('docklandApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


