var app = angular.module("stationApp", []);
      //Controller Part
      app.controller("StationListCtrl", function ($scope, $http) {
        //Initialize page with default data which is blank in this example
        $scope.stations = [];
        $scope.form = {
          nom: "",
          description: ""
        };
        //Now load the data from server
        _refreshPageData();
       
        /* Private Methods */
        //HTTP GET- get all fruits collection
        function _refreshPageData() {
          $http({
            method: 'GET',
            url: '../api/stations/findAll'
          }).then(function successCallback(response) {
            $scope.stations = response.data.stations;
          }, function errorCallback(response) {
            console.log(response.statusText);
          });
        }
        function _success(response) {
          _refreshPageData();
        }
        function _error(response) {
          alert(response.data.message || response.statusText);
        }
        
      });
