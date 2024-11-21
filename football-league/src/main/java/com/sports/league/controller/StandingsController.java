package com.sports.league.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sports.league.model.Standings;
import com.sports.league.service.IStandingsService;
import com.sports.league.utils.ISchemaUtility;

@RestController
@RequestMapping("standings")
public class StandingsController {

	@Autowired
	IStandingsService service;
	
	@Autowired
	ISchemaUtility schemaUtility;


	@GetMapping("/get_standings")
	public Standings getStandings(@RequestParam("country") String country,
			@RequestParam("league") String league,
			@RequestParam("team") String team) {

		return service.getStandings(country, league, team);
	}

	@GetMapping("/standings-toggle")
	public String toggleOfflineMode() {
		return schemaUtility.toggleOfflineMode();
	}

}
