package com.sckroll.sckrollagile.web.apis;

import java.util.List;

import com.sckroll.sckrollagile.domain.application.BoardService;
import com.sckroll.sckrollagile.domain.application.TeamService;
import com.sckroll.sckrollagile.domain.common.security.CurrentUser;
import com.sckroll.sckrollagile.domain.model.board.Board;
import com.sckroll.sckrollagile.domain.model.team.Team;
import com.sckroll.sckrollagile.domain.model.user.SimpleUser;
import com.sckroll.sckrollagile.web.results.ApiResult;
import com.sckroll.sckrollagile.web.results.MyDataResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MeApiController {

	private TeamService teamService;
	private BoardService boardService;

	public MeApiController(TeamService teamService,
		BoardService boardService) {

		this.teamService = teamService;
		this.boardService = boardService;
	}

	@GetMapping("/api/me")
	public ResponseEntity<ApiResult> getMyData(
		@CurrentUser SimpleUser currentUser) {

		List<Team> teams =
			teamService.findTeamsByUserId(currentUser.getUserId());
		List<Board> boards =
			boardService.findBoardByMembership(currentUser.getUserId());
		return MyDataResult.build(currentUser, teams, boards);
	}
}
