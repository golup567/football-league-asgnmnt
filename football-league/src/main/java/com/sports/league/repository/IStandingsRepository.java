package com.sports.league.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sports.league.model.Standings;

public interface IStandingsRepository extends JpaRepository<Standings, Integer>{

	@Query("select s from Standings s where s.countryName=?1 and s.leagueName=?2 and s.teamName=?3")
	public Standings getStandings(String country, String league, String team);

}
