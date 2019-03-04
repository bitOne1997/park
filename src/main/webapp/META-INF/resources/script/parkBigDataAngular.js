var app=angular.module("parkBigDataApp", []);
app.controller("parkBigDataCtrl", ['$scope','$interval','httpService',
function($scope,$interval,httpService) {
var data={low:0,count:10};
$scope.items={};
var lastestId=0;
httpService.getPosData(data).then(function(response){  
    lastestId=response[0].id;
     angular.forEach(response,function(item){
        item.isNew=false;
    });
     $scope.items=response;
}); 
$interval(function(){
    httpService.getPosData(data).then(function(response){  
    angular.forEach(response,function(item){
        if(item.id>lastestId){
             item.isNew=true;
        }
        else{
             item.isNew=false;
        }
    });
    $scope.items=response;
});
},2000);
}]);
app.service("httpService",function($http,$q){
    return {
        getPosData:function(data){
            var deferred=$q.defer();
            var promise=deferred.promise; 
            $http({
                url:"/park/pos/charge/getByCount",
                method:"post",
                data:angular.toJson(data)
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

