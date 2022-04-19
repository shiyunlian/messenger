package com.shiyun.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.shiyun.messenger.database.DatabaseClass;
import com.shiyun.messenger.exception.DataNotFoundException;
import com.shiyun.messenger.model.Message;

import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;

public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1L, "test1", "Elsa1"));
		messages.put(2L, new Message(2L, "test2", "Elsa2"));
	}
	
	public List<Message> getAllMessages(){
		// pass a collection to the ArrayList constructor initialized the List with those elements
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<>();
		// get a cal instance
		Calendar cal = Calendar.getInstance();
		for (Message message: messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		// turn the messages.values() into a list
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		
		// if start+size is greater than the size of list, return an empty list
		if(start + size > list.size()) {
			return new ArrayList<Message>();
		}
		
		// return the sublist of the list
		return list.subList(start, start + size);
	}
	
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null) {
			throw new DataNotFoundException("Message with id " + id + " not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId()<=0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}

}
