'use strict';

angular.module('docklandApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
