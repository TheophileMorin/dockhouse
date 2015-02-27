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
