package com.sckroll.sckrollagile.infrastructure.repository;

import javax.persistence.EntityManager;

import com.sckroll.sckrollagile.domain.model.board.BoardMember;
import com.sckroll.sckrollagile.domain.model.board.BoardMemberRepository;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateBoardMemberRepository
	extends HibernateSupport<BoardMember> implements BoardMemberRepository {

	HibernateBoardMemberRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
