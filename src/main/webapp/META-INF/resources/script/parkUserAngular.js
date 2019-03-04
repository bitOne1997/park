angular.module("parkUserApp", ['ui.bootstrap'])
.controller("parkUserCtrl", function($scope, $http, $q,  getPositionData) {  
    $scope.parks=[]; 
      $scope.getArea = function() {
        getPositionData.getArea($scope.zoneCenterId).then(function(result) {
            $scope.areas = result;  
             $scope.parks=[];
            angular.forEach(result,function(area){
            getPositionData.getOutsideParkByAreaId(area.id).then(function(result) {
                 angular.forEach(result,function(result){
                     $scope.parks.push(result);
                 });
            
         
        });
            });      
        });
        
    };
     $scope.getZoneCenter = function() {
        getPositionData.getZoneCenter().then(function(result) {
            $scope.zoneCenters = result;
        });
    };
    
    $scope.getZoneCenter();
     $scope.getStreets = function() {
         if($scope.areaId==undefined||$scope.areaId==null){
            return;
        }
        getPositionData.getStreetByAreaId($scope.areaId).then(function(result) {
            $scope.streets = result;
           
        });
        getPositionData.getOutsideParkByAreaId($scope.areaId).then(function(result){
            $scope.parks=result;
        });
    };   
      var getAreaById = function(areaid) {
        getPositionData.getAreaById(areaid).then(function(result) {
            var selectedArea = result;
            $scope.zoneCenterId = selectedArea.zoneid;
            $scope.getArea();
        });
    };
        $scope.getParks = function(streetId) {
        if (streetId==undefined||streetId==null) {
            return;
        };
      
        getPositionData.getOutsideParkByStreetId(streetId).then(function(result) {
            $scope.parks = result;   
            });           
    };
})
.factory("getPositionData", function($http, $q) {
    var getZoneCenter = function() {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/zoneCenter/getByStartAndCount",
            method : 'post',
            params : {
                start : 0,
                count : 200
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
    var getOutsideParkByAreaId = function(areaId) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/getOutsideParkByAreaId/" + areaId,
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
        getOutsideParkByAreaId:getOutsideParkByAreaId,
        getStreetById : getStreetById
    };
    });
