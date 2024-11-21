package com.sports.league.model;

import com.sports.league.controller.dto.StandingResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="standings")
public class Standings {

	public Standings() {
		
	}
	
	public Standings(String teamName, String teamId, String countryName,  String leaugeName,
			String leaugeId, Integer overallLeaguePosition) {
		super();
		this.teamName = teamName;
		this.teamId = teamId;
		this.countryName = countryName;
		this.leagueName = leaugeName;
		this.leagueId = leaugeId;
		this.overallLeaguePosition = overallLeaguePosition;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="team_name")
	private String teamName;
	
	@Column(name="team_id")
	private String teamId;

	@Column(name="country_name")
	private String countryName;

	@Column(name="league_name")
	private String leagueName;
	
	@Column(name="league_id")
	private String leagueId;

	@Column(name="overall_league_position")
	private Integer overallLeaguePosition;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountry(String country) {
		this.countryName = country;
	}


	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public Integer getOverallLeaguePosition() {
		return overallLeaguePosition;
	}

	public void setOverallLeaguePosition(Integer overallLeaguePosition) {
		this.overallLeaguePosition = overallLeaguePosition;
	}

	public static Standings fromRecord(StandingResponse response) {
		return new Standings(response.team_name(),response.team_id(),response.country_name(),response.league_name(),response.league_id(),response.overall_league_position());
	}
}
