angular.module("outsideParkStatusApp", ['ui.bootstrap']).controller("outsideParkStatusCtrl", function($scope, $http, $q, getDataService, getPositionData) {
    var dateInitial = function() {
        $('.date').val(new Date().format('yyyy-MM-dd'));
        $('.date').datepicker({
            autoClose : true,
            dateFormat : "yyyy-mm-dd",
            days : ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
            daysShort : ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            daysMin : ["日", "一", "二", "三", "四", "五", "六"],
            months : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthsShort : ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
            showMonthAfterYear : true,
            viewStart : 0,
            weekStart : 1,
            yearSuffix : "年",
            isDisabled : function(date) {
                return date.valueOf() > Date.now() ? true : false;
            }
        });
        $scope.date = $('#date').val();
    };

    $scope.getZoneCenter = function() {
        getPositionData.getZoneCenter().then(function(result) {
            $scope.zoneCenters = result;
        });
    };
    $scope.getZoneCenter();
    var selectParks = [];
    var getSelectData = function() {
        var options = $('#park-select').get(0).options;
        for (var i = 0; i < options.length; i++) {
            var item = {
                value : $(options[i]).val(),
                name : $(options[i]).text()
            };
             selectParks.push(item);
        };
        $scope.parks = selectParks;
    };
    getSelectData();
    $scope.getArea = function() {
        getPositionData.getArea($scope.zoneCenterId).then(function(result) {
            $scope.areas = result;
            if($scope.areas!=null&&$scope.areas.length==1){
                $scope.areaId=$scope.areas[0].id;
                $scope.getStreets();
            }
        });
    };
    
    var filtPark=function(orignParks){
        var parks=[];
        for (var i=0; i < orignParks.length; i++) {
         for (var j=0; j < selectParks.length; j++) {
           if(selectParks[j].value==orignParks[i].id){
              parks.push(selectParks[j]); 
           }          
         };
        };
        return parks;
    };
    
    var getpark = function(parkid) {
        var deferred = $q.defer();
        $http({
            url : '/park/getPark/' + parkid
        }).success(function(result) {
            deferred.resolve(result.body);
        });
        return deferred.promise;
    };
    $scope.getStreets = function() {
         if($scope.areaId==undefined||$scope.areaId==null){
            return;
        }
        getPositionData.getStreetByAreaId($scope.areaId).then(function(result) {
            $scope.streets = result;
            if ($scope.streets!=null&&$scope.streets.length==1) {
                $scope.streetid=$scope.streets[0].id;
                $scope.getParks($scope.streetid);
            };
        });
    };
    var getAreaById = function(areaid) {
        getPositionData.getAreaById(areaid).then(function(result) {
            var selectedArea = result;
            $scope.zoneCenterId = selectedArea.zoneid;
            $scope.getArea();
        });
    };
    var parkToZoneCenter = function() {
        $scope.parkid = selectParks[0].value;       
        if ($scope.parkid == undefined) {
            return;
        }
        getpark($scope.parkid).then(function(result) {
            $scope.streetid = result.streetId;
            getPositionData.getStreetById(result.streetId).then(function(result) {
                $scope.areaId = result.areaid;
                getAreaById($scope.areaId);
                $scope.getStreets();
            });
        });
    };
    parkToZoneCenter();
    var dateInitialparkcharge = function() {
        $('#parkMonth').val(new Date().format('yyyy-MM'));
        $('#parkMonth').datepicker({
            autoClose : true,
            dateFormat : "yyyy-mm",
            months : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthsShort : ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
            showMonthAfterYear : true,
            viewStart : 1,
            weekStart : 1,
            yearSuffix : "年",
            isDisabled : function(date) {
                return date.valueOf() > Date.now() ? true : false;
            }
        });
        $scope.monthDate = $('#parkMonth').val();
    };
    dateInitialparkcharge();
    dateInitial();

    $scope.getParks = function(streetId) {
        if (streetId==undefined||streetId==null) {
            return;
        };
        getPositionData.getOutsideParkByStreetId(streetId).then(function(result) {
            $scope.parks = filtPark(result);
            if($scope.parks!=undefined&&$scope.parks.length==1){
                $scope.parkid=$scope.parks[0].value;
             
                 $scope.selectPosdataByParkAndRange();
            }
        });
    };

    //作图
    var renderChart = function(category, seriesData) {
        var myChart = echarts.init(document.getElementById('chart_park_period_charge'));
        var option = {
            title : {
                text : '停车场每日应收/实收费用对比',
                subtext : '月单位'
            },
            tooltip : {
                trigger : 'axis'
            },
            legend : {
                data : ['应收费用', '实收费用']
            },
            toolbox : {
                show : true,
                feature : {
                    mark : {
                        show : true
                    },
                    saveAsImage : {
                        show : true
                    }
                }
            },
            calculable : true,
            xAxis : [{
                type : 'category',
                boundaryGap : false,
                data : category
            }],
            yAxis : [{
                type : 'value',
                axisLabel : {
                    formatter : '{value} 元'
                }
            }],
            series : seriesData,
        };
        myChart.setOption(option);
    };

    var chartData = function() {
        var series1 = {
            name : '应收费用',
            type : 'line',
            data : $scope.totalMoney
        };
        var series2 = {
            name : '实收费用',
            type : 'line',
            data : $scope.realMoney
        };
        renderChart($scope.catagory, [series1, series2]);
    };
    //根据日期范围获取停车场收费
    $scope.getParkChargeByRange = function() {
        var dateselect = $('#parkMonth').val();
        var dateStart = new Date(dateselect.substring(0, 7) + '-01');
        var dateEnd = dateStart;
        dateEnd.setMonth(dateStart.getMonth() + 1);

        
        getDataService.getParkChargeByRange($scope.parkid, dateselect.substring(0, 7) + '-01', dateEnd.getFullYear() + '-' + (dateEnd.getMonth()+1) + '-01').then(function(result) {
           $scope.catagory = [];
        $scope.totalMoney = [];
        $scope.realMoney = [];
            $.each(result, function(name, value) {
                var date = new Date(parseInt(name));
                var month = date.getMonth() + 1;
                var day = date.getDate();
                $scope.catagory.push(month + '-' + day);
                $scope.totalMoney.push(value['totalMoney']);
                //console.log($scope.totalMoney);

                $scope.realMoney.push(value['realMoney']);
                //console.log($scope.realMoney);

            });
            chartData();
        });
    };

    //获取posdata 并处理
    $scope.selectPosdataByParkAndRange = function() {
        if($scope.parkid==undefined||$scope.parkid==null){
            return;
        }
        $scope.getParkById();
        $scope.getParkChargeByRange();
        var date = $('#date').val();
        var dateInit = new Date(date);
        dateInit.setDate(dateInit.getDate() + 1);
        var dateEnd = dateInit.getFullYear() + "-" + (dateInit.getMonth() + 1) + "-" + dateInit.getDate();
        getDataService.selectPosdataByParkAndRange($scope.parkid, date, dateEnd).then(function(data) {
            console.log(data);

            $scope.dayChargeTotal = 0;
            $scope.dayRealReceiveMoney = 0;
            $scope.dayCarsCount = 0;
            $scope.carsInCount = 0;
            $scope.carsOutCount = 0;
            if (data.status == 1002) {
                getPosChargeDataByParkAndRange($scope.parkid, date, dateEnd);                               
                return;
            }
            data = data['body'];
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                if (data[i]['mode'] == 1) {
                    $scope.dayChargeTotal += data[i]['money'];
                    $scope.dayRealReceiveMoney = $scope.dayRealReceiveMoney + data[i]['giving'] + data[i]['realmoney'] - data[i]['returnmoney'];
                    $scope.carsOutCount++;
                }
                if (data[i]['mode'] == 0) {
                    $scope.dayCarsCount++;
                    $scope.carsInCount++;
                }
            }
        });
    };
   
    var getPosChargeDataByParkAndRange = function(parkid, startDay, endDay) {
        getDataService.getPosChargeDataByParkAndRange(parkid, startDay, endDay).then(function(data) {
            console.log(data);

            if (data.status == 1002) {
                return;
            }
            data = data['body'];
            $scope.dayChargeTotal = 0;
            $scope.dayRealReceiveMoney = 0;
            $scope.dayCarsCount = 0;
            $scope.carsInCount = 0;
            $scope.carsOutCount = 0;
            for (var i = 0; i < data.length; i++) {
                $scope.dayChargeTotal += data[i]['chargeMoney'];
                $scope.dayRealReceiveMoney = $scope.dayRealReceiveMoney + data[i]['chargeMoney'];
                $scope.carsInCount++;
                if (data[i]['exitDate'] != null) {
                    $scope.carsOutCount++;
                    $scope.dayCarsCount++;
                }
            }
        });
    };
    //获取停车场 得到停车位数据
    $scope.getParkById = function() {
        getDataService.getParkById($scope.parkid).then(function(result) {
            $scope.carportsCount = result.portCount;
            $scope.selectedParkName = result.name;
            $scope.selectedParkNum = result.id;
            $scope.principalName = result.contact;
            $scope.contactNumber = result.number;
        });
    };
    $scope.selectPosdataByParkAndRange();

}).service("getDataService", function($http, $q) {
    var getParkChargeByRange = function(parkId, startDay, endDay) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/pos/getParkChargeByRange',
            method : 'post',
            data : {
                'parkId' : parkId,
                'startDay' : startDay,
                'endDay' : endDay
            }
        }).success(function(response) {
            deferred.resolve(response);
        });
        return promise;
    };
    
     var getParkRecordsCountByRange = function(parkId, startDay, endDay) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/pos/getParkRecordsCountByRange',
            method : 'post',
            data : {
                'parkId' : parkId,
                'startDay' : startDay,
                'endDay' : endDay
            }
        }).success(function(response) {
            deferred.resolve(response);
        });
        return promise;
    };

    var selectPosdataByParkAndRange = function(parkId, startDay, endDay) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/pos/selectPosdataByParkAndRange',
            method : 'post',
            data : {
                'parkId' : parkId,
                'startDay' : startDay,
                'endDay' : endDay
            }
        }).success(function(response) {
            deferred.resolve(response);
        });
        return promise;
    };
    var getPosChargeDataByParkAndRange = function(parkId, startDay, endDay) {
        var deferred = $q.defer();
        $http({
            url : '/park/pos/charge/getByParkAndRange',
            method : 'post',
            data : {
                'parkId' : parkId,
                'startDay' : startDay,
                'endDay' : endDay
            }
        }).success(function(response) {
            deferred.resolve(response);
        });
        return deferred.promise;
    };
    var getParkById = function(id) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/getPark/' + id,
            method : 'get'
        }).success(function(response) {
            if (response.status = 1001) {
                deferred.resolve(response.body);
            };
        });
        return promise;
    };
    return {
        getParkChargeByRange : getParkChargeByRange,
        getParkRecordsCountByRange:getParkRecordsCountByRange,
        selectPosdataByParkAndRange : selectPosdataByParkAndRange,
        getParkById : getParkById,
        getPosChargeDataByParkAndRange : getPosChargeDataByParkAndRange
    };
}).factory("getPositionData", function($http, $q) {
    var getZoneCenter = function() {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/zoneCenter/getByStartAndCount",
            method : 'post',
            params : {
                start : 0,
                count : 100
            }
        }).success(function(response) {
            deferred.resolve(response.body);
        });
        return promise;
    };
    var getAreaById = function(areaid) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        if (!areaid) {
            return;
        }
        $http({
            url : '/park/area/selectByPrimaryKey/' + areaid,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getArea = function(zoneid) {
        if (!zoneid) {
            return;
        }
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : '/park/area/getByZoneCenterId/' + zoneid,
            method : 'get',
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getStreetByAreaId = function(areaid) {
       
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/street/getByAreaid/" + areaid,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getOutsideParkByStreetId = function(streetId) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/getOutsideParkByStreetId/" + streetId,
            method : 'get'
        }).success(function(response) {
            if (response.status == 1001) {
                deferred.resolve(response.body);
            }
        });
        return promise;
    };
    var getStreetById = function(streetId) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url : "/park/street/selectByPrimaryKey/" + streetId,
            method : 'get'
        }).success(function(response) {
            deferred.resolve(response.body);
        });
        return promise;
    };
    return {
        getZoneCenter : getZoneCenter,
        getArea : getArea,
        getStreetByAreaId : getStreetByAreaId,
        getAreaById : getAreaById,
        getOutsideParkByStreetId : getOutsideParkByStreetId,
        getStreetById : getStreetById
    };

});
