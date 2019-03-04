var pageApp = angular.module("pageApp", ['ui.bootstrap','tm.pagination']);

pageApp.controller("pageCtrl", ['$scope', '$http',  'textModal', '$modal', '$timeout', function($scope, $http, textModal,$uibModal, $timeout){
	
	$scope.page = {
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
         $scope.page.items  = $scope.currentData;
    };
    
	
	$scope.page.checked = function(index){
		if($scope.page.items[index].checked){
			$scope.page.checkedIndex = index;
			return;
		}
			
		for(var i = 0; i < $scope.page.items.length; i++){
			var item = $scope.page.items[i];
			if(item.checked != undefined && item.checked == true){
				$scope.page.checkedIndex = i;
				return;
			}
		}
		$scope.page.checkedIndex = -1;
			
	};
	
	$scope.page.remove = function(){
		if($scope.page.checkedIndex == -1)
			return;
		var id = $scope.page.items[$scope.page.checkedIndex].id;
		$http.get('page/delete/' + id)
		.success(function(response){
			if(response.status == 1001)
				textModal.open($scope, "成功","操作成功");
			else
				textModal.open($scope, "失败","操作失败");
			$scope.page.refresh();
		})
		.error(function(response){
			textModal.open($scope, "失败","操作失败");
		});
	};
	
	$scope.page.refresh = function(){
		$http.get('page/get')
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
	
	$scope.page.insert = function(){	
		$uibModal.open({
			templateUrl: '/park/views/template/ucNewPage.html',
			controller: 'modifyCrtl',
			scope:$scope,
			resolve: {
				index: function(){
						return undefined;
				}
			}
			
		});
	};
	
	$scope.page.update = function(){	
		if($scope.page.checkedIndex == -1)
			return;
		$uibModal.open({
			templateUrl: '/park/views/template/ucNewPage.html',
			controller: 'modifyCrtl',
			scope:$scope,
			resolve: {
				index: function(){
						return $scope.page.checkedIndex;
				}
			}
		});
	};

	$scope.page.refresh();
	
}]);

pageApp.controller('modifyCrtl',  function($scope, $modalInstance, $http, $timeout, index){
	
	var url = 'page/create';
	if(index != undefined){
		$scope.tempPage = $scope.$parent.page.items[index];
		url = 'page/update';
	}
	else{
		$scope.tempPage = {	
				id:0,
				uri:"",
				pageKey:"",
				description:""
		};
	}
	
	
	$scope.loading = false;
	$scope.submitted = false;
	$scope.result = "";
	
	
	$scope.submit = function(){
		$scope.loading = true;
		var args = {
				id:parseInt($scope.tempPage.id),
				uri:$scope.tempPage.uri,
				pageKey:$scope.tempPage.pageKey,
				description:$scope.tempPage.description
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
					$scope.$parent.page.refresh();
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


var page = angular.module('pageApp');

page.service('textModal',  ['$uibModal', function($uibModal){
	
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
pageApp.controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
	$scope.text = msg;
	
	$scope.close = function(){
		$uibModalInstance.close('cancel');
	};
});

