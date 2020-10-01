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

/**
 * LogFilter logs client IP Address,Date and Time when Filter.doFilter() method
 * is called
 * 
 * @author Branislav
 *
 */
public class LogFilter implements Filter {

	/**
	 * Called by the web container to indicate to a filter that it is being taken
	 * out of service.
	 */
	@Override
	public void destroy() {
		logger.info("LogFilter destroyed");
	}

	/**
	 * Called by the web container to indicate to a filter that it is being placed
	 * into service.
	 * 
	 * @param filterConfig
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("LogFilter initialized");
	}

	private static final Logger logger = Logger.getLogger(LogFilter.class);

	/**
	 * The doFilter method of the Filter is called by the container each time a
	 * request/response pair is passed through the chain due to a client request for
	 * a resource at the end of the chain
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {

		logger.info("Client ip : " + arg0.getRemoteAddr() + " / Time : " + new Date().toString());

		arg2.doFilter(arg0, arg1);
	}

}
