package com.iessanvicente.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.iessanvicente.springboot.backend.chat.models.documents.Message;

@Controller
public class ChatController {
	
	private String[] colors = {"red", "blue", "purple", "darkred", "teal", "darkslateblue","maroon", "magenta", "orange"};
	
	@MessageMapping("/message")
	@SendTo("/chat/message")
	public Message receiveMessage(Message message) {
		message.setDate(new Date().getTime());
		System.out.println(message);
		if(message.getType().equals("NEW_USER")) {
			message.setColor(colors[new Random().nextInt(colors.length)]);
			message.setText(message.getUsername() + " connected");
		}
		return message;
	}
	
	@MessageMapping("/writting")
	@SendTo("/chat/writting")
	public String receiveMessage(String username) {
		return username;
	}
}
