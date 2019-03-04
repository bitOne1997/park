(function($) {

    $.fn.dataCard = {};

    $.fn.dataCard.initial = function() {
        bindTrClick();
        bindRefreshClick();
        bindAddBtnClick();
        bindUpdateBtnClick();
        bindSubmitDataCardBtnClick();
        bindDeleteBtnClick();
        renderDataCard(0, $.fn.page.pageSize);
        fillParkName();
        renderPagination();
        bindKeywordsSearch();
        KeywordsSearch();
    };

    /**bind tr click*/
    var bindTrClick = function() {
        var dataCardBody = $("#dataCardBody");
        dataCardBody.on('click', 'tr', function(event) {
            if (event.target.nodeName.toLowerCase() != 'input')
                $(this).find('input[type="checkbox"]').click();
        });
    };

    /**bind refresh click***/
    var bindRefreshClick = function() {
        var refreshBtn = $('#refresh');
        refreshBtn.on('click', $(this), function() {
            renderDataCard($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
            $(this).blur();
        });

    };

    var bindSearchParkChange = function() {
        $('select#searchPark').on('change', $(this), function() {

            renderDataCardCarport(0, $.fn.page.pageSize);
        });
    };
	var bindKeywordsSearch = function() {
		$('#keywords').keyup(function(event) {
			if (event.keyCode == "13") {
				var val = $('#keywords').val();
				if (val != "")
					KeywordsSearch(val);
			}
		});
		$('#keywordsSearch').on('click', $(this), function() {
			var val = $('#keywords').val();
			if (val == "" || val == null) {
				alert("请输入关键字");
				return;
			}
			KeywordsSearch(val);

		});
	};

/*	var KeywordsSearch = function(keywords) {
		var errorHandle = function() {
			alert("发送请求失败");
		};
		var data = {
			"keywords" : keywords
		};
		$.ajax({
			url : $.fn.config.webroot + "/getChannelDetailByKeywords",
			type : 'post',
			dataType : 'json',
			contentType : 'application/json;charset=utf-8',
			data : $.toJSON(data),
			success : function(data) {
				fillchannelTbody(data);
				$('#pagination').html('');
			},
			error : errorHandle
		});
	};*/
    var KeywordsSearch = function() {
    	  var keywords = $('#keywords').val();
          $.ajax({
    url : $.fn.config.webroot + "/dataUsage/getCardDetailByKeywords",
    type : 'post',
    data : $.toJSON({
        "keywords" : keywords
    }),
    contentType : 'application/json;charset=utf-8',
    success : function(data) {
        data = data['body']['cards'];
        data.sort(function(a, b) {
            return b['dataUsage'] - a['dataUsage'];
        })
        var dataCardBody = $("#dataCardBody");
        dataCardBody.html('');

        for (var i = 0; i < data.length; i++) {
            var tr = $("<tr></tr>");
            tr.append('<td><input type="checkbox" /></td>');
            tr.append('<td>' + data[i]['id'] + '</td>');
            tr.append('<td>' + data[i]['parkName'] + '</td>');
            tr.append('<td>' + data[i]['cardNumber'] + '</td>');
            tr.append('<td>' + data[i]['phoneNumber'] + '</td>');
            var typeMessage = "";
            if (data[i]['type'] == 0)
                typeMessage = '出入口卡';
            else if (data[i]['type'] == 1)
                typeMessage = '车位发布器卡';
            else if (data[i]['type'] == 2)
                typeMessage = '室外车位卡';
            else
                typeMessage = '室内车位卡';
            tr.append('<td>' + typeMessage + '</td>');
            tr.append('<td>' + data[i]['position'] + '</td>');
            tr.append('<td>(' + data[i]['longitude'] + ', ' + data[i]['latitude'] + ')</td>');
            tr.append('<td>' + data[i]['status'] + '</td>');
            tr.append('<td>' + data[i]['dataUsage'] + '</td>');
            if (i % 2 == 0) {
                tr.addClass('active');
            } else {
                tr.addClass('default');
            }
            tr.attr("id", data[i]['id']);
            tr.attr("parkId", data[i]["parkId"]);
            tr.attr("type", data[i]["type"]);
            tr.attr("longitude", data[i]["longitude"]);
            tr.attr("latitude", data[i]["latitude"]);
            dataCardBody.append(tr);
        }
        $('#pagination').html('');
    }
});
    };
    	
    	/*
        $('#keywordsSearch').on('click', $(this), function() {
            var keywords = $('#keywords').val();
                      $.ajax({
                url : $.fn.config.webroot + "/dataUsage/getCardDetailByKeywords",
                type : 'post',
                data : $.toJSON({
                    "keywords" : keywords
                }),
                contentType : 'application/json;charset=utf-8',
                success : function(data) {
                    data = data['body']['cards'];
                    data.sort(function(a, b) {
                        return b['dataUsage'] - a['dataUsage'];
                    })
                    var dataCardBody = $("#dataCardBody");
                    dataCardBody.html('');

                    for (var i = 0; i < data.length; i++) {
                        var tr = $("<tr></tr>");
                        tr.append('<td><input type="checkbox" /></td>');
                        tr.append('<td>' + data[i]['id'] + '</td>');
                        tr.append('<td>' + data[i]['parkName'] + '</td>');
                        tr.append('<td>' + data[i]['cardNumber'] + '</td>');
                        tr.append('<td>' + data[i]['phoneNumber'] + '</td>');
                        var typeMessage = "";
                        if (data[i]['type'] == 0)
                            typeMessage = '出入口卡';
                        else if (data[i]['type'] == 1)
                            typeMessage = '车位发布器卡';
                        else if (data[i]['type'] == 2)
                            typeMessage = '室外车位卡';
                        else
                            typeMessage = '室内车位卡';
                        tr.append('<td>' + typeMessage + '</td>');
                        tr.append('<td>' + data[i]['position'] + '</td>');
                        tr.append('<td>(' + data[i]['longitude'] + ', ' + data[i]['latitude'] + ')</td>');
                        tr.append('<td>' + data[i]['status'] + '</td>');
                        tr.append('<td>' + data[i]['dataUsage'] + '</td>');
                        if (i % 2 == 0) {
                            tr.addClass('active');
                        } else {
                            tr.addClass('default');
                        }
                        tr.attr("id", data[i]['id']);
                        tr.attr("parkId", data[i]["parkId"]);
                        tr.attr("type", data[i]["type"]);
                        tr.attr("longitude", data[i]["longitude"]);
                        tr.attr("latitude", data[i]["latitude"]);
                        dataCardBody.append(tr);
                    }
                    $('#pagination').html('');
                }
            });
        });
    };*/

    var renderDataCardCarport = function(low, count) {
        renderPagination();
        var cols = $('#dataCardTable').find('thead tr th').length;
        $("#dataCardBody").html('<tr><td colspan="' + cols + '"></td></tr>');

        var url = '';
        /*var parkId = $('select#searchPark').val();
         if (parkId<1) {
         url=$.fn.config.webroot + "/getdataCardDetail?low=" + low + "&count=" + count  +"&_t=" + (new Date()).getTime();
         } else {
         url=$.fn.config.webroot + "/getParkdataCardDetail?low=" + low + "&count=" + count +  "&parkId=" + parkId +"&_t=" + (new Date()).getTime();
         }
         */
        url = $.fn.config.webroot + "/dataUsage/getCard?low=" + low + "&count=" + count + "&_t=" + (new Date()).getTime();
        $.ajax({
            url : url,
            type : 'get',
            success : function(data) {
                if (data[status] == 1001)
                    fillDataCardTbody(data['body']);
                else
                    errorHandle(data);
            },
            error : function(data) {
                alert("操作失败~~~");
                errorHandle(data);
            }
        });
    };
    var renderPagination = function() {
        var parkId = $('select#searchPark').val();
        $.ajax({
            url : $.fn.config.webroot + "/dataUsage/count?parkId=" + parkId,
            type : 'get',
            success : function(data) {
                data = data["body"];
                var paginationUl = $.fn.page.initial(data['count'], pageClickFunc);
                $('#pagination').append(paginationUl);
            },
            error : function(data) {

            }
        });
    };
    var pageClickFunc = function(index) {
        renderBusinessCarport($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
    };

    $.fn.page.initial = function(itemCount, func) {
        $.fn.page.pageCount = Math.ceil(itemCount / $.fn.page.pageSize);
        $.fn.page.pageFunction = func;
        $.fn.page.pagination();
        return $.fn.page.paginationUl;
    };

    /**bind add DataCard click**/
    var bindAddBtnClick = function() {
        $('#addDataCard').on('click', $(this), function() {
            addBtnClickHandle();
        });
    };

    var addBtnClickHandle = function() {
        $('#addDataCardForm')[0].reset();
        $('select#parkName').removeAttr('DataCardId');
        $('select#parkName').removeAttr('disabled');
        $('select#parkName').html('');
        $('#addDataCardResult').html('');
        $('#addDataCardModal').modal('show');
        fillParkName();
        fillMacInfo();
    };

    var fillParkName = function() {
        $.fn.loader.appendLoader($('#parkNameLoader'));
        var successFunc = function(data) {
            $.fn.loader.removeLoader($('#parkNameLoader'));
            data = data['body'];
            var parkNameSelect = $('select#parkName');
            parkNameSelect.html('');
            for (var i = 0; i < data.length; i++) {
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
            },
            error : function(data) {
                errorFunc(data);
            }
        });
    };

    var fillMacInfo = function(additionalOption) {
        $.fn.loader.appendLoader($('#macIdLoader'));
        var successFunc = function(data) {
            $.fn.loader.removeLoader($('#macIdLoader'));
            data = data['body'];
            var macIdSelect = $('select#macId');
            macIdSelect.html('');
            for (var i = 0; i < data.length; i++) {
                macIdSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['mac'] + '</option>'));
            }
            if (additionalOption != undefined) {
                for (var i = 0; i < additionalOption.length; i++) {
                    macIdSelect.append(additionalOption[i]);
                }
            }
        };
        var errorFunc = function(data) {
            $.fn.loader.removeLoader('#macIdLoader');
            $('#macIdLoader').append('<span>获取mac信息失败，请稍后重试</span>');
        };
        $.ajax({
            url : $.fn.config.webroot + '/getUnBoundHardwares/1?_t=' + (new Date()).getTime(),
            type : 'get',
            contentType : 'application/json;charset=utf-8',
            success : function(data) {
                successFunc(data);
            },
            error : function(data) {
                errorFunc(data);
            }
        });
    };
    /**bind update DataCard click **/
    var bindUpdateBtnClick = function() {
        $('#updateDataCard').on('click', $(this), function() {
            updateBtnClickHandle();
        });
    };

    var updateBtnClickHandle = function() {
        $('#addDataCardForm')[0].reset();
        $('#addDataCardResult').html('');
        var checkedTr = $('#dataCardBody').find('input[type="checkbox"]:checked').parents('tr');
        if (checkedTr.length == 0) {
            alert('请选择一个列表项');
            return;
        } else
            assignAddDataCardForm($(checkedTr[0]));
        $('#addDataCardModal').modal('show');
    };

    var assignAddDataCardForm = function(checkedTr) {

        var tds = checkedTr.find('td');
        $('select#parkName').attr('DataCardId', parseInt($(tds[1]).text()));
        $('select#parkName').val(parseInt(checkedTr.attr('parkId')));
        $('input#cardNumber').val($(tds[3]).text());
        $('input#phoneNumber').val($(tds[4]).text());
        $('select#type').val(parseInt(checkedTr.attr('type')));
        $('input#position').val($(tds[6]).text());
        $('input#longitude').val(parseFloat(checkedTr.attr('longitude')));
        $('input#latitude').val(parseFloat(checkedTr.attr('latitude')));

    };

    /**bind submit button of adding and updating DataCard */
    var bindSubmitDataCardBtnClick = function() {

        $('#submitDataCardBtn').on('click', $(this), function() {
            var url = '';
            var DataCardFields = getAddDataCardFormValue();
            if (DataCardFields['id'] != undefined && DataCardFields['id'] != null) {
                url = $.fn.config.webroot + '/dataUsage/updateCard';
            } else {
                url = $.fn.config.webroot + '/dataUsage/insertCard';
            }

            var addDataCardResultDiv = $('#addDataCardResult');
            $.fn.loader.appendLoader(addDataCardResultDiv);

            $.ajax({
                url : url,
                type : 'post',
                contentType : 'application/json;charset=utf-8',
                datatype : 'json',
                data : $.toJSON(DataCardFields),
                success : function(data) {
                    if (data['status'] = 1001) {
                        $.fn.loader.removeLoader(addDataCardResultDiv);
                        addDataCardResultDiv.append($.fn.tip.success('提交操作完成'));
                        setTimeout('$("#addDataCardResult").html(""); $("#addDataCardModal").modal("hide");$("#refresh").click()', 500);
                    } else {
                        $.fn.loader.removeLoader(addDataCardResultDiv);
                        addDataCardResultDiv.append($.fn.tip.error('提交操作未完成'));

                    }
                },
                error : function(data) {
                    $.fn.loader.removeLoader(addDataCardResultDiv);
                    addDataCardResultDiv.append($.fn.tip.error('提交操作失败'));
                    setTimeout('$("#addDataCardResult").html("");', 3000);
                }
            });
        });
    };

    var getAddDataCardFormValue = function() {
        var DataCardFields = {};
        var DataCardId = $('select#parkName').attr('DataCardId');
        if (DataCardId != undefined && DataCardId != null) {
            DataCardFields['id'] = parseInt(DataCardId);
        }

        DataCardFields['parkId'] = parseInt($('select#parkName').val());
        DataCardFields['cardNumber'] = $('input#cardNumber').val();
        DataCardFields['phoneNumber'] = $('input#phoneNumber').val();
        DataCardFields['type'] = parseInt($('select#type').val());
        DataCardFields['position'] = $('input#position').val();
        DataCardFields['latitude'] = parseFloat($('input#latitude').val());
        DataCardFields['longitude'] = parseFloat($('input#longitude').val());
        DataCardFields['status'] = 0;
        DataCardFields['dataUsage'] = "0";

        return DataCardFields;

    };

    /**bind delete DataCard click **/
    var bindDeleteBtnClick = function() {
        $('#deleteDataCard').on('click', $(this), function() {
            var checkedTr = $('#dataCardBody').find('input[type="checkbox"]:checked').parents('tr');

            if (checkedTr.length != 1)
                return;
            var modal = new $.Modal("dataCardDelete", "删除流量卡", "是否删除流量卡!");
            $('#showMessage').html(modal.get());
            modal.show();
            var callback = deleteClickHandle;
            modal.setSubmitClickHandle(callback);
        });
    };

    var deleteClickHandle = function() {
        var checkedTr = $('#dataCardBody').find('input[type="checkbox"]:checked').parents('tr');
        var id = parseInt($(checkedTr.find('td')[1]).text());
        $.ajax({
            url : $.fn.config.webroot + '/dataUsage/deleteCard/' + id,
            type : 'get',
            contentType : 'application/json;charset=utf-8',
            success : function(data) {
                $('#refresh').click();
            },
            error : function(data) {
                errorHandle(data);
            }
        });
    };

    /**bind search DataCard click **/
    var bindSeacherBtnClick = function() {

    };

    /*** get table dataCard item ***/
    var renderDataCard = function(low, count) {

        var cols = $('#dataCardTable').find('thead tr th').length;
        $("#dataCardBody").html('<tr><td colspan="' + cols + '"></td></tr>');
        $.fn.loader.appendLoader($('#dataCardBody').find('td'));

        $.ajax({
            url : $.fn.config.webroot + "/dataUsage/getCard?low=" + low + "&count=" + count + "&_t=" + (new Date()).getTime(),
            type : 'get',
            success : function(data) {
                if (data['status'] == 1001)
                    fillDataCardTbody(data['body']);
                else
                    errorHandle(data);
            },
            error : function(data) {
                errorHandle(data);
            }
        });
    };

    var fillDataCardTbody = function(data) {
        var dataCardBody = $("#dataCardBody");
        dataCardBody.html('');
        //	alert(data.length);
        data = data['cards']
        for (var i = 0; i < data.length; i++) {
            var tr = $("<tr></tr>");
            tr.append('<td><input type="checkbox" /></td>');
            tr.append('<td>' + data[i]['id'] + '</td>');
            tr.append('<td>' + data[i]['parkName'] + '</td>');
            tr.append('<td>' + data[i]['cardNumber'] + '</td>');
            tr.append('<td>' + data[i]['phoneNumber'] + '</td>');
            var typeMessage = "";
            if (data[i]['type'] == 0)
                typeMessage = '出入口卡';
            else if (data[i]['type'] == 1)
                typeMessage = '车位发布器卡';
            else if (data[i]['type'] == 2)
                typeMessage = '室外车位卡';
            else
                typeMessage = '室内车位卡';
            tr.append('<td>' + typeMessage + '</td>');
            tr.append('<td>' + data[i]['position'] + '</td>');
            tr.append('<td>(' + data[i]['longitude'] + ', ' + data[i]['latitude'] + ')</td>');
            tr.append('<td>' + data[i]['status'] + '</td>');
            tr.append('<td>' + data[i]['dataUsage'] + '</td>');
            if (i % 2 == 0) {
                tr.addClass('active');
            } else {
                tr.addClass('default');
            }
            tr.attr("id", data[i]['id']);
            tr.attr("parkId", data[i]["parkId"]);
            tr.attr("type", data[i]["type"]);
            tr.attr("longitude", data[i]["longitude"]);
            tr.attr("latitude", data[i]["latitude"]);
            dataCardBody.append(tr);
        }
    };

    var errorHandle = function(data) {
        var modal = new $.Modal('errorHandle', "失败", "操作失败" + data['message']);
        $('#showErrorMessage').html(modal.get());
        modal.show();
    };

    /***render pagination****/
    var renderPagination = function() {

        $.ajax({
            url : $.fn.config.webroot + "/dataUsage/count",
            type : 'get',
            success : function(data) {
                var paginationUl = $.fn.page.initial(data['body']['count'], pageClickFunc);
                $('#pagination').append(paginationUl);
            },
            error : function(data) {

            }
        });
    };

    var pageClickFunc = function(index) {
        renderDataCard($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
    };

})(jQuery); 