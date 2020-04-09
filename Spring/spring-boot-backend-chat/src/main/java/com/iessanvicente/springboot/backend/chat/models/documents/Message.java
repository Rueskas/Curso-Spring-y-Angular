package com.iessanvicente.springboot.backend.chat.models.documents;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable{
	
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
