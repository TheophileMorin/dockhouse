/**
 * Created by BWI on 24/02/15.
 */
(function (){
    "use strict";

    angular
        .module('docklandApp')
        .config(config);

    config.$inject = ['$stateProvider', '$urlRouterProvider', '$httpProvider', '$locationProvider', '$translateProvider', 'tmhDynamicLocaleProvider', 'httpRequestInterceptorCacheBusterProvider', 'RestangularProvider', 'LoggerProvider', 'CONFIG'];

    function config($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $translateProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider, RestangularProvider, LoggerProvider, CONFIG) {

        /**
         * Cache everything except rest api requests
         */
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        /**
         * Route and Navigation configuration
         */
        $urlRouterProvider.otherwise('/');
        $stateProvider.state('site', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: 'scripts/components/navbar/navbar.html',
                    controller: 'NavbarController as nav'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('language');
                    return $translate.refresh();
                }]
            }
        });


        /**
         * Language configuration
         */

        // Initialize angular-translate
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'i18n/{lang}/{part}.json'
        });

        $translateProvider.preferredLanguage('en');
        $translateProvider.useCookieStorage();

        tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js');
        tmhDynamicLocaleProvider.useCookieStorage('NG_TRANSLATE_LANG_KEY');


        /**
         * HTTP and REST Service configuration
         */
        $httpProvider.interceptors.push('authInterceptor');

        /**
         * Logger Configuration
         */
        LoggerProvider.enabled(true);
        LoggerProvider.setLevel(CONFIG.LOG_LEVEL);

    }

})();
