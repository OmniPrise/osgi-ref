package com.omniprise.osgi.webapp.ws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omniprise.osgi.webapp.ws.content.Message;

@Path("/webapp")

public class WebAppService {
	private final static Logger log = LoggerFactory.getLogger(WebAppService.class);
	
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@GET
	@Path("/message")
	@Produces("application/json")
	public Response getSummary() throws Exception {
		Message result = new Message();
		result.setTimestamp(new Date());
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").setPrettyPrinting().create();
		String resultJson = gson.toJson(result);

		return Response.ok(resultJson, MediaType.APPLICATION_JSON).build();
	}
}
