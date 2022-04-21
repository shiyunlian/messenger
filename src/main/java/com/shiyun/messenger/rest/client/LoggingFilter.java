package com.shiyun.messenger.rest.client;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LoggingFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// print the headers for every request
		System.out.println("Request filter");
		System.out.println("Headers: "+requestContext.getHeaders());
	}
}
