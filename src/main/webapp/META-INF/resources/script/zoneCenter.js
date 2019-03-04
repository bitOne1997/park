var zoneCenterApp=angular.module("zoneCenterApp",['ui.bootstrap', 'tm.pagination']);
zoneCenterApp.controller("zoneCenterCtrl",['$scope', '$http','$modal', 'textModal', '$timeout',
function($scope,$http,$uibModal,textModal,$timeout){
    $scope.zoneCenters=[];
    $scope.checkedIndex=-1;
    $scope.start=0;
    $scope.count=800;
     $scope.paginationConf = {
        currentPage : 1,
        totalItems : 500,
        itemsPerPage : 10,
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
         $scope.zoneCenters = $scope.currentData;
    };
    $scope.refreshZoneCenter=function(){
        $http({
            url:'/park/zoneCenter/getByStartAndCount',
            method:'post',
            params:{start:$scope.start,count:$scope.count}
        }).success(function(response){
            if(response.status==1001){
                getInitail(response.body);
            }
            else{
               textModal.open($scope,"错误","数据请求失败");
            }
        }).error(function(){
               textModal.open($scope,"错误","数据请求失败");
        });
    };
    $scope.insertZoneCenter=function(){
        $uibModal.open({
            templateUrl: '/park/views/template/ucNewZoneCenter.html',
            controller: 'zoneCenterModify',
            scope:$scope,
            resolve: {
                index: function(){
                        return undefined;
                }
            }
        });
    };
    $scope.updateZoneCenter=function(){
        if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
        $uibModal.open({
            templateUrl: '/park/views/template/ucNewZoneCenter.html',
            controller: 'zoneCenterModify',
            scope:$scope,
            resolve: {
                index: function(){
                        return $scope.checkedIndex;
                }
            }
        });
    };
    $scope.deleteZoneCenter=function(){
        if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
        var id=$scope.zoneCenters[$scope.checkedIndex].id;
        $http({
            url:'/park/zoneCenter/delete/'+id,
            method:'get'
        }).success(function(response){
            if(response.status==1001)
            {
                textModal.open($scope, "成功","操作成功");
                $scope.refreshZoneCenter();
            }
            else{
                textModal.open($scope, "失败","操作失败");
            }
        }).error(function(){
             textModal.open($scope, "失败","操作失败");
        });
    };
        $scope.selectChange=function(index){
        if($scope.zoneCenters[index].checked){
            $scope.checkedIndex = index;
            return;
        }
        for(var i = 0; i < $scope.zoneCenters.length; i++){
            var item = $scope.zoneCenters[i];
            if(item.checked != undefined && item.checked == true){
                $scope.checkedIndex = i;
                return;
            }
        }
        $scope.checkedIndex = -1;
    };
    $scope.refreshZoneCenter();
}]);

zoneCenterApp.controller("zoneCenterModify",function($scope, textModal,$modalInstance, $http, $timeout, index){
    var url = '/park/zoneCenter/insert';
    $scope.tempZoneCenter={};
    if(index != undefined){
        $scope.tempZoneCenter = $scope.$parent.zoneCenters[index];
        url = '/park/zoneCenter/update';
    }
    else{
   $scope.tempZoneCenter.num='Z-'+(new Date()).format('yyyyMMddhhmmssS');
    }
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result="请求中";
    $scope.submit = function(){
        $scope.loading = true;
        $scope.submitted = false;
        delete $scope.tempZoneCenter['checked'];
        $http({
            url:url,
            method:'post',
            data:$scope.tempZoneCenter,
        }).success(function(response){          
            if(response.status==1001){ 
               $scope.loading =false;
               $scope.submitted = true;
               $scope.result="成功";             
               $timeout(function(){                                
                   $scope.result="";
                    
                    $modalInstance.close('ok');
               },2000);
                                        
               $scope.$parent.refreshZoneCenter();
              
            }
            else
            {
                textModal.open($scope, "失败","操作失败");
            }
        }).error(function(){
            textModal.open($scope, "失败","操作失败");
        });
    };
    $scope.close = function(){
        $modalInstance.close('cancel');
    };

});

var zoneCenterService=angular.module("zoneCenterApp");
zoneCenterService.service('textModal',  ['$uibModal', function($uibModal){
    
    this.open = function($scope, header, body){
        $scope.textShowModal = $uibModal.open({
            templateUrl: '/park/views/template/text-modal.html',
            controller: 'textCtrl',
            scope: $scope,
            resolve:{
                msg:function(){ return {header:header, body:body}; }
            }
        });
    };
    
    this.close = function($scope, header, body){
        $scope.textShowModal.close('cancel');
    };
  
    
}]);


  zoneCenterService.controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
    $scope.text = msg;   
    $scope.close = function(){
        $uibModalInstance.close('cancel');
    };
});
