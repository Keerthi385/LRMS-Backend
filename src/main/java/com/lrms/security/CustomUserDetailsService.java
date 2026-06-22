package com.lrms.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lrms.entity.User;	
import com.lrms.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			User user = userRepository.findByEmail(email)
					.orElseThrow(() ->
						new UsernameNotFoundException("user not found")
							);
			return org.springframework.security.core.userdetails
					.User
					.withUsername(user.getEmail())
					.password(user.getPassword())
					.authorities(
							"ROLE_" + user.getRole().name()
							)
					.build();
	}
	
}
