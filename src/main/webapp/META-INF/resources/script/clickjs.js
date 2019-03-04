$(function(){ 	
	//alert("js load ok");
	var aa=$('#daohang li a');
	var bb=$('#daohang li');
//	alert(window.location.href);
	//bb.addClass("active");
//	alert("add class ok");
	var url=window.location.href;
	var subUrl=url.substring(url.length-4);
	//alert(subUrl);
	$('#daohang li a').removeClass("active");
	switch (subUrl) {
	case "cess":
		$('.daohang1 a').addClass("active");
		break;
	case "ark/":
		$('.daohang1 a').addClass("active");
		break;
	case "arks":
		$('.daohang2 a').addClass("active");
		break;
	case "nnel":
		$('.daohang3 a').addClass("active");
		break;
	case "port":
		$('.daohang4 a').addClass("active");
		break;
	case "ware":
		$('.daohang5 a').addClass("active");
		break;
	case "sers":
		$('.daohang6 a').addClass("active");
		break;
	default:
		//alert("unknow url");
		break;
	}
//	aa.on('click',$(this),function(){
//	alert("click ok");
//	alert(window.location.href);
	
//	}
	
	//alert("remove class ok");
	

});