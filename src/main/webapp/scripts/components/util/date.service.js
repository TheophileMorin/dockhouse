/**
 * Created by BWI on 24/02/15.
 */
(function (){
    "use strict";

    angular
        .module('docklandApp')
        .factory('DateUtils', DateUtils);

    //DateUtils.$inject = [''];

    /* @ngInject */
    function DateUtils(){

        return {
            formatDateForUI: formatDateForUI
        };

        ////////////////

        /**
         * Format a date aaaa-mm-dd to dd/mm/aaaa
         * @param dateToFormat
         * @returns {Date}
         */
        function formatDateForUI(dateToFormat) {
            var datesplited = dateToFormat.split("-");
            return new Date(new Date(datesplited[0], datesplited[1] - 1, datesplited[2]));
        }

    }


})();
