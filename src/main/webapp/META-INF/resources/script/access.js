(function($){
	
	$.fn.access = {};
	$.fn.access.initial = function(){
		bindTrClick();
		bindRefreshClick();
		bindDeleteClick();
//		renderAccess(0, $.fn.page.pageSize);
		getAllAccessCount();
		fillSearchPark();
		bindSearchParkChange();
	};
	
	/**bind tr click*/
	var bindTrClick = function(){
		var accessBody = $("#accessBody");
		accessBody.on('click', 'tr', function(event){
			if(event.target.nodeName.toLowerCase() != 'input')
				$(this).find('input[type="checkbox"]').click();
		});
	};
	
	var bindRefreshClick = function(){
		var refreshBtn = $('#refresh');
		refreshBtn.on('click', $(this), function(){
			
			renderAccess($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
			$(this).blur();
		});
		
	};
	
	/** bind search park change event **/
	var bindSearchParkChange = function(){
		$('select#searchPark').on('change', $(this), function(){
			
			renderAccess(0, $.fn.page.pageSize);
		});
	};
	
	/** fill search park **/
	var fillSearchPark = function(){
		var successFunc = function(data){
			data = data['body'];
			var parkNameSelect = $('select#searchPark');
			
			for(var i = 0; i < data.length; i++){
				parkNameSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['name'] +'</option>'));
			}
			if(data.length > 0)
				parkNameSelect.change();
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
	
	
	/*** get table access item ***/
	var renderAccess = function(low, count){
		renderPagination();
		var cols = $('#accessTable').find('thead tr th').length;
		$("#accessBody").html('<tr><td colspan="' + cols + '"></td></tr>');
		$.fn.loader.appendLoader($('#accessBody').find('td'));

		var accessBody = $("#accessBody");
		
		var parkId = $('select#searchPark').val();
		$.ajax({
			url:$.fn.config.webroot + "/getAccessDetail?low=" + low + "&count=" + count + "&parkId=" + parkId,
			type: 'get',
			success: function(data){
				fillAccessTbody(data);
			},
			error: function(data){
				errorHandle(data);
			}
		});
	};
	
	
	var fillAccessTbody = function(data){
		var accessBody = $("#accessBody");
		$.fn.loader.removeLoader($('#accessDiv'));
		accessBody.html('');
		data = $.parseJSON(data["body"]);
		for(var i = 0; i < data.length; i++){
			var tr = $("<tr></tr>");
			tr.append('<td><input type="checkbox" /></td>');
			tr.append('<td>' + data[i]['id']+ '</td>');
			tr.append('<td>' + data[i]['name']+ '</td>');
			tr.append('<td>' + data[i]['channelNumber']+ '</td>');
			tr.append('<td>' + data[i]['date']+ '</td>');
			if( i % 2 == 0){
				tr.addClass('success');
			}else{
				tr.addClass('active');
			}
			accessBody.append(tr);
		}
	};
	
	var errorHandle = function(data){
		alert("get accesses failed");
	};
	
	var getAllAccessCount=function(){
		$.ajax({
			url:$.fn.config.webroot+'/getAllAccessCount',
			type:'get',
			contentType:'application/json;charset=utf-8',
			success:function(data){
			//	alert(data);
				$("h2").html("出入口实时流量管理  	数据总量: "+data["num"]);
				//alert(data["num"]);
			}
		}
				);
	};
	/**
	 * delete access
	 */
	var bindDeleteClick = function(){
		$('#deleteAccess').on('click', $(this), function(){
			var checkedTr = $('#accessBody').find('input[type="checkbox"]:checked').parents('tr');
			
			if(checkedTr.length != 1)
				return;
			var modal = new $.Modal("accessDelete", "删除访问记录", "是否访问记录!");
			$('#showMessage').html(modal.get());
			modal.show();
			var callback = deleteClickHandle;
			modal.setSubmitClickHandle(callback);
		});
		
	}
	var deleteClickHandle = function(){
		var checkedTr = $('#accessBody').find('input[type="checkbox"]:checked').parents('tr');
		var id = parseInt($(checkedTr.find('td')[1]).text());
		$.ajax({
			url:$.fn.config.webroot + '/delete/access/' + id,
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
	

	/***render pagination****/
	var renderPagination = function(){
		var parkId = $('select#searchPark').val();
		$.ajax({
			url:$.fn.config.webroot + "/getAccessCount?&parkId=" + parkId,
			type: 'get',
			success: function(data){
				data = $.parseJSON(data["body"]);
				var paginationUl = $.fn.page.initial(data['count'], pageClickFunc);
				$('#pagination').append(paginationUl);
			},
			error: function(data){
				
			}
		});
	};
	
	var pageClickFunc = function(index){
		renderAccess($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
	};
	
})(jQuery);