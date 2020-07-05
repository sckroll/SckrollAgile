package com.sckroll.sckrollagile.web.apis;

import com.sckroll.sckrollagile.domain.application.TeamService;
import com.sckroll.sckrollagile.domain.common.security.CurrentUser;
import com.sckroll.sckrollagile.domain.model.team.Team;
import com.sckroll.sckrollagile.domain.model.user.SimpleUser;
import com.sckroll.sckrollagile.web.payload.CreateTeamPayload;
import com.sckroll.sckrollagile.web.results.ApiResult;
import com.sckroll.sckrollagile.web.results.CreateTeamResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TeamApiController {

	private TeamService teamService;

	public TeamApiController(TeamService teamService) {
		this.teamService = teamService;
	}

	@PostMapping("/api/teams")
	public ResponseEntity<ApiResult> createTeam(
		@RequestBody CreateTeamPayload payload,
		@CurrentUser SimpleUser currentUser) {

		Team team = teamService.createTeam(payload.toCommand(currentUser.getUserId()));
		return CreateTeamResult.build(team);
	}
}
