package com.school.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class LogFilter implements Filter{
	@Override
	public void destroy() {
		logger.info("LogFilter destroyed");
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("LogFilter initialized");
	}
	private static final Logger logger = Logger.getLogger(LogFilter.class);
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		
			logger.info("Client ip : " + arg0.getRemoteAddr() + " / Time : " + new Date().toString());
		
			arg2.doFilter(arg0, arg1);
	}

}
