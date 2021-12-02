package com.evertonmota.cursomc.dto;

import java.io.Serializable;

public class EmailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public EmailDTO() {
	}

	private String email;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
