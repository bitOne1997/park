var app = angular.module("presentDataApp", []);
app.controller("presentDataCtrl", ['$scope', 'getDataService', function ($scope, getDataService) {
    //$scope.processData = function (data) {
    //    $scope.dayAmountMoney = 0;
    //    $scope.dayRealMoney = 0;
    //    $scope.dayOutCount = 0;
    //    $scope.dayParkCount = 0;
    //    $scope.dayCarportCount = 0;
    //    $scope.dayOnlineCarportCount = 0;
    //    $scope.dayCarportLeftCount = 0;
    //    angular.forEach(data, function (value) {
    //        $scope.dayAmountMoney += value.amountmoney;
    //        $scope.dayRealMoney += value.realmoney;
    //        $scope.dayParkCount += value.parkcount;
    //        $scope.dayCarportCount += value.carportcount;
    //        $scope.dayOnlineCarportCount += value.onlinecarportcount;
    //        $scope.dayCarportLeftCount += value.carportleftcount;
    //        $scope.dayOutCount += value.outcount;
    //        $scope.dayAmountMoney = parseInt($scope.dayAmountMoney);
    //    });
    //};

    getDataService.getZoneCenterInfo().then(function (result) {
        $scope.zoneCenters = result;
        //if($scope.zoneCenter.chargeMoney=='null'||$scope.zoneCenter.chargeMoney=='undefiend'){
        //    $scope.zoneCenter.chargeMoney=0;
        //}
        //console.log($scope.dayOnlineCarportCount + "  " + $scope.dayOutCount);
    });
    startDate = new Date().format('yyyy-MM-dd 00:00:00');
    //console.log(startDate);
    endDate = new Date().format('yyyy-MM-dd hh:mm:ss');
    //console.log(endDate);
    /*根据时分秒获得金额统计*/
    var getParkChargeByTime = function () {
        getDataService.getParkChargeByTime(startDate, endDate).then(function (result) {
            $scope.data = result['body'];
            $scope.cashAmount = ($scope.data.cashAmount + $scope.data.cashAmount2).toFixed(2);
            $scope.cashCount = $scope.data.cashCount + $scope.data.cashCount2;

            //console.log($scope.data);
            var fakeData = {
                'status': 0,
                'data': [{
                    'value': $scope.data.wechartAmount,
                    'name': '微信'
                }, {
                    'value': $scope.data.alipayAmount,
                    'name': '支付宝'
                }, {
                    'value': $scope.cashAmount,
                    'name': '现金'
                }, {
                    'value': $scope.data.appAmount,
                    'name': 'APP'
                }]
            };
            $scope.edata = fakeData.data;
        });
    };
    getParkChargeByTime();

    var getOnlineData = function () {
        getDataService.getOnlineData().then(function (result) {
            $scope.parkCountdata = result;
        });
    };
    getOnlineData();

}]);
app.service('getDataService', ['$http', '$q', function ($http, $q) {
    var getParkChargeByTime = function (startDate, endDate) {
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
    };
    var getZoneCenterInfo = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url: "/park/outsideParkInfo/zoneCenterInfo",
            method: 'post',
            params: {
                start: 0,
                count: 100
            }
        }).success(function (response) {
            if (response.status == 1001) {
                //console.log(response);
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getOnlineData = function () {
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
    return {
        getZoneCenterInfo: getZoneCenterInfo,
        getParkChargeByTime: getParkChargeByTime,
        getOnlineData: getOnlineData
    };
}]);
app.directive('pieChart', function ($window) {
    //定义饼图指令
    return {
        restrict: 'A',
        link: function ($scope, element, attrs) {
            //attrs是DOM元素的属性集合
            var myChart = echarts.init(element[0]);
            $scope.$watch(attrs.eData, function (newValue, oldValue, scope) {
                var legend = [];
                angular.forEach(function (val) {
                    legend.push(val.name);
                });
                var option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left', // 'center' | 'left' | {number},
                        y: '35%', // 'center' | 'bottom' | {number}
                        //left: '20%',
                        data: newValue
                    },

                    color: ['#6abe83', '#5291b5', '#FF9900', '#979798', '#e56056', '#a83279'],
                    series: [{
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['60%', '60%'],
                        data: newValue,
                        label: {
                            normal: {
                                textStyle: {
                                    fontWeight: 'bolder',
                                    fontSize:15
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                lineStyle: {
                                    width: 3
                                },
                                smooth: 0.2,
                                length: 10,
                                length2: 20
                            }
                        },
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }]
                };
                myChart.setOption(option);
            }, true);
            window.addEventListener("resize", function () {
                //这里使用$window.onresize方法会使前面的图表无法调整大小
                myChart.resize();
            });
        }
    };
});