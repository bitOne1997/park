package com.park.interceptor;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.park.controller.ChargeController;
import com.park.service.TokenService;
import com.park.service.TokenUsageService;

public class loginInterceptor implements HandlerInterceptor{
	@Autowired
	TokenService tokenService;
	
	@Autowired
	TokenUsageService tokenUsageService;
	private static Log logger = LogFactory.getLog(ChargeController.class);
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String url = request.getRequestURI();		
		String token = request.getHeader("token");
		if(token != null && tokenService.validToken(token)){
			if(!url.contains("access")){
			//	tokenUsageService.insertRecord(token, url);
			}
			return true; 
		}
		HttpSession session  = request.getSession();
		Object username = session.getAttribute("username");
		if(username != null)
			return true;
		else{
			session.setAttribute("redirectUrl", url);
			request.getRequestDispatcher("/login").forward(request, response);
		}			
					
		return false;
		
		
	}

}
