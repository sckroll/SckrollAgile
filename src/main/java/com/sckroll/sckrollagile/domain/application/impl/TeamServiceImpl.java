package com.sckroll.sckrollagile.domain.application.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.sckroll.sckrollagile.domain.application.TeamService;
import com.sckroll.sckrollagile.domain.application.commands.CreateTeamCommand;
import com.sckroll.sckrollagile.domain.common.event.DomainEventPublisher;
import com.sckroll.sckrollagile.domain.model.team.Team;
import com.sckroll.sckrollagile.domain.model.team.TeamRepository;
import com.sckroll.sckrollagile.domain.model.team.events.TeamCreatedEvent;
import com.sckroll.sckrollagile.domain.model.user.UserId;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

	private TeamRepository teamRepository;
	private DomainEventPublisher domainEventPublisher;

	public TeamServiceImpl(TeamRepository teamRepository,
		DomainEventPublisher domainEventPublisher) {

		this.teamRepository = teamRepository;
		this.domainEventPublisher = domainEventPublisher;
	}

	@Override
	public List<Team> findTeamsByUserId(UserId userId) {
		return teamRepository.findTeamsByUserId(userId);
	}

	@Override
	public Team createTeam(CreateTeamCommand command) {
		Team team = Team.create(command.getName(), command.getUserId());
		teamRepository.save(team);
		domainEventPublisher.publish(new TeamCreatedEvent(this, team));
		return team;
	}
}
