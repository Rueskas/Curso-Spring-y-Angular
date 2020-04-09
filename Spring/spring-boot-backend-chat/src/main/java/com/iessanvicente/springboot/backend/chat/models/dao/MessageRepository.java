package com.iessanvicente.springboot.backend.chat.models.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iessanvicente.springboot.backend.chat.models.documents.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	public List<Message> findFirst10ByOrderByDateDesc();
}
