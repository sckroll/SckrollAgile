package com.sckroll.sckrollagile.domain.application.impl;

import com.sckroll.sckrollagile.domain.application.UserService;
import com.sckroll.sckrollagile.domain.application.commands.RegistrationCommand;
import com.sckroll.sckrollagile.domain.common.event.DomainEventPublisher;
import com.sckroll.sckrollagile.domain.common.mail.MailManager;
import com.sckroll.sckrollagile.domain.common.mail.MessageVariable;
import com.sckroll.sckrollagile.domain.model.user.RegistrationException;
import com.sckroll.sckrollagile.domain.model.user.RegistrationManagement;
import com.sckroll.sckrollagile.domain.model.user.SimpleUser;
import com.sckroll.sckrollagile.domain.model.user.User;
import com.sckroll.sckrollagile.domain.model.user.UserRepository;
import com.sckroll.sckrollagile.domain.model.user.events.UserRegisteredEvent;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private RegistrationManagement registrationManagement;
	private DomainEventPublisher domainEventPublisher;
	private MailManager mailManager;
	private UserRepository userRepository;

	public UserServiceImpl(RegistrationManagement registrationManagement,
		DomainEventPublisher domainEventPublisher, MailManager mailManager,
		UserRepository userRepository) {
		this.registrationManagement = registrationManagement;
		this.domainEventPublisher = domainEventPublisher;
		this.mailManager = mailManager;
		this.userRepository = userRepository;
	}

	@Override
	public void register(RegistrationCommand command) throws RegistrationException {
		Assert.notNull(command, "Parameter `command` must not be null");

		User newUser = registrationManagement.register(
			command.getUsername(), command.getEmailAddress(), command.getPassword());

		sendWelcomeMessage(newUser);
		domainEventPublisher.publish(new UserRegisteredEvent(newUser));
	}

	@Override
	public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {

		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException("No user found");
		}

		User user;
		if (username.contains("@")) {
			user = userRepository.findByEmailAddress(username);
		} else {
			user = userRepository.findByUsername(username);
		}
		if (user == null) {
			throw new UsernameNotFoundException(
				"No user found by `" + username + "`");
		}
		return new SimpleUser(user);
	}

	private void sendWelcomeMessage(User user) {
		mailManager.send(user.getEmailAddress(),
			"SckrollAgile에 가입하신 것을 환영합니다!", "welcome.ftl",
			MessageVariable.from("user", user));
	}
}
