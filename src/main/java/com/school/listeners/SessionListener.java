package com.school.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * Gets notified about Session lifecycle and logs changes and SessionID
 * 
 * @author Branislav
 *
 */
public class SessionListener implements HttpSessionListener {
	private static final Logger logger = Logger.getLogger(SessionListener.class);

	/**
	 * Gets notified by HttpSessionEvent when the Session is created and logs the
	 * change
	 *
	 * @param se
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("Session Created : ID = " + se.getSession().getId());
	}

	/**
	 * Gets notified by HttpSessionEvent when the Session is destroyed and logs the
	 * change
	 * @param se
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info("Session Destroyed : ID = " + se.getSession().getId());
	}

}