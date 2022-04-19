package com.shiyun.messenger.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)//use @Produces to specify the expected response body format
@Produces(MediaType.TEXT_PLAIN)//use @Consumes to specify the expected request body format
public class InjectDemoResource {

	@GET
	@Path("/annotations")
	// MatrixParam: http://localhost:8080/messenger/webapi/injectdemo/annotations;matrixParam=value
	// HeaderParam: set any custom name of header value, eg. key="customHeaderValue", value="header value"
	// CookieParam: set any custom name of cookie value
	// To create a test cookie you need to install and turn on the interceptor 
	// (there's a thing in Postman to the top-right that says Interceptor/Proxy next to the Sign In option). 
	// Then, in the headers section enter Cookie for type and then in the value column enter the name of the parameter (cookie) followed by = and value. 
	// So I have: Cookie  |   cookieParam="hello"
	// Press send and now your cookie shows up in cookies.
	public String getParamsUsingAnnotations(@MatrixParam("matrixParam") String matrixParamValue,
										@HeaderParam("headerParam") String headerValue,
										@CookieParam("cookieParam") String cookieValue) {
		return "Matrix param: " + matrixParamValue + " Header value: " + headerValue + " Cookie value: " + cookieValue;
	}
	
	@GET
	@Path("/context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, 
									@Context HttpHeaders headers) {
		// get the uri info
		String path = uriInfo.getAbsolutePath().toString();
		// get the cookies from headers
		String cookies = headers.getCookies().toString();
		return "Path: " + path + " Cookies: " + cookies;
	}
}
