package com.sckroll.sckrollagile.domain.application.impl;

import com.sckroll.sckrollagile.domain.application.UserService;
import com.sckroll.sckrollagile.domain.application.commands.RegistrationCommand;
import com.sckroll.sckrollagile.domain.model.user.RegistrationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	// private RegistrationManagement registrationManagement;
	// private DomainEventPublisher domainEventPublisher;
	// private MailManager mailManager;

	// public UserServiceImpl(RegistrationManagement registrationManagement,
	// 					   DomainEventPublisher domainEventPublisher,
	// 					   MailManager mailManager) {
	// 	this.registrationManagement = registrationManagement;
	// 	this.domainEventPublisher = domainEventPublisher;
	// 	this.mailManager = mailManager;
	// }

	@Override
	public void register(RegistrationCommand command) throws RegistrationException {
		// Assert.notNull(command, "Parameter `command` must not be null");

		// User newUser = registrationManagement.register(
		// 	command.getUsername(),
		// 	command.getEmailAddress(),
		// 	command.getPassword());

		// sendWelcomeMessage(newUser);
		// domainEventPublisher.publish(new UserRegisteredEvent(newUser));
	}

	// private void sendWelcomeMessage(User user) {
	// 	mailManager.send(
	// 		user.getEmailAddress(),
	// 		"Welcome to TaskAgile",
	// 		"welcome.ftl",
	// 		MessageVariable.from("user", user)
	// 	);
	// }
}
