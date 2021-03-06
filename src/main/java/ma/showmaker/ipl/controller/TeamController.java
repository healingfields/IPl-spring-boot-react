package ma.showmaker.ipl.controller;

import ma.showmaker.ipl.model.Match;
import ma.showmaker.ipl.model.Team;
import ma.showmaker.ipl.repository.MatchRepository;
import ma.showmaker.ipl.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TeamController {


    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository){
        this.teamRepository = teamRepository;

    }
    @Autowired
    public void setMatchRepository(MatchRepository matchRepository){
        this.matchRepository = matchRepository;

    }

    @GetMapping("/teams")
    public Iterable<Team> getAllTeams(){
        return this.teamRepository.findAll();   
    }


    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Team team = teamRepository.findByName(teamName);
        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName, 4));
        /*for (Match match:matches){
            System.out.println(match.toString());
        }*/
        return team;
    }

    @GetMapping("/team/{teamName}/matches/")
    public List<Match> getTeamMatchesByDate(@PathVariable String teamName, @RequestParam int year){
        /* return matchRepository.getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
                teamName, LocalDate.of(year,1,1), LocalDate.of(year+1,1,1),
                teamName, LocalDate.of(year,1,1), LocalDate.of(year+1,1,1));*/
        return matchRepository.getTeamMatchesBetweenDates(teamName, LocalDate.of(year,1,1), LocalDate.of(year+1,1,1));
    }
}
