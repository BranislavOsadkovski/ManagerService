package com.school.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class ServletRequestLogFilter implements Filter {

	private final static Logger logger = Logger.getLogger(ServletRequestLogFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
		logger.info("ServletRequestLogFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Enumeration<String> params = httpRequest.getParameterNames();
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			String value = request.getParameter(name);
			logger.info("Client Address : " + httpRequest.getRemoteAddr() + " Parameter={" + name + " : " + value + "}");
		}
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				logger.info(httpRequest.getRemoteAddr() + " Cookie{" + c.getName() + " : " + c.getValue() + "}");
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
		logger.info("ServletRequestLogFilter Destroyed");
	}
}
