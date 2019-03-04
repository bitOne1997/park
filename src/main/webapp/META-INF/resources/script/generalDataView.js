angular.module("generalDataViewApp",[]).
controller("generalDataViewCtrl",['$scope','getDataService',function($scope,getDataService){
 //  $scope.dayAmountMoney=33333;
  // $('span').attr('data-to',333);
  $scope.processData=function(data){
      $scope.dayAmountMoney=0;
      $scope.dayRealMoney=0;
      $scope.dayOutCount=0;
      $scope.dayParkCount=0;
      $scope.dayCarportCount=0;
      $scope.dayOnlineCarportCount=0;
      $scope.dayCarportLeftCount=0;
      angular.forEach(data,function(value){
          $scope.dayAmountMoney+=value.amountmoney;
          $scope.dayRealMoney+=value.realmoney;
          $scope.dayParkCount+=value.parkcount;
          $scope.dayCarportCount+=value.carportcount;
          $scope.dayOnlineCarportCount+=value.onlinecarportcount;
          $scope.dayCarportLeftCount+=value.carportleftcount;
          $scope.dayOutCount+=value.outcount;
      });
  };
  $scope.toParkInfo=function(){
     parent.location.href="/park/parkingInfo/";
  };
     getDataService.getZoneCenterInfo().then(function(result){
      $scope.zoneCenters=result;
      $scope.processData(result);
    //console.log($scope.dayOnlineCarportCount+"  "+$scope.dayOutCount);
  });
}]).
service('getDataService',['$http','$q',function($http,$q){    
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
     var getZoneCenterInfo = function() {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/outsideParkInfo/zoneCenterInfo",
            method : 'post',
            params : {
                start : 0,
                count : 100
            }
        }).success(function(response) {
            if(response.status==1001){
                deferred.resolve(response.body);
            }            
        });
        return promise;
    };
     var getArea = function(zoneid) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/area/getByZoneCenterId/' + zoneid,
            method : 'get',
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        }).error(function(){
            deferred.reject("error");
        });
        return promise;
    };
    var getAreaInfo=function(zoneid){
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/outsideParkInfo/areaInfo/' + zoneid,
            method : 'get',
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        }).error(function(){
            deferred.reject("error");
        });
        return promise;
    };
    var getStreetInfo=function(areaid){
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/outsideParkInfo/streetInfo/' + areaid,
            method : 'get',
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        }).error(function(){
            deferred.reject("error");
        });
        return promise;
    };
     var getParkInfo=function(streetid){
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/outsideParkInfo/parkInfo/' + streetid,
            method : 'get',
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        }).error(function(){
            deferred.reject("error");
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
    
    return{
        getZoneCenter:getZoneCenter,
        getZoneCenterInfo:getZoneCenterInfo,
        getAreaInfo:getAreaInfo,
        getStreetInfo:getStreetInfo,
        getParkInfo:getParkInfo,
        getAreaByZoneId:getArea,
        getStreetByAreaId:getStreetByAreaId,
        getOutsideParkByStreetId:getOutsideParkByStreetId
    };
}]);