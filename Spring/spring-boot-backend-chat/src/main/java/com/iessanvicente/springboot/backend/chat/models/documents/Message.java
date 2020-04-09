package com.iessanvicente.springboot.backend.chat.models.documents;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable{
	@Id
	private String id;
	
	private String username;
	private String text;
	private Long date;
	private String type;
	private String color;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
