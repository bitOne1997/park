var chargeApp = angular.module("feeCriterionApp", ['ui.bootstrap',
  'tm.pagination']);
chargeApp.controller("feeCriterionCtrl", ['$scope','$http','textModal','$modal','$timeout',
      function ($scope, $http, textModal, $uibModal, $timeout) {
        $scope.criterion = {
          items: [],
          checkedIndex: -1
        };
        $scope.criterion.checked = function (index) {
          if ($scope.criterion.items[index].checked) {
            $scope.criterion.checkedIndex = index;
            return;
          }
          for (var i = 0; i < $scope.criterion.items.length; i++) {
            var item = $scope.criterion.items[i];
            if (item.checked != undefined
              && item.checked == true) {
              $scope.criterion.checkedIndex = i;
              return;
            }
          }
          $scope.criterion.checkedIndex = -1;
        };
        $scope.criterion.remove = function () {
          if ($scope.criterion.checkedIndex == -1)
            return;
          var id = $scope.criterion.items[$scope.criterion.checkedIndex].id;
          $http
            .get('delete/' + id)
            .success(
              function (response) {
                if (response.status == 1001)
                  textModal.open($scope,
                    "成功", "操作成功");
                else
                  textModal.open($scope,
                    "失败", "操作失败");
                $scope.criterion
                  .refreshStaffPage();
              }).error(
            function (response) {
              textModal.open($scope,
                "失败", "操作失败");
            });
        };

        // $scope.criterion.refresh = function() {
        // $http.get('get').success(function(response) {
        // if (response.status == 1001)
        // $scope.criterion.items = response.body;
        // else
        // textModal.open($scope, "失败", "操作失败");
        // }).error(function(response) {
        // textModal.open($scope, "失败", "操作失败");
        // });
        // };
        $scope.criterion.search = function () {
          $http(
            {
              url: '/park/fee/criterion/searchByKeywords',
              method: "post",
              data: {
                'keywords': $scope.criterion.searchKeywords
              }
            }).success(function (response) {
            if (response.status == 1001) {
              $scope.criterion.items = response.body;
            } else {
            }
          }).error(function () {
          });
        };
        $scope.paginationConf = {
          currentPage: 1,
          totalItems: 800,
          itemsPerPage: 10,
          pagesLength: 10,
          perPageOptions: [10, 20, 30, 40, 50],
          rememberPerPage: 'perPageItems',
          onChange: function () {

          }
        };
        $scope.getCounts = function () {
          $http
            .get('getCount')
            .success(
              function (response) {
                //console.log(response);
                if (response.status == 1001) {
                  $scope.paginationConf.totalItems = response.body;
                }
              });
        };
        $scope.criterion.refreshStaffPage = function () {
          $scope.getCounts();
          var data = {
            start: $scope.paginationConf.currentPage,
            count: $scope.paginationConf.itemsPerPage
          };
          $http({
            url: '/park/fee/criterion/getByPage',
            method: 'post',
            data: angular.toJson(data)
          }).success(function (response) {
            //console.log(response);
            $scope.criterion.items = response.body;
          });
        };
        $scope.$watch('paginationConf', function () {
          $scope.criterion.refreshStaffPage();
        }, true);
        $scope.criterion.myKeyup = function (e) {
          var keycode = window.event ? e.keyCode
            : e.which;
          if (keycode == 13) {
            $scope.criterion.search();
          }
        };
        $scope.criterion.insert = function () {
          $uibModal.open({
            templateUrl: 'modifyFeeCriterion',
            controller: 'modifyCrtl',
            scope: $scope,
            resolve: {
              index: function () {
                return undefined;
              }
            }
          });
        };
        $scope.criterion.insertModel1 = function () {
          $uibModal.open({
            templateUrl: 'FeeCriterionModel1',
            controller: 'model1Crtl',
            scope: $scope,
            resolve: {
              index: function () {
                return undefined;
              }
            }
          });
        };
        $scope.criterion.insertModel2 = function () {
          $uibModal.open({
            templateUrl: 'FeeCriterionModel2',
            controller: 'model2Crtl',
            scope: $scope,
            resolve: {
              index: function () {
                return undefined;
              }
            }
          });
        };
        $scope.criterion.insertModel4 = function () {
          $uibModal.open({
            templateUrl: 'FeeCriterionModel4',
            controller: 'model4Crtl',
            scope: $scope,
            resolve: {
              index: function () {
                return undefined;
              }
            }
          });
        };
        $scope.criterion.update = function () {
          if ($scope.criterion.checkedIndex == -1)
            return;
          var templateurl = "modifyFeeCriterion";
          if ($scope.criterion.items[$scope.criterion.checkedIndex].type == 1) {
            templateurl = "FeeCriterionModel1";
          }
          if ($scope.criterion.items[$scope.criterion.checkedIndex].type == 2) {
            templateurl = "FeeCriterionModel2";
          }
          $uibModal
            .open({
              templateUrl: templateurl,
              controller: 'modifyCrtl',
              scope: $scope,
              resolve: {
                index: function () {
                  return $scope.criterion.checkedIndex;
                }
              }
            });
        };

        $scope.criterion.refreshStaffPage();

      }]);

