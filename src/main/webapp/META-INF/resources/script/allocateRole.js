var userApp = angular.module("userApp", ['ui.bootstrap',,'tm.pagination']);

userApp.controller("userCtrl", ['$scope', '$http',  'textModal', '$modal', '$timeout', function($scope, $http, textModal,$uibModal, $timeout){
	
	$scope.user = {
			items:[],
			checkedIndex:-1
	};
	
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
        $scope.user.items = $scope.currentData;
    };
	
	$scope.user.refresh = function(){
		$http.get('/park/getParkUser')
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
	


	$scope.user.allocateRoles = function(index){
		$uibModal.open({
			templateUrl:'/park/views/template/ucAllocateRoles.html',
			controller:'allocateRoleCtrl',
			$scope:$scope,
			resolve:{
				userId: function(){
					return $scope.user.items[index].id;
				}
			}
		});
	};

	
	$scope.user.refresh();
	
}]);





userApp.controller('allocateRoleCtrl', function($scope, $modalInstance, $http, $timeout, userId){
	$scope.allocatedRoles = [];
	
	$http.get('detail/' + userId)
	.success(function(response){
		if(response.status == 1001)
			$scope.allocatedRoles = response.body;
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
		var roleIds = [];
		for(var i = 0; i < $scope.allocatedRoles.length; i++){
			var role = $scope.allocatedRoles[i];
			if(role.allocated){
				roleIds.push(role.id);
			}
		}
		
		var args = {userId: userId, roleIds: roleIds};
		$http.post("user/update", args)
		.success(function(response){	
			$scope.loading = false;
			$scope.submitted = true;
			if(response.status == 1001){
				$scope.result="成功";
				$timeout(function(){
					$scope.result="";
					$modalInstance.close('ok');
					//$scope.$parent.user.refresh();
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

var user = angular.module('userApp');

user.service('textModal',  ['$uibModal', function($uibModal){
	
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
userApp.controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
	$scope.text = msg;
	
	$scope.close = function(){
		$uibModalInstance.close('cancel');
	};
});

