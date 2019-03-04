(function($) {
    $.fn.demo = {};
    $.fn.demo.initial = function() {
    getTotalAccessCount();
    getCarportCount();
    getAccessCountToday();
    getAccessCountYestoday();
    };
    var getTotalAccessCount = function() {
        var loader = new $.Loader();
         $('#totalAccessCount').append(loader.get());
         loader.show();
        $.ajax({
            url : $.fn.config.webroot + '/getAllAccessCount',
            type : 'get',
            dataType : 'json',
            contentType : 'application/json;charset=utf-8',
            success : function(data) {
                //alert(data['num']);
                loader.remove();
                $('#totalAccessCount').text(data['num']);
            },
        });
    };
    var getAccessCountToday = function() {
        var myDate = new Date();
        var month = myDate.getMonth() + 1;
        var date = myDate.getFullYear() + '-' + month + '-' + myDate.getDate();
        var data = {
            "date" : date
        };
        $.ajax({
            url : $.fn.config.webroot + '/getAccessCountByDate',
            type : 'post',
            dataType : 'json',
            data : $.toJSON(data),
            contentType : 'application/json;charset=utf-8',
            success : function(data) {
                    $('#todayAccessCount').text(data['num']);
            },
        });
    };
    var getAccessCountYestoday = function() {
        var myDate = new Date();
        myDate.setDate(myDate.getDate() - 1);
        var month = myDate.getMonth() + 1;
        var date = myDate.getFullYear() + '-' + month + '-' + myDate.getDate();
        var data = {
            "date" : date
        };
        $.ajax({
            url : $.fn.config.webroot + '/getAccessCountByDate',
            type : 'post',
            dataType : 'json',
            data : $.toJSON(data),
            contentType : 'application/json;charset=utf-8',
            success : function(data) {
                    $('#yestodayAccessCount').text(data['num']);
            },
        });
    };
    var getCarportCount=function(){
        $.ajax({
            url:$.fn.config.webroot+"/getParkCount",
            type:'get',
            dataType:'json',
            contentType:'application/json;charset=utf-8',
            success:function(data){
                data=data['body'];
               
                data=$.parseJSON(data);
                $('#carportCount').text(data['count']);
            }
        });
    };
})(jQuery); 