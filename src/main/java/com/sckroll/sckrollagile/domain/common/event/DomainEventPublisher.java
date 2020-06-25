package com.sckroll.sckrollagile.domain.common.event;

public interface DomainEventPublisher {

	void publish(DomainEvent event);
}
