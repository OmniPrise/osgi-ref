package com.omniprise.osgi.webapp.ws.interceptor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.PreMatching;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PreMatching
public class ConfidentialInterceptor extends AbstractPhaseInterceptor<Message> {
	private final static Logger log = LoggerFactory.getLogger(ConfidentialInterceptor.class);
	
	private static final String URI_MATCHER = "(.*)//.*?:(.*?)/.*";
	
	private String httpsPort = "8443";
	
	public ConfidentialInterceptor() {
		this(Phase.RECEIVE);
		log.debug("Registered ConfidentialInterceptor");
	}
	
	public ConfidentialInterceptor(String phase) {
		super(phase);
	}

	public void handleMessage(Message message) {
		log.debug("Received handleMessage request");
		
		String url = (String)message.get(Message.REQUEST_URL);

		if (url.startsWith("http:")) {
			log.debug("Non HTTPS request that requires confidential communicaiton");
			Pattern pattern = Pattern.compile(URI_MATCHER);
			Matcher matcher = pattern.matcher(url);
			
			HttpServletResponse response = (HttpServletResponse)message.get(AbstractHTTPDestination.HTTP_RESPONSE);

			if (!matcher.matches()) {
				log.error("Unable to match inbound URI for rewriting " + url);
				throw new Fault(new Exception("Unable to rewrite url for confidentiality " + url));
			} else {
				url = url.replaceAll("http:", "https:").replaceAll(matcher.group(2), httpsPort);
			}

			log.debug("Build REDIRECT response");
			response.setHeader("Location", url);
			Fault fault = new Fault(new AuthenticationException());
			fault.setStatusCode(307);
			throw fault;
		}
	}

	public String getHttpsPort() {
		return httpsPort;
	}

	public void setHttpsPort(String httpsPort) {
		this.httpsPort = httpsPort;
	}
}
