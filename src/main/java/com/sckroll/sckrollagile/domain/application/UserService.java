package com.sckroll.sckrollagile.domain.application;

import com.sckroll.sckrollagile.domain.application.commands.RegistrationCommand;
import com.sckroll.sckrollagile.domain.model.user.RegistrationException;

public interface UserService {

	void register(RegistrationCommand command) throws RegistrationException;
}
