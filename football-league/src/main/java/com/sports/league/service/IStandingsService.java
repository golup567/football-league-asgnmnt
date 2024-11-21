package com.sports.league.service;

import java.util.List;

import com.sports.league.controller.dto.StandingResponse;
import com.sports.league.model.Standings;

public interface IStandingsService {

	public Standings getStandings(String country, String league, String team);

	public void save(List<StandingResponse> standingResponses);

	public Standings save(Standings standings);

	public List<Standings> findAll();

}
