var businessCarportApp = angular.module('businessCarportApp', ['ui.bootstrap','tm.pagination']);
businessCarportApp.controller('businessCarportCtrl', ['$scope', '$http','$uibModal','$timeout','$interval',
function($scope, $http, $modal,$timeout,$interval) {
    $scope.carportDetails = [];
    $scope.selectedIndex = -1;
    $scope.selectValue=-1;
    $scope.parks=[];

    $scope.getParks=function(){
        $http({
            url:'getParks?_t=' + (new Date()).getTime(),
            method:'get'
        }).success(function(response){
            if(response.status=1001){
                var body=response.body;
                for(var i=0;i<body.length;i++){
                    if(body[i].type==3){
                        $scope.parks.push(body[i]);
                    }
                }
                $scope.selectValue=$scope.parks[0];
                $scope.refreshData();
            }
        });
    };
    $scope.selectChange=function(){
        $scope.refreshData();
    };
      $scope.paginationConf = {
        currentPage : 1,
        totalItems : 500,
        itemsPerPage : 30,
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
        $scope.carportDetails = $scope.currentData;
    };
    $scope.refreshData = function() {     
        $http.get('getBusinessCarportDetail?low=0&count=1000&parkId='+$scope.selectValue.id).success(function(response) {
            if (response.status == 1001) {
                getInitail(response.body);
            }
        });
        
    };
     $scope.getParks();
      $interval(function(){
          $scope.refreshData();
      },8000);
    
   $scope.addMacAndInsertBusinessCarport=function(){
       $modal.open({
           templateUrl:"addMacAndBind",
           controller:"addMacAndInsertBusinessCarportCtrl",
           scope:$scope
       });
   };
    $scope.checked = function(index){
        $scope.selectedIndex = index;
        if($scope.parks[index].checked==false)
        $scope.parks[index].checked=true;
        else{
            $scope.parks[index].checked=false;
        }
    };
    $scope.addData = function() {
        //console.log($scope.checked);
           var getdata=$http({
            method:'get',
            url:'/park/getBusinessCarportDetail',
            headers:{"token":"6f13b8f3-cc3f-4e2a-a5b4-01b9cf6b40ca-1458491724564"},
            params:{low:0,count:1000,parkId:109}
          }).success(function(data){
            //console.log(data);
        });
    };
}])
.controller("addMacAndInsertBusinessCarportCtrl",["$scope","$http","$uibModalInstance",function($scope,$http,$uibModalInstance){
    $scope.parks=$scope.$parent.parks;
    $scope.selectP=$scope.$parent.selectValue;
    $scope.loading = false;
    $scope.submitted = false;
    $scope.close = function(){
        $uibModalInstance.close();
    };
    $scope.submit=function(){        
        var postdata={"mac":$scope.mac,"macDesc":$scope.macDesc,"parkId":$scope.selectP.id,"carportNumber":parseInt($scope.carportNumber)};
        $http({
            url:"/park/addMacAndInsertBusinessCarport",
            method:"post",
            data:angular.toJson(postdata)
        }).success(function(response){
            $scope.loading =false;
            $scope.submitted = true;
            $scope.result=response.message;
        });
    };
}])
.controller("testctrl",function($uibModalInstance){
      $scope.close = function(){
        $uibModalInstance.close();
    };
});

