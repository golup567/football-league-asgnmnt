package com.sports.league.client.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.league.client.IStandingsApiClient;
import com.sports.league.controller.dto.StandingResponse;
import com.sports.league.exception.ApplicationException;
import com.sports.league.model.Standings;
import com.sports.league.utils.ISchemaUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StandingsApiClient implements IStandingsApiClient{
	
	@Autowired
	ISchemaUtility schemaUtility;
	
	
	@Value("${api.key}") 
	private String apiKey;

	private ObjectMapper mapper=new ObjectMapper();
	
	@Override
	public Standings fetchStandingsFromApi(String countryName, String leagueName, String teamName) {
		log.info("inside method fetchStandingsFromApi" ,kv("countryName" ,countryName),kv("league" ,leagueName),kv("team" ,teamName));
		var country=schemaUtility.getCountry(countryName);
		var league=schemaUtility.getLeague(leagueName);
		
		log.info("inside method fetchStandingsFromApi" ,kv("country" ,country),kv("teamName" ,teamName),kv("league",league));

		try(HttpClient client = HttpClient.newBuilder().build();) {
				HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("https://apiv3.apifootball.com/?action=get_standings&league_id="+league.league_id()+"&APIkey="+apiKey))
			      .header("Content-Type", "application/json")
			      .GET()
			      .build();
		
		var response =client.send(request,BodyHandlers.ofString());
		if(response.statusCode()!=200) {
			throw new RuntimeException("Http request is failed with status:"+response.statusCode());
		}
		List<Standings> standings= parseToStandings(response.body());		
		var result=standings.stream().filter(e->e.getTeamName().equals(teamName)&& e.getCountryName().equals(country.country_name())).findAny();
		if(result.isEmpty()) {
			throw new ApplicationException("No data found..");
		}
		return result.get();
		}catch(ApplicationException ex) {
			log.error("Error occured @fetchStandingsFromApi",kv("@cause1",ex.getMessage()));
			throw new ApplicationException(ex);
		}
		catch(Exception ex) {
			log.error("Error occured @fetchStandingsFromApi",kv("@cause2",ex.getMessage()));

			throw new ApplicationException("Something wrong happend..");
		}
	}

	private List<Standings> parseToStandings(String response) {
		try {
			List<Standings> standings =new ArrayList<Standings>();
			var ar=mapper.readValue(response, StandingResponse[].class); 
			Stream.of(ar).forEach(e->standings.add(Standings.fromRecord(e)));
			return  standings;
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new ApplicationException("Unable to parse response");
		}
	}

}
