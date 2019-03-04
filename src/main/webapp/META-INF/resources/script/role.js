var roleApp = angular.module("roleApp", ['ui.bootstrap','tm.pagination']);

roleApp.controller("roleCtrl", ['$scope', '$http',  'textModal', '$modal', '$timeout', function($scope, $http, textModal,$uibModal, $timeout){
	
	$scope.role = {
			items:[],
			checkedIndex:-1
	};
	
	 $scope.paginationConf = {
        currentPage : 1,
        totalItems : 500,
        itemsPerPage : 20,
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
         $scope.role.items  = $scope.currentData;
    };
	
	$scope.role.checked = function(index){
		if($scope.role.items[index].checked){
			$scope.role.checkedIndex = index;
			return;
		}
			
		for(var i = 0; i < $scope.role.items.length; i++){
			var item = $scope.role.items[i];
			if(item.checked != undefined && item.checked == true){
				$scope.role.checkedIndex = i;
				return;
			}
		}
		$scope.role.checkedIndex = -1;
			
	};
	
	$scope.role.remove = function(){
		if($scope.role.checkedIndex == -1)
			return;
		var id = $scope.role.items[$scope.role.checkedIndex].id;
		$http.get('role/delete/' + id)
		.success(function(response){
			if(response.status == 1001)
				textModal.open($scope, "成功","操作成功");
			else
				textModal.open($scope, "失败","操作失败");
			$scope.role.refresh();
		})
		.error(function(response){
			textModal.open($scope, "失败","操作失败");
		});
	};
	
	$scope.role.refresh = function(){
		$http.get('role/get')
		.success(function(response){
			if(response.status == 1001)
				getInitail(response.body);
			else
				textModal.open($scope, "失败","操作失败");
		})
		.error(function(response){
			textModal.open($scope, "失败","操作失败");
		});
	};
	
	$scope.role.insert = function(){	
		$uibModal.open({
			templateUrl: '/park/views/template/ucNewRole.html',
			controller: 'modifyCrtl',
			scope:$scope,
			resolve: {
				index: function(){
						return undefined;
				}
			}
			
		});
	};
	
	$scope.role.update = function(){	
		if($scope.role.checkedIndex == -1)
			return;
		$uibModal.open({
			templateUrl: '/park/views/template/ucNewRole.html',
			controller: 'modifyCrtl',
			scope:$scope,
			resolve: {
				index: function(){
						return $scope.role.checkedIndex;
				}
			}
		});
	};

	$scope.role.allocatePages = function(index){
		$uibModal.open({
			templateUrl:'/park/views/template/ucAllocatePages.html',
			controller:'allocatePageCtrl',
			$scope:$scope,
			resolve:{
				roleId: function(){
					return $scope.role.items[index].id;
				}
			}
		});
	};

	
	$scope.role.refresh();
	
}]);





roleApp.controller('allocatePageCtrl', function($scope, $modalInstance, $http, $timeout, roleId){
	$scope.allocatedPages = [];
	
	$http.get('role/pageDetail/' + roleId)
	.success(function(response){
		if(response.status == 1001)
			$scope.allocatedPages = response.body;
		//else
			//textModal.open($scope, "失败","操作失败");
	})
	.error(function(response){
		//textModal.open($scope, "失败","操作失败");
	});
	
	
	$scope.loading = false;
	$scope.submitted = false;
	$scope.result = "";
	
	
	$scope.submit = function(){
		$scope.loading = true;
		var pageIds = [];
		for(var i = 0; i < $scope.allocatedPages.length; i++){
			var page = $scope.allocatedPages[i];
			if(page.allocated){
				pageIds.push(page.id);
			}
		}
		
		var args = {roleId: roleId, pageIds: pageIds};
		$http.post("role/page/update", args)
		.success(function(response){	
			$scope.loading = false;
			$scope.submitted = true;
			if(response.status == 1001){
				$scope.result="成功";
				$timeout(function(){
					$scope.result="";
					$modalInstance.close('ok');
					$scope.$parent.role.refresh();
				}, 2000);
			}else{
				$scope.result="失败:" + response.status;
				
			}			
		}).error(function(){
			$scope.loading = false;
			$scope.submitted = true;
			$scope.result="失败";
			//$modalInstance.close('faled');
		});
		
		
	};

	$scope.close = function(){
		$modalInstance.close('cancel');
	};
	
});

roleApp.controller('modifyCrtl',  function($scope, $modalInstance, $http, $timeout, index){
	
	var url = 'role/create';
	if(index != undefined){
		$scope.tempRole = $scope.$parent.role.items[index];
		url = 'role/update';
	}
	else{
		$scope.tempRole = {	
				id:0,
				name:"",
				description:""
		};
	}
	
	
	$scope.loading = false;
	$scope.submitted = false;
	$scope.result = "";
	
	
	$scope.submit = function(){
		$scope.loading = true;
		var args = {
				id:parseInt($scope.tempRole.id),
				name:$scope.tempRole.name,
				description:$scope.tempRole.description
		};
		$http.post(url, args)
		.success(function(response){	
			$scope.loading = false;
			$scope.submitted = true;
			if(response.status == 1001){
				$scope.result="成功";
				$timeout(function(){
					$scope.result="";
					$modalInstance.close('ok');
					$scope.$parent.role.refresh();
				}, 2000);
			}else{
				$scope.result="失败:" + response.status;
				
			}			
		}).error(function(){
			$scope.loading = false;
			$scope.submitted = true;
			$scope.result="失败";
			//$modalInstance.close('faled');
		});
	};
	
	$scope.close = function(){
		$modalInstance.close('cancel');
	};

});


var role = angular.module('roleApp');

role.service('textModal',  ['$uibModal', function($uibModal){
	
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

//show message dilog controller
roleApp.controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
	$scope.text = msg;
	
	$scope.close = function(){
		$uibModalInstance.close('cancel');
	};
});

