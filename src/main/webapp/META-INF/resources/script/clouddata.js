var cloudApp = angular.module("cloudDataApp", []);
cloudApp.controller("parkBigDataCtrl", ['$scope', '$interval', 'httpService',
  function ($scope, $interval, httpService) {
    var data = {low: 0, count: 10};
    $scope.items = {};
    var lastestId = 0;
    httpService.getPosData(data).then(function (response) {
      lastestId = response[0].id;
      angular.forEach(response, function (item) {
        item.isNew = false;
      });
      $scope.items = response;
    });
    $interval(function () {
      httpService.getPosData(data).then(function (response) {
        angular.forEach(response, function (item) {
          if (item.id > lastestId) {
            item.isNew = true;
          }
          else {
            item.isNew = false;
          }
        });
        $scope.items = response;
      });
    }, 2000);
    

    
  }]);
cloudApp.service("httpService", function ($http, $q) {

	
  return {
		getParkChargeByTime:function (startDate, endDate) {
		    var deferred = $q.defer();
		    $http({
		      url: '/park/pos/charge/getParkByCountMoney',
		      method: 'post',
		      data: {
		        'startDate': startDate,
		        'endDate': endDate
		      }
		    }).success(function (response) {
		      //console.log(response);
		      deferred.resolve(response);
		    });
		    return deferred.promise;
		  },
    getPosData: function (data) {
      var deferred = $q.defer();
      var promise = deferred.promise;
      $http({
        url: "/park/pos/charge/getByCount",
        method: "post",
        data: angular.toJson(data)
      }).success(function (response) {
        //console.log(response);
        if (response.status == 1001) {
          deferred.resolve(response.body);
        } else {
          deferred.reject(response);
        }
      }).error(function () {
        deferred.reject();
      });
      return promise;
    },
    getZoneCenterInfo: function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
          headers: {'token': 'f9e08257652deaa76de81c8225801482'},
          url: "/park/outsideParkInfo/zoneCenterInfo",
          method: 'post',
          params: {
            start: 0,
            count: 100
          }
        }).success(function (response) {
          //console.log(response);
          if (response.status == 1001) {
            deferred.resolve(response.body);
          }
        });
        return promise;
      },
      getPosData: function (data) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
          headers: {'token': 'f9e08257652deaa76de81c8225801482'},
          url: "/park/pos/charge/getByCount",
          method: "post",
          data: angular.toJson(data)
        }).success(function (response) {
          //console.log(response);
          if (response.status == 1001) {
            deferred.resolve(response.body);
          } else {
            deferred.reject(response);
          }
        }).error(function () {
          deferred.reject();
        });
        return promise;
      },
      getOnlineData: function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
          url: "/park/getParkStatusInfo",
          method: "get"
        }).success(function (response) {
          //console.log(response);
          if (response.status == 1001) {
            deferred.resolve(response.body);
          } else {
            deferred.reject(response);
          }
        }).error(function () {
          deferred.reject();
        });
        return promise;
      }
  };
});
cloudApp.controller("getdataCtrl", ["$scope", '$interval', "httpService", function ($scope, $interval, httpService) {
  $scope.processData = function (data) {
    $scope.dayCarportCount = 0;
    $scope.dayOnlineCarportCount = 0;
    $scope.dayCarportLeftCount = 0;
    angular.forEach(data, function (value) {
      $scope.dayCarportCount += value.portCount;
      $scope.dayOnlineCarportCount += value.onlinecarportcount;
      $scope.dayCarportLeftCount += value.carportleftcount;
    });
  };
  startDate = new Date().format('yyyy-MM-dd 00:00:00');
  endDate = new Date().format('yyyy-MM-dd hh:mm:ss');
  var getParkChargeByTime = function () {
  	httpService.getParkChargeByTime(startDate, endDate).then(function (result) {
        $scope.clouddata = result['body'];
      });
  };
  getParkChargeByTime();
  $scope.items = {};

  httpService.getPosData({low: 0, count: 10}).then(function (response) {
    $scope.items = response;
  });
  $interval(function () {
    httpService.getZoneCenterInfo().then(function (result) {
      $scope.processData(result);
    });
  }, 6000);
  var getOnlineData = function () {
    httpService.getOnlineData().then(function (result) {
      $scope.data = result;
      //console.log($scope.data);
    });
  };
  getOnlineData();

}]);
cloudApp.service("httpService2", ['$http', '$q', function ($http, $q) {
  return {

    getZoneCenterInfo: function () {
      var deferred = $q.defer();
      var promise = deferred.promise;
      $http({
        headers: {'token': 'f9e08257652deaa76de81c8225801482'},
        url: "/park/outsideParkInfo/zoneCenterInfo",
        method: 'post',
        params: {
          start: 0,
          count: 100
        }
      }).success(function (response) {
        //console.log(response);
        if (response.status == 1001) {
          deferred.resolve(response.body);
        }
      });
      return promise;
    },
    getPosData: function (data) {
      var deferred = $q.defer();
      var promise = deferred.promise;
      $http({
        headers: {'token': 'f9e08257652deaa76de81c8225801482'},
        url: "/park/pos/charge/getByCount",
        method: "post",
        data: angular.toJson(data)
      }).success(function (response) {
        //console.log(response);
        if (response.status == 1001) {
          deferred.resolve(response.body);
        } else {
          deferred.reject(response);
        }
      }).error(function () {
        deferred.reject();
      });
      return promise;
    },
    getOnlineData: function () {
      var deferred = $q.defer();
      var promise = deferred.promise;
      $http({
        url: "/park/getParkStatusInfo",
        method: "get"
      }).success(function (response) {
        //console.log(response);
        if (response.status == 1001) {
          deferred.resolve(response.body);
        } else {
          deferred.reject(response);
        }
      }).error(function () {
        deferred.reject();
      });
      return promise;
    }
  };
}]);
