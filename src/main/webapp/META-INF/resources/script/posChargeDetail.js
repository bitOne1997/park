(function($){	
	$.fn.park = {};	
	parkId=-1;
	$.fn.park.initial = function(){
		
		renderPagination();
		intervalRenderPagination();
		rendnumcard();
		initialData();
		bindParkChange();
		
	};	
	var initialData=function(){
	     var parkId=$('#parkNamesearch').val();
	     if(parkId){
	        renderPark(parkId,0, $.fn.page.pageSize); 
	     }
	     $("#startDate").val(new Date().format("yyyy-MM-dd"));
	     $("#startDay").val(new Date().format("yyyy-MM-dd"));
         $("#endDay").val(new Date().format("yyyy-MM-dd"));
	    $('#getExcelByDayRange').on('click',function(){
	        window.location.href="/park/pos/getExcelByDayAndPark?parkId="+$('#parkSelected').val()+"&startday="+$("#startDate").val();
	    });
	     $('#getExcelByRange').on('click',function(){
            window.location.href="/park/pos/getExcelByDayRangeAndPark?parkId="+$('#parkSelected2').val()+"&startday="+$("#startDay").val()+"&endday="+$("#endDay").val();
        });
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
	};
	var rendnumcard=function(){
		$.ajax({
			url:$.fn.config.webroot + "/pos/getCountsByCard" ,
			type:'get',
			datatype:'json',
			success:function(data){
				if (data['status']==1001) {
					data=data['body'];
					var strp='';
				    for(var i=0;i<data.length;i++){
				        strp+='车牌号:<span style="color:#003399;"><strong>'+data[i]['CardSnr']+'</strong></span>&nbsp;&nbsp; 停车次数:<span style="color:#E53333;"><strong>'+data[i]['num'] +'</strong></span>&nbsp; &nbsp;&nbsp; &nbsp; &nbsp;';
                        if((i+1)%3==0){
                            strp+="</br>";
                        }
				    }	
				    $('#carnumshow').html(strp);
				}
			}
		});
	};
	
	var bindParkChange=function(){
		$('#parkSelected3').on('change',function(){
		 parkId=$('#parkSelected3').val();
		renderPark(parkId,0, $.fn.page.pageSize);
        renderPagination();
		});
	};
	/*** get table park item ***/
	var renderPark = function(parkId,low, count){
		var cols = $('#parkTable').find('thead tr th').length;
		$("#parkBody").html('<tr><td colspan="' + cols + '"></td></tr>');		
		var loader = new $.Loader();
		var loaderTd = $('#parkBody').find('td');
		loaderTd.append(loader.get());
		loader.show();	
		var url="/pos/selectPosdataByPage";
		var data = {'low': low, 'count': count, 'T': new Date().getTime()};
		if(parkId!=-1){
		    url="/pos/selectPosdataByPageAndPark";
		    data = {'parkId':parseInt(parkId),'low': low, 'count': count, 'T': new Date().getTime()};
		}
		$.ajax({
			//url:$.fn.config.webroot + "/getParkDetail?low=" + low + "&count=" + count + "&_t=" + new Date().getTime(),
			url: $.fn.config.webroot + url ,
			type: 'post',
			contentType: 'application/json;charset=utf-8',			
			datatype: 'json',
			data: $.toJSON(data),
			success: function(data){
				loader.remove();
				fillParkTbody(data);
			},
			error: function(data){
				loader.remove();
				errorHandle(data);
			}
		});
	};
	var renderParkByPark=function(parkId,low,count){
	    var cols = $('#parkTable').find('thead tr th').length;
        $("#parkBody").html('<tr><td colspan="' + cols + '"></td></tr>');       
        var loader = new $.Loader();
        var loaderTd = $('#parkBody').find('td');
        loaderTd.append(loader.get());
        loader.show();  
        var data = {'parkId':parkId,'low': low, 'count': count, 'T': new Date().getTime()};
        $.ajax({
            //url:$.fn.config.webroot + "/getParkDetail?low=" + low + "&count=" + count + "&_t=" + new Date().getTime(),
            url: $.fn.config.webroot + "/pos/selectPosdataByPageAndPark" ,
            type: 'post',
            contentType: 'application/json;charset=utf-8',          
            datatype: 'json',
            data: $.toJSON(data),
            success: function(data){
                loader.remove();
                fillParkTbody(data);
            },
            error: function(data){
                loader.remove();
                errorHandle(data);
            }
        });
	};
	var fillParkTbody = function(data){
		var parkBody = $("#parkBody");
		parkBody.html('');
		data = data["body"];
		for(var i = 0; i < data.length; i++){
			var tr = $('<tr></tr>');
			tr.append('<td><input type="checkbox" /></td>');
			tr.append('<td>' + data[i]['id']+ '</td>');
		//	tr.append('<td>' + data[i]['credencesnr']+ '</td>');
			tr.append('<td>' + data[i]['cardsnr']+ '</td>');
		//	var type=data[i]['cardtype']=="1"?"小车":"大车";
		//	tr.append('<td>' + type + '</td>');
			tr.append('<td>' + data[i]['sitename']+ '</td>');			
			tr.append('<td>' + data[i]['backbyte']+ '</td>');
			var mode=parseInt(data[i]['mode'])==0?"进场":"出场";
			if(data[i]['isarrearage']==true){
			    mode="补交";
			}
			tr.append('<td>' + mode + '</td>');
			tr.append('<td>' + data[i]['userid']+ '</td>');
			tr.append('<td>' + data[i]['possnr']+ '</td>');
			tr.append('<td>' + data[i]['money']+ '</td>');			
			tr.append('<td>' + data[i]['giving']+ '</td>');
			tr.append('<td>' + data[i]['realmoney']+ '</td>');
			tr.append('<td>' + data[i]['returnmoney']+ '</td>');
			tr.append('<td>' + data[i]['starttime']+ '</td>');	
			var endtime=data[i]['endtime']
			if (data[i]['endtime']==undefined) {
			    endtime='';
			};
			tr.append('<td>' + endtime+ '</td>');
	//		tr.append('<td>' + data[i]['sysid']+ '</td>');
	        
			if( i % 2 == 0){
				tr.addClass('success');
			}else{
				tr.addClass('active');
			}			
			parkBody.append(tr);
		}
	};
	var errorHandle = function(data){
		var modal = new $.Modal('errorHandle', "失败", "操作失败" + data['message']);
		$('#showErrorMessage').html(modal.get());
		modal.show();
	};
	/***render pagination****/
	
	function renderPagination(){
	   var url='/pos/getPosdataCount';
         if(parkId!=-1)
         {
             url='/pos/getPosdataCountByPark/'+parkId;
         }  
		$.ajax({
			url:$.fn.config.webroot + url,
			type: 'get',
			success: function(data){				
				var paginationUl = $.fn.page.initial(data['count'], pageClickFunc);
				$('#pagination').append(paginationUl);
			},
			error: function(data){
			}
		});
	}
	var intervalRenderPagination=function(){
		setInterval(renderPagination,60000);
	};
	var pageClickFunc = function(index){
		renderPark(parkId,$.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
	};	
})(jQuery);