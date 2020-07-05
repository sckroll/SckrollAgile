package com.sckroll.sckrollagile.web.apis;

import com.sckroll.sckrollagile.domain.application.BoardService;
import com.sckroll.sckrollagile.domain.common.security.CurrentUser;
import com.sckroll.sckrollagile.domain.model.board.Board;
import com.sckroll.sckrollagile.domain.model.user.SimpleUser;
import com.sckroll.sckrollagile.web.payload.CreateBoardPayload;
import com.sckroll.sckrollagile.web.results.ApiResult;
import com.sckroll.sckrollagile.web.results.CreateBoardResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BoardApiController {

	private BoardService boardService;

	public BoardApiController(BoardService boardService) {
		this.boardService = boardService;
	}

	@PostMapping("/api/boards")
	public ResponseEntity<ApiResult> createBoard(
		@RequestBody CreateBoardPayload payload,
		@CurrentUser SimpleUser currentUser) {

		Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
		return CreateBoardResult.build(board);
	}
}
