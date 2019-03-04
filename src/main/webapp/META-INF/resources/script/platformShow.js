(function($) {
    $.fn.platformShow = {};
    $.fn.platformShow.initial = function() {
        getData();
        chart3Init();
        chart2Init();
        chart4Init();
        setInterval(getData,60000);
    };
    var map1;
    var map4;
    var chart1data = [];
    var chart1Init = function() {
        map1 = new BMap.Map("container1");
        var point = new BMap.Point(118.8, 32.0625);
        map1.centerAndZoom(point, 12);
        map1.setMinZoom(11);
        map1.setMaxZoom(14);
        map1.enableScrollWheelZoom();
        heatmapOverlay = new BMapLib.HeatmapOverlay({
            "radius" : 20
        });
        map1.addOverlay(heatmapOverlay);
        heatmapOverlay.setDataSet({
            data : chart1data,
            max : 150
        });
        heatmapOverlay.show();
    };
    var chart4Init = function() {
        map4 = new BMap.Map("container4");
        point = new BMap.Point(118.8, 32.0625);
        map4.centerAndZoom(point, 6);
        map4.setMinZoom(5);
        map4.setMaxZoom(8);
        map4.enableScrollWheelZoom();
        mapClusterer = new BMapLib.MarkerClusterer(map4, {
            maxZoom : 9,
            isAverangeCenter : true
        });

    };
    var mapClusterer;
    var myIconred = new BMap.Icon("http://112.74.109.240/parkYanCheng/images/red.png", new BMap.Size(23, 25), {
    });
    var myIcongreen = new BMap.Icon("http://112.74.109.240/parkYanCheng/images/green.png", new BMap.Size(23, 25), {
    });
    var myIconyellow = new BMap.Icon("http://112.74.109.240/parkYanCheng/images/yellow.png", new BMap.Size(23, 25), {
    });
    var showparks = function(data_info) {
        map4.clearOverlays();
        mapClusterer.clearMarkers();
        for (var i = 0; i < data_info.length; i++) {
            var point = new BMap.Point(data_info[i][0], data_info[i][1]);
            var marker;
            // 创建标注
            if (data_info[i][3] == 0) {
                marker = new BMap.Marker(new BMap.Point(data_info[i][0], data_info[i][1]), {
                    icon : myIconred
                });
            } else if (data_info[i][3] < 10) {
                marker = new BMap.Marker(new BMap.Point(data_info[i][0], data_info[i][1]), {
                    icon : myIconyellow
                });
            } else {
                marker = new BMap.Marker(new BMap.Point(data_info[i][0], data_info[i][1]), {
                    icon : myIcongreen
                });
            }

            mapClusterer.addMarker(marker);
        }
    };
    var getData = function() {
        var data_info = [];
        $.ajax({
            url : '/park/getParks',
            type : 'get',
            contentType : 'application/json;charset=utf-8',
            datatype : 'json',
            success : function(data) {
                var parkdata = data.body;
                chart1data = [];
                data_info = new Array(parkdata.length);
                for (var i = 0; i < parkdata.length; i++) {
                    var tmpdata = {};
                    var tmparray = new Array(4);
                    tmparray[0] = parkdata[i].longitude;
                    tmparray[1] = parkdata[i].latitude;
                    var v_html = '<div id="tipsjt"></div>';
                    v_html += '    <h1 class="green relative">' + parkdata[i].name + '<i class="i pointer" onclick="closeTip()"></i></h1>';
                    v_html += '<p class="font14">空余车位：<b class="red">' + parkdata[i].portLeftCount + '</b> 个' + (parkdata[i].portLeftCount > 0 ? '<a href="#" class="but_b back_orange font18 radius_3 absolute reservation" style="right:10px;" pid="' + i + '"><i class="i"></i>预定</a>' : '') + '</p>';
                    v_html += '<p class="green font14">收费标准：</p> ';
                    v_html += '  <div class="color_9">';
                    tmparray[2] = v_html;
                    tmparray[3] = parkdata[i].portLeftCount;
                    tmpdata['lng'] = parseFloat(tmparray[0]);
                    tmpdata['lat'] = parseFloat(tmparray[1]);
                    tmpdata['count'] = parseInt(tmparray[3]);
                    data_info[i] = tmparray;
                    if (tmpdata.lng != 0 && tmpdata.lat != 0 && tmpdata.count != 0)
                        chart1data.push(tmpdata);
                }
                showparks(data_info);
                chart1Init();
            },
        });
    };

    var chart3Init = function() {
        var myChart = echarts.init(document.getElementById('container3'));
       option = {
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['江宁区','玄武区','雨花台区','鼓楼区','浦口区']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {
                show: true, 
                type: ['pie', 'funnel'],
                option: {
                    funnel: {
                        x: '25%',
                        width: '50%',
                        funnelAlign: 'center',
                        max: 1548
                    }
                }
            },
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'停车次数',
            type:'pie',
            radius : ['50%', '70%'],
            itemStyle : {
                normal : {
                    label : {
                        show : true
                    },
                    labelLine : {
                        show : true
                    }
                },
                emphasis : {
                    label : {
                        show : true,
                        position : 'center',
                        textStyle : {
                            fontSize : '30',
                            fontWeight : 'bold'
                        }
                    }
                }
            },
            data:[
                {value:93215, name:'江宁区'},
                {value:41310, name:'玄武区'},
                {value:22334, name:'雨花台区'},
                {value:13075, name:'鼓楼区'},
                {value:8548, name:'浦口区'}
            ]
        }
    ]
};
        myChart.setOption(option);
    };
    var chart2Init = function() {
        var myChart = echarts.init(document.getElementById('container2'));
        var labelTop = {
            normal : {
                label : {
                    show : true,
                    position : 'center',
                    formatter : '{b}',
                    textStyle : {
                        baseline : 'bottom'
                    }
                },
                labelLine : {
                    show : false
                }
            }
        };
        var labelFromatter = {
            normal : {
                label : {
                    formatter : function(params) {
                        return  params.value + '';
                    },
                    textStyle : {
                        baseline : 'top'
                    }
                }
            },
        };
        var labelBottom = {
            normal : {
                color : '#ccc',
                label : {
                    show : true,
                     formatter : '\n{c}',
                    position : 'center'
                },
                labelLine : {
                    show : false
                }
            },
            emphasis : {
                color : 'rgba(0,0,0,0)'
            }
        };
        var radius = [40, 47];
        option = {
            legend : {
                x : 'center',
                y : 'center',
                //     orient : 'vertical',
                //     x : 'left',
                data : ['江宁万达', '农机所', '观梅苑西', '黄金海岸苏果超市', '太平花苑', '中兴通信', '江苏第二师范', '浦口高新']
            },
            title : {
                text : '停车场剩余车位数',
                subtext : '',
                x : 'center'
            },
            toolbox : {
                show : true,
                feature : {
                    dataView : {
                        show : false,
                        readOnly : false
                    },
                    magicType : {
                        show : false,
                        type : ['pie', 'funnel'],
                        option : {
                            funnel : {
                                width : '20%',
                                height : '30%',
                                itemStyle : {
                                    normal : {
                                        label : {
                                            formatter : function(params) {
                                                return 'other\n' + params.value + '\n'
                                            },
                                            textStyle : {
                                                baseline : 'middle'
                                            }
                                        }
                                    },
                                }
                            }
                        }
                    },
                    restore : {
                        show : true
                    },
                    saveAsImage : {
                        show : true
                    }
                }
            },
            series : [ {
                type : 'pie',
                center : ['10%', '30%'],
                radius : radius,
               x : '0%', // for funnel
                itemStyle : labelFromatter,
                data : [ 
                {
                    name : 'other',
                    value : 4,
                    itemStyle : labelBottom
                },{
                    name : '江宁万达',
                    value : 1,
                    itemStyle : labelTop
                }]
            }, {
                type : 'pie',
                center : ['30%', '30%'],
                radius : radius,
                x : '20%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 21,
                    itemStyle : labelBottom
                }, {
                    name : '农机所',
                    value : 130,
                    itemStyle : labelTop
                }]
            },{
                type : 'pie',
                center : ['50%', '30%'],
                radius : radius,
                x : '40%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 130,
                    itemStyle : labelBottom
                }, {
                    name : '观梅苑西',
                    value : 14,
                    itemStyle : labelTop
                }]
            },{
                type : 'pie',
                center : ['70%', '30%'],
                radius : radius,
                x : '60%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 31,
                    itemStyle : labelBottom
                }, {
                    name : '黄金海岸苏果超市',
                    value : 4,
                    itemStyle : labelTop
                }]
            }, {
                type : 'pie',
                center : ['90%', '30%'],
                radius : radius,
                x : '80%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 13,
                    itemStyle : labelBottom
                }, {
                    name : '太平花苑',
                    value : 73,
                    itemStyle : labelTop
                }]
            }, {
                type : 'pie',
                center : ['10%', '70%'],
                radius : radius,
                y : '55%', // for funnel
                x : '0%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 28,
                    itemStyle : labelBottom
                }, {
                    name : '中兴通信',
                    value : 2,
                    itemStyle : labelTop
                }]
            }, {
                type : 'pie',
                center : ['30%', '70%'],
                radius : radius,
                y : '55%', // for funnel
                x : '20%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 38,
                    itemStyle : labelBottom
                }, {
                    name : '江苏第二师范',
                    value : 2,
                    itemStyle : labelTop
                }]
            }, {
                type : 'pie',
                center : ['50%', '70%'],
                radius : radius,
                y : '55%', // for funnel
                x : '40%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 78,
                    itemStyle : labelBottom
                }, {
                    name : '浦口高新',
                    value : 22,
                    itemStyle : labelTop
                }]
            }, {
                type : 'pie',
                center : ['70%', '70%'],
                radius : radius,
                y : '55%', // for funnel
                x : '60%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 83,
                    itemStyle : labelBottom
                }, {
                    name : '南京科技馆',
                    value : 17,
                    itemStyle : labelTop
                }]
            }, {
                type : 'pie',
                center : ['90%', '70%'],
                radius : radius,
                y : '55%', // for funnel
                x : '80%', // for funnel
                itemStyle : labelFromatter,
                data : [{
                    name : 'other',
                    value : 89,
                    itemStyle : labelBottom
                }, {
                    name : '浦口中心医院',
                    value : 11,
                    itemStyle : labelTop
                }]
            }]
        };
        myChart.setOption(option);
    };
})(jQuery);
