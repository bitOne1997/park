var apiUserApp = angular.module("apiUserApp", ['ui.bootstrap', 'ngActivityIndicator']);

apiUserApp.controller("apiUserCtrl", ['$scope', '$http',  '$modal', '$timeout', function($scope, $http, $uibModal, $timeout){
	$scope.apiUsers = [];
	$scope.selectedIndex = -1;
	
	$scope.userCount = 0;
	$scope.currentPage = 1;
	$scope.pageSize = 100;
	$scope.pageNumber = 0;
	
	$scope.refreshUser = function(){	
		$http.post('items', {'start': ($scope.currentPage - 1) * $scope.pageSize, 'len': $scope.pageSize})
		.success(function(response){
			$scope.showGetUserLoading = false;
			if(response.status == 1001){
				$scope.apiUsers = response.body.users;
				$scope.showGetUserError = false;			
			}else{
				$scope.showGetUserError = true;
			}		
		})
		.error(function(){
			$scope.showGetUserError = true;
		});
		
		$scope.getUserCount();
	};
	
	
	$scope.getUserCount = function(){
		$http.get('count')
		.success(function(response){
			if(response.status == 1001){
				$scope.userCount = response.body.count;
				$scope.pageNumber = $scope.userCount / $scope.pageNumber + 1;
			}else{
				$scope.userCount = 0;
				$scope.pageNumber = 0;
			}
		})
		.error(function(){
			$scope.userCount = 0;
			$scope.pageNumber = 0;
		});
	};
	

	$scope.insertUserModal = function(){	
		$uibModal.open({
			templateUrl: '/park/views/template/ucNewApiUser.html',
			controller: 'modifyCrtl',
			scope:$scope,
			resolve: {
				user: function(){
						return undefined;
				}
			}
		});
	};
	
	$scope.updateUserModal = function(){
				
		$scope.updateModal = $uibModal.open({
			templateUrl: '/park/views/template/ucNewApiUser.html',
			controller: 'modifyCrtl',
			scope:$scope,
			resolve:{
				user:function(){
					return $scope.apiUsers[$scope.selectedIndex];
				}
			}
		});
	};

	
	$scope.deleteUserModal = function(){
		
		if($scope.selectedIndex < 0)
			return;
		$scope.deleteModal = $uibModal.open({
			templateUrl: '/park/views/template/ucApiUserInfo.html',
			controller: 'deleteCtrl',
			scope:$scope,
			resolve:{
				id: function(){return $scope.apiUsers[$scope.selectedIndex].id;}
				}
		});
	};
	
	$scope.showTokenModal = function(index){
		
		if(index < 0)
			return;
		$scope.deleteModal = $uibModal.open({
			templateUrl: '/park/views/template/ucApiUserInfo.html',
			controller: 'showTokenCtrl',
			scope:$scope,
			resolve:{
				tokenId: function(){return $scope.apiUsers[index].tokenId;}
				}
		});
	};
	
	
	$scope.checked = function(index){
		$scope.selectedIndex = index;
	};
	
	$scope.nextPage = function(index){
		$scope.currentPage = index;
		$scope.refreshUser();
	}
	
	$scope.refreshUser();
	
}]);

apiUserApp.controller('modifyCrtl',  function($scope, $modalInstance, $http, $timeout, user){
	$scope.tempUser = { 'username':'','companyInfo':'','contact':'','number':''};
	var url = 'insert';
	if(user != undefined){
		$scope.tempUser = user;
		url = 'update';
	}
	
	$scope.showLoader = false;
	$scope.showResult = false;
	$scope.result = "";
	
	
	$scope.submit = function(){
		$scope.showLoader = true;
		$http.post(url, $scope.tempUser)
		.success(function(response){	
			$scope.showLoader = false;
			$scope.showResult = true;
			if(response.status == 1001){
				$scope.result="成功";
				$timeout(function(){
					$scope.result="";
					$modalInstance.close('ok');
					$scope.$parent.refreshUser();
				}, 2000);
			}else{
				$scope.result="失败,用户名不可以相同";
				
			}			
		}).error(function(){
			$scope.showLoader = false;
			$scope.showResult = true;
			$scope.result="失败";
			$modalInstance.close('faled');
		});
	};
	
	$scope.close = function(){
		$modalInstance.close('cancel');
	};

});


apiUserApp.controller('deleteCtrl', function($scope, $modalInstance, $http, $timeout, id){
	$scope.info="  确定删除Token信息!";
	$scope.showLoader = false;
	$scope.showResult = false;
	$scope.result = "";
	$scope.submit = function(){	
		$scope.showLoader = true;
		$scope.showResult = false;
		$http.get('delete/' + id)
		.success(function(response){
			$scope.showLoader = false;
			$scope.showResult = true;
			if(response.status == 1001){
				$scope.result = "成功";
				$timeout(function(){
					$scope.close();
					$scope.$parent.refreshUser();
				},2000);
				
			}else{
				$scope.result = "失败";
			}
			
			
		}).error(function(){
			$scope.showLoader = false;
			$scope.showResult = true;
			$scope.result = "失败";
		});
		
	};
	
	$scope.close = function(){
		$modalInstance.close('cancel');
	};
});

apiUserApp.controller('showTokenCtrl', function($scope, $modalInstance, $http, $timeout, tokenId){
	$scope.info="";
	$scope.showLoader = false;
	$scope.showCount = false;
	$scope.tokenCount=0;
	
	$http.get('/park/token/' + tokenId).success(function(response){
		if(response.status == 1001){
			$scope.info = response.body.token;
		}
		else
			$scope.info="获取token失败，请稍后重试";
	}).error(function(){
		$scope.info="获取token失败，请稍后重试";
	});
	
	$http.get('/park/token/usage/count/' + tokenId).success(function(response){
		if(response.status == 1001){
			$scope.showCount = true;
			$scope.tokenCount = response.body.count;
		}
		else
			$scope.tokenCount="获取tokenCount失败，请稍后重试";
	}).error(function(){
		$scope.tokenCount="获取tokenCount失败，请稍后重试";
	});
	
	$scope.submit = function(){
		$modalInstance.close('cancel');
	};
	
	$scope.close = function(){
		$modalInstance.close('cancel');
	};

});