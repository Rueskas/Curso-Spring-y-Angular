package com.iessanvicente.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.iessanvicente.springboot.backend.chat.models.documents.Message;
import com.iessanvicente.springboot.backend.chat.models.services.IMessageService;

@Controller
public class ChatController {
	
	private String[] colors = {"red", "blue", "purple", "darkred", "teal", "darkslateblue","maroon", "magenta", "orange"};
	@Autowired
	private IMessageService messageService;
	@Autowired
	private SimpMessagingTemplate webSocket;
	@MessageMapping("/message")
	@SendTo("/chat/message")
	public Message receiveMessage(Message message) {
		message.setDate(new Date().getTime());
		if(message.getType().equals("NEW_USER")) {
			message.setColor(colors[new Random().nextInt(colors.length)]);
			message.setText(message.getUsername() + " connected");
			return message;
		} else if (message.getType().equals("MESSAGE")) {
			return messageService.save(message);
		} else {
			return message;
		}
	}
	
	@MessageMapping("/writting")
	@SendTo("/chat/writting")
	public String receiveMessage(String username) {
		return username;
	}
	
	@MessageMapping("/history")
	public void getHistory(String id) {
		System.out.println(messageService.getLast10Messages());
		webSocket.convertAndSend("/chat/history/" + id, messageService.getLast10Messages());
	}
}
