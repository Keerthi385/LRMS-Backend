package com.lrms.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrms.dto.LoginRequestDTO;
import com.lrms.dto.LoginResponseDTO;
import com.lrms.dto.UserRequestDTO;
import com.lrms.dto.UserResponseDTO;
import com.lrms.entity.User;
import com.lrms.service.UserService;
import com.lrms.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	public UserController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/register")
	public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO dto) {
		return userService.registerUser(dto);
	}
	
	@PostMapping("/login")
	public LoginResponseDTO loginUser(@Valid @RequestBody LoginRequestDTO dto) {
		return userService.loginUser(dto);
	}
}
