package it.epicode.eaw.auth.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.epicode.eaw.auth.entity.ERole;
import it.epicode.eaw.auth.entity.Role;
import it.epicode.eaw.auth.exception.MyAPIException;
import it.epicode.eaw.auth.payload.LoginDto;
import it.epicode.eaw.auth.payload.RegisterDto;
import it.epicode.eaw.auth.repository.RoleRepository;
import it.epicode.eaw.auth.security.JwtTokenProvider;
import it.epicode.eaw.models.Utente;
import it.epicode.eaw.repository.UtenteRepo;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UtenteRepo userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UtenteRepo userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {

		// add check for username exists in database
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
		}

		// add check for email exists in database
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
		}

		Utente user = new Utente();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setLastname(registerDto.getLastname());
		user.setDataReggistrazione(LocalDate.now());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER).get();
		roles.add(userRole);

		user.setRoles(roles);
		System.out.println(user);
		userRepository.save(user);

		return "User registered successfully!.";
	}

	public void changePermissions(long id, ERole roles) {
		Set<Role> role = new HashSet<Role>();
		role.add(roleRepository.findByRoleName(roles).get());
		Utente u = userRepository.findById(id).get();
		u.setRoles(role);
		userRepository.save(u);
	}

	public ERole getRole(String role) {
		if (role.equals("ROLE_ADMIN"))
			return ERole.ROLE_ADMIN;
		else if (role.equals("ROLE_MODERATOR"))
			return ERole.ROLE_MODERATOR;
		else
			return ERole.ROLE_USER;
	}
}
