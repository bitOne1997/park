(function($) {
	$.fn.carportstatus={};
	$.fn.carportstatus.initial = function(){
	    fillSearchPark();
	    bindSearchParkChange();
	};
	var setCarport=function(num){
	var i=0;
	$('.col-xs-1').each(function(){  
	     $(this).show();
	     i++;  
    if(i>num)
    {
        $(this).hide();
    } 
     
    });
	};
	var bindSearchParkChange = function(){
        $('select#searchPark').on('change', $(this), function(){
            
          getdata();
        });
    };
	var getdata=function(){
	    var parkid=$('select#searchPark').val();
		$.ajax({
			url: "/park/getBusinessCarportDetail?low=" + 0 + "&count=" + 50 +  "&parkId=" +parkid +"&_t=" + (new Date()).getTime(),
			type: 'get',
			success: function(data){
				data = data["body"];
				data.sort(function(a,b){
				    return a['carportNumber']>b['carportNumber']?1:-1;
				});
				var length=data.length;
				length>60?length=60: length=length ;
				setCarport(length);
				var i=0;
				
				$('.col-xs-1').each(function(){
				    if(i>length){
				        return;
				        };
				    var status=data[i]["status"];
				     console.log(status);
				    if (status==0) {
				        $(this).find('img').attr('src','img/gcar.png');
				        $(this).find('p span').html('空闲');
				        $(this).find('p span').removeClass("glyphicon-remove");
				        $(this).find('p span').addClass("glyphicon-ok");
				        $(this).find('p').val(data[i]['carportNumber']);
				    }
				    else{
				         $(this).find('img').attr('src','img/rcar.png');
                        $(this).find('p span').html('占用');
                        $(this).find('p span').removeClass("glyphicon-ok");
                        $(this).find('p span').addClass("glyphicon-remove");
                        $(this).find('p').val(data[i]['carportNumber']);
				    }
				    ;
				    
				    i++;
				});
			},
			error: function(data){
				
			}
		});
	};
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
         //   readCookieSetSelect();
        };
        var errorFunc = function(data){
        };
        $.ajax({
            url: '/park/getParks?_t=' + (new Date()).getTime(),
            type: 'get',
            contentType: 'application/json;charset=utf-8',          
            success: function(data){successFunc(data);},
            error: function(data){errorFunc(data);}
        });
    };
})(jQuery);