/**
 * Created by lenovo on 2018/9/27.
 */
(function($) {
  $.fn.map = {};
  $.fn.map.initial = function() {
    mapInitial();
    $.fn.map.getData();
    // setInterval("$.fn.map.getData()",8000000);
  };
  var map;
  var point;
  var mapClusterer;
  var myIconred = new BMap.Icon("red_park4848.png", new BMap.Size(
    48, 48), {});
  var myIcongreen = new BMap.Icon("green_park4848.png",
    new BMap.Size(48, 48), {});
  // var myIconyellow = new
  // BMap.Icon("http://112.74.109.240/parkYanCheng/images/yellow.png", new
  // BMap.Size(23, 25),{
  // });

  var mapInitial = function() {
    map = new BMap.Map("bmap1");
    point = new BMap.Point(118.800016, 32.064245);
    map.centerAndZoom(point, 12);
    map.setMinZoom(11);
    map.setMaxZoom(16);
    map.setMapStyle({
      styleJson :[
        {
          "featureType": "land",
          "elementType": "all",
          "stylers": {
            "color": "#477396"
          }
        },
        {
          "featureType": "manmade",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "building",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "subway",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "railway",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "highway",
          "elementType": "geometry",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "building",
          "elementType": "all",
          "stylers": {
            "visibility": "off"
          }
        },
        {
          "featureType": "water",
          "elementType": "all",
          "stylers": {
            "color": "#6fa8dcff"
          }
        },
        {
          "featureType": "arterial",
          "elementType": "all",
          "stylers": {
            "color": "#b477a9ff",
            "weight": "0.1"
          }
        },
        {
          "featureType": "arterial",
          "elementType": "labels.text.fill",
          "stylers": {
            "color": "#351c75ff",
            "weight": "0.1"
          }
        },
        {
          "featureType": "poilabel",
          "elementType": "labels.text.fill",
          "stylers": {
            "color": "#351c75ff",
            "weight": "0.1",
            "lightness": 3
          }
        },
        {
          "featureType": "poilabel",
          "elementType": "labels.text.stroke",
          "stylers": {
            "color": "#a2c4c9ff"
          }
        },
        {
          "featureType": "green",
          "elementType": "all",
          "stylers": {
            "color": "#6fa8dcff"
          }
        }
      ]
    });
    map.enableScrollWheelZoom();
    mapClusterer = new BMapLib.MarkerClusterer(map, {
      maxZoom : 16,
      isAverangeCenter : true
    });

    var cr = new BMap.CopyrightControl({
      anchor : BMAP_ANCHOR_TOP_LEFT
    }); // 设置版权控件位置
    map.addControl(cr); // 添加版权控件
    var bs = map.getBounds(); // 返回地图可视区域
    var content = "";/*
     * "<p><span style='color:#006600;'><strong>蓝色</strong></span><strong>表示车位充足</strong></p><p style='margin-top:10px;margin-bottom:10px'><span
     * style='color:#FFE500;'><strong>黄色</strong></span><strong>表示车位紧张</strong></p><p><span
     * style='color:#E53333;'><strong>红色</strong></span><strong>表示无车位</strong></p>"
     */
    ;
    cr.addCopyright({
      id : 1,
      content : content,
      bounds : bs
    });
  };
  var data_info;
  var opts = {
    width : 90, // 信息窗口宽度
    height : 100, // 信息窗口高度
    title : "停车场信息:", // 信息窗口标题
    enableMessage : true,// 设置允许信息窗发送短息
  };
  function addClickHandler(content, marker) {
    marker.addEventListener("click", function(e) {
      var infoWindow = new BMapLib.SearchInfoWindow(map, content, {
        width : 390, // 宽度
        height : 420, // 高度
        panel : "panel", // 检索结果面板
        enableAutoPan : true, // 自动平移
        enableSendToPhone : true, // 是否显示发送到手机按钮
        searchTypes : [ BMAPLIB_TAB_SEARCH, // 周边检索
          BMAPLIB_TAB_TO_HERE, // 到这里去
          BMAPLIB_TAB_FROM_HERE // 从这里出发
        ]
      });
      infoWindow.open(e.target.getPosition());
    });
  }
  function openInfo(content, e) {
    var p = e.target;
    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
    map.openInfoWindow(infoWindow, point); // 开启信息窗口
  }
  var showparks = function(title) {
    map.clearOverlays();
    mapClusterer.clearMarkers();
    for (var i = 0; i < data_info.length; i++) {
      var point = new BMap.Point(data_info[i][0], data_info[i][1]);
      var marker; // 创建标注
      if (data_info[i][3] == 0) {
        marker = new BMap.Marker(new BMap.Point(data_info[i][0],
          data_info[i][1]), {
          icon : myIconred
        });
      }
      /*
       * else if(data_info[i][3]<10) { marker= new BMap.Marker(new
       * BMap.Point(data_info[i][0],data_info[i][1]),{icon:
       * myIconyellow}); }
       */
      else {
        marker = new BMap.Marker(new BMap.Point(data_info[i][0],
          data_info[i][1]), {
          icon : myIcongreen
        });
      }
      var content = data_info[i][2];
      var opts = {
        position : point, // 指定文本标注所在的地理位置
        offset : new BMap.Size(-8, -15)
        // 设置文本偏移量
      };
      var label = new BMap.Label(data_info[i][3], opts);
      label.setStyle({
        color : "#fff",
        "font-weight" : "bold",
        "border" : "none",
        "background-color" : "rgba(0, 0, 0, 0)"
      });
      map.addOverlay(label); // 将标注添加到地图中
      mapClusterer.addMarker(marker);
      addClickHandler(content, marker);
    }
  };
  $.fn.map.getData = function() {
    $
      .ajax({
        url : '/park/getParks',
        type : 'get',
        contentType : 'application/json;charset=utf-8',
        datatype : 'json',
        success : function(data) {
          var parkdata = data.body;
          data_info = new Array(parkdata.length);
          for (var i = 0; i < parkdata.length; i++) {
            var tmparray = new Array(4);
            tmparray[0] = parkdata[i].longitude;
            tmparray[1] = parkdata[i].latitude;
            var v_html = '<div id="tipsjt"></div>';
            v_html += '    <h4 class="font14 green relative">'
              + parkdata[i].name
              + '<i class="i pointer" onclick="closeTip()"></i></h4>';
            v_html += "<img style='float:right;margin:4px;margin-top:10px' id='imgDemo' src='http://onz6nkuxs.bkt.clouddn.com/timg.jpg'  height='304' title='停车场'/>";
            v_html += '<p class="font14">空余车位：<b class="red">'
              + parkdata[i].portLeftCount
              + '</b> 个'
              + (parkdata[i].portLeftCount > 0 ? '<a href="#" class="but_b back_orange font18 radius_3 absolute reservation" style="right:10px;" pid="'
              + i + '"><i class="i"></i>预定</a>'
                : '') + '</p>';
            v_html += '<p class="green font14">收费标准：</p> ';
            v_html += '  <div class="color_9">';
            tmparray[2] = v_html;
            tmparray[3] = parkdata[i].portLeftCount;
            data_info[i] = tmparray;

          }
          showparks();
        },
      });
  };

})(jQuery);