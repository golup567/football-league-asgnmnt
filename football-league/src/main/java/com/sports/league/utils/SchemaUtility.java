package com.sports.league.utils;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.league.controller.dto.Country;
import com.sports.league.controller.dto.League;
import com.sports.league.exception.ApplicationException;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service("schemaUtility")
@Slf4j
public class SchemaUtility implements ISchemaUtility{
	@Value("classpath:/schema/leagues.json") // Do not use field injection
	private Resource leaguesResource;

	@Value("classpath:/schema/countries.json") // Do not use field injection
	private Resource countriesResource;

	private boolean  offlineMode=false;
	
	private List<League> leagues;
	private List<Country> countries;
	private ObjectMapper mapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		loadConutries();
		loadLeagues();
	}

	@Override
	public Country getCountry(String countryName) {
		log.info("inside method getCountry" ,kv("countryName" ,countryName));
		for(var country:countries) {
			if(country.country_name().equals(countryName)) {
				return country;
			}
		}
		throw new ApplicationException("Invalid country name:"+countryName);
	}

	@Override
	public League getLeague(String leagueName) {
		log.info("inside method getLeague" ,kv("leagueName" ,leagueName));
		for(var league:leagues) {
			if(league.league_name().equals(leagueName)) {
				return league;
			}
		}
		throw new ApplicationException("Invalid league name:"+leagueName);
	}

	private void loadLeagues() {
		TypeReference<List<League>> typeReference = new TypeReference<List<League>>(){};
		try {
			InputStream inputStream = leaguesResource.getInputStream();
			leagues= mapper.readValue(inputStream,typeReference);

			log.info("Leagues loaded in system");

		} catch (IOException e){
			log.error("Unable to load Leagues" ,kv("@cause" ,e.getMessage()));
		}
	}

	private void loadConutries() {
		TypeReference<List<Country>> typeReference = new TypeReference<List<Country>>(){};
		try {
			InputStream inputStream = leaguesResource.getInputStream();
			countries = mapper.readValue(inputStream,typeReference);

			log.info("Countries loaded in system");

		} catch (IOException e){
			log.error("Unable to load Countries" ,kv("@cause" ,e.getMessage()));
		}
	}

	@Override
	public boolean isOfflineMode() {
		return offlineMode;
	}
	
	@Override
	public String toggleOfflineMode() {
		offlineMode = !offlineMode;
		return "Offline mode: " + offlineMode;
	}
}
