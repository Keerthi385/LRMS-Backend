package com.lrms.dto;

import com.lrms.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {
	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@NotBlank(message = "Email canoot be empty")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Name cannot be empty")
	private String password;
	
	private Role role;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
