(function () {
    'use strict';

    angular
        .module('dockhouseApp')
        .directive('jsonParsing', jsonParsing);

    jsonParsing.$inject = [];

    /* @ngInject */
    function jsonParsing() {
        // Usage:
        //          allow to parse Json text into objects in HTML select fields.
        // Creates:
        //
        var directive = {
            restrict: 'A',
            require: 'ngModel',
            link: link
        };
        return directive;

        function link(scope, element, attrs, ngModel) {
            function into(input) {
                if(input !== null) {
                    console.log("input");
                    return JSON.parse(input);
                }
            }
            ngModel.$parsers.push(into);
        }
    }
})();
