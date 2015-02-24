(function (){
    "use strict";

    angular
        .module('docklandApp')
        .controller('LanguageController',LanguageController);

    LanguageController.$inject = ['$translate', 'Language'];

    /* @ngInject */
    function LanguageController($translate, Language){
        /* jshint validthis: true */
        var vm = this;

        vm.languages = '';
        vm.changeLanguage = changeLanguage;
        vm.activate = activate;

        activate();

        ////////////////

        function activate(){
            Language.getAll().then(function (languages) {
                vm.languages = languages;
            });
        }

        function changeLanguage(languageKey) {
            $translate.use(languageKey);
        }
    }
})();