chargeApp.controller('modifyCrtl', function ($scope, $modalInstance, $http,
                                             $timeout, index) {

  var url = 'insert';
  $scope.tempCriterion = {};
  $scope.onetimeExpense = false;
  if (index != undefined) {
    $scope.tempCriterion = $scope.$parent.criterion.items[index];
    if ($scope.tempCriterion.isonetimeexpense == 1) {
      $scope.onetimeExpense = true;
    }
    url = 'modify';
  } else {
    $scope.tempCriterion.maxexpense = 9999;
    $scope.tempCriterion.nightstarttime = '24:00';
    $scope.tempCriterion.nightendtime = '00:00';
  }

  $scope.loading = false;
  $scope.submitted = false;
  $scope.result = "";

  $scope.submit = function () {
    $scope.loading = true;
    delete $scope.tempCriterion['checked'];
    $http.post(url, $scope.tempCriterion).success(function (response) {
      $scope.loading = false;
      $scope.submitted = true;
      if (response.status == 1001) {
        $scope.result = "成功";
        $timeout(function () {
          $scope.result = "";
          $modalInstance.close('ok');
          $scope.$parent.criterion.refresh();
        }, 2000);
      } else {
        $scope.result = "失败:" + response.status;

      }
    }).error(function () {
      $scope.loading = false;
      $scope.submitted = true;
      $scope.result = "失败";
      // $modalInstance.close('faled');
    });
  };

  $scope.close = function () {
    $modalInstance.close('cancel');
  };

});
chargeApp.controller('model4Crtl', function ($scope, $modalInstance, $http,
                                             $timeout, index) {

  var url = 'insert';
  $scope.tempCriterion = {};
  $scope.onetimeExpense = false;
  if (index != undefined) {
    $scope.tempCriterion = $scope.$parent.criterion.items[index];
    if ($scope.tempCriterion.isonetimeexpense == 1) {
      $scope.onetimeExpense = true;
    }
    url = 'modify';
  } else {
    $scope.tempCriterion.maxexpense = 9999;
    $scope.tempCriterion.nightstarttime = '24:00';
    $scope.tempCriterion.nightendtime = '00:00';
  }

  $scope.loading = false;
  $scope.submitted = false;
  $scope.result = "";

  $scope.submit = function () {
    $scope.loading = true;
    delete $scope.tempCriterion['checked'];
    $scope.tempCriterion.type = 4;
    $http.post(url, $scope.tempCriterion).success(function (response) {
      $scope.loading = false;
      $scope.submitted = true;
      if (response.status == 1001) {
        $scope.result = "成功";
        $timeout(function () {
          $scope.result = "";
          $modalInstance.close('ok');
          $scope.$parent.criterion.refresh();
        }, 2000);
      } else {
        $scope.result = "失败:" + response.status;

      }
    }).error(function () {
      $scope.loading = false;
      $scope.submitted = true;
      $scope.result = "失败";
      // $modalInstance.close('faled');
    });
  };

  $scope.close = function () {
    $modalInstance.close('cancel');
  };

});
chargeApp.controller('model1Crtl', function ($scope, $modalInstance, $http,
                                             $timeout, index) {

  var url = 'insert';
  $scope.tempCriterion = {};
  $scope.onetimeExpense = false;
  if (index != undefined) {
    $scope.tempCriterion = $scope.$parent.criterion.items[index];
    if ($scope.tempCriterion.isonetimeexpense == 1) {
      $scope.onetimeExpense = true;
    }
    url = 'modify';
  } else {
    $scope.tempCriterion.maxexpense = 9999;
    $scope.tempCriterion.nightstarttime = '24:00';
    $scope.tempCriterion.nightendtime = '00:00';
    $scope.tempCriterion.step1capacity = 1440;
    $scope.tempCriterion.step2capacity = 1440;
    $scope.tempCriterion.timeoutpriceinterval = 1440;
    $scope.tempCriterion.timeoutpriceinterval2 = 1440;
    $scope.tempCriterion.step2price = 0;
    $scope.tempCriterion.step2pricelarge = 0;
    $scope.tempCriterion.type = 1;
  }
  $scope.loading = false;
  $scope.submitted = false;
  $scope.result = "";

  $scope.submit = function () {
    $scope.loading = true;
    delete $scope.tempCriterion['checked'];
    $http.post(url, $scope.tempCriterion).success(function (response) {
      $scope.loading = false;
      $scope.submitted = true;
      if (response.status == 1001) {
        $scope.result = "成功";
        $timeout(function () {
          $scope.result = "";
          $modalInstance.close('ok');
          $scope.$parent.criterion.refresh();
        }, 2000);
      } else {
        $scope.result = "失败:" + response.status;

      }
    }).error(function () {
      $scope.loading = false;
      $scope.submitted = true;
      $scope.result = "失败";
      // $modalInstance.close('faled');
    });
  };

  $scope.close = function () {
    $modalInstance.close('cancel');
  };

});
chargeApp.controller('model2Crtl', function ($scope, $modalInstance, $http,
                                             $timeout, index) {
  var url = 'insert';
  $scope.tempCriterion = {};
  $scope.onetimeExpense = false;
  if (index != undefined) {
    $scope.tempCriterion = $scope.$parent.criterion.items[index];
    if ($scope.tempCriterion.isonetimeexpense == 1) {
      $scope.onetimeExpense = true;
    }
    url = 'modify';
  } else {
    $scope.tempCriterion.maxexpense = 9999;
    $scope.tempCriterion.nightstarttime = '24:00';
    $scope.tempCriterion.nightendtime = '00:00';
    $scope.tempCriterion.type = 2;

  }

  $scope.loading = false;
  $scope.submitted = false;
  $scope.result = "";

  $scope.submit = function () {
    $scope.loading = true;
    delete $scope.tempCriterion['checked'];
    $http.post(url, $scope.tempCriterion).success(function (response) {
      $scope.loading = false;
      $scope.submitted = true;
      if (response.status == 1001) {
        $scope.result = "成功";
        $timeout(function () {
          $scope.result = "";
          $modalInstance.close('ok');
          $scope.$parent.criterion.refresh();
        }, 2000);
      } else {
        $scope.result = "失败:" + response.status;

      }
    }).error(function () {
      $scope.loading = false;
      $scope.submitted = true;
      $scope.result = "失败";
      // $modalInstance.close('faled');
    });
  };

  $scope.close = function () {
    $modalInstance.close('cancel');
  };

});

var feeCriterion = angular.module('feeCriterionApp');

feeCriterion.service('textModal', ['$uibModal', function ($uibModal) {

  this.open = function ($scope, header, body) {
    $scope.textShowModal = $uibModal.open({
      templateUrl: '/park/views/template/text-modal.html',
      controller: 'textCtrl',
      scope: $scope,
      resolve: {
        msg: function () {
          return {
            header: header,
            body: body
          };
        }
      }
    });
  };

  this.close = function ($scope, header, body) {
    $scope.textShowModal.close('cancel');
  };

}]);

// show message dilog controller
feeCriterion.controller('textCtrl', function ($scope, $uibModalInstance, $http,
                                              msg) {
  $scope.text = msg;

  $scope.close = function () {
    $uibModalInstance.close('cancel');
  };
});
