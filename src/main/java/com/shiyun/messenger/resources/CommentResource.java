package com.shiyun.messenger.resources;

import java.util.List;

import com.shiyun.messenger.model.Comment;
import com.shiyun.messenger.model.Profile;
import com.shiyun.messenger.service.CommentService;
import com.shiyun.messenger.service.ProfileService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/") // the class level @Path annotatioin is optional for sub resources
@Consumes(MediaType.APPLICATION_JSON)//use @Produces to specify the expected response body format
@Produces(MediaType.APPLICATION_JSON)//use @Consumes to specify the expected request body format
public class CommentResource {
	
	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getComments(@PathParam("messageId") long messageId){
		return commentService.getAllComments(messageId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId){
		return commentService.getComment(messageId, commentId);
	}
	
    @POST
    @Path("/{commentId}") //eg. http://localhost:8080/messenger/webapi/messages/1/comments
	public Comment addMessage(@PathParam("messageId") long messageId, Comment comment) {
    	return commentService.addComment(messageId, comment);
    }
    
    @PUT
    @Path("/{commentId}") //eg. http://localhost:8080/messenger/webapi/messages/1/comments/1
    public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId, Comment comment) {
    	comment.setId(commentId);
    	return commentService.updateComment(messageId, comment);
    }
    
    @DELETE
    @Path("/{commentId}")
    public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
    	commentService.removeComment(messageId, commentId);
    }


}
