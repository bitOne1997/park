(function($){
	
$.fn.parkChart = {};
$.fn.parkChart.theme = Highcharts.getOptions();
$.fn.parkChart.chart;
$.fn.parkChart.dayAccess = {'entranceAccess': 0, 'exitAccess':0};
$.fn.parkChart.monthAccess = {'entranceAccess':0, 'exitAccess':0};
$.fn.parkChart.yearAccess = {'entranceAccess': 0, 'exitAccess':0};
$.fn.parkChart.intervalHandler = null;

/**
 * init content
 */
$.fn.parkChart.initial = function(){
	var chatContent = $('#chart-content');	
	$('#date').val(new Date().format('yyyy-MM-dd'));
	$('#date').datepicker({
		autoClose: true,
        dateFormat: "yyyy-mm-dd",
        nextText:"下月",
        preText:"上月",       
        dayNames:["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        dayNamesMin:["日", "一", "二", "三", "四", "五", "六"],
        monthNames:["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
       monthNamesShort:["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
	
	});
		
	Highcharts.setOptions(Highcharts.themeGray);
	$.fn.parkChart.renderChart(chatContent);
    autocomplete();
	$('#parkName').text($('#park-select').find('option:selected').text());
	$('#park-select').on('change', $(this), function(){
		$('#parkName').text($('#park-select').find('option:selected').text());
		$.fn.parkChart.renderChart(chatContent);
	});
	$('#date').on('change', $(this), function(){
		$.fn.parkChart.renderChart(chatContent);
	});
	
	$('#theme').on('change', $(this), function(){
		
		var value = $(this).val();
		var options;
		switch(parseInt(value)){
		case 0:
			options =  Highcharts.setOptions($.fn.parkChart.theme);
			break;
		case 1:
			options = Highcharts.setOptions(Highcharts.themeGray);
			break;
		case 2:
			options = Highcharts.setOptions(Highcharts.themeDarkBlue);
			break;
		case 3:
			options = Highcharts.setOptions(Highcharts.themeDarkGreen);
			break;
		case 4:
			options = Highcharts.setOptions(Highcharts.themeDarkUnica);
			break;
		case 5:
			options = Highcharts.setOptions(Highcharts.themeGrid);
			break;
			default:
				Highcharts.setOptions($.fn.parkChart.theme);
		}
	
		$.fn.parkChart.renderChart(chatContent);
	})
	
};

var autocomplete=function(){
    var selectdata=[];
    var tmpdata={};
    $('#park-select option').each(function(){
        var data=$(this).text();
        var val=$(this).val();
        if(data!=undefined){
            selectdata.push(data);
        }
        tmpdata[val]=data;
    });
    $('#autocompletetag').autocomplete(
        {
            source:selectdata
        }
    )
    $('#autocompletebtn').on('click',$(this),function(){
        var selectedtxt=$('#autocompletetag').val();
    //      $("#park-select").find("option:selected").attr("selected",false);
    //      var txt="option[text='"+selectedtxt+"']";
     //   var id = $("#park-select").find(txt).val();
     var id;
     $.each(tmpdata,function(key,value){
         if(value==selectedtxt){
             id=key;
         }
     })
        $("#park-select").val(id);
    })

}
/**
 * render chart
 */
$.fn.parkChart.renderChart = function(chatContent){
	var parkId = parseInt($('#park-select').val());
	var date = $('#date').val();
	var aa=date.split('-');
    date=aa[0].substring(0,4)+'-'+aa[1]+'-'+aa[2];
    $('#date').val(date);
	var url = $.fn.config.webroot + '/getHourCountByPark?_t=' + (new Date()).getTime();
		
	$.ajax({
		url:url,
		type: 'post',
		contentType: 'application/json;charset=utf-8',			
		datatype: 'json',
		data: $.toJSON({'parkId':parkId, 'date':date}),
		success: function(data){
			if(data.status == 1001){
			$.fn.parkChart.renderChartContent(data.body, chatContent);
		}
		},
		error: function(data){}
	});

};

/**
 * update chart content dynamic
 */
$.fn.parkChart.updateChart = function(){
	$.fn.parkChart.updateLeftPort();
	$.fn.parkChart.updateDayAccess();
	$.fn.parkChart.updateMonthAccess();
	
};

var parseAccessData = function(data, isMonthAccess){
	var dateTime = [];
	var entranceAccess = [];
	var exitAccess = [];
	var entranceSumAccess = 0;
	var exitSumAccess = 0;
	
	for(var i = 0; i < 24; i++){
		var entranceValue = data.entrance[i] || 0;
		var exitValue = data.exit[i] || 0;
		dateTime.push(i + ":00");
		entranceAccess.push(entranceValue);
		exitAccess.push(exitValue);
		entranceSumAccess += entranceValue;
		exitSumAccess += exitValue;
	}
	
	
	if(entranceAccess.length == 0){
		for(var i = 0; i < 24; i++){
			dateTime.push(i + ":00");
			entranceAccess.push(0);
			exitAccess.push(0);
		}
	}
	
	if(isMonthAccess == true){
		
		var monthEntranceAccess = 0;
		var monthExitAccess = 0;
		for(var i = 0; i < 31; i++){
			var entranceValue = data.entrance[i] || 0;
			var exitValue = data.exit[i] || 0;
			monthEntranceAccess += entranceValue;
			monthExitAccess += exitValue;
		}
		
		$.fn.parkChart.monthAccess['entranceAccess'] = monthEntranceAccess == undefined ? 0 : monthEntranceAccess;
		$.fn.parkChart.monthAccess['exitAccess'] = monthExitAccess == undefined ? 0 : monthExitAccess;
	}else{
		$.fn.parkChart.dayAccess = {"entranceAccess":entranceSumAccess, 'exitAccess': exitSumAccess};
	}
	
	return {'dateTime': dateTime, 'entranceAccess': entranceAccess, 'exitAccess': exitAccess};
};

/**
 * update day access
 */
$.fn.parkChart.updateDayAccess = function(){
	var chart = $.fn.parkChart.chart;
	var parkId = parseInt($('#park-select').val());
	var date = $('#date').val();
	
	var url = $.fn.config.webroot + '/getHourCountByPark?_t=' + (new Date()).getTime();
	
	var successFunc = function(data){
		var chartData = parseAccessData(data);
		if(chart.series == undefined || chart.series.length <2)
			return;
		var entranceSeries = chart.series[0];
		var exitSeries = chart.series[1];
		entranceSeries.setData(chartData['entranceAccess']);
		exitSeries.setData(chartData['exitAccess']);
	
		$('#dayAccessVal').text($.fn.parkChart.dayAccess['entranceAccess'] + $.fn.parkChart.dayAccess['exitAccess']);
	};	
	
	
	$.ajax({
		url:url,
		type: 'post',
		contentType: 'application/json;charset=utf-8',			
		datatype: 'json',
		data: $.toJSON({'parkId':parkId, 'date':date}),
		success: function(data){
			successFunc(data['body']);
		},
		error: function(data){}
	});
};


/**
 * update month access
 */
$.fn.parkChart.updateMonthAccess = function(){
	var chart = $.fn.parkChart.chart;
	var parkId = parseInt($('#park-select').val());
	var date = $('#date').val();
	$.fn.parkChart.monthAccess['entranceAccess']
	var url = $.fn.config.webroot + '/getDayCountByPark?_t=' + (new Date()).getTime();
	
	var successFunc = function(data){
		var chartData = parseAccessData(data, true);
		//chart.setTitle(null, {text:"日流量(入口："+ $.fn.parkChart.dayAccess['entranceAccess']+ " 出口:"+ $.fn.parkChart.dayAccess['exitAccess'] +" )" 
		//		+ " 月流量(入口:" +$.fn.parkChart.monthAccess['entranceAccess'] + " 出口:" + $.fn.parkChart.monthAccess['exitAccess']+ " )"});
		
		$('#monthAccessVal').text($.fn.parkChart.monthAccess['entranceAccess'] + $.fn.parkChart.monthAccess['exitAccess']);
	};	
	
	
	$.ajax({
		url:url,
		type: 'post',
		contentType: 'application/json;charset=utf-8',			
		datatype: 'json',
		data: $.toJSON({'parkId':parkId, 'date':date}),
		success: function(data){
			successFunc(data['body']);
		},
		error: function(data){}
	});
};




/**
 * update left port
 */
$.fn.parkChart.updateLeftPort = function(){
	var chart = $.fn.parkChart.chart;
	var parkId = parseInt($('#park-select').val());
	var test = 1;
	 $.ajax({
		url:$.fn.config.webroot + '/getPark/' + parkId + '?_t=' + (new Date()).getTime(),
		type: 'get',
		contentType: 'application/json;charset=utf-8',			
		success: function(data){
			if(data.status == 1001){
			/*	var series = chart.series[2];
				var seriesData = [{
		            name: '无车',
		            y: data.body.portLeftCount,
		            color: Highcharts.getOptions().colors[2] 
		        }, {
		            name: '有车',
		            y: data.body.portCount-data.body.portLeftCount,
		            color: Highcharts.getOptions().colors[1] 
		        }];
		       
				series.setData(seriesData); */
				$('#leftPortVal').text(data.body.portLeftCount);
			}
		},
		error: function(data){}
	 });
};

/**
 * render chart content
 */
$.fn.parkChart.renderChartContent = function(data, chatContent){
	
	if($.fn.parkChart.intervalHandler != null || $.fn.parkChart.intervalHandler != undefined){
		clearInterval($.fn.parkChart.intervalHandler);
		$.fn.parkChart.intervalHandler = null;
	}
	
	var chartData = parseAccessData(data);
		
	chatContent.highcharts({
		chart: {
			 events: {  
                 load: function() {  
                	 $.fn.parkChart.chart = this;
                	 $.fn.parkChart.updateChart();

                	 
                	 $.fn.parkChart.intervalHandler = setInterval(function(){
                		 $.fn.parkChart.updateChart();
                	 }, 1000 * 10);

                 }                                                               
             }   
		},
	    title: {
	        text: '停车场流量统计'
	    },
	    xAxis: {
	        categories: chartData['dateTime']
	    },
	   /* labels: {
	        items: [{
	            html: '剩余车位(' + new Date().format('yyyy-MM-dd hh:mm:ss')+')',
	            style: {left: '35px',
	                top: '-40',
	                color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'}
	        }]
	    },*/
	    series: [{
	        type: 'column',
	        name: '入口',
	        color:Highcharts.getOptions().colors[1],
	        data: chartData['entranceAccess'],
	        dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                //align: 'right',
                x: 0,
                y: -15,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
	    }, {
	        type: 'column',
	        name: '出口',
	        color:Highcharts.getOptions().colors[2],
	        data: chartData['exitAccess'],
	        dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                //align: 'right',
                x: 0,
                y: -15,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
	    }/*,  {
	        type: 'pie',
	        name: '车位状况',
	        data: [{
	            name: '无车',
	            y: 13,
	            color: Highcharts.getOptions().colors[2] 
	        }, {
	            name: '有车',
	            y: 23,
	            color: Highcharts.getOptions().colors[1] 
	        }],
	        center: [80, 10],
	        size: 100,
	        showInLegend: false,
	        dataLabels: {
	            enabled: true,
	            format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	        }
	    }*/]
	});
};



})(jQuery);