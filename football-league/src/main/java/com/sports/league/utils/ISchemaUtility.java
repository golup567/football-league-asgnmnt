package com.sports.league.utils;

import com.sports.league.controller.dto.Country;
import com.sports.league.controller.dto.League;

public interface ISchemaUtility {

	public Country getCountry(String countryName);

	public League getLeague(String leagueName);

	public boolean isOfflineMode();

	public String toggleOfflineMode();

}
