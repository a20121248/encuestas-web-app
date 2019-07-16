package com.ms.encuestas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jboss.logging.Logger;

@SuppressWarnings("rawtypes")
public class LoggerWrapper {

	public StatusError status = StatusError.getInstance();
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final String name;
	private Logger log;

	protected LoggerWrapper(String name) {
		this.name = name;
		log = Logger.getLogger(name);
	}

	protected LoggerWrapper(Class clazz) {
		this.name = clazz.getName();
		log = Logger.getLogger(clazz);
	}

	public String getName() {
		return name;
	}

	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	public void debug(Object message) {
		Date d = new Date();
		log.debug("(" + df.format(d) + ") - " + message);
	}

	public void debug(Object message, Throwable t) {
		Date d = new Date();
		log.debug("(" + df.format(d) + ") - " + message, t);
	}

	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public void info(Object message) {
		Date d = new Date();
		log.info("(" + df.format(d) + ") - " + message);
		status.INFO();
	}

	public void info(Object message, Throwable t) {
		Date d = new Date();
		log.info("(" + df.format(d) + ") - " + message, t);
		status.INFO();
	}

	public void warn(Object message) {
		Date d = new Date();
		log.warn("(" + df.format(d) + ") - " + message);
		status.WARNING();
	}

	public void warn(Object message, Throwable t) {
		Date d = new Date();
		log.warn("(" + df.format(d) + ") - " + message, t);
		status.WARNING();
	}

	public void error(Object message) {
		Date d = new Date();
		log.error("(" + df.format(d) + ") - " + message);
		status.ERROR();
	}

	public void error(Object message, Throwable t) {
		Date d = new Date();
		log.error("(" + df.format(d) + ") - " + message, t);
		status.ERROR();
	}

	public void fatal(Object message) {
		Date d = new Date();
		log.fatal("(" + df.format(d) + ") - " + message);
		status.FATAL();
	}

	public void fatal(Object message, Throwable t) {
		Date d = new Date();
		log.fatal("(" + df.format(d) + ") - " + message, t);
		status.FATAL();
	}

	public static LoggerWrapper getLogger(String name) {
		LoggerWrapper logger = new LoggerWrapper(name);
		return logger;
	}

	public static LoggerWrapper getLogger(Class clazz) {
		LoggerWrapper logger = new LoggerWrapper(clazz);
		return logger;
	}
}