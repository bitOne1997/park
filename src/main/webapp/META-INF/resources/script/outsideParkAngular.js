var outsideParkApp = angular.module("outsideParkApp", ['ui.bootstrap']);
outsideParkApp.controller("outsideParkCtrl", function($scope, $http, $timeout, $q,$modal, getPositionData) {
   
    getPositionData.getZoneCenter().then(function(result) {
        $scope.zoneCenters = result;
    });
    $scope.getAreas = function() {
        getPositionData.getArea($scope.zoneCenterId).then(function(result) {
            $scope.areas = result;
        });
    };
    $scope.getStreets = function() {
        getPositionData.getStreetById($scope.areaId).then(function(result) {
            $scope.streets = result;
        });
    };
    $scope.clearPosition = function() {
        $scope.areas = [];
        $scope.streets = [];
        $scope.zoneCenterId = -1;
        $scope.isPositionChange = true;
    };

    $scope.getZoneCenter = function() {
        getPositionData.getZoneCenter().then(function(result) {
            $scope.zoneCenters = result;
        });
    };
    $scope.getZoneCenter();
    $scope.getArea = function() {
        getPositionData.getArea($scope.zoneCenterId).then(function(result) {
            $scope.areas = result;
        });
    };
    $scope.getStreets = function() {
        if ($scope.areaId == undefined || $scope.areaId == null) {
            return;
        }
        getPositionData.getStreetByAreaId($scope.areaId).then(function(result) {
            $scope.streets = result;
        });
    };

  
    var getAreaById = function(areaid) {
        getPositionData.getAreaById(areaid).then(function(result) {
            var selectedArea = result;
            $scope.zoneCenterId = selectedArea.zoneid;
            $scope.getArea();
        });
    };
    var streetToZoneCenter = function() {
        if ($scope.selectedStreetId == null || $scope.selectedStreetId == undefined) {
            return;
        }
        getPositionData.getStreetById($scope.selectedStreetId).then(function(result) {
            $scope.areaId = result.areaid;
            getAreaById($scope.areaId);
            $scope.getStreets();
        });
    };

    $scope.selectedStreetId = $('#selectedStreetId').text();
    $scope.streetIdChange = function() {
        streetToZoneCenter();
    };
    $scope.$watch($scope.selectedStreetId, function() {
        if ($scope.selectedStreetId == '')
            return;
        streetToZoneCenter();
    });
});
outsideParkApp.factory("getPositionData", function($http, $q) {
    var getZoneCenter = function() {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/zoneCenter/getByStartAndCount",
            method : 'post',
            params : {
                start : 0,
                count : 100
            }
        }).success(function(response) {
            deferred.resolve(response.body);
        });
        return promise;
    };
    var getAreaById = function(areaid) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        if (!areaid) {
            return;
        }
        $http({
            url : '/park/area/selectByPrimaryKey/' + areaid,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getArea = function(zoneid) {
        if (!zoneid) {
            return;
        }
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/area/getByZoneCenterId/' + zoneid,
            method : 'get',
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getStreetByAreaId = function(areaid) {

        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/street/getByAreaid/" + areaid,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getOutsideParkByStreetId = function(streetId) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/getOutsideParkByStreetId/" + streetId,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getStreetById = function(streetId) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/street/selectByPrimaryKey/" + streetId,
            method : 'get'
        }).success(function(response) {
            deferred.resolve(response.body);
        });
        return promise;
    };
    return {
        getZoneCenter : getZoneCenter,
        getArea : getArea,
        getStreetByAreaId : getStreetByAreaId,
        getAreaById : getAreaById,
        getOutsideParkByStreetId : getOutsideParkByStreetId,
        getStreetById : getStreetById
    };

});

