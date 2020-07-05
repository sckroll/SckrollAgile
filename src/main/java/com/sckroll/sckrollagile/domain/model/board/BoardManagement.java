package com.sckroll.sckrollagile.domain.model.board;

import com.sckroll.sckrollagile.domain.model.team.TeamId;
import com.sckroll.sckrollagile.domain.model.user.UserId;

import org.springframework.stereotype.Component;

@Component
public class BoardManagement {

	private BoardRepository boardRepository;
	private BoardMemberRepository boardMemberRepository;

	public BoardManagement(BoardRepository boardRepository,
		BoardMemberRepository boardMemberRepository) {

		this.boardRepository = boardRepository;
		this.boardMemberRepository = boardMemberRepository;
	}

	public Board createBoard(UserId creatorId, String name,
		String description, TeamId teamId) {

		Board board = Board.create(
			creatorId, name, description, teamId);
		boardRepository.save(board);

		// 보드 생성자를 보드의 멤버로 추가
		BoardMember boardMember =
			BoardMember.create(board.getId(), creatorId);
		boardMemberRepository.save(boardMember);
		return board;
	}
}
