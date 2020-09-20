package com.school.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter{
	private final static Logger logger = Logger.getLogger(AuthenticationFilter.class);
	@Override
	public void init(FilterConfig cfg) {
		logger.info("AuthenticationFilter initialized");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String uri = httpRequest.getRequestURI();
		logger.info("Requested Resourse : "+uri);
		
		chain.doFilter(request, response);
		
	}
	@Override
	public void destroy() {
		logger.info("AuthenticationFilter destroyed");
	}
}
