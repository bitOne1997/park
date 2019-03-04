var park = angular.module('park', ['ui.router', 'ui.bootstrap', 'oc.lazyLoad']);

park.config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider){

	$urlRouterProvider.otherwise("/dashboard1")
	$stateProvider
	.state('dashboard1', {
		url:"/dashboard1",
		templateUrl:'pages/dashboard.html',
		//controller:'dashboardCtrl',
		resolve:{
			loadPlugin:function($ocLazyLoad){
				return $ocLazyLoad.load(['dist/js/controller/dashboardCtrl.js']);
			}
		}
	})
	.state('dashboard2',{
		url:"/dashboard2",
		templateUrl:'pages/dashboard2.html',
		//controller:'dashboardCtrl2',
		resolve:{
			loadPlugin:function($ocLazyLoad){
				return $ocLazyLoad.load(['dist/js/controller/dashboardCtrl2.js']);
			}
		}
	})
	.state('chart',{
		url:'/chart',
		templateUrl:'pages/charts/chartjs.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('flot',{
		url:'/flot',
		templateUrl:'pages/charts/flot.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('inline',{
		url:'/inline',
		templateUrl:'pages/charts/inline.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('morris',{
		url:'/morris',
		templateUrl:'pages/charts/morris.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('page404',{
		url:'/page404',
		templateUrl:'pages/examples/404.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('page500',{
		url:'/page500',
		templateUrl:'pages/examples/500.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('blank',{
		url:'/blank',
		templateUrl:'pages/examples/blank.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('invoice-print',{
		url:'/invoice-print',
		templateUrl:'pages/examples/invoice-print.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('lockscreen',{
		url:'/lockscreen',
		templateUrl:'pages/examples/lockscreen.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('login',{
		url:'/login',
		templateUrl:'pages/examples/login.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('profile',{
		url:'/profile',
		templateUrl:'pages/examples/profile.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('register',{
		url:'/register',
		templateUrl:'pages/examples/register.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('advanced',{
		url:'/advanced',
		templateUrl:'pages/forms/advanced.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})

	.state('editors',{
		url:'/editors',
		templateUrl:'pages/forms/editors.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('general',{
		url:'/general',
		templateUrl:'pages/forms/general.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('boxed',{
		url:'/boxed',
		templateUrl:'pages/layout/boxed.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('fixed',{
		url:'/fixed',
		templateUrl:'pages/layout/fixed.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('top-nav',{
		url:'/top-nav',
		templateUrl:'pages/layout/top-nav.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('sliderbar',{
		url:'/sliderbar',
		templateUrl:'pages/layout/collapsed-sliderbar.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('compose',{
		url:'/compose',
		templateUrl:'pages/mailbox/compose.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('mailbox',{
		url:'/mailbox',
		templateUrl:'pages/mailbox/mailbox.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('read-mail',{
		url:'/read-mail',
		templateUrl:'pages/mailbox/read-mail.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('data',{
		url:'/data',
		templateUrl:'pages/tables/data.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('simple',{
		url:'/simple',
		templateUrl:'pages/tables/simple.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('button',{
		url:'/button',
		templateUrl:'pages/UI/buttons.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('uigeneral',{
		url:'/uigeneral',
		templateUrl:'pages/UI/general.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('uiblank',{
		url:'/uiblank',
		templateUrl:'pages/UI/blank.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('icons',{
		url:'/icons',
		templateUrl:'pages/UI/icons.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('modals',{
		url:'/modals',
		templateUrl:'pages/UI/modals.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('sliders',{
		url:'/sliders',
		templateUrl:'pages/UI/sliders.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('timeline',{
		url:'/timeline',
		templateUrl:'pages/UI/timeline.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('calendar',{
		url:'/calendar',
		templateUrl:'pages/UI/calendar.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
	.state('widgets',{
		url:'/widgets',
		templateUrl:'pages/UI/widgets.html',
		//controller:'chartCtrl',
		//resolve:{
		//	loadPlugin:function($ocLazyLoad){
		//		return $ocLazyLoad.load(['dist/js/controller/chartCtrl.js']);
		//	}
		//}
	})
});