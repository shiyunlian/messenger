package com.shiyun.messenger.rest.client;

import com.shiyun.messenger.model.Message;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestApiClient {

	public static void main(String[] args) {
		
		// use ClintBuilder to get the Client object
		Client client = ClientBuilder.newClient();
		
		WebTarget baseTarget = client.target("http://localhost:8080/messenger/webapi/");
		WebTarget messagesTarget = baseTarget.path("messages");
		WebTarget singleMessageTarget = messagesTarget.path("{messageId}");
		
		Message message1 = singleMessageTarget.resolveTemplate("messageId", "1").request(MediaType.APPLICATION_JSON).get(Message.class);
		Message message2 = singleMessageTarget.resolveTemplate("messageId", "2").request(MediaType.APPLICATION_JSON).get(Message.class);
		
		Message newMessage = new Message(4, "test4", "Shiyun");
		Response postResponse = messagesTarget.request().post(Entity.json(newMessage));
		System.out.println(postResponse);
		//System.out.println(postResponse.readEntity(Message.class));
		System.out.println(postResponse.readEntity(Message.class).getMessage());
		
		//Response response = client.target("http://localhost:8080/messenger/webapi/messages/1").request().get();
		//Message message = response.readEntity(Message.class);
		
		// same as below
		//WebTarget target = client.target("http://localhost:8080/messenger/webapi/messages/1");
		//Builder builder = target.request();
		//Response response = builder.get();
		//Message message = response.readEntity(Message.class);
		
		// same as below
		//Message message = client.target("http://localhost:8080/messenger/webapi/messages/1").request(MediaType.APPLICATION_JSON).get(Message.class);
		//System.out.println(message1.getMessage());
		//System.out.println(message2.getMessage());

	}

}
