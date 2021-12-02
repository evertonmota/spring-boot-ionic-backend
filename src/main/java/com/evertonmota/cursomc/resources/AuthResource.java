package com.evertonmota.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evertonmota.cursomc.dto.EmailDTO;
import com.evertonmota.cursomc.security.JWTUtil;
import com.evertonmota.cursomc.security.UserSS;
import com.evertonmota.cursomc.services.AuthService;
import com.evertonmota.cursomc.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	
	@RequestMapping(value="/refresh_token", method = RequestMethod.GET)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		
		response.addHeader("Authorization", "Bear " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/forgot", method = RequestMethod.GET)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto){

		authService.sendNewPassword(objDto.getEmail());
		
		return ResponseEntity.noContent().build();
	}
}
