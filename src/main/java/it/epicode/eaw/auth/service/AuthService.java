package it.epicode.eaw.auth.service;

import it.epicode.eaw.auth.payload.LoginDto;
import it.epicode.eaw.auth.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
