package com.school.listeners;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.log4j.Logger;

public class RequestServletListener implements ServletRequestListener {
	private static final Logger  logger = Logger.getLogger(RequestServletListener.class);
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletRequest sr = sre.getServletRequest();
		logger.info("ServletRequest Destroyed RemoteIP = " + sr.getRemoteAddr());
	
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		ServletRequest sr = sre.getServletRequest();
		logger.info("ServletRequest Initialized RemoteIP = " + sr.getRemoteAddr());
	}

}
