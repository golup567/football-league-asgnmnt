package com.sports.league.client;

import com.sports.league.model.Standings;

public interface IStandingsApiClient {

	public Standings  fetchStandingsFromApi(String country, String league, String team);

}
