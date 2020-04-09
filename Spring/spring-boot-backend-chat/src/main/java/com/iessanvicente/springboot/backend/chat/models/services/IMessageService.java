package com.iessanvicente.springboot.backend.chat.models.services;

import java.util.List;

import com.iessanvicente.springboot.backend.chat.models.documents.Message;

public interface IMessageService {
	public List<Message> getLast10Messages();
	public Message save(Message message);
}
