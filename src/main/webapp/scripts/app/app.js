(function (){
    'use strict';

    angular.module('docklandApp', [
        'LocalStorageModule',
        'tmh.dynamicLocale',
        'ngResource',
        'ui.router',
        'ngCookies',
        'pascalprecht.translate',
        'ngCacheBuster',
        'restangular',
        'logger'])

        .run(function ($rootScope, $location, $window, $http, $state, $translate, Auth, Principal, Language, ENV, CONFIG) {
            $rootScope.ENV = ENV;
            $rootScope.VERSION = CONFIG.VERSION;
            $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
                $rootScope.toState = toState;
                $rootScope.toStateParams = toStateParams;

                if (Principal.isIdentityResolved()) {
                    Auth.authorize();
                }

                // Update the language
                Language.getCurrent().then(function (language) {
                    $translate.use(language);
                });
            });

            $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
                var titleKey = 'global.title';

                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;

                // Set the page title key to the one configured in state or use default one
                if (toState.data.pageTitle) {
                    titleKey = toState.data.pageTitle;
                }
                $translate(titleKey).then(function (title) {
                    // Change window title with translated one
                    $window.document.title = title;
                });
            });

            $rootScope.back = function() {
                // If previous state is 'activate' or do not exist go to 'home'
                if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                    $state.go('home');
                } else {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            };
        })

        .factory('authInterceptor', function ($rootScope, $q, $location, localStorageService) {
            return {
                // Add authorization token to headers
                request: function (config) {
                    config.headers = config.headers || {};
                    var token = localStorageService.get('token');

                    if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                        config.headers.Authorization = 'Bearer ' + token.access_token;
                    }

                    return config;
                }
            };
        });
})();
