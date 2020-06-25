package com.sckroll.sckrollagile.domain.model.user;

import com.sckroll.sckrollagile.domain.model.user.events.UserRegisteredEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventHandler {

	private final static Logger log =
		LoggerFactory.getLogger(UserRegisteredEventHandler.class);

	@EventListener(UserRegisteredEvent.class)
	public void handleEvent(UserRegisteredEvent event) {
		log.debug("Handling `{}` registration event",
			event.getUser().getEmailAddress());
		// 이것은 오직 도메인 이벤트 리스너를 위한 데모임
	}
}
