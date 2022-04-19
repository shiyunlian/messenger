package com.shiyun.messenger.exception;

import com.shiyun.messenger.model.ErrorMessage;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "http://shiyun.com");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}

}
