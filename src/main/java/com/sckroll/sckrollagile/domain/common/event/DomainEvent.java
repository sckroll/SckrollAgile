package com.sckroll.sckrollagile.domain.common.event;

import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7823322301695252133L;

	public DomainEvent(Object source) {
		super(source);
	}

	public long occuredAt() {
		// 잠재적인 구현체의 타임스탬프를 반환
		return getTimestamp();
	}
}
