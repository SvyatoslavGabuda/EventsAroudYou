package it.epicode.eaw.auth.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.eaw.auth.entity.ERole;
import it.epicode.eaw.auth.payload.JWTAuthResponse;
import it.epicode.eaw.auth.payload.LoginDto;
import it.epicode.eaw.auth.payload.RegisterDto;
import it.epicode.eaw.auth.service.AuthService;
import it.epicode.eaw.models.Utente;
import it.epicode.eaw.service.UtenteService;

@CrossOrigin(origins = "*", maxAge = 600000)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	UtenteService uSer;
	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// Build Login REST API
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {

		String token = authService.login(loginDto);
		Utente u = uSer.findByUsername(loginDto.getUsername());

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setUsername(loginDto.getUsername());
		jwtAuthResponse.setAccessToken(token);
		
		if(u.getRoles().isEmpty()) {			
		jwtAuthResponse.getRoles().add(ERole.ROLE_USER) ;		
		}else {			
			Set<ERole> a = new HashSet<>();
			u.getRoles().forEach(role-> a.add(role.getRoleName()));
			jwtAuthResponse.setRoles(a);
		}


		return ResponseEntity.ok(jwtAuthResponse);
	}

	// Build Register REST API
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
