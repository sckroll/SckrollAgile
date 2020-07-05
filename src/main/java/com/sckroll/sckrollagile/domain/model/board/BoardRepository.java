package com.sckroll.sckrollagile.domain.model.board;

import java.util.List;

import com.sckroll.sckrollagile.domain.model.user.UserId;

public interface BoardRepository {

	List<Board> findBoardsByMembership(UserId userId);
	void save(Board board);
}
