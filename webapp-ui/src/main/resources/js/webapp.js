angular.module('webApp', ['ui.bootstrap', 'ngCookies'])
.controller('webTabCtrl', ['$scope', '$http', '$rootScope', '$cookies', '$window', function($scope, $http, $rootScope, $cookies, $window) {
  $rootScope.selectedTab = 0;
  $rootScope.message = { "timestamp": '' };
  $scope.getMessage = function() {
    $http.get('/cxf/demo/webapp/message').then(
      function(response) {
        $rootScope.message = response.data;
      }
    );
  };
}]);