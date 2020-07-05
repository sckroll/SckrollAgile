package com.sckroll.sckrollagile.domain.application;

import java.util.List;

import com.sckroll.sckrollagile.domain.application.commands.CreateBoardCommand;
import com.sckroll.sckrollagile.domain.model.board.Board;
import com.sckroll.sckrollagile.domain.model.user.UserId;

public interface BoardService {

	List<Board> findBoardByMembership(UserId userId);
	Board createBoard(CreateBoardCommand command);
}
