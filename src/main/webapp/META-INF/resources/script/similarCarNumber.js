angular.module("similarCarNumberApp", ['ui.bootstrap', 'tm.pagination']).controller("similarCarNumberCtrl", ['$scope', '$http', '$modal', 'textModal', '$timeout',
function($scope, $http, $uibModal, textModal, $timeout) {
    $scope.similarCarNumbers = [];
    $scope.selectedIndex = -1;
    $scope.selectValue = -1;
    $scope.parks = [];

    $scope.getParks = function() {
        $http({
            url : 'getParks?_t=' + (new Date()).getTime(),
            method : 'get'
        }).success(function(response) {
            if (response.status = 1001) {
                var body = response.body;
                for (var i = 0; i < body.length; i++) {
                    if (body[i].type == 3) {
                        $scope.parks.push(body[i]);
                    }
                }
                $scope.selectValue = $scope.parks[0];
                $scope.refreshSimilarCarNumber();
            }
        });
    };
    $scope.start = 0;
    $scope.count = 800;
    $scope.paginationConf = {
        currentPage : 1,
        totalItems : 500,
        itemsPerPage : 10,
        pagesLength : 10,
        perPageOptions : [10, 20, 30, 40, 50],
        rememberPerPage : 'perPageItems',
        onChange : function() {
            getInitail($scope.pagedata);
        }
    };
    $scope.pagedata = [];
    $scope.currentData = [];
    var getInitail = function(data) {
        $scope.pagedata = data;
        $scope.paginationConf.totalItems = data.length;
        $scope.currentData = [];
        var start = ($scope.paginationConf.currentPage - 1) * $scope.paginationConf.itemsPerPage;
        for (var i = 0; i < $scope.paginationConf.itemsPerPage; i++) {
            if (data.length > (start + i))
                $scope.currentData[i] = data[start + i];
        };
        $scope.similarCarNumbers = $scope.currentData;
    };
    $scope.parkselectChange = function() {
        $scope.refreshSimilarCarNumber();
    };
    $scope.refreshSimilarCarNumber = function() {
        $scope.similarCarNumbers = [];
        $http({
            url : '/park/similarcarnumber/getByPark',
            method : 'post',
            data : {
                start : $scope.start,
                count : $scope.count,
                parkId : $scope.selectValue.id
            }
        }).success(function(response) {
            if (response.status == 1001) {
                getInitail(response.body);
            } else {
                textModal.open($scope, "错误", "数据请求失败");
            }
        }).error(function() {
            textModal.open($scope, "错误", "数据请求失败");
        });
    };
    $scope.insertSimilarCarNumber = function() {
        $uibModal.open({
            templateUrl : 'similarCarNumberModel',
            controller : 'similarCarNumberInsert',
            scope : $scope,
            resolve : {
                index : function() {
                    return undefined;
                }
            }
        });
    };
    $scope.updateSimilarCarNumber = function() {
        if ($scope.checkedIndex == -1) {
            alert("请选择");
            return;
        }
        $uibModal.open({
            templateUrl : 'similarCarNumberModel',
            controller : 'similarCarNumberUpdate',
            scope : $scope,
            resolve : {
                index : function() {
                    return $scope.checkedIndex;
                }
            }
        });
    };
    $scope.deleteSimilarCarNumber = function() {
        if ($scope.checkedIndex == -1) {
            alert("请选择");
            return;
        }
        var id = $scope.similarCarNumbers[$scope.checkedIndex].id;
        $http({
            url : '/park/similarcarnumber/delete/' + id,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                textModal.open($scope, "成功", "操作成功");
                $scope.refreshSimilarCarNumber();
            } else {
                textModal.open($scope, "失败", "操作失败");
            }
        }).error(function() {
            textModal.open($scope, "失败", "操作失败");
        });
    };
    $scope.selectChange = function(index) {

        if ($scope.similarCarNumbers[index].checked) {
            $scope.similarCarNumbers[index].checked = false;
        } else {
            $scope.similarCarNumbers[index].checked = true;
        }
        if ($scope.similarCarNumbers[index].checked) {
            $scope.checkedIndex = index;
            return;
        }
        for (var i = 0; i < $scope.similarCarNumbers.length; i++) {
            var item = $scope.similarCarNumbers[i];
            if (item.checked != undefined && item.checked == true) {
                $scope.checkedIndex = i;
                return;
            }
        }
        $scope.checkedIndex = -1;
    };
    $scope.getParks();
}]).controller("similarCarNumberUpdate", function($scope, textModal, $modalInstance, $http, $timeout, index) {
    var url = '/park/similarcarnumber/update';
    $scope.parks = $scope.$parent.parks;
    $scope.tmpSimilarCarNumber = $scope.$parent.similarCarNumbers[index];
    $scope.selectPark = $scope.tmpSimilarCarNumber.parkid;
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result = "";
    $scope.submit = function() {
        $scope.loading = true;
        $scope.submitted = false;
        delete $scope.tmpSimilarCarNumber['checked'];
        $http({
            url : url,
            method : 'post',
            data : $scope.tmpSimilarCarNumber,
        }).success(function(response) {
            if (response.status == 1001) {
                $scope.loading = false;
                $scope.submitted = true;
                $scope.result = "成功";
                $timeout(function() {
                    $scope.result = "";

                    $modalInstance.close('ok');
                }, 2000);

                $scope.$parent.refreshSimilarCarNumber();

            } else {
                textModal.open($scope, "失败", "操作失败");
            }
        }).error(function() {
            textModal.open($scope, "失败", "操作失败");
        });
    };
    $scope.close = function() {
        $modalInstance.close('cancel');
    };

}).controller("similarCarNumberInsert", function($scope, textModal, $modalInstance, $http, $timeout, index) {
    var url = '/park/similarcarnumber/insert';
    $scope.parks = $scope.$parent.parks;
    $scope.tmpSimilarCarNumber = {};
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result = "";
    $scope.selectPark=$scope.$parent.selectValue.id;
    $scope.submit = function() {
        $scope.tmpSimilarCarNumber.parkid = $scope.selectPark;
        $scope.loading = true;
        $scope.submitted = false;

        $http({
            url : url,
            method : 'post',
            data : $scope.tmpSimilarCarNumber,
        }).success(function(response) {
            if (response.status == 1001) {
                $scope.loading = false;
                $scope.submitted = true;
                $scope.result = "成功";
                $timeout(function() {
                    $scope.result = "";

                    $modalInstance.close('ok');
                }, 2000);

                $scope.$parent.refreshSimilarCarNumber();

            } else {
                textModal.open($scope, "失败", "操作失败");
            }
        }).error(function() {
            textModal.open($scope, "失败", "操作失败");
        });
    };
    $scope.close = function() {
        $modalInstance.close('cancel');
    };

}).
service('textModal', ['$uibModal',
function($uibModal) {
    this.open = function($scope, header, body) {
        $scope.textShowModal = $uibModal.open({
            templateUrl : '/park/views/template/text-modal.html',
            controller : 'textCtrl',
            scope : $scope,
            resolve : {
                msg : function() {
                    return {
                        header : header,
                        body : body
                    };
                }
            }
        });
    };
    this.close = function($scope, header, body) {
        $scope.textShowModal.close('cancel');
    };
}]).controller('textCtrl',  function($scope, $uibModalInstance, $http, msg){
    $scope.text = msg;
    
    $scope.close = function(){
        $uibModalInstance.close('cancel');
    };
});


