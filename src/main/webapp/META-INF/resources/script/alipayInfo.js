var alipayInfoApp=angular.module("alipayInfoApp",['ui.bootstrap']);
alipayInfoApp.controller("alipayInfoCtrl",['$scope', '$http','$modal', 'textModal', '$timeout',
function($scope,$http,$uibModal,textModal,$timeout){
    $scope.alipayInfos=[];
    $scope.checkedIndex=-1;
    $scope.start=0;
    $scope.count=50;
    $scope.refreshAlipayInfo=function(){
        var datasend={start:$scope.start,count:$scope.count};
        $http({
            url:'/park/alipayInfo/getbyCount',
            method:'post',
            data:angular.toJson(datasend)
        }).success(function(response){
            if(response.status==1001){
                $scope.alipayInfos=response.body;
            }
            else{
               textModal.open($scope,"错误","数据请求失败");
            }
        }).error(function(){
               textModal.open($scope,"错误","数据请求失败");
        });
    };
    $scope.insertAlipayInfo=function(){
        $uibModal.open({
            templateUrl: 'alipayInfoModify',
            controller: 'alipayInfoInsertCtrl',
            scope:$scope,
            resolve: {
                index: function(){
                        return undefined;
                }
            }
        });
    };
    $scope.updateAlipayInfo=function(){
        if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
        $uibModal.open({
            templateUrl: 'alipayInfoModify',
            controller: 'alipayInfoUpdateCtrl',
            scope:$scope,
            resolve: {
                index: function(){
                        return $scope.checkedIndex;
                }
            }
        });
    };
    $scope.deleteAlipayInfo=function(){
        if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
        var id=$scope.alipayInfos[$scope.checkedIndex].id;
        $http({
            url:'/park/alipayInfo/delete/'+id,
            method:'get'
        }).success(function(response){
            if(response.status==1001)
            {
                textModal.open($scope, "成功","操作成功");
                $scope.refreshalipayInfo();
            }
            else{
                textModal.open($scope, "失败","操作失败");
            }
        }).error(function(){
             textModal.open($scope, "失败","操作失败");
        });
    };
    $scope.selectChange=function(index){
          
           if($scope.alipayInfos[index].checked){
               $scope.alipayInfos[index].checked=false;
           }
           else{
               $scope.alipayInfos[index].checked=true;
           }
        if($scope.alipayInfos[index].checked){
            $scope.checkedIndex = index;
            return;
        }
        for(var i = 0; i < $scope.alipayInfos.length; i++){
            var item = $scope.alipayInfos[i];
            if(item.checked != undefined && item.checked == true){
                $scope.checkedIndex = i;
                return;
            }
        }
        $scope.checkedIndex = -1;
    };
    $scope.refreshAlipayInfo();
}]);

alipayInfoApp.controller("alipayInfoInsertCtrl",function($scope, textModal,$modalInstance, $http, $timeout, index){
    var url = '/park/alipayInfo/insert';
    $scope.tempalipayInfo={};      
 
  
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result="";
    $scope.submit = function(){
        $scope.loading = true;
        $scope.submitted = false;
        delete $scope.tempalipayInfo['checked'];
        $http({
            url:url,
            method:'post',
            data:$scope.tempalipayInfo,
        }).success(function(response){          
            if(response.status==1001){ 
               $scope.loading =false;
               $scope.submitted = true;
               $scope.result="成功";             
               $timeout(function(){                                
                   $scope.result="";                   
                   $modalInstance.close('ok');
               },2000);                                        
               $scope.$parent.refreshAlipayInfo();              
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

alipayInfoApp.controller("alipayInfoUpdateCtrl",function($scope, textModal,$modalInstance, $http,$timeout, index){
    var url = '/park/alipayInfo/update';
    $scope.tempalipayInfo=$scope.$parent.alipayInfos[index];      
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result="";
    
    $scope.submit = function(){
        $scope.loading = true;
        $scope.submitted = false;
        delete $scope.tempalipayInfo['checked'];
        $http({
            url:url,
            method:'post',
            data:$scope.tempalipayInfo,
        }).success(function(response){          
            if(response.status==1001){ 
               $scope.loading =false;
               $scope.submitted = true;
               $scope.result="成功";             
               $timeout(function(){                                
                   $scope.result="";                   
                   $modalInstance.close('ok');
               },2000);                                        
               $scope.$parent.refreshAlipayInfo();              
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


var alipayInfoService=angular.module("alipayInfoApp");
alipayInfoService.service('textModal',  ['$uibModal', function($uibModal){   
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

  alipayInfoService.controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
    $scope.text = msg;   
    $scope.close = function(){
        $uibModalInstance.close('cancel');
    };
});
