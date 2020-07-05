package com.sckroll.sckrollagile.domain.model.board;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sckroll.sckrollagile.domain.common.model.AbstractBaseEntity;
import com.sckroll.sckrollagile.domain.model.user.UserId;

@Entity
@Table(name = "board_member")
public class BoardMember extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BoardMemberId id;

	public static BoardMember create(BoardId boardId, UserId userId) {
		BoardMember boardMember = new BoardMember();
		boardMember.id = new BoardMemberId(boardId, userId);
		return boardMember;
	}

	public BoardId getBoardId() {
		return id.getBoardId();
	}

	public UserId getUserId() {
		return id.getUserId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BoardMember)) return false;
		BoardMember that = (BoardMember) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
	  	return Objects.hash(id);
	}

	@Override
	public String toString() {
	  	return "BoardMember{" + "id=" + id + '}';
	}

	@Embeddable
	public static class BoardMemberId implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "board_id")
		private long boardId;

		@Column(name = "user_id")
		private long userId;

		public BoardMemberId() {
		}

		public BoardMemberId(BoardId boardId, UserId userId) {
			this.boardId = boardId.value();
			this.userId = userId.value();
		}

		public BoardId getBoardId() {
			return new BoardId(boardId);
		}

		public UserId getUserId() {
			return new UserId(userId);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof BoardMemberId)) return false;
			BoardMemberId that = (BoardMemberId) o;
			return boardId == that.boardId && userId == that.userId;
		}

		@Override
		public int hashCode() {
		  	return Objects.hash(boardId, userId);
		}

		@Override
		public String toString() {
		  	return "BoardMemberId{" + "boardId=" + boardId +
				", userId=" + userId + '}';
		}
	}
}
