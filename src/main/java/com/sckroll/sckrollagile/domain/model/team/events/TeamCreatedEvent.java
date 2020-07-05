package com.sckroll.sckrollagile.domain.model.team.events;

import com.sckroll.sckrollagile.domain.common.event.DomainEvent;
import com.sckroll.sckrollagile.domain.model.team.Team;

public class TeamCreatedEvent extends DomainEvent {

	private static final long serialVersionUID = -1392851829921049056L;

	private Team team;

	public TeamCreatedEvent(Object source, Team team) {
		super(source);
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}
}
