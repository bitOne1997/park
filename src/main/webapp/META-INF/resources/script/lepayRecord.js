angular.module("lepayRecordApp",['ui.bootstrap']).
controller("lepayRecordCtrl",["$scope","$uibModal","lepayRecordHttpService",function($scope,$uibModal,lepayRecordHttpService){
    lepayRecordHttpService.getByCount({start:0,count:1000}).then(function(response){
        $scope.items=response;
    });
     $scope.paginationConf = {
        currentPage : 1,
        totalItems : 500,
        itemsPerPage : 20,
        pagesLength : 10,
        perPageOptions : [10,20, 30, 40, 50],
        rememberPerPage : 'perPageItems',
        onChange : function() {
            getInitail($scope.pagedata);
        }
    };
    $scope.pagedata = [];
    $scope.currentData=[];
    var getInitail = function(data) {
        $scope.pagedata = data;
        $scope.paginationConf.totalItems = data.length;      
        $scope.currentData=[];
        var start = ($scope.paginationConf.currentPage - 1) * $scope.paginationConf.itemsPerPage;
        for (var i = 0; i < $scope.paginationConf.itemsPerPage; i++) {
            if(data.length>(start + i))
            $scope.currentData[i] = data[start + i];
        };
         $scope.posdata = $scope.currentData;
    };
    $scope.showPosChargeData=function(data){
   
        lepayRecordHttpService.getPosChargeDataById({id:data}).then(function(response){
           getInitail(response);
       
           $uibModal.open({
               templateUrl:"showPosChargeModal",
               scope:$scope,
               controller:"showPosChargeDataCtrl"
           }); 
        });
    };
}]).
controller("showPosChargeDataCtrl",function($scope,$uibModalInstance){
  
    $scope.close=function(){
        $uibModalInstance.close();
    };
}
).
service("lepayRecordHttpService",["$http","$q",function($http,$q){
    return {
        getAmount:function(){
           var deferred=$q.defer();
           var promise=deferred.promise;           
             $http({
                 url:"/park/lepay/record/getAmount",
                 method:'get'
             }).success(function(response){
                 if(response.status==1001){
                    deferred.resolve(response.body); 
                 }
             });
             return promise;
        },
        getByCount:function(data){
            var deferred=$q.defer();
           var promise=deferred.promise;           
             $http({
                 url:"/park/lepay/record/getByCount",
                 method:'post',
                 data:angular.toJson(data)
             }).success(function(response){
                 if(response.status==1001){
                    deferred.resolve(response.body); 
                 }
             });
             return promise;
        },
        getPosChargeDataById:function(data){
           var deferred=$q.defer();
           var promise=deferred.promise;  
           $http({
               url:"/park/pos/charge/getById",
               method:"post",
               data:angular.toJson(data)
           }).success(function(response){
                if(response.status==1001){
                    deferred.resolve(response.body); 
                 }
           }); 
             return promise;
        }
        
    };
}]);
