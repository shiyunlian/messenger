package com.shiyun.messenger.rest.client;

import java.util.List;

import com.shiyun.messenger.model.Message;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class GenericDemo {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		List<Message> response = client.target("http://localhost:8080/messenger/webapi/")
				.path("messages")
				.queryParam("year", 2022)
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Message>>(){});
		
		System.out.println(response);
	}

}
