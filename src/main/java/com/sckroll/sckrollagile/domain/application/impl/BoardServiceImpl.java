package com.sckroll.sckrollagile.domain.application.impl;

import java.util.List;

import com.sckroll.sckrollagile.domain.application.BoardService;
import com.sckroll.sckrollagile.domain.application.commands.CreateBoardCommand;
import com.sckroll.sckrollagile.domain.common.event.DomainEventPublisher;
import com.sckroll.sckrollagile.domain.model.board.Board;
import com.sckroll.sckrollagile.domain.model.board.BoardManagement;
import com.sckroll.sckrollagile.domain.model.board.BoardRepository;
import com.sckroll.sckrollagile.domain.model.board.events.BoardCreatedEvent;
import com.sckroll.sckrollagile.domain.model.user.UserId;

public class BoardServiceImpl implements BoardService {

	private BoardRepository boardRepository;
	private BoardManagement boardManagement;
	private DomainEventPublisher domainEventPublisher;

	public BoardServiceImpl(BoardRepository boardRepository,
		BoardManagement boardManagement,
		DomainEventPublisher domainEventPublisher) {

		this.boardRepository = boardRepository;
		this.boardManagement = boardManagement;
		this.domainEventPublisher = domainEventPublisher;
	}

	@Override
	public List<Board> findBoardByMembership(UserId userId) {
		return boardRepository.findBoardsByMembership(userId);
	}

	@Override
	public Board createBoard(CreateBoardCommand command) {
		Board board = boardManagement.createBoard(
			command.getUserId(), command.getName(),
			command.getDescription(), command.getTeamId());
		domainEventPublisher.publish(new BoardCreatedEvent(this, board));
		return board;
	}
}
