package com.sckroll.sckrollagile.domain.application;

import java.util.List;

import com.sckroll.sckrollagile.domain.application.commands.CreateTeamCommand;
import com.sckroll.sckrollagile.domain.model.team.Team;
import com.sckroll.sckrollagile.domain.model.user.UserId;

public interface TeamService {

	List<Team> findTeamsByUserId(UserId userId);
	Team createTeam(CreateTeamCommand command);
}
