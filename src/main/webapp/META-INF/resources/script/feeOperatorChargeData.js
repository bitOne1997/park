angular.module("feeOperatorChargeDataApp",['ui.bootstrap']).
controller("feeOperatorChargeDataCtrl",["$scope",'getData',function($scope,getData){
               
                $scope.refresh=function(){
                var curDate = new Date();
                var nextDate = new Date();
                nextDate.setDate(nextDate.getDate() );
                var data={startDay:nextDate.format('yyyy-MM-dd'),endDay:curDate.format('yyyy-MM-dd')};
                     getData.getFeeOperatorDataByDateRange(data).then(function(result){
                    $scope.items=result;
                });
                };
                $scope.refresh();
                $scope.refresh=function(){
                var curDate = new Date();
                var nextDate = new Date();
                nextDate.setDate(nextDate.getDate() );
                var data={startDay:nextDate.format('yyyy-MM-dd'),endDay:curDate.format('yyyy-MM-dd')};
                     getData.getFeeOperatorDataByDateRange(data).then(function(result){
                    $scope.items=result;
                });
                };
                $scope.weekData=function(){
                var curDate = new Date();
                var nextDate = new Date();
                nextDate.setDate(nextDate.getDate()-7 );
                var data={startDay:nextDate.format('yyyy-MM-dd'),endDay:curDate.format('yyyy-MM-dd')};
                     getData.getFeeOperatorDataByDateRange(data).then(function(result){
                    $scope.items=result;
                });
                };
                $scope.monthData=function(){
                var curDate = new Date();
                var nextDate = new Date();
                nextDate.setDate(nextDate.getDate() -30);
                var data={startDay:nextDate.format('yyyy-MM-dd'),endDay:curDate.format('yyyy-MM-dd')};
                     getData.getFeeOperatorDataByDateRange(data).then(function(result){
                    $scope.items=result;
                });
                };
                $scope.yearData=function(){
                var curDate = new Date();
                var nextDate = new Date();
                nextDate.setDate(nextDate.getDate()-365 );
                var data={startDay:nextDate.format('yyyy-MM-dd'),endDay:curDate.format('yyyy-MM-dd')};
                     getData.getFeeOperatorDataByDateRange(data).then(function(result){
                    $scope.items=result;
                });
                };
}]).
service("getData",['$http','$q',function($http,$q){
    return {
        getFeeOperatorDataByDateRange:function(data){
            var deffer=$q.defer();
            var promise=deffer.promise;
            $http({
                url:'/park/pos/charge/getFeeOperatorChargeData',
                method:'post',
                data:data
            }).success(function(response){
                if(response.status==1001){
                     deffer.resolve(response.body);
                }
            });
            return promise;
        }
    };
}]);
