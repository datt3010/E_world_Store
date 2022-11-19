package com.eworld.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.eworld.contstant.CategoryStatus;
import com.eworld.service.CategoryService;

@Component
public class GlobalInterceptor implements HandlerInterceptor{
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
			request.setAttribute("cates", categoryService.findAll(CategoryStatus.ACTIVE));
	}
}
