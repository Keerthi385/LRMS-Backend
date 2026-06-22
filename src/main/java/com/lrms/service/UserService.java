package com.lrms.service;

import java.util.List;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lrms.dto.LoginRequestDTO;
import com.lrms.dto.LoginResponseDTO;
import com.lrms.dto.UserRequestDTO;
import com.lrms.dto.UserResponseDTO;
import com.lrms.entity.User;
import com.lrms.enums.Role;
import com.lrms.exception.ConflictException;
import com.lrms.exception.ResourceNotFoundException;
import com.lrms.exception.UnauthorizedException;
import com.lrms.repository.UserRepository;
import com.lrms.util.JwtUtil;

@Service
public class UserService {
		private final UserRepository userRepository;
		private final BCryptPasswordEncoder passwordEncoder;
		private final JwtUtil jwtUtil;
		
		public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
			this.userRepository = userRepository;
			this.passwordEncoder = passwordEncoder;
			this.jwtUtil = jwtUtil;
		}
		
		public User saveUser(User user) {
			if(userRepository.existsByEmail(user.getEmail())) {
				throw new ConflictException("Email already exists!");
			}
			user.setPassword(
			        passwordEncoder.encode(user.getPassword())
			);

			return userRepository.save(user);
		}
		
		public List<User> getAllUsers() {
		    return userRepository.findAll();
		}
		
		public UserResponseDTO registerUser(UserRequestDTO dto) {
			if(userRepository.existsByEmail(dto.getEmail())) {
				throw new ConflictException("Email already exists!");
			}
			User user = new User();
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setPassword(
				passwordEncoder.encode(dto.getPassword())
			);
			user.setRole(Role.STUDENT);
			
			User savedUser = userRepository.save(user);
			
			UserResponseDTO response = new UserResponseDTO();
			
			response.setId(savedUser.getId());
			response.setName(savedUser.getName());
			response.setEmail(savedUser.getEmail());
			response.setRole(savedUser.getRole());
			
			return response;
		}
		
		public LoginResponseDTO loginUser(LoginRequestDTO dto) {
			User user = userRepository
					.findByEmail(dto.getEmail())
					.orElseThrow(() ->
							new ResourceNotFoundException("Email not found!")
					);
			boolean matched = passwordEncoder.matches(dto.getPassword(), user.getPassword());
			
			if(!matched) {
				throw new UnauthorizedException("Invalid Credentials");
			}
			String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
			return new LoginResponseDTO(token);
		}
		
}
