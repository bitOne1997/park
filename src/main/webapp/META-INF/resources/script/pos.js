var posApp=angular.module("posApp",['ui.bootstrap','tm.pagination']);
posApp.controller("posCtrl",['$scope', '$http','$modal', 'textModal', '$timeout',
function($scope,$http,$uibModal,textModal,$timeout){
    $scope.poses=[];
    $scope.checkedIndex=-1;
    $scope.start=0;
    $scope.count=800;
    $scope.refreshPos=function(){
        $http({
            url:'/park/pos/getByStartAndCount',
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
    
     $scope.paginationConf = {
        currentPage : 1,
        totalItems : 500,
        itemsPerPage : 30,
        pagesLength : 10,
        perPageOptions : [20, 30, 40, 50],
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
        $scope.poses = $scope.currentData;
    };
    $scope.searchByParkNameAndNumber=function(){
        if ($scope.searchText==""||$scope.searchText==undefined) {
             textModal.open($scope,"提示","请输入查询关键字");
             return;
        };
         $http({
            url:'/park/pos/getByParkNameAndNumber',
            method:'post',
            data:{parkName:$scope.searchText,num:$scope.searchText}
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
    $scope.poskeyup = function(e) {
		var keycode = window.event ? e.keyCode
				: e.which;
		if (keycode == 13) {
			$scope.searchByParkNameAndNumber();
		}
	};
    $scope.insertPos=function(){
        $uibModal.open({
            templateUrl: '/park/views/template/ucNewPos.html?v=1.0',
            controller: 'posModify',
            scope:$scope,
            resolve: {
                index: function(){
                        return undefined;
                }
            }
        });
    };
    $scope.updatePos=function(){
        if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
        $uibModal.open({
            templateUrl: '/park/views/template/ucNewPos.html?v=1.0',
            controller: 'posModify',
            scope:$scope,
            resolve: {
                index: function(){
                        return $scope.checkedIndex;
                }
            }
        });
    };
    $scope.deletePos=function(){
        if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
        var id=$scope.poses[$scope.checkedIndex].id;
        $http({
            url:'/park/pos/delete/'+id,
            method:'get'
        }).success(function(response){
            if(response.status==1001)
            {
                textModal.open($scope, "成功","操作成功");
                $scope.refreshPos();
            }
            else{
                textModal.open($scope, "失败","操作失败");
            }
        }).error(function(){
             textModal.open($scope, "失败","操作失败");
        });
    };
     $scope.selectChange=function(index){
        if($scope.poses[index].checked){
            $scope.checkedIndex = index;
            return;
        }
        for(var i = 0; i < $scope.poses.length; i++){
            var item = $scope.poses[i];
            if(item.checked != undefined && item.checked == true){
                $scope.checkedIndex = i;
                return;
            }
        }
        $scope.checkedIndex = -1;
    };
    $scope.refreshPos();
}]);

posApp.controller("posModify",function($scope, textModal,$modalInstance, $http,$timeout,getPositionData,index){
    var url = '/park/pos/insert';
    $scope.parks=[];
   
  // $scope.tempPos={};
  $scope.getZoneCenter=function(){
      getPositionData.getZoneCenter().then(function(result){
      $scope.zoneCenters=result;
  });
  };
   $scope.getZoneCenter();
   $scope.getArea=function(){
       getPositionData.getArea($scope.zoneCenterId).then(
           function(result){
               $scope.areas=result;
            //   $scope.zoneCenterId=$scope.areas.zoneid;               
           }
       );
    };
  
   $scope.getStreets=function(){     
      getPositionData.getStreetByAreaId($scope.areaId).then(
         function(result){
             $scope.streets=result;
         } 
      );
    };
  var selectedArea;
  var getAreaById=function(areaid){
      getPositionData.getAreaById(areaid).then(
          function(result){
              selectedArea=result;
              $scope.zoneCenterId=selectedArea.zoneid;
              $scope.getArea();
          }
      );
  };
   $scope.getStreetById=function(){
       if ($scope.tempPos.streetid==undefined||$scope.tempPos.streetid==null) {
           return;
       };
        $http({
            url:"/park/street/selectByPrimaryKey/"+$scope.tempPos.streetid,
            method:'get'
        }).success(function(response){
            if(response.status==1001){               
                $scope.areaId=response.body.areaid;
                getAreaById($scope.areaId);               
                $scope.getStreets();           
            }
        });
  };
    if(index != undefined){
        $scope.tempPos = $scope.$parent.poses[index];
        url = '/park/pos/update';
        $scope.getStreetById();        
    } 
    $scope.getParks=function(streetId){
       getPositionData.getOutsideParkByStreetId(streetId).then(function(result){
            $scope.parks=result;
        });
    };    
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result="";
    $scope.submit = function(){
        $scope.loading = true;
        $scope.submitted = false;
        delete $scope.tempPos['checked'];
        $http({
            url:url,
            method:'post',
            data:$scope.tempPos,
        }).success(function(response){          
            if(response.status==1001){ 
               $scope.loading =false;
               $scope.submitted = true;
               $scope.result="成功";             
               $timeout(function(){                                
                   $scope.result="";                   
                   $modalInstance.close('ok');
               },2000);
                                        
               $scope.$parent.refreshPos();
              
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

posApp.service('textModal',  ['$uibModal', function($uibModal){
    
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
posApp.factory("getPositionData",function($http,$q){
      var getZoneCenter=function(){
            var deferred=$q.defer();
            var promise=deferred.promise;
            $http({
                url:"/park/zoneCenter/getByStartAndCount",
                method:'post',
                params:{start:0,count:100}
            }).success(function(response){   
                    deferred.resolve(response.body);           
            });
            return promise;
        };
       var getAreaById=function(areaid){
           var deferred=$q.defer();
           var promise=deferred.promise;
           if (!areaid) {
            return;
            }
            $http({
                url:'/park/area/selectByPrimaryKey/'+areaid,
                method:'get'
            }).success(function(response){
                if(response.status==1001){
                    deferred.resolve(response.body); 
                }
            });
            return promise;
       };
       var getArea=function(zoneid){          
           if (!zoneid) {
            return;
        }
         var deferred=$q.defer();
         var promise=deferred.promise;
            $http({
                url:'/park/area/getByZoneCenterId/'+zoneid,
                method:'get',
            }).success(function(response){
                if(response.status==1001){
                     deferred.resolve(response.body);  
                }
            });
            return promise;
        };
        var getStreetById=function(areaid){
           var deferred=$q.defer();
           var promise=deferred.promise;           
             $http({
                 url:"/park/street/getByAreaid/"+areaid,
                 method:'get'
             }).success(function(response){
                 if(response.status==1001){
                    deferred.resolve(response.body); 
                 }
             });
             return promise;
         }; 
         var getOutsideParkByStreetId=function(streetId){
           var deferred=$q.defer();
           var promise=deferred.promise;           
             $http({
                 url:"/park/getOutsideParkByStreetId/"+streetId,
                 method:'get'
             }).success(function(response){
                 if(response.status==1001){
                    deferred.resolve(response.body); 
                 }
             });
             return promise;
         };
         return {
             getZoneCenter:getZoneCenter,
             getArea:getArea,            
             getStreetByAreaId:getStreetById,
             getAreaById:getAreaById,
             getOutsideParkByStreetId:getOutsideParkByStreetId
         };
          
});

  posApp.controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
    $scope.text = msg;   
    $scope.close = function(){
        $uibModalInstance.close('cancel');
    };
});
