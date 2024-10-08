package mk.ukim.finki.wp.liga.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Data
@Setter
@Getter
@Entity
public class FootballTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<FootballPlayer> players;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "football_team_fixtures",
            joinColumns = @JoinColumn(name = "football_team_id"),
            inverseJoinColumns = @JoinColumn(name = "football_match_id")
    )
    private List<FootballMatch> footballFixtures;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "football_team_results",
            joinColumns = @JoinColumn(name = "football_team_id"),
            inverseJoinColumns = @JoinColumn(name = "football_match_id")
    )
    private List<FootballMatch> footballResults;

    private int teamMatchesPlayed;
    private int teamLeaguePoints;
    private int teamWins;
    private int teamLoses;
    private int teamDraws;
    @Lob
    @Column(name="football_team_logo")
    private byte [] logo;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "team_last_five_matches", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "match_result")
    private List<String> lastFiveMatches = new ArrayList<>();

    public FootballTeam(String teamName, byte [] logo) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
        this.footballFixtures = new ArrayList<>();
        this.footballResults = new ArrayList<>();
        this.teamMatchesPlayed = 0;
        this.teamLeaguePoints = 0;
        this.teamWins = 0;
        this.teamLoses = 0;
        this.teamDraws = 0;
        this.logo = logo;
    }
    public void addMatchResult(String result) {
        if (lastFiveMatches.size() == 5) {
            lastFiveMatches.remove(0);
        }
        lastFiveMatches.add(result);
    }


    public FootballTeam() {

    }
    @Override
    public String toString() {
        return "FootballTeam{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
