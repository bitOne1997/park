(function($){
	
	$.fn.user = {};
	$.fn.user.initial = function(){
		bindTrClick();
		bindRefreshClick();
		renderuser(0, $.fn.page.pageSize);
	//	fillParkSelect();
		renderPagination();
		bindAddButton();
		bindDeleteButton();
		bindAddPageRadio();
		bindSearchPark();
	};
	
	
	var bindSearchPark = function(){
		$('#searchContent').keyup(function (event) {
    if (event.keyCode == "13") {
    var val = $('#searchContent').val();
    if(val!="")
    searchPark(val);
 }
});
		$('#searchButton').on('click', $(this), function(){
			var val = $('#searchContent').val();
			if(val==""||val==null)
			{
			    return;
			}
			searchPark(val);
			
		});
	};
	
	var searchPark = function(username){
		$.ajax({
			url: $.fn.config.webroot + "/getParkByNameandParkId",
			type: 'post',	
			contentType: 'application/json;charset=utf-8',	
			datatype: 'json',
			data:$.toJSON({"username":username}),
			success: function(data){
				filluserTbody(data);
				$('#pagination').html('');
			},
			error: function(data){
				errorHandle(data);
			}
		});
	};
	
	/**bind tr click*/
	var bindTrClick = function(){
		var userBody = $("#userBody");
		userBody.on('click', 'tr', function(event){
			if(event.target.nodeName.toLowerCase() != 'input')
				$(this).find('input[type="checkbox"]').click();
		});
	};
	
	var fillParkSelect = function(){
		var successFunc = function(data){
			data = data['body'];
			var parkNameSelect = $('select#parkSelect');
			
			for(var i = 0; i < data.length; i++){
				parkNameSelect.append($('<option value = ' + data[i]['id'] + '>' + data[i]['name'] +'</option>'));
			}
			
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
	
	var bindRefreshClick = function(){
		var refreshBtn = $('#refresh');
		refreshBtn.on('click', $(this), function(){
			renderuser($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
			$(this).blur();
		});
		
	};
	
	/*** get table user item ***/
	var renderuser = function(low, count){
		
		var cols = $('#userTable').find('thead tr th').length;
		$("#userBody").html('<tr><td colspan="' + cols + '"></td></tr>');

		$.fn.loader.appendLoader($('#userBody').find('td'));

		$.ajax({
			url:$.fn.config.webroot + "/getParkUserDetail?low=" + low + "&count=" + count,
			type: 'get',
			success: function(data){
				filluserTbody(data);
			},
			error: function(data){
				errorHandle(data);
			}
		});
	};
	
	
	var filluserTbody = function(data){
		var userBody = $("#userBody");
		$.fn.loader.removeLoader($('#userDiv')); 
		userBody.html('');
		data = data["body"];
		//console.log("data-----"+data.length);
		
		for(var i = 0; i < data.length; i++){
			var tr = $("<tr></tr>");
			tr.attr("role", data[i]['role']);
			tr.attr("id", data[i]['id']);
			tr.append('<td><input type="checkbox" /></td>');
			tr.append('<td>' + data[i]['id']+ '</td>');
			tr.append('<td>' + data[i]['username']+ '</td>');
			var role = "管理员用户";
			if(data[i]['role'] == 1)
				role = "普通用户";
			tr.append('<td>' + role + '</td>');
			
			var parkName="";
			if(data[i]['role'] == 0)
				parkName="所有停车场";
			else{
				for(var j = 0; j < data[i]["parkName"].length; j++){
					parkName += "   ";
					parkName += data[i]["parkName"][j];
				}
				
			}
						
			tr.append('<td>' + parkName + '</td>');
					
			if( i % 2 == 0){
				tr.addClass('active');
			}else{
				tr.addClass('default');
			}
			userBody.append(tr);
		}
	};
	
	
	var errorHandle = function(data){
		alert("get useres failed");
	};
	
	/***render pagination****/
	var renderPagination = function(){

		$.ajax({
			url:$.fn.config.webroot + "/getParkUserCount",
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
		renderuser($.fn.page.pageSize * ($.fn.page.currentPage - 1), $.fn.page.pageSize);
	};
	
	var bindAddButton = function(){
		$('#addUser').on('click', $(this), function(){
			$('#addUserForm')[0].reset();			
			$('#addUserResult').html('');
			$('#addUserModal').modal('show');
		});
		
		var addUserResultDiv = $('#addUserResult');
		$('#submitUserBtn').on('click', $(this), function(){
			var parkUser = {};
			parkUser.username = $('#username').val();
			parkUser.password = $('#password').val();
			var repeatPassword = $('#passwordRepeat').val();
			if(parkUser.password != repeatPassword){
				var modal = new $.Modal('errorHandle', "失败", "两次输入的密码不同");
				$('#showErrorMessage').html(modal.get());
				modal.show();
				return;
			}
			
			parkUser.role = 0;			
			if(!$("#adminType")[0].checked){
				parkUser.role = 1;
			}
			
			parkUser.parkList = [];
			if(parkUser.role == 1){
				parkUser.parkList = $("#parkSelect").val();
			}
			
			
			$.ajax({
				url: $.fn.config.webroot + '/insert/parkUser',
				type: 'post',
				contentType: 'application/json;charset=utf-8',			
				datatype: 'json',
				data: $.toJSON(parkUser),
				success: function(data){
					if(data['status'] = 1001){
						$.fn.loader.removeLoader(addUserResultDiv);
						addUserResultDiv.append($.fn.tip.success('提交操作完成'));
						setTimeout('$("#addUserResult").html(""); $("#addUserModal").modal("hide");$("#refresh").click()', 500);
					}
				},
				error: function(data){
					$.fn.loader.removeLoader(addChannelResultDiv);
					addUserResultDiv.append($.fn.tip.error('提交操作未完成'));
					setTimeout('$("#addUserResult").html("");', 3000);
				}
			});
			
		});
	};
	
	
	
	var bindDeleteButton = function(){
		$('#deleteUser').on('click', $(this), function(){
			var checkedTr = $('#userBody').find('input[type="checkbox"]:checked').parents('tr');
			if(checkedTr > 1)
				return;
			var modal = new $.Modal("userDelete","删除用户","是否删除用户!" );
			var callback = deleteClickHandle;
			modal.setSubmitClickHandle(callback);
			$('#showMessage').html(modal.get());
			modal.show();
		});
	};
	
	var deleteClickHandle = function(){
		var checkedTr = $('#userBody').find('input[type="checkbox"]:checked').parents('tr');
		var id = parseInt($(checkedTr.find('td')[1]).text());
		$.ajax({
			url:$.fn.config.webroot + "/delete/parkUser/" + id,
			type: 'get',
			success: function(data){
				$('#refresh').click();
			},
			error: function(data){
				errorHandle(data);
			}
		});
	};
	
	var errorHandle = function(data){
		var modal = new $.Modal('errorHandle', "失败", "操作失败" + data['message']);
		$('#showErrorMessage').html(modal.get());
		modal.show();
	};
	
	var bindAddPageRadio = function(){
		var bindFunction = function(){
			var parkSelect = $("#parkNameDiv");
			if($("#adminType")[0].checked){
				parkSelect.hide();
			}else{
				parkSelect.show();
			}
		};
		$('#adminType').on('change', $(this), bindFunction);
		$('#normalType').on('change', $(this), bindFunction);
		
	};
	
})(jQuery);