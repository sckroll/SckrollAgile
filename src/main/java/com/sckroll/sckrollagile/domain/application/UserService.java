package com.sckroll.sckrollagile.domain.application;

import com.sckroll.sckrollagile.domain.application.commands.RegistrationCommand;
import com.sckroll.sckrollagile.domain.model.user.RegistrationException;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	void register(RegistrationCommand command) throws RegistrationException;
}
