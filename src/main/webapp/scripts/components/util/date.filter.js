/**
 * Created by BWI on 03/03/15.
 */
(function (){
    "use strict";

    angular
        .module('dockhouseApp')
        .filter('jsr310ToDate', jsr310ToDate);

    jsr310ToDate.$inject = ['$filter'];

    function jsr310ToDate($filter) {
        return function(item) {
            if (item){
                var formattedDate = new Date(item[0]+"/"+item[1]+"/"+item[2]);
                return $filter('date')(formattedDate,'longDate');
            }
        }
    }

})();
