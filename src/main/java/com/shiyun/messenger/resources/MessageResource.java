package com.shiyun.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.shiyun.messenger.model.Message;
import com.shiyun.messenger.resources.beans.MessageFilterBean;
import com.shiyun.messenger.service.MessageService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)//use @Produces to specify the expected response body format
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})//use @Consumes to specify the expected request body format
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) {
    	// call the service and return response if year is queried
    	// http://localhost:8080/messenger/webapi/messages?year=2022
    	if (filterBean.getYear() > 0) {
    		return messageService.getAllMessagesForYear(filterBean.getYear());
    	}
    	
    	// call the service and return response if start and size are queried
    	// http://localhost:8080/messenger/webapi/messages?start=2&size=2
    	if (filterBean.getStart() > 0 && filterBean.getSize() > 0) {
    		return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
    	}
    	// call the service and return response
    	return messageService.getAllMessages();	
    }

    @GET
    @Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
    	Message message = messageService.getMessage(messageId);
    	message.addLink(getUriForSelf(uriInfo, message), "self");
    	message.addLink(getUriForProfile(uriInfo, message), "profile");
    	message.addLink(getUriForComments(uriInfo, message), "comments");
    	return message;
    }

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getAbsolutePathBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build();
		return uri.toString();
	}
    

    // no matter what http methods are, when the below path matches, the return type will be a CommentResource
    // which will look for the actual method in CommentResource class
    @Path("/{messageId}/comments")    
    public CommentResource getCommentResource() {
    	return new CommentResource();
    }
    
    @POST
    // accept the Model type as argument to bind to the request body
    public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
    	
    	Message newMessage = messageService.addMessage(message);//create a message
    	String newId = String.valueOf(newMessage.getId());//get the new created message id
    	URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();	// add the message if to the original uri
    	return Response.created(uri).entity(newMessage).build();
    	
    	// create a new Response with 201 created status, add a hard coded URI location in the header, finally build and return this Response
    	//return Response.created(new URI("/messenger/webapi/messages/" + newMessage.getId())).entity(newMessage).build();
    	
    	// create a new Response with 201 created status without a URI location in the header, finally build and return this Response
    	//return Response.status(Status.CREATED).entity(newMessage).build();
    }
    
    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
    	message.setId(messageId);
    	return messageService.updateMessage(message);
    }
    
    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long messageId) {
    	messageService.removeMessage(messageId);
    }
    
}
