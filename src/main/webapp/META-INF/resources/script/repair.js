(function($) {
    $.fn.repair = {};
    $.fn.repair.initial = function() {
        fillParkName();
        bindButton();
        
       
    };
    var isupdate = false;
    var id;
    var parkdata={};
    var bindButton = function() {
        $('#refresh').on('click', function() {
            getdata();
        });
        $('#addRecord').on('click', function() {
            $('#addRecordModal').modal();
        });
        $('#updateRecord').on('click', function() {
            fillForm();
           
        });
        $('#deleteRecord').on('click', function() {
            deleteRecord();
        });
        $('#submitBtn').on('click', function() {
            submitInfo();
        });
    };
    var deleteRecord = function() {
        var checkedTr = $('#repairBody').find('input[type="checkbox"]:checked').parents('tr');
        if (checkedTr.length == 0)
            return;
        var tds = checkedTr.find('td');
        $.ajax({
            url : $.fn.config.webroot + "/repair/delete/" + tds[1].text(),
            dataType : 'json',
            success : function(data) {
                if (data.status = 1001) {
                    alert('数据删除成功');
                    $('#refresh').click();
                } else
                    alert('数据删除失败');
            },
            error : function() {
                alert("数据操作出现问题");
            }
        });
    };
    var submitInfo = function() {

        var data = {};
        var url;
        if (isupdate) {
            url = $.fn.config.webroot + "/repair/update";
            data.id = id;
            isupdate = false;
        } else {
            url = $.fn.config.webroot + "/repair/insert";
        }
        data.hardwaretype = $('#hardwaretype').val();

        data.faulttype = $('#faulttype').val();

        data.location = $('#location').val();

        data.parkid = $('#parkName').val();

        data.submitworker = $('#submitworker').val();

        data.solveworker = $('#solveworker').val();

        data.status = $('#status').val();

        data.description = $('#description').val();

        $.ajax({
            url : url,
            contentType : "application/json;charset=utf-8",
            type : 'post',
            data : $.toJSON(data),
            dataType : 'json',
            success : function(data) {
                if (data.status = 1001) {
                    $('#addRepairForm')[0].reset();
                    alert('数据操作成功');
                    $('#refresh').click();
                } else
                    alert('数据操作失败');
            },
            error : function() {
                alert('数据操作失败');
            }
        });
    };
    var fillForm = function() {
        $('#addRepairForm')[0].reset();
        var checkedTr = $('#repairBody').find('input[type="checkbox"]:checked').parents('tr');
        if (checkedTr.length == 0)
            return;
        var tds = checkedTr.find('td');

        isupdate = true;

        id = parseInt($(tds[1]).text());

        $('#hardwaretype').val(parseInt($(tds[12]).text()));

        $('#faulttype').val(parseInt($(tds[13]).text()));

        $('#location').val($(tds[4]).text());

        $('#parkName').val($(tds[11]).text());

        $('#submitworker').val($(tds[6]).text());

        $('#solveworker').val($(tds[7]).text());

        $('#status').val(parseInt($(tds[9]).text()));

        $('#description').val($(tds[10]).text());
        
        
         $('#addRecordModal').modal();
    };
    var getdata = function() {
        $.ajax({
            url : $.fn.config.webroot + "/repair/getall",
            type : 'get',
            datatype : 'json',
            success : function(data) {
                if (data['status'] == 1001) {
                    data = data['body'];
                    fillTable(data);
                }
            },
        });
    };
    var fillTable = function(data) {
        var repairBody = $("#repairBody");
        repairBody.html('');
        for (var i = 0; i < data.length; i++) {
            var tr = $('<tr></tr>');
            tr.append('<td><input type="checkbox" /></td>');
            tr.append('<td>' + data[i]['id'] + '</td>');
            var hardwaretype;
            switch(data[i]['hardwaretype']) {
            case 0:
                hardwaretype = "车流量探测器";
                break;
            case 1:
                hardwaretype = "停车位探测器";
                break;
            case 2:
                hardwaretype = "剩余车位发布器";
                break;
            default:
                hardwaretype = "";
                break;
            }
            tr.append('<td>' + hardwaretype + '</td>');
            var faulttype;
            switch(data[i]['faulttype']) {
            case 0:
                faulttype = "有车显示无车";
                break;
            case 1:
                faulttype = "其他";
                break;
            case 2:
                faulttype = "";
                break;
            default:
                faulttype = "";
                break;
            }
            tr.append('<td>' + faulttype + '</td>');
            tr.append('<td>' + data[i]['location'] + '</td>');
            
            tr.append('<td>' + parkdata[data[i]['parkid']] + '</td>');
            tr.append('<td>' + data[i]['submitworker'] + '</td>');
            tr.append('<td>' + data[i]['solveworker'] + '</td>');
            tr.append('<td>' + data[i]['submittime'] + '</td>');
            tr.append('<td>' + data[i]['status'] + '</td>');
            tr.append('<td>' + data[i]['description'] + '</td>');
            tr.append('<td name="parkid" style="display:none">' + data[i]['parkid'] + '</td>');
            tr.append('<td name="hardwaretype" style="display:none">' + data[i]['hardwaretype'] + '</td>');
            tr.append('<td name="faulttype" style="display:none">' + data[i]['faulttype'] + '</td>');
            repairBody.append(tr);
        }
    };
    var fillParkName = function() {
        var successFunc = function(data) {
            data = data['body'];
            var parkNameSelect = $('select#parkName');
            parkNameSelect.html('');
            for (var i = 0; i < data.length; i++) {
                parkdata[data[i]['id']]=data[i]['name'];
                parkNameSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['name'] + '</option>'));
            }
            
            
        };
        var errorFunc = function(data) {
            $.fn.loader.removeLoader('#parkNameLoader');
            $('#parkNameLoader').append('<span>获取停车场名称失败，请稍后重试</span>');
        };
        $.ajax({
            url : $.fn.config.webroot + '/getParks?_t=' + (new Date()).getTime(),
            type : 'get',
            contentType : 'application/json;charset=utf-8',
            success : function(data) {
                successFunc(data);
                getdata();
            },
            error : function(data) {
                errorFunc(data);
            }
        });
    };
})(jQuery);
