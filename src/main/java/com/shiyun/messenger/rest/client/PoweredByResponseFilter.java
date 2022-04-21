package com.shiyun.messenger.rest.client;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PoweredByResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		// create new a header value
		responseContext.getHeaders().add("X-Powered-By", "Java");
		// print the headers for every response
		System.out.println("Response filter");
		System.out.println("Headers: "+responseContext.getHeaders());
		
	}

}
