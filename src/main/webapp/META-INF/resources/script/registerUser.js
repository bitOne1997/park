(function($){
	
	$.fn.register_user = {};
	$.fn.register_user.initial = function(){
		$("#submit_btn").on('click', $(this), function(){
			var username = $('input[name="userName"]').val();
			var number = $('input[name="number"]').val();
			var passwd = $('input[name="passwd"]').val();
			
			var data = {};
			data['userName'] = username;
			data['number'] = number;
			data['passwd'] = passwd;
			
			var data_json = $.toJSON(data);

			$.ajax({
				url:$.config.webroot + "/insert/user",
				type: 'post',
				contentType: 'application/json;charset=utf-8',			
				datatype: 'json',
				data: data_json,
				success: function(data){
					data = $.parseJSON(data);
					alert(data['status']);
				},
				error: function(data){}
			});
			
		});
		
	};
	
	
})(jQuery);