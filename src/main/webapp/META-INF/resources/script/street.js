var streetApp=angular.module("streetApp",['ui.bootstrap', 'tm.pagination']);
streetApp.controller("streetCtrl",['$scope', '$http','$modal', 'textModal', '$timeout',
function($scope,$http,$uibModal,textModal,$timeout){
    $scope.streets=[];
    $scope.checkedIndex=-1;
    $scope.start=0;
    $scope.count=800;
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
         $scope.streets = $scope.currentData;
    };
    $scope.refreshStreet=function(){
        $http({
            url:'/park/street/getByStartAndCount',
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
    $scope.insertStreet=function(){
        $uibModal.open({
            templateUrl: '/park/views/template/ucNewStreet.html',
            controller: 'streetModify',
            scope:$scope,
            resolve: {
                index: function(){
                        return undefined;
                }
            }
        });
    };
    $scope.updateStreet=function(){
        if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
        $uibModal.open({
            templateUrl: '/park/views/template/ucNewStreet.html',
            controller: 'streetModify',
            scope:$scope,
            resolve: {
                index: function(){
                        return $scope.checkedIndex;
                }
            }
        });
    };
    
    //删除多条
    $scope.deleteStreet=function(){
    	if($scope.checkedIndex==-1){
            alert("请选择");
            return;
        }
    	var id = $scope.users[$scope.checkedIndex].id;
        /*if($scope.selected[0]==""||$scope.selected.length==0){
            alert("请选择");
            return;
        }
	        	id=$scope.selected;*/
/*	        	console.log(id);*/
	        	$http({
	                url:'/park/street/deleteAll/'+id,
	                method:'get'
	            }).success(function(response){
	                if(response.status==1001)
	                {
	                	textModal.open($scope, "成功","删除成功");
	                    $scope.refreshStreet();
	                }else{
	                    textModal.open($scope, "失败","删除失败");
	                }
	            }).error(function(){
	                 textModal.open($scope, "失败","操作失败");
	            });
    };
    //多选checkbox框
    /*$scope.selected = [];
    $scope.deleteSelection = function ($event, id) {
        var checkbox = $event.target;
        var checked = checkbox.checked;
        if (checked) {
            $scope.selected.push(id);
        }else{
        	var idx = $scope.selected.indexOf(id) ;
			$scope.selected.splice(idx,1) ;
        }
    }
    $scope.isSelected = function(id){
         return $scope.selected.indexOf(id)>=0;
    }*/
    
    //删除一条
    $scope.selectChange=function(index){
        if($scope.streets[index].checked){
            $scope.checkedIndex = index;
            return;
        }
        for(var i = 0; i < $scope.streets.length; i++){
            var item = $scope.streets[i];
            if(item.checked != undefined && item.checked == true){
                $scope.checkedIndex = i;
                return;
            }
        }
        $scope.checkedIndex = -1;
    };
    $scope.refreshStreet();
}]);

streetApp.controller("streetModify",function($scope, textModal,$modalInstance, $http, positionService,$timeout, index){
    var url = '/park/street/insert';
    $scope.tempStreet={};      
    $scope.getZoneCenter=function(){
      positionService.getZoneCenter().then(function(result){
      $scope.zoneCenters=result;
  });
  };
   $scope.getZoneCenter();
   $scope.areas=[];  
   $scope.getArea=function(){
       positionService.getArea($scope.zoneCenterId).then(
           function(result){
               $scope.areas=result;            
           }
       );
    };    
   var getAreaById=function(areaid){
      positionService.getAreaById(areaid).then(
          function(result){
              selectedArea=result;
              $scope.zoneCenterId=selectedArea.zoneid;
              $scope.getArea();
          }
      );
  };
     if(index != undefined){
        $scope.tempStreet = $scope.$parent.streets[index];
        url = '/park/street/update';
        getAreaById($scope.tempStreet.areaid);
    }
     else{
        $scope.tempStreet.number='S-'+(new Date()).format('yyyyMMddhhmmssS');
    }
    $scope.streetTypes=[{"id":0,"name":"自营路段"},{"id":1,"name":"其它路段"}];
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result="";
    $scope.submit = function(){
        $scope.loading = true;
        $scope.submitted = false;
        delete $scope.tempStreet['checked'];
        $http({
            url:url,
            method:'post',
            data:$scope.tempStreet,
        }).success(function(response){          
            if(response.status==1001){ 
               $scope.loading =false;
               $scope.submitted = true;
               $scope.result="成功";             
               $timeout(function(){                                
                   $scope.result="";
                    
                    $modalInstance.close('ok');
               },500);
                                        
               $scope.$parent.refreshStreet();
              
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

var streetService=angular.module("streetApp");
streetService.service('textModal',  ['$uibModal', function($uibModal){   
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

streetService.service("positionService",function($http,$q){
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
           var deferred=$q.defer();
           var promise=deferred.promise;
           if (!zoneid) {
            return;
        }
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
         return {
             getZoneCenter:getZoneCenter,
             getArea:getArea,            
             getStreetByAreaId:getStreetById,
             getAreaById:getAreaById
         };
});

  streetService.controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
    $scope.text = msg;   
    $scope.close = function(){
        $uibModalInstance.close('cancel');
    };
});
