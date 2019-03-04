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
      var dateInitial=function(){
        $('.date').datepicker({
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
        });
    };  
   dateInitial();


    $scope.getExcelByParkAndDay=function(){
         $window.location.href="/park/getExcelByParkAndDay?date="+$scope.searchDate+"&parkId="+$('#park-select').val();
     };
     
     $scope.selectedPark={};
     $scope.selectParks = [];
     var getSelectData = function() {
         var options = $('#get_Park').get(0).options;
         for (var i = 0; i < options.length; i++) {
             var item = {
                 value : $(options[i]).val(),
                 name : $(options[i]).text()
             };
              $scope.selectParks.push(item);
         };
         $scope.selectedPark=$scope.selectParks[0];
     };
     getSelectData();


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

