package com.school.listeners;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.log4j.Logger;

/**
 * RequestServletListener receives notification when requests come in and out of
 * scope of web application and log the notification and RemoteIP of the request
 * 
 * @author Branislav
 *
 */
public class RequestServletListener implements ServletRequestListener {
	private static final Logger logger = Logger.getLogger(RequestServletListener.class);

	/**
	 * Receives event when request is about to go out of scope of the web
	 * application.
	 * 
	 * @param sre
	 */
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletRequest sr = sre.getServletRequest();
		logger.info("ServletRequest Destroyed RemoteIP = " + sr.getRemoteAddr());

	}

	/**
	 * Receives event when request is about to come into scope of the web
	 * application.
	 * 
	 * @param sre
	 */
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		ServletRequest sr = sre.getServletRequest();
		logger.info("ServletRequest Initialized RemoteIP = " + sr.getRemoteAddr());
	}

}