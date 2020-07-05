package com.sckroll.sckrollagile.domain.model.board.events;

import com.sckroll.sckrollagile.domain.common.event.DomainEvent;
import com.sckroll.sckrollagile.domain.model.board.Board;

public class BoardCreatedEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	private Board board;

	public BoardCreatedEvent(Object source, Board board) {
		super(source);
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}
}
