package com.sports.league.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StandingResponse(String team_id,String team_name,String country_id,String country_name,String league_id,String league_name,int overall_league_position) {

}
