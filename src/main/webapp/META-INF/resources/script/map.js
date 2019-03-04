(function($){
    $.fn.map={};    
    $.fn.map.initial=function(){
        mapInitial();
        $.fn.map.getData();
  //      setInterval("$.fn.map.getData()",8000000); 
    };
    var map;
    var point;
    var mapClusterer;
	var myIconred = new BMap.Icon("/park/img/red_park4848.png", new BMap.Size(48, 48),{
    });
	var myIcongreen = new BMap.Icon("/park/img/green_park4848.png", new BMap.Size(48, 48),{
    });
	//var myIconyellow = new BMap.Icon("http://112.74.109.240/parkYanCheng/images/yellow.png", new BMap.Size(23, 25),{
   // });   

    var mapInitial=function(){
        map = new BMap.Map("parkmap");
        point = new BMap.Point(112.537091,33.003344);
        map.centerAndZoom(point, 12);
        map.setMinZoom(11);
        map.setMaxZoom(14);     
        map.enableScrollWheelZoom();                    
        mapClusterer = new BMapLib.MarkerClusterer(map,{maxZoom:11,isAverangeCenter:true});
        
        var cr = new BMap.CopyrightControl({anchor: BMAP_ANCHOR_TOP_LEFT});   //设置版权控件位置
        map.addControl(cr); //添加版权控件
        var bs = map.getBounds();   //返回地图可视区域
        var content= "";/*"<p><span style='color:#006600;'><strong>蓝色</strong></span><strong>表示车位充足</strong></p><p style='margin-top:10px;margin-bottom:10px'><span style='color:#FFE500;'><strong>黄色</strong></span><strong>表示车位紧张</strong></p><p><span style='color:#E53333;'><strong>红色</strong></span><strong>表示无车位</strong></p>"*/;
cr.addCopyright({id: 1, content: content, bounds: bs}); 
    };
    var data_info;
    var opts = {
                width : 90,     // 信息窗口宽度
                height: 100,     // 信息窗口高度
                title : "停车场信息:" , // 信息窗口标题
                enableMessage:true,//设置允许信息窗发送短息
               };
    function addClickHandler(content,marker){
        marker.addEventListener("click",function(e){
            var infoWindow = new BMapLib.SearchInfoWindow(map,content,
                    {
                        width: 390, //宽度
                        height: 420, //高度
                        panel : "panel", //检索结果面板
                        enableAutoPan : true, //自动平移
                        enableSendToPhone: true, //是否显示发送到手机按钮
                        searchTypes :[
                            BMAPLIB_TAB_SEARCH,   //周边检索
                            BMAPLIB_TAB_TO_HERE,  //到这里去
                            BMAPLIB_TAB_FROM_HERE //从这里出发
                        ]
                        }
                    );
            infoWindow.open(e.target.getPosition()); 
            }
        );
    }
    function openInfo(content,e){
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
        map.openInfoWindow(infoWindow,point); //开启信息窗口
    } 
    var showparks=function(title){
        map.clearOverlays();
        mapClusterer.clearMarkers();
        for(var i=0;i<data_info.length;i++){
            var point=new BMap.Point(data_info[i][0],data_info[i][1]);
            var marker ;  // 创建标注
            if(data_info[i][3]==0)
		    {
		        marker= new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]),{icon: myIconred});  
		    }
			/* else if(data_info[i][3]<10)
			 {
			     marker= new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]),{icon: myIconyellow}); 
			 }*/
			 else
			 {
			     marker= new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]),{icon: myIcongreen}); 
			 }
            var content = data_info[i][2];
            var opts = {
                      position : point,    // 指定文本标注所在的地理位置
                      offset   : new BMap.Size(-8, -15)    //设置文本偏移量
                };
            var label = new BMap.Label(data_info[i][3], opts);
            label.setStyle({color:"#3c8dbc", "font-weight":"bold", "border":"none","background-color": "rgba(0, 0, 0, 0)"  });
            map.addOverlay(label);               // 将标注添加到地图中
            mapClusterer.addMarker(marker);
            addClickHandler(content,marker);
        }
    };
    $.fn.map.getData=function(){
        $.ajax({
            url:'/park/getParks',
            type: 'get',
            contentType: 'application/json;charset=utf-8',          
            datatype: 'json',
            success: function(data){
                var parkdata=data.body;
                data_info=new Array(parkdata.length);
                for(var i=0;i<parkdata.length;i++){
                    var tmparray=new Array(4);
                    tmparray[0]=parkdata[i].longitude;
                    tmparray[1]=parkdata[i].latitude;
                      var v_html = '<div id="tipsjt"></div>';
                        v_html += '    <h4 class="font14 green relative">'+'<a href=/park/outsideParkStatusAdminWithPark/' +parkdata[i].id+'/ target=_blank/>'+ parkdata[i].name +'</a>'+ '<i class="i pointer" onclick="closeTip()"></i></h4>';
                        v_html += "<img style='float:right;margin:4px;margin-top:10px' id='imgDemo' src='http://onz6nkuxs.bkt.clouddn.com/timg.jpg'  height='304' title='停车场'/>";
                        v_html += '<p class="font14">空余车位：<b class="red">' + parkdata[i].portLeftCount + '</b> 个' + (parkdata[i].portLeftCount > 0 ? '<a href="#" class="but_b back_orange font18 radius_3 absolute reservation" style="right:10px;" pid="' + i+ '"><i class="i"></i>预定</a>' : '') + '</p>';
                        v_html += '<p class="green font14">收费标准：</p> ';
                        v_html += '  <div class="color_9">';
                    tmparray[2]= v_html;
                    tmparray[3]= parkdata[i].portLeftCount;
                    data_info[i]=tmparray;
                    
                }
                showparks();
            }
        });
    };

})(jQuery);