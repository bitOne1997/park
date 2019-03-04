(function($){
	 
	$.fn.businessCarport = {};
	
	$.fn.businessCarport.initial = function(){
		
		bindTrClick();
	//	bindRefreshClick();
		bindAddBtnClick();
		bindUpdateBtnClick();
		bindSubmitBusinessCarportBtnClick();
		bindDeleteBtnClick();
		bindSeacherBtnClick();
		//renderBusinessCarport(0, $.fn.page.pageSize);					
	//	fillSearchPark();
		bindSearchParkChange();
		bindaddBusinessCarportNum();
		bindaddCarportSubmit();		
		setInterval("$.fn.businessCarport.bindStatusbutton()",1000);
		// setInterval(function(){
			// renderBusinessCarport($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
// 
		// }, 5000);
	};
	
	/**bind tr click*/
	var bindTrClick = function(){
		var businessCarportBody = $("#businessCarportBody");
		businessCarportBody.on('click', 'tr', function(event){
			if(event.target.nodeName.toLowerCase() != 'input')
				$(this).find('input[type="checkbox"]').click();
		});
	};
	var bindaddCarportSubmit=function(){
	    $('#addCarportSubmit').on('click',function(){
	        var carportstart=parseInt($('#carportStartNum').val());
	        var carporttotal=parseInt($('#carportTotalNum').val());
	        var parkid=parseInt($('#parkNameSelect').val());
	        if(carportstart==''||carporttotal==''||isNaN(carportstart)||isNaN(carporttotal)){
	            alert("输入错误,请检查");
	            return;
	        }
	        var data={"carportStart":carportstart,"carportTotal":carporttotal,"parkId":parkid};
	        $.ajax({
	            url:$.fn.config.webroot +"/insertBusinessCarportNum",
	            type:'post',
	            dataType:'json',
	            contentType: 'application/json;charset=utf-8',
	            data:$.toJSON(data),
	            success:function(data){
	                $('#insertCarportNumResult').append($.fn.tip.success('提交操作完成,插入车位数'+data['num']));
	                setTimeout('$("#insertCarportNumResult").html(""); $("#addCarportNum").modal("hide");$("#refresh").click()', 1000);
	            },
	            error:function(){
	                 $('#insertCarportNumResult').append($.fn.tip.error('提交操作失败'));
	                 setTimeout( $('#insertCarportNumResult').html(''),3000);
	            }
	        });
	    });
	};
	var readCookieSetSelect=function(){
		if($.cookie('carportParkSelectValue')){
			var aa=$.cookie('carportParkSelectValue');
			$('select#searchPark').val($.cookie('carportParkSelectValue'));
			if($('select#searchPark').find("option:selected").text().length<3){
				$('select#searchPark option:last').attr('selected','selected');
			}
		}
		
	};
	
	/**bind refresh click***/
	var bindRefreshClick = function(){
		var refreshBtn = $('#refresh');
		refreshBtn.on('click', $(this), function(){
			renderBusinessCarport($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
			$(this).blur();
		});
		
	};
	
	/** bind search park change event **/
	var bindSearchParkChange = function(){
		$('select#searchPark').on('change', $(this), function(){
			
		//	if($(this).val()!=-1){
		//		$.cookie('carportParkSelectValue',$(this).val(),{path:'/',expires:10});
		//	}
		//	renderBusinessCarport(0, $.fn.page.pageSize);
		
		//bindStatusbutton();
		});
	};
	
	/** fill search park **/
	var fillSearchPark = function(){
		var successFunc = function(data){
			data = data['body'];
			var parkNameSelect = $('select#searchPark');
			
			for(var i = 0; i < data.length; i++){
				if(data[i]['type']==3)
				{
				parkNameSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['name'] +'</option>'));
				}
			}
			if(data.length > 0)
				parkNameSelect.change();
			readCookieSetSelect();
		};
		var errorFunc = function(data){
		};
		$.ajax({
			url:$.fn.config.webroot + '/getParks?_t=' + (new Date()).getTime(),
			type: 'get',
			contentType: 'application/json;charset=utf-8',			
			success: function(data){successFunc(data);},
			error: function(data){errorFunc(data);}
		});
	};
	
	/**bind add BusinessCarport click**/
	var bindAddBtnClick = function(){
		$('#addBusinessCarport').on('click', $(this), function(){
			addBtnClickHandle();
		});
	};
	var bindaddBusinessCarportNum=function(){
	    $('#addBusinessCarportNum').on('click',function(){
	        fillParkName1();
	        $('#addCarportNum').modal('show');
	    });
	};
	
	var addBtnClickHandle = function(){
		$('#addBusinessCarportForm')[0].reset();
		$('select#businessCarportStatus').removeAttr('disabled');
		$('select#parkName').removeAttr('BusinessCarportId');
		$('select#parkName').removeAttr('disabled');
		$('select#parkName').html('');
		$('#addBusinessCarportResult').html('');	
		$('#addBusinessCarportModal').modal('show');
		fillParkName();
		fillMacInfo();
	};
	
	var fillParkName1 = function(){
        $.fn.loader.appendLoader($('#parkNameLoader1'));
        var successFunc = function(data){
            $.fn.loader.removeLoader($('#parkNameLoader1'));
            data = data['body'];
            var parkNameSelect = $('select#parkNameSelect');
            parkNameSelect.html('');
            for(var i = 0; i < data.length; i++){
                if(data[i]['type']==3){
                    parkNameSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['name'] +'</option>'));
                }                                                       
            }
            if($('#searchPark').val() != -1)
                parkNameSelect.val($('#searchPark').val());
        };
        var errorFunc = function(data){
            $.fn.loader.removeLoader('#parkNameLoader1');
            $('#parkNameLoader1').append('<span>获取停车场名称失败，请稍后重试</span>');
        };
        $.ajax({
            url:$.fn.config.webroot + '/getParks?_t=' + (new Date()).getTime(),
            type: 'get',
            contentType: 'application/json;charset=utf-8',          
            success: function(data){successFunc(data);},
            error: function(data){errorFunc(data);}
        });
    };
    
    
	var fillParkName = function(){
		$.fn.loader.appendLoader($('#parkNameLoader'));
		var successFunc = function(data){
			$.fn.loader.removeLoader($('#parkNameLoader'));
			data = data['body'];
			var parkNameSelect = $('select#parkName');
			parkNameSelect.html('');
			for(var i = 0; i < data.length; i++){
				if(data[i]['type']==3){
					parkNameSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['name'] +'</option>'));
				}														
			}
			if($('#searchPark').val() != -1)
				parkNameSelect.val($('#searchPark').val());
		};
		var errorFunc = function(data){
			$.fn.loader.removeLoader('#parkNameLoader');
			$('#parkNameLoader').append('<span>获取停车场名称失败，请稍后重试</span>');
		};
		$.ajax({
			url:$.fn.config.webroot + '/getParks?_t=' + (new Date()).getTime(),
			type: 'get',
			contentType: 'application/json;charset=utf-8',			
			success: function(data){successFunc(data);},
			error: function(data){errorFunc(data);}
		});
	};
	
	var fillMacInfo = function(additionalOption){
		$.fn.loader.appendLoader($('#macIdLoader'));
		var successFunc = function(data){
			$.fn.loader.removeLoader($('#macIdLoader'));
			data = data['body'];
			var macIdSelect = $('select#macId');
			macIdSelect.html('');
			macIdSelect.append($('<option value =-1>暂不绑定硬件</option>'));
			for(var i = 0; i < data.length; i++){
				macIdSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['mac'] +'</option>'));
			}
			if(additionalOption != undefined){
				for(var i = 0; i < additionalOption.length; i++){
					macIdSelect.append(additionalOption[i]);
				}
			}
		};
		var errorFunc = function(data){
			$.fn.loader.removeLoader('#macIdLoader');
			$('#macIdLoader').append('<span>获取mac信息失败，请稍后重试</span>');
		};
		$.ajax({
			url:$.fn.config.webroot + '/getUnBoundHardwares/0?_t=' + (new Date()).getTime(),
			type: 'get',
			contentType: 'application/json;charset=utf-8',
			success: function(data){successFunc(data);},
			error: function(data){errorFunc(data);}
		});
	};
	
	/**bind update BusinessCarport click **/
	var bindUpdateBtnClick = function(){
		$('#updateBusinessCarport').on('click', $(this), function(){
			updateBtnClickHandle();
		});
	};
	
	var updateBtnClickHandle = function(){
		$('#addBusinessCarportForm')[0].reset();
		$('#addBusinessCarportResult').html('');
	//	var checkedTr = $('#businessCarportBody').find('input[type="checkbox"]:checked').parents('tr');
	var checkedTr = $('input[type="checkbox"]:checked').parents('tr');
		if(checkedTr.length == 0){
			alert('请选择一个列表项');
			return;
		}else
			assignAddBusinessCarportForm($(checkedTr[0]));
			$('#businessCarportStatus').attr('disabled', 'disabled');
			$('#addBusinessCarportModal').modal('show');
	};
	
	var assignAddBusinessCarportForm = function(checkedTr){
		
		var tds = checkedTr.find('td');
		$('select#parkName').attr('businessCarportId', parseInt($(tds[1]).text()));
		$('select#parkName').html('');
		$('select#parkName').append($('<option value=-1>' + $(tds[2]).text() + '</option>'));
		$('select#parkName').attr('disabled', 'true');
		$('input#businessCarportNumber').val(parseInt($(tds[3]).text()));
		var additionalOption = [];
		if($(tds[4]).attr("data") != undefined)
			additionalOption.push($('<option value=' + parseInt($(tds[4]).attr('data')) + ' selected>' + $(tds[4]).text() + '</option>'));
		fillMacInfo(additionalOption);
		
		$('select#businessCarportStatus').val(parseInt($(tds[5]).attr('data')));
		$('input#businessCarportFloor').val(parseInt($(tds[6]).text()));
		$('input#businessCarportPosition').val($(tds[7]).text());
		$('input#businessCarportDesc').val($(tds[8]).text());	
			
	};
	
	/**bind submit button of adding and updating BusinessCarport */
	var bindSubmitBusinessCarportBtnClick = function(){
		
		$('#submitBusinessCarportBtn').on('click', $(this), function(){		
			var url = '';
			var BusinessCarportFields = getAddBusinessCarportFormValue();
			if(BusinessCarportFields['id'] != undefined && BusinessCarportFields['id'] != null ){
				url = $.fn.config.webroot + '/update/businessCarport';
			}else{
				url = $.fn.config.webroot + '/insert/businessCarport';
			}
			
			var addBusinessCarportResultDiv = $('#addBusinessCarportResult');
			$.fn.loader.appendLoader(addBusinessCarportResultDiv);
			
			$.ajax({
				url:url,
				type: 'post',
				contentType: 'application/json;charset=utf-8',			
				datatype: 'json',
				data: $.toJSON(BusinessCarportFields),
				success: function(data){
					if(data['status'] = 1001){
						$.fn.loader.removeLoader(addBusinessCarportResultDiv);
						addBusinessCarportResultDiv.append($.fn.tip.success('提交操作完成'));
						setTimeout('$("#addBusinessCarportResult").html(""); $("#addBusinessCarportModal").modal("hide");$("#refresh").click()', 500);
					}
				},
				error: function(data){
					$.fn.loader.removeLoader(addBusinessCarportResultDiv);
					addBusinessCarportResultDiv.append($.fn.tip.error('提交操作未完成'));
					setTimeout('$("#addBusinessCarportResult").html("");', 3000);
				}
			});
		});
	};
	
	var getAddBusinessCarportFormValue = function(){
		var BusinessCarportFields = {};
		var BusinessCarportId = $('select#parkName').attr('businessCarportId');
		if( BusinessCarportId != undefined && BusinessCarportId != null){
			BusinessCarportFields['id'] = parseInt(BusinessCarportId);
		}
			
		BusinessCarportFields['parkId'] = parseInt($('select#parkName').val());
		BusinessCarportFields['carportNumber'] = parseInt($('input#businessCarportNumber').val());
		BusinessCarportFields['macId'] = parseInt($('select#macId').val());
		BusinessCarportFields['status'] = parseFloat($('select#businessCarportStatus').val());
		BusinessCarportFields['floor'] = parseInt($('input#businessCarportFloor').val());
		BusinessCarportFields['position'] = $('input#businessCarportPosition').val();
		BusinessCarportFields['description'] = $('input#businessCarportDesc').val();
		BusinessCarportFields['date'] = (new Date()).format('yyyy-MM-dd hh:mm:ss');
		return BusinessCarportFields;

	};
	
	/**bind delete BusinessCarport click **/
	var bindDeleteBtnClick = function(){
		$('#deleteBusinessCarport').on('click', $(this), function(){
			var checkedTr = $('#businessCarportBody').find('input[type="checkbox"]:checked').parents('tr');
			//var checkedTr=$('input[type="checkbox"]:checked').parents('tr');
			if(checkedTr > 1)
				return;
			var modal = new $.Modal("businessCarportDelete", "删除停车位", "是否删除停车位!");
			$('#showMessage').html(modal.get());
			modal.show();
			var callback = deleteClickHandle;
			modal.setSubmitClickHandle(callback);
		});
	};
	
	var deleteClickHandle = function(){
	//	var checkedTr = $('#businessCarportBody').find('input[type="checkbox"]:checked').parents('tr');
		  var checkedTr=$('input[type="checkbox"]:checked').parents('tr');
		var id = parseInt($(checkedTr.find('td')[1]).text());
		$.ajax({
			url:$.fn.config.webroot + '/delete/businessCarport/' + id,
			type: 'get',
			contentType: 'application/json;charset=utf-8',
			success: function(data){
				$('#refresh').click();
			},
			error: function(data){
				errorHandle(data);
			}
		});
	};
	
	/**bind search BusinessCarport click **/
	var bindSeacherBtnClick = function(){
		
	};
	
	/*** get table businessCarport item ***/
	var renderBusinessCarport = function(low, count){
		renderPagination();
		var cols = $('#businessCarportTable').find('thead tr th').length;
		$("#businessCarportBody").html('<tr><td colspan="' + cols + '"></td></tr>');
		$.fn.loader.appendLoader($('#businessCarportBody').find('td'));
		var parkId=-1;
		if($.cookie('carportParkSelectValue')){
			parkId = $.cookie('carportParkSelectValue');
		};
		$.ajax({
			url:$.fn.config.webroot + "/getBusinessCarportDetail?low=" + low + "&count=" + count +  "&parkId=" + parkId +"&_t=" + (new Date()).getTime(),
			type: 'get',
			success: function(data){
				fillBusinessCarportTbody(data);
			},
			error: function(data){
				errorHandle(data);
			}
		});
	};
	
	
	var fillBusinessCarportTbody = function(data){
		var businessCarportBody = $("#businessCarportBody");
		$.fn.loader.removeLoader($('#businessCarportDiv'));
		businessCarportBody.html('');
		var wuche="<button type='button' class='btn btn-success'>无车</button>";
		var youche="<button type='button' class='btn btn-danger'>有车</button>";
		data = data["body"];
		for(var i = 0; i < data.length; i++){
			var tr = $("<tr></tr>");
			tr.append('<td><input type="checkbox" /></td>');
			tr.append('<td>' + data[i]['id']+ '</td>');
			tr.append('<td>' + data[i]['parkName']+ '</td>');
			tr.append('<td>' + data[i]['carportNumber']+ '</td>');
			if(data[i]['macId'] != undefined)
				tr.append('<td data=' + data[i]['macId'] + '>' + data[i]['mac']+ '</td>');
			else
				tr.append('<td></td>');

			tr.append('<td data=' + data[i]['status'] + '>' + (data[i]['status'] == 0 ? wuche:youche)+ '</td>');

			tr.append('<td>' + data[i]['floor']+ '</td>');
			if(data[i]['position']==undefined)
			data[i]['position']='';
			tr.append('<td>' + data[i]['position']+ '</td>');
			
			var desc ='';
			if(data[i]['description'] != undefined)
				desc = data[i]['description'];
			tr.append('<td>' + desc + '</td>');
			
			tr.append('<td>' + data[i]['date']+ '</td>');
			if( i % 2 == 0){
				tr.addClass('success');
			}else{
				tr.addClass('active');
			}
			
			var statusButton = tr.find('td button');
			
			statusButton.on('click', $(this), function(){
				var button = $(this);
				dateInitial();
				$('#carportUsage').modal('show');
				$('#carportUsage').attr('carportId', $($(button).parents('tr').find('td')[1]).text());
				var curDate = new Date();
				var nextDate = new Date();
				nextDate.setDate(nextDate.getDate() + 1);
				$('#carportStartDate').val(curDate.format('yyyy-MM-dd'));
				$('#carportEndDate').val(nextDate.format('yyyy-MM-dd'));
				
				$('#carportStartDateForTable').val(curDate.format('yyyy-MM-dd'));
				$('#carportEndDateForTable').val(nextDate.format('yyyy-MM-dd'));
				$('#carportEndDate').on('change', $(this), function(){renderCarportStatusChart();});
				$('#carportEndDateForTable').on('change', $(this), function(){renderCarportStatusTable();});
				renderCarportStatusTable();
				renderCarportStatusChart();
			});
			businessCarportBody.append(tr);
		}
	};
	$.fn.businessCarport.bindStatusbutton=function(){
	       
            var statusButton = $('td button');
            $.each(statusButton,function(){
                $(this).unbind();
                $(this).on('click', $(this), function(){
                var button = $(this);
                dateInitial();
                $('#carportUsage').modal('show');
                $('#carportUsage').attr('carportId', $($(button).parents('tr').find('td')[1]).text());
                var curDate = new Date();
                var nextDate = new Date();
                nextDate.setDate(nextDate.getDate() + 1);
                $('#carportStartDate').val(curDate.format('yyyy-MM-dd'));
                $('#carportEndDate').val(nextDate.format('yyyy-MM-dd'));
                
                $('#carportStartDateForTable').val(curDate.format('yyyy-MM-dd'));
                $('#carportEndDateForTable').val(nextDate.format('yyyy-MM-dd'));
                $('#carportEndDate').on('change', $(this), function(){renderCarportStatusChart();});
                $('#carportEndDateForTable').on('change', $(this), function(){renderCarportStatusTable();});
                renderCarportStatusTable();
                renderCarportStatusChart();
            });
            });
         
	};
	var errorHandle = function(data){
		var modal = new $.Modal('errorHandle', "失败", "操作失败" + data['message']);
		$('#showErrorMessage').html(modal.get());
		modal.show();
	};
	
	var dateInitial=function(){
		$('.date').val(new Date().format('yyyy-MM-dd'));
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
	
	//Highcharts.setOptions({ global: { useUTC: false } });   
	
	var renderCarportStatusChart = function(){
	//	event.stopPropagation();
		var id = $('#carportUsage').attr('carportId');
		var startDay = $('#carportStartDate').val();
		var endDay = $('#carportEndDate').val();
		$.ajax({
			url:$.fn.config.webroot + "/getDayCarportStatusDetail?carportId="+ id + "&startDay=" + startDay + "&endDay=" + endDay,
			type:'get',
			success: function(data){
				var carportUsage = data['body']['carportStatusDetail'];
				if(carportUsage.length == 0){
					$('#carportUsageChart').html('');
					return;
				}
					
				var chartData = [];
				startDay = startDay + " 00:00:00";
				endDay = endDay + " 00:00:00";
				var parsedStartDay = Date.parse(startDay);
				var parsedEndDay = Date.parse(endDay);
				chartData.push([parsedStartDay, null, null]);
				var summary = 0;
				for(var i = 0; i < carportUsage.length; i++){
					var startTime = carportUsage[i]['startTime'];
					var endTime = carportUsage[i]['endTime'];
					if(startTime == undefined)
						continue;
						//只允许最新的数据未出场是空的  担心异常
					if (endTime==undefined&&i!=(carportUsage.length-1)) {
                        continue;
                    };
					var startMilliSec = Date.parse(startTime);
					startMilliSec = startMilliSec > parsedStartDay ? startMilliSec : parsedStartDay;					
					  var endTimeMillSec;
                    if(endTime!=undefined){
                        endTimeMillSec = Date.parse(endTime);
                    }
                    else{
                        endTimeMillSec= new Date().getTime();
                    }
					endTimeMillSec = endTimeMillSec < parsedEndDay ? endTimeMillSec : parsedEndDay;
					chartData.push([startMilliSec,null, null ]);
					chartData.push([startMilliSec,0, 1]);
					chartData.push([endTimeMillSec,0, 1]);
					chartData.push([endTimeMillSec,null, null ]);
					summary += (endTimeMillSec - startMilliSec);
				}
				var rate = summary / ((parsedEndDay - parsedStartDay) * 1.0);
				rate *= 100;
				rate = rate.toFixed(2);
				
				chartData.push([parsedEndDay, null, null]);
				$('#carportUsageChart').html("");
				$('#carportUsageChart').highcharts({
					 chart: {
					        type: 'arearange',
					        zoomType: 'x'
					    },
					    
					    title: {
					        text: '停车位使用分布情况'+ "(利用率:" + rate + "%)"
					    },
					
					    xAxis: {
					        type: 'datetime'
					    },
					    
					    yAxis: {
					    	  max:1,
					        title: {
					            text: null
					        }
					    },
					
					    tooltip: {
					        crosshairs: true,
					        shared: true,
					        valueSuffix: ''
					    },
					    
					    legend: {
					        enabled: false
					    },
					    series: [{
					        name: '正在使用',
					        data: chartData
					    }]
				});
				
			}
			
		});
		
	};
	
	var renderCarportStatusTable = function(button){
		var id = $('#carportUsage').attr('carportId');
		
		$.ajax({
			url:$.fn.config.webroot + "/getCarportStatusDetail?carportId="+id + "&_t=" + (new Date()).getTime(),
			type: 'get',
			success: function(data){				
				var carportUsage = data['body']['carportStatusDetail'];
				var tbody = $('#carportUsageTbody');
				tbody.html('');
				for(var i = 0; i < carportUsage.length; i++){
					var tr = $('<tr></tr>');
					tr.append('<td>' + (carportUsage[i]['id']) + '</td>');
					tr.append('<td>' + (carportUsage[i]['startTime'] == undefined ? '' : carportUsage[i]['startTime']) + '</td>');
//					if(carportUsage[i]['startTime']!=undefined&&carportUsage[i]['endTime']!=undefined){
//						var date1=new Date(carportUsage[i]['startTime']);
//						var date2=new Date(carportUsage[i]['endTime']);					
//						var miliseconds=date2-date1;			
//						var days  = miliseconds/1000/60/60/24;
//						var daysRound = Math.floor(days);
//						var hours = miliseconds/1000/60/60-(24 * daysRound);
//						var hoursRound = Math.floor(hours);
//						var minutes = miliseconds/1000/60-(24 * 60 * daysRound) - (60 * hoursRound);
//						var minutesRound = Math.floor(minutes);
//						var seconds = miliseconds/1000 - (24 * 60 * 60 * daysRound) - (60 * 60 * hoursRound) - (60 * minutesRound);
//						//alert("时间占用 小时："+hoursRound+"  分钟："+minutesRound+"  秒: "+seconds);
//					}
					var startTime=new Date(carportUsage[i]['startTime']);
					var inputStartDate =  $('#carportStartDateForTable').val() + " 00:00:00";
					var inputEndDate =  $('#carportEndDateForTable').val() + " 00:00:00";
					var inputEndTime =  Date.parse(inputEndDate);
				    var inputStartTime = Date.parse(inputStartDate);
					if(startTime < inputEndTime && startTime >= inputStartTime){
						tr.append('<td>' + (carportUsage[i]['endTime'] == undefined ? '' : carportUsage[i]['endTime']) + '</td>');
						//tr.append('<td>' + (carportUsage[i]['expense'] == undefined ? '' : carportUsage[i]['expense']) + '</td>');
						//tr.append('<td>' + (carportUsage[i]['actualExpense'] == undefined ? '' : carportUsage[i]['actualExpense']) + '</td>');
						tbody.append(tr);
					}
						
					
				}
				
			},
			error: function(data){
				errorHandle(data);
			}
		});
	};
	
	/***render pagination****/
	var renderPagination = function(){
		var parkId = $('select#searchPark').val();
		$.ajax({
			url:$.fn.config.webroot + "/getBusinessCarportCount?parkId=" + parkId,
			type: 'get',
			success: function(data){
				data = data["body"];
				var paginationUl = $.fn.page.initial(data['count'], pageClickFunc);
				$('#pagination').append(paginationUl);
			},
			error: function(data){
				
			}
		});
	};
	
	var pageClickFunc = function(index){
		renderBusinessCarport($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
	};
	
})(jQuery);