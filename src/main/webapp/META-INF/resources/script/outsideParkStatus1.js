angular.module("outsideParkStatus1App",['ui.bootstrap'])
.controller('outsideParkStatus1Ctrl',['$scope','getDataService',function($scope,getDataService){
  $scope.isShow=1;
  $scope.zoneCenters=[];
  getDataService.getZoneCenterInfo().then(function(result){
      $scope.zoneCenters=result;
  });
  $scope.areas=[];
    $scope.getAreaInfo=function(zoneCenter){
        $scope.isShow=2;
        getDataService.getAreaInfo(zoneCenter.id).then(function(result){
            $scope.areas=result;
        });
        $scope.show2data=zoneCenter;
    };
    $scope.showBack=function(){
        if($scope.isShow>1){
            $scope.isShow-=1;
        }
    };
    var getShow1Info=function(){
        if($scope.zoneCenters==undefined){
            return;
        }
        $scope.show1AreaCount=0;
        $scope.show1StreetCount=0;
        $scope.show1ParkCount=0;
        $scope.show1AmountMoney=0;
        $scope.show1RealMoney=0;
        $scope.show1EntranceCount=0;
        $scope.show1OutCount=0;
        $scope.show1CarportCount=0;
        $scope.show1CarportLeftCount=0;
        $scope.show1Onlinecarportcount=0;
        $scope.show1Onlineparkcount=0;
        $scope.show1Arrearage=0;
        angular.forEach($scope.zoneCenters,function(value,num){
            $scope.show1AreaCount+=value.areacount;
            $scope.show1StreetCount+=value.streetcount;
            $scope.show1ParkCount+=value.parkcount;
            $scope.show1AmountMoney+=value.amountmoney;
            $scope.show1RealMoney+=value.realmoney;
            $scope.show1EntranceCount+=value.entrancecount;
            $scope.show1OutCount+=value.outcount;
            $scope.show1CarportCount+=value.carportcount;
            $scope.show1CarportLeftCount+=value.carportleftcount;       
            $scope.show1Onlinecarportcount+=value.onlinecarportcount; 
            $scope.show1Onlineparkcount+=value.onlineparkcount; 
            $scope.show1Arrearage+=value.arrearage;
        });        
    };
    $scope.$watch('zoneCenters',function(oldvalue,newvalue){
        getShow1Info();
    });
    
    $scope.getStreetInfo=function(area){
        $scope.isShow=3;
        getDataService.getStreetInfo(area.id).then(function(result){
            $scope.streets=result;
        });
        $scope.show3data=area;
    };
    $scope.getParkInfo=function(street){
        $scope.isShow=4;
        getDataService.getParkInfo(street.id).then(function(result){
            $scope.parks=result;
        });
        $scope.show4data=street;
    };
}])
.service('getDataService',['$http','$q',function($http,$q){
    
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
