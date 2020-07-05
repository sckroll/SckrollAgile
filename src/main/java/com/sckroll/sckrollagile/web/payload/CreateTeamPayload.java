package com.sckroll.sckrollagile.web.payload;

import com.sckroll.sckrollagile.domain.application.commands.CreateTeamCommand;
import com.sckroll.sckrollagile.domain.model.user.UserId;

public class CreateTeamPayload {

	private String name;

	public CreateTeamCommand toCommand(UserId userId) {
		return new CreateTeamCommand(userId, name);
	}

	public void setName(String name) {
		this.name = name;
	}
}
