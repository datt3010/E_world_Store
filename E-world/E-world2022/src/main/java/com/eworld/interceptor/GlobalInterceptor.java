package com.eworld.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.eworld.contstant.CategoryStatus;
import com.eworld.dto.BrandDto;
import com.eworld.filter.BrandFilter;
import com.eworld.service.BrandService;
import com.eworld.service.CategoryService;

@Component
public class GlobalInterceptor implements HandlerInterceptor{
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandSerivce;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
					
			request.setAttribute("cates", categoryService.findAll(CategoryStatus.ACTIVE));
			
			Pageable pageable = PageRequest.of(0,5,Direction.ASC,"name");	
			BrandFilter filter = BrandFilter.builder()
					.keyword(null)
					.build();
			Page<BrandDto> brands = brandSerivce.findPaging(filter, pageable);
			request.setAttribute("brands", brands);
	}
	
}
