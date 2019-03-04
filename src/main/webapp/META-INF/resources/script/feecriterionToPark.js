angular.module("feecriterionToParkApp", ['ui.bootstrap']).
controller("feecriterionToParkCtrl", ['$scope', '$http', '$modal', 'textModal', '$timeout',
function($scope, $http, $uibModal, textModal, $timeout) {
    $scope.feeCritionToParks = [];
    $scope.selectedIndex = -1;
    $scope.parkId = -1;
    $scope.parks = [];
    $scope.selectParkName="";
    $scope.getParks = function() {
        $http({
            url : '/park/getParks?_t=' + (new Date()).getTime(),
            method : 'get'
        }).success(function(response) {
            if (response.status = 1001) {
                var body = response.body;
                for (var i = 0; i < body.length; i++) {
                    if (body[i].type == 3) {
                        $scope.parks.push(body[i]);
                    }
                }
                $scope.parkId = $scope.parks[0].id;
                $scope.selectParkName=$scope.parks[0].name;
                $scope.refreshFeecriterionToPark();
            }
        });
    };
   
 $scope.feecriterionToParks=[];
   
    $scope.parkselectChange = function(parkid) {
          $scope.feeCritionToParks = [];
        $scope.refreshFeecriterionToPark();
        angular.forEach($scope.parks,function(item){
            if(item.id==$scope.parkId){
                $scope.selectParkName=item.name;
                return;
            }
        });
    };
    $scope.refreshFeecriterionToPark = function() {
        $scope.feeCritionToParks = [];
        
        $http({
            url : '/park/feecriterionToPark/getByPark',
            method : 'post',
            data : {
                parkId :$scope.parkId
            }
        }).success(function(response) {
            if (response.status == 1001) {
               $scope.feeCritionToParks=response.body;
            } else {
                textModal.open($scope, "错误", "数据请求失败");
            }
        }).error(function() {
            textModal.open($scope, "错误", "数据请求失败");
        });
    };
    $scope.insertFeecriterionToPark = function() {
        $uibModal.open({
            templateUrl : 'feecriterionToParkModel',
            controller : 'feecriterionToParkInsert',
            scope : $scope,
            resolve : {
                index : function() {
                    return undefined;
                }
            }
        });
    };
    $scope.updateFeecriterionToPark = function() {
        if ($scope.checkedIndex == -1) {
            alert("请选择");
            return;
        }
        $uibModal.open({
            templateUrl : 'feecriterionToParkModel',
            controller : 'feecriterionToParkUpdate',
            scope : $scope,
            resolve : {
                index : function() {
                    return $scope.checkedIndex;
                }
            }
        });
    };
    $scope.deleteFeecriterionToPark = function() {
        if ($scope.checkedIndex == -1) {
            alert("请选择");
            return;
        }
        var id = $scope.feeCritionToParks[$scope.checkedIndex].id;
        $http({
            url : '/park/feecriterionToPark/delete/' + id,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                textModal.open($scope, "成功", "操作成功");
                $scope.refreshFeecriterionToPark();
            } else {
                textModal.open($scope, "失败", "操作失败");
            }
        }).error(function() {
            textModal.open($scope, "失败", "操作失败");
        });
    };
        $scope.refreshStaffPage=function(){
            var data={start:1,count:1000};
             $http({
                url:'/park/fee/criterion/getByPage',
                method:'post',
                data:angular.toJson(data)
             }).success(function(response){
                  $scope.feecriterions= response.body;
             });
        };
    $scope.refreshStaffPage();
    $scope.selectChange = function(index) {

        if ($scope.feeCritionToParks[index].checked) {
            $scope.feeCritionToParks[index].checked = false;
        } else {
            $scope.feeCritionToParks[index].checked = true;
        }
        if ($scope.feeCritionToParks[index].checked) {
            $scope.checkedIndex = index;
            return;
        }
        for (var i = 0; i < $scope.feeCritionToParks.length; i++) {
            var item = $scope.feeCritionToParks[i];
            if (item.checked != undefined && item.checked == true) {
                $scope.checkedIndex = i;
                return;
            }
        }
        $scope.checkedIndex = -1;
    };
    $scope.getParks();
}]).
controller("feecriterionToParkUpdate", function($scope, textModal, $modalInstance, $http, $timeout, index) {
    var url = '/park/feecriterionToPark/update';
  $scope.feecriterions=$scope.$parent.feecriterions;
    $scope.tmpfeecriterionToPark = {};
    $scope.tmpfeecriterionToPark.criterionid=$scope.$parent.feeCritionToParks[index].criterionId;
    $scope.tmpfeecriterionToPark.other=$scope.$parent.feeCritionToParks[index].other;
    $scope.tmpfeecriterionToPark.cartype=$scope.$parent.feeCritionToParks[index].carType;
    $scope.tmpfeecriterionToPark.id=$scope.$parent.feeCritionToParks[index].id;
    $scope.selectParkName=$scope.$parent.selectParkName;
    $scope.carTypes=[{id:0,name:"包月车"},{id:9,name:"临停车"},{id:8,name:"包月过期"},{id:1,name:"包月类型A"},{id:2,name:"包月类型B"},{id:3,name:"包月类型C"},{id:4,name:"包月类型D"}];
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result = "";
    $scope.submit = function() {
        $scope.loading = true;
        $scope.submitted = false;
        delete $scope.tmpfeecriterionToPark['checked'];
        delete $scope.tmpfeecriterionToPark['name'];
        $http({
            url : url,
            method : 'post',
            data : $scope.tmpfeecriterionToPark,
        }).success(function(response) {
            if (response.status == 1001) {
                $scope.loading = false;
                $scope.submitted = true;
                $scope.result = "成功";
                $timeout(function() {
                    $scope.result = "";

                    $modalInstance.close('ok');
                }, 2000);

                $scope.$parent.refreshFeecriterionToPark();

            } else {
                 $scope.submitted = true;
                 $scope.result = "失败";
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
controller("feecriterionToParkInsert", function($scope, textModal, $modalInstance, $http, $timeout, index) {
    var url = '/park/feecriterionToPark/insert';
    $scope.feecriterions=$scope.$parent.feecriterions;
    $scope.selectParkName=$scope.$parent.selectParkName;
    $scope.tmpfeecriterionToPark = {};
    $scope.loading = false;
    $scope.submitted = false;
    $scope.result = "";
    $scope.carTypes=[{id:0,name:"包月车"},{id:9,name:"临停车"},{id:8,name:"包月过期"},{id:1,name:"包月类型A"},{id:2,name:"包月类型B"},{id:3,name:"包月类型C"},{id:4,name:"包月类型D"}];

    $scope.submit = function() {
        $scope.tmpfeecriterionToPark.parkid = $scope.$parent.parkId;
     
        $scope.loading = true;
        $scope.submitted = false;

        $http({
            url : url,
            method : 'post',
            data : $scope.tmpfeecriterionToPark,
        }).success(function(response) {
            if (response.status == 1001) {
                $scope.loading = false;
                $scope.submitted = true;
                $scope.result = "成功";
                $timeout(function() {
                    $scope.result = "";

                    $modalInstance.close('ok');
                }, 2000);

                $scope.$parent.refreshFeecriterionToPark();

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

