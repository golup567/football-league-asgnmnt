package com.sports.league.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sports.league.client.IStandingsApiClient;
import com.sports.league.controller.dto.StandingResponse;
import com.sports.league.model.Standings;
import com.sports.league.repository.IStandingsRepository;
import com.sports.league.service.IStandingsService;
import com.sports.league.utils.ISchemaUtility;
import com.sports.league.utils.SchemaUtility;

import lombok.extern.slf4j.Slf4j;


@Service("standingsService")
@Slf4j
public class StandingsService implements IStandingsService{

	boolean offlineMode =false ;

	@Autowired
	private IStandingsRepository repository;

	@Autowired
	private IStandingsApiClient standingsApiClient;


	@Autowired
	ISchemaUtility schemaUtility;
	
	@Override
	public Standings getStandings(String country, String league, String team) {
		log.info("inside method getStandings" ,kv("countryName" ,country),kv("league" ,league),kv("team" ,team));
		if (schemaUtility.isOfflineMode()) {
			var data= repository.getStandings(country, league, team);
			log.info("inside method getStandings" ,kv("data" ,data));
			return data;

		}
		var standings = standingsApiClient.fetchStandingsFromApi(country, league, team);
		return standings;
	}

	@Override
	public List<Standings> findAll() {
		return repository.findAll();
	}
	
	@Override
	public void save(List<StandingResponse> standingResponses) {
		for(var stan:standingResponses) {
			save(Standings.fromRecord(stan));
		}
	}
	
	@Override
	public Standings save(Standings standings) {
		return repository.save(standings);
	}

}
