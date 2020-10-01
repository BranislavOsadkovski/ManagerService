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

/**
 * AuthenticationFilter is used to log the resource that is requested by the
 * client each time the Filter.doFilter() method is called
 * 
 * @author Branislav
 *
 */
public class AuthenticationFilter implements Filter {

	private final static Logger logger = Logger.getLogger(AuthenticationFilter.class);

	/**
	 * Called by the web container to indicate to a filter that it is being placed
	 * into service.
	 * 
	 * @param cfg
	 */
	@Override
	public void init(FilterConfig cfg) {
		logger.info("AuthenticationFilter initialized");
	}

	/**
	 * The doFilter method of the Filter is called by the container each time a
	 * request/response pair is passed through the chain due to a client request for
	 * a resource at the end of the chain
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		logger.info("Requested Resourse : " + uri);

		chain.doFilter(request, response);

	}

	/**
	 * Called by the web container to indicate to a filter that it is being taken
	 * out of service.
	 */
	@Override
	public void destroy() {
		logger.info("AuthenticationFilter destroyed");
	}
}
