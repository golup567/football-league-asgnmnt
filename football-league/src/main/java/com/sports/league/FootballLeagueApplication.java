package com.sports.league;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.league.controller.dto.StandingResponse;
import com.sports.league.service.IStandingsService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class FootballLeagueApplication {

	 @Value("classpath:/schema/standings.json") // Do not use field injection
	 private Resource resource;
	 

	public static void main(String[] args) {
		SpringApplication.run(FootballLeagueApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(IStandingsService standingsService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<StandingResponse>> typeReference = new TypeReference<List<StandingResponse>>(){};
			InputStream inputStream = resource.getInputStream();
			try {
				List<StandingResponse> standingResponses = mapper.readValue(inputStream,typeReference);
				if(standingsService.findAll().size()<1) {
					standingsService.save(standingResponses);
				}
			log.info("data loaded in system");

			} catch (IOException e){
				log.error("Unable to save standings" ,kv("@cause" ,e.getMessage()));
			}
		};
	}
}
