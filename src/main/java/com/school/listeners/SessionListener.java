package com.school.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener implements HttpSessionListener{
	private static final Logger logger = Logger.getLogger(SessionListener.class);
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("Session Created : ID = "+se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info("Session Destroyed : ID = "+se.getSession().getId());
	}
	
}
