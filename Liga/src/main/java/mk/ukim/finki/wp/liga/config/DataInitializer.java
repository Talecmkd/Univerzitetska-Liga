package mk.ukim.finki.wp.liga.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.service.basketball.BasketballMatchService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballPlayerService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballTeamService;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final FootballTeamService footballTeamService;
    private final FootballPlayerService footballPlayerService;
    private final FootballMatchService footballMatchService;
    private final BasketballPlayerService basketballPlayerService;
    private final BasketballTeamService basketballTeamService;
    private final BasketballMatchService basketballMatchService;

    public DataInitializer(FootballTeamService footballTeamService, FootballPlayerService footballPlayerService, FootballMatchService footballMatchService, BasketballPlayerService basketballPlayerService, BasketballTeamService basketballTeamService, BasketballMatchService basketballMatchService) {
        this.footballTeamService = footballTeamService;
        this.footballPlayerService = footballPlayerService;
        this.footballMatchService = footballMatchService;
        this.basketballPlayerService = basketballPlayerService;
        this.basketballTeamService = basketballTeamService;
        this.basketballMatchService = basketballMatchService;
    }


    @PostConstruct
    @Transactional
    public void initData() {
        for (int i = 1; i <= 8; i++) {
            this.footballTeamService.create("Team " + i,  null);
        }

        for (int i = 1; i <= 8; i++) {
            this.footballPlayerService.create(null, "Player " + i, "Surname"+i, null, 211111 + i, null, null, this.footballTeamService.findById((long) i));
        }
        for (int i = 1; i <= 8; i++) {
            FootballTeam home = this.footballTeamService.findByName("Team " + i);
            FootballTeam away = this.footballTeamService.findByName("Team " + (i % 5 + 1)); // Rotate to the first team after the last one

            // Assuming the teams exist and service methods are correctly implemented
            if (home != null && away != null) {
                this.footballMatchService.createAndAddToFixtures(home, away, 0, 0, LocalDateTime.now());
            }
        }
        for (int i = 1; i <= 8; i++) {
            this.basketballTeamService.create("Team " + i,  null);
        }
        for (int i = 1; i <= 8; i++) {
            this.basketballPlayerService.create(null, "Player " + i, "Surname"+i, null, i, null, null, this.basketballTeamService.findById((long) i));
        }
        for (int i = 1; i <= 8; i++) {
            BasketballTeam home = this.basketballTeamService.findByName("Team " + i);
            BasketballTeam away = this.basketballTeamService.findByName("Team " + (i % 5 + 1)); // Rotate to the first team after the last one

            // Assuming the teams exist and service methods are correctly implemented
            if (home != null && away != null) {
                this.basketballMatchService.createAndAddToFixtures(home, away, 0, 0, LocalDateTime.now());
            }
        }
    }
}
