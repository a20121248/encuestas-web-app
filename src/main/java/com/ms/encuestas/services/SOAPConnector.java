package com.ms.encuestas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SOAPConnector extends WebServiceGatewaySupport {
	private static final Logger logger = LoggerFactory.getLogger(SOAPConnector.class);
	
	public Object callWebService(String url, Object request) {
		return getWebServiceTemplate().marshalSendAndReceive(url, request);
	}
}
