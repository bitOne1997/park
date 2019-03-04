var chargeApp = angular.module("feeDetailApp", ['ui.bootstrap','tm.pagination']);

chargeApp.controller("feeDetailCtrl", ['$scope', '$http', '$window','textModal', 'textModalTest','$modal', '$timeout',
function($scope, $http,$window, textModal,textModalTest, $uibModal, $timeout) {

    //define table content
    $scope.detail = {
        items : [],
        loading : false,
        page : {
            hidden : true,
            allCounts : 0,
            size : 100,
            indexRange : [1],
            index : 1
        }
    };
 $scope.searchDate=new Date().format('yyyy-MM-dd');
 $scope.startDate=new Date().format('yyyy-MM-dd 00:00:00');
 $scope.endDate=new Date().format('yyyy-MM-dd 23:59:59');
      var dateInitial=function(){
        /*$('.date').datepicker({
            autoClose: true,
            dateFormat: "yyyy-mm-dd",
            days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
            daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            daysMin: ["日", "一", "二", "三", "四", "五", "六"],
            months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
            showMonthAfterYear: true,
            viewStart: 0,
            weekStart: 1,
            yearSuffix: "年",
            isDisabled: function(date){return date.valueOf() > Date.now() ? true : false;}        
        });*/
    	  
    	$('#date').datetimepicker({
			format: 'YYYY-MM-DD',
			locale: moment.locale('zh-cn')
		});
    	$('#date2').datetimepicker({
  		   format: 'YYYY-MM-DD HH:mm:ss',
  		   locale: moment.locale('zh-cn')
  	 	});
   	   	$('#date3').datetimepicker({
  		   format: 'YYYY-MM-DD HH:mm:ss',
  		   locale: moment.locale('zh-cn')
  	 	});
    };  
   dateInitial();

     $scope.paginationConf = {
        currentPage : 1,
        totalItems : 500,
        itemsPerPage : 10,
        pagesLength : 10,
        perPageOptions : [10, 30, 40, 50],
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
         $scope.detail.items = $scope.currentData;
    };

    $scope.detail.getCount = function() {
        $http.get('count').success(function(response) {
            if (response.status == 1001) {
                $scope.detail.page.hidden = false;
                $scope.detail.page.allCounts = response.body;
                var maxIndex = Math.ceil($scope.detail.page.allCounts / $scope.detail.page.size);
                $scope.detail.page.indexRange = [1];
                for (var i = 2; i <= maxIndex; i++)
                    $scope.detail.page.indexRange.push(i);

            } else
                textModal.open($scope, "错误", "获取计费错误: " + response.status);
        }).error(function(response) {
            textModal.open($scope, "错误", "获取计费错误: " + response.status);

        });
    };

    $scope.selectedPark={};
    $scope.selectParks = [];
    var getSelectData = function() {
        var options = $('#get_Park').get(0).options;
        //console.log("options-----"+options);
        for (var i = 0; i < options.length; i++) {
            var item = {
                value : $(options[i]).val(),
                name : $(options[i]).text()
            };
             $scope.selectParks.push(item);
        };
        $scope.selectedPark=$scope.selectParks[0];
        //console.log("$scope.selectedPark-----"+$scope.selectedPark);
    };
    getSelectData();
    
     $scope.$watch("selectedPark",function(){
        $scope.detail.getPage();
    },true);
    //previous page
    $scope.detail.previousPage = function() {
        if ($scope.detail.page.index <= 1)
            return;
        $scope.detail.page.index--;
        $scope.detail.getPage();
    };
    $scope.searchText="";
    $scope.searchByCardnumber=function(){
        if($scope.searchText==""||$scope.searchText==undefined){
            return;
        }
        $http({
            url:'getByCardnumberAuthority',
            method:'post',
            data:{"cardNumber":$scope.searchText}
        }).success(function(response){
            if(response.status==1001){
                getInitail(response.body);
            }
        });
    };
    $scope.getExcelByDay=function(){  
         $window.location.href="getExcelByDay?date="+$scope.searchDate;
        };
    $scope.getExcelByDayRange=function(){  
         $window.location.href="getExcelByDayRange?startDate="+$scope.startDate+"&endDate="+$scope.endDate;
        };
    $scope.getExcelByParkAndDay=function(){
         $window.location.href="getExcelByParkAndDay?date="+$("#date").val()+"&parkId="+$('#park-select').val();
     };
     $scope.getExcelByParkAndDayRange=function(){
         $window.location.href="getExcelByParkAndDayRange?startDate="+$("#date2").val()+"&endDate="+$("#date3").val()
         +"&parkId="+$('#park-select2').val();
     };
     $scope.searchByParkName=function(){
        if($scope.searchParkNameText==""||$scope.searchParkNameText==undefined){
            return;
        }
        $http({
            url:'getByParkName',
            method:'post',
            data:{"parkName":$scope.searchParkNameText}
        }).success(function(response){
            if(response.status==1001){
               getInitail(response.body);
            }
        });
    };
    //first page
    $scope.detail.firstPage = function() {
        if ($scope.detail.page.index <= 1)
            return;
        $scope.detail.page.index = 1;
        $scope.detail.getPage();
    };

    //next page
    $scope.detail.nextPage = function() {
        if ($scope.detail.page.index >= $scope.detail.page.indexRange.length)
            return;
        $scope.detail.page.index++;
        $scope.detail.getPage();
    };

    //last page
    $scope.detail.lastPage = function() {
        if ($scope.detail.page.index >= $scope.detail.page.indexRange.length)
            return;
        $scope.detail.page.index = $scope.detail.page.indexRange.length;
        $scope.detail.getPage();
    };

    //get one page detail
    $scope.detail.getPage = function() {
        if ($scope.detail.page.index > $scope.detail.page.indexRange.length) {
            textModal.open($scope, "错误", "当前页不存在!");
            return;
        }
        $scope.detail.loading = true;

        var pageArgs = {
            page : {
                index : $scope.detail.page.index,
                size : $scope.detail.page.size
            },
            property : ["id", "name", "sex", "phone", "createTime"],
            order : {
                createTime : true
            }
        };

          $http.post('pageByParkId', {
            start : 0,
            count : 1000,
            parkId: parseInt($scope.selectedPark.value)
        }).success(function(response) {
            $scope.detail.loading = false;

            if (response.status == 1001) {
                $scope.detail.items = [];
                getInitail(response.body);
            } else
                textModal.open($scope, "错误", "获取计费信息错误:" + response.status);

        }).error(function(response) {
            $scope.detail.loading = false;
            textModal.open($scope, "错误", "获取计费信息错误: " + +response.status);

        });
    };

    //init page
   // $scope.detail.getCount();
    $scope.detail.refresh = $scope.detail.getPage;
    $scope.detail.getPage();

}]);

var feeDetail = angular.module('feeDetailApp');

feeDetail.service('textModal', ['$uibModal',
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

}]);
feeDetail.service('textModalTest', function($uibModal) {
    var modalInstance;
    var open=function($scope){
    modalInstance = $uibModal.open({
        templateUrl:'myModalTest',
        scope : $scope,
  //      controller:'feeDetailCtrl',
        backdrop:'static'
    });
    modalInstance.opened.then(function(        
    ){
       console.log('modalInstance is opened'); 
    });
    modalInstance.result.then(function(result){
        console.log(result);
    });
};
    
    var close=function(result){
        modalInstance.close(result);
        return '能得到消息吗';
    };
    return{
        open:open,
        close:close
    };
});
//show message dilog controller
feeDetail.controller('textCtrl', function($scope, $uibModalInstance, $http, msg) {
    $scope.text = msg;
    $scope.close = function() {
        $uibModalInstance.close('cancel');
    };
});

