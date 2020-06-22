package com.sckroll.sckrollagile.web.apis;

import javax.validation.Valid;

import com.sckroll.sckrollagile.domain.application.UserService;
import com.sckroll.sckrollagile.domain.model.user.EmailAddressExistsException;
import com.sckroll.sckrollagile.domain.model.user.RegistrationException;
import com.sckroll.sckrollagile.domain.model.user.UsernameExistsException;
import com.sckroll.sckrollagile.web.payload.RegistrationPayload;
import com.sckroll.sckrollagile.web.results.ApiResult;
import com.sckroll.sckrollagile.web.results.Result;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegistrationApiController {

	private UserService service;

	public RegistrationApiController(UserService service) {
		this.service = service;
	}

	@PostMapping("/api/registrations")
	public ResponseEntity<ApiResult> register(
		@Valid @RequestBody RegistrationPayload payload) {
		try {
			service.register(payload.toCommand());
			return Result.created();
		} catch (RegistrationException e) {
			String errorMessage = "Registration failed";
			if (e instanceof UsernameExistsException) {
				errorMessage = "Username already exists";
			} else if (e instanceof EmailAddressExistsException) {
				errorMessage = "Email address already exists";
			}
			return Result.failure(errorMessage);
		}
	}
}
