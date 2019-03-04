 angular.module("udpTestApp", ['ui.bootstrap']).
 controller("udpTestCtrl",function($scope,$interval,httpService){
     $scope.upSend=function(){
         httpService.send({message:"up"}).then(function(response){
             
         });
     };
     $scope.items={};
     $scope.downSend=function(){
         httpService.send({message:"down"}).then(function(response){
             
         });
     };
     $scope.sendByMacDown=function(data){
         httpService.sendByMac({message:"down",mac:data}).then(function(response){            
         });
     };
      $scope.sendByMacUp=function(data){
         httpService.sendByMac({message:"up",mac:data}).then(function(response){            
         });
     };
     $scope.getConnectors=function(){
         httpService.getConnectors().then(function(result){
             $scope.items=result;
         });
     };
     $scope.getConnectors();
     $interval(function(){$scope.getConnectors();},5000);
 }).
 service("httpService",function($http,$q){
     return{
         send:function(data){
            var deferred=$q.defer();
            var promise=deferred.promise;
            $http({
                url:'/park/udp/send',
                method:'post',
                data:data
            }).success(function(response){
                if (response.status==1001) {
                    deferred.resolve(response.body);
                } else {
                  deferred.reject(response);
                }
            }).error(function(){
              deferred.reject();
            });
            return promise;
         },        
         sendByMac:function(data){
               var deferred=$q.defer();
            var promise=deferred.promise;
            $http({
                url:'/park/udp/sendByMac',
                method:'post',
                data:data
            }).success(function(response){
                if (response.status==1001) {
                    deferred.resolve(response.body);
                } else {
                  deferred.reject(response);
                }
            }).error(function(){
              deferred.reject();
            });
            return promise;
         },
         getConnectors:function(){
             var deferred=$q.defer();
            var promise=deferred.promise;
             $http({
                url:'/park/udp/getConnectors',
            }).success(function(response){
                if (response.status==1001) {
                    deferred.resolve(response.body);
                } else {
                  deferred.reject(response);
                }
            }).error(function(){
              deferred.reject();
            });
            return promise;
         }
     };
 });
