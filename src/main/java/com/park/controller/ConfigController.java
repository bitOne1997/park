package com.park.controller;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;
@Controller
@RequestMapping("config")
public class ConfigController {
	
	
	private static String url;
	
	@Value("#{prop.ActiveMqUrl}")
	public void seturl(String activeurl) {
		url=activeurl;
	}
	@RequestMapping("layout")
	@ResponseBody
	public String layout(HttpServletRequest request,HttpServletResponse response){
	//	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/spring-mvc.xml");
	//	 org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver aLayoutViewResolver=(VelocityLayoutViewResolver) context.getBean("viewResolver");
	//	 aLayoutViewResolver.setLayoutUrl("layout2.vm");
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
	//	webApplicationContext.getBeanDefinitionNames();
	//	org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver aLayoutViewResolver=(VelocityLayoutViewResolver) webApplicationContext.getBean("viewResolver");
	//	aLayoutViewResolver.setLayoutUrl("layout2.vm"); 
		StringBuilder dd=new StringBuilder();
		StringBuilder ee=new StringBuilder();
		String[] aaa=webApplicationContext.getBeanDefinitionNames();
		for (int i = 0; i < aaa.length; i++) {
			dd.append(aaa[i]);
			dd.append("\r\n");
			System.out.println(aaa[i]);
		}
//	 org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver aLayoutViewResolver=(VelocityLayoutViewResolver) webApplicationContext.getBean("viewResolver");
//		
//		 aLayoutViewResolver.clearCache();
//		 aLayoutViewResolver.setLayoutUrl("layout2.vm");
		
		//ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext(),"org.springframework.web.servlet.FrameworkServlet.CONTEXT");
		//ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:conf/spring-mvc.xml");
		ApplicationContext context=(ApplicationContext) request.getSession().getServletContext().getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring");
		Enumeration<Object> fff=request.getSession().getServletContext().getAttributeNames();
		
		for(Enumeration e=fff;e.hasMoreElements();){
		    String thisName=e.nextElement().toString();
			System.out.println("jieguo: "+thisName);
		}
		String[] bbb=context.getBeanDefinitionNames();
		
		for (int i = 0; i < aaa.length; i++) {
			ee.append(aaa[i]+"\n");
			System.out.println(aaa[i]);
			ee.append("\r\n");
		}
		return webApplicationContext.getBeanDefinitionCount()+";;"+context.getBeanDefinitionCount()+ee.toString();
	}
	
	@RequestMapping("getvalue")
	@ResponseBody String getvalue() {
		
		return url;
	}
	
}
