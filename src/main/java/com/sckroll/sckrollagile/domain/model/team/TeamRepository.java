package com.sckroll.sckrollagile.domain.model.team;

import java.util.List;

import com.sckroll.sckrollagile.domain.model.user.UserId;

public interface TeamRepository {

	List<Team> findTeamsByUserId(UserId userId);
	void save(Team team);
}
