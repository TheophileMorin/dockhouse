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
/**
 * Created by BWI on 24/02/15.
 */
(function (){
    "use strict";

    angular
        .module('dockhouseApp')
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
            data: {
                displayName: 'global.menu.route',
                proxy: 'site'
            },
            views: {
                'navbar@': {
                    templateUrl: 'scripts/components/navbar/navbar.html',
                    controller: 'NavbarController as nav'
                },
                'breadcrumbs@': {
                    templateUrl: 'scripts/components/breadcrumbs/breadcrumbs.html',
                    controller: 'BreadcrumbsController as breadCtrl'
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
        RestangularProvider.setBaseUrl('/dockhouse/api');

        /**
         * Logger Configuration
         */
        LoggerProvider.enabled(true);
        LoggerProvider.setLevel(CONFIG.LOG_LEVEL);

    }

})();
