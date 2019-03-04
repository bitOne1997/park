(function($) {
    $.fn.parkChart = {};
    $.fn.parkChart.theme = Highcharts.getOptions();
    $.fn.parkChart.chart;
    $.fn.parkChart.initial = function() {

        var chatContent = jQuery('#chart-content');
        jQuery('#date').val(new Date().format('yyyy-MM-dd'));
        jQuery('#date').datepicker({
            autoClose : true,
            dateFormat : "yyyy-mm-dd",
            nextText : "下月",
            preText : "上月",
            dayNames : ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
            dayNamesMin : ["日", "一", "二", "三", "四", "五", "六"],
            monthNames : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthNamesShort : ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
        });
        Highcharts.setOptions(Highcharts.themeGray);
        $('#parkName').text($('#park-select').find('option:selected').text());
        bindParkSelectChange(chatContent);
        $('#date').on('change', $(this), function() {
            $.fn.parkChart.renderChart(chatContent);
        });
        bindThemeChange();
        readCookieSetSelect();
        $.fn.parkChart.renderChart(chatContent);

        autocomplete();
    };
    var bindParkSelectChange = function(chatContent) {
        $('#park-select').on('change', $(this), function() {
            $('#parkName').text($('#park-select').find('option:selected').text());
            $.fn.parkChart.renderChart(chatContent);
            $.cookie('selectValue', $(this).val(), {
                path : '/',
                expires : 10
            });
        });
    };
    var autocomplete = function() {
        var selectdata = [];
        var tmpdata = {};
        $('#park-select option').each(function() {
            var data = $(this).text();
            var val = $(this).val();
            if (data != undefined) {
                selectdata.push(data);
            }
            tmpdata[val] = data;
        });
        $('#autocompletetag').autocomplete({
            source : selectdata
        });
        $('#autocompletebtn').on('click', $(this), function() {
            var selectedtxt = $('#autocompletetag').val();
            //      $("#park-select").find("option:selected").attr("selected",false);
            //      var txt="option[text='"+selectedtxt+"']";
            //   var id = $("#park-select").find(txt).val();
            var id;
            $.each(tmpdata, function(key, value) {
                if (value == selectedtxt) {
                    id = key;
                }
            });
            $("#park-select").val(id);
        });
    };
    var bindThemeChange = function() {
        $('#theme').on('change', $(this), function() {
            var value = $(this).val();
            var options;
            switch(parseInt(value)) {
            case 0:
                options = Highcharts.setOptions($.fn.parkChart.theme);
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
        });
    };
    var readCookieSetSelect = function() {
        if ($.cookie('selectValue')) {
            $('#park-select').val($.cookie('selectValue'));
            if ($('#park-select').find("option:selected").text().length < 3) {
                $('#park-select option:last').attr('selected', 'selected');
            }
        }
    };
    var renderchart = function(chatContent) {
        var parkId = parseInt($('#park-select').val());
        var date = $('#date').val();
        var aa = date.split('-');
        //	alert(aa);
        date = aa[0].substring(0, 4) + '-' + aa[1] + '-' + aa[2];
        $('#date').val(date);
        //	alert(date);
        var url = $.fn.config.webroot + '/getHourCountByChannel?_t=' + (new Date()).getTime();
        $.ajax({
            url : url,
            type : 'post',
            contentType : 'application/json;charset=utf-8',
            datatype : 'json',
            data : $.toJSON({
                'parkId' : parkId,
                'date' : date
            }),
            success : function(data) {
                if (data.status == 1001) {
                    $.fn.parkChart.renderChartContent(data.body, chatContent);
                }
            },
            error : function(data) {
            }
        });
    };

    $.fn.parkChart.renderChart = function(chatContent) {
        //setInterval(renderchart,10000,chatContent);
        renderchart(chatContent);
    };

    var parseAccessData = function(data, isMonthAccess) {
        var dateTime = [];
        var series = [];
        var i = 0;
        for (var k = 0; k < 24; k++) {
            dateTime.push(k + ":00");
        }
        for (var key in data) {
            var accessFlow = [];
            for (var j = 0; j < 24; j++) {
                var flowValue = data[key][j] || 0;
                accessFlow.push(flowValue);
            }
            if (accessFlow.length == 0) {
                for (var i = 0; i < 24; i++) {
                    accessFlow.push(0);
                }
            }
            series[i] = {
                //			type:'bar',
                name : key,
                data : accessFlow,
            };
            i++;
        }
        return {
            'dateTime' : dateTime,
            'series' : series
        };
    };
    $.fn.parkChart.updateChart = function() {
        var parkId = parseInt($('#park-select').val());
        var date = $('#date').val();
        var chart = $.fn.parkChart.chart;
        var url = $.fn.config.webroot + '/getHourCountByChannel?_t=' + (new Date()).getTime();
        $.ajax({
            url : url,
            type : 'post',
            contentType : 'application/json;charset=utf-8',
            datatype : 'json',
            data : $.toJSON({
                'parkId' : parkId,
                'date' : date
            }),
            success : function(data) {
                if (data.status == 1001) {
                    var chartData = parseAccessData(data.body);
                    var seriesData = chartData['series'];
                    $.each(seriesData, function(name, value) {
                        $.fn.parkChart.chart.series[name].setData(value.data);
                    });
                }
            },
            error : function(data) {
            }
        });
    };
    $.fn.parkChart.renderChartContent = function(data, chatContent) {
        var chartData = parseAccessData(data);
        chatContent.highcharts({
            chart : {
                type : 'bar',
                events : {
                    load : function() {
                        $.fn.parkChart.chart = this;
                        setInterval(function() {
                            $.fn.parkChart.updateChart();
                        }, 1000 * 3);
                    }
                }
            },
            title : {
                text : '硬件流量统计'
            },
            xAxis : {
                categories : chartData['dateTime']
            },
            plotOptions : {
                bar : {
                    dataLabels : {
                        enabled : true
                    }
                }
            },
            series : chartData['series']
        });
    };

})(jQuery); 