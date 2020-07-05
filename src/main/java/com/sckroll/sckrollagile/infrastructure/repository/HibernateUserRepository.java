package com.sckroll.sckrollagile.infrastructure.repository;

import javax.persistence.EntityManager;

import com.sckroll.sckrollagile.domain.model.user.User;
import com.sckroll.sckrollagile.domain.model.user.UserRepository;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserRepository extends HibernateSupport<User>
	implements UserRepository {

	public HibernateUserRepository(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public User findByUsername(String username) {
		Query<User> query = getSession().createQuery(
			"from User where username = :username",
			User.class);
		query.setParameter("username", username);
		return query.uniqueResult();
	}

	@Override
	public User findByEmailAddress(String emailAddress) {
		Query<User> query = getSession().createQuery(
			"from User where emailAddress = :emailAddress",
			User.class);
		query.setParameter("emailAddress", emailAddress);
		return query.uniqueResult();
	}

	@Override
	public void save(User user) {
		entityManager.persist(user);
		entityManager.flush();
	}
}
