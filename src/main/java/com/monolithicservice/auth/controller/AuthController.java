package com.monolithicservice.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monolithicservice.auth.dto.AuthRequest;
import com.monolithicservice.auth.dto.AuthResponse;
import com.monolithicservice.auth.model.User;
import com.monolithicservice.auth.repository.UserRepository;
import com.monolithicservice.auth.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;

	public AuthController(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
		User user = userRepo.findByEmail(authRequest.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}

		String token = jwtUtil.generateToken(user.getEmail());
		return ResponseEntity.ok(new AuthResponse(token));
	}
}
