package com.apple.recommendation;

import com.apple.recommendation.exception.InValidDataException;
import com.apple.recommendation.util.Gender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionApp {


    public static void main(String args[]) throws InValidDataException {
        SolutionApp solutionApp = new SolutionApp();
        RecommendationService recommendationService;
        List<Player> players = new ArrayList<>();

        players.add(solutionApp.createPlayer("UserA", Gender.FEMALE,25, Arrays.asList(new String[]{"Cricket","Tennis"})));
        Player player1 = solutionApp.createPlayer("UserB", Gender.MALE,27, Arrays.asList(new String[]{"Cricket","Football","Movies"}));
        players.add(player1);
        players.add(solutionApp.createPlayer("UserC", Gender.MALE,26, Arrays.asList(new String[]{"Movies","Tennis","Football","Cricket"})));
        players.add(solutionApp.createPlayer("UserD", Gender.FEMALE,24, Arrays.asList(new String[]{"Tennis","Football","Badminton"})));
        Player player2 =solutionApp.createPlayer("UserE", Gender.FEMALE,32, Arrays.asList(new String[]{"Cricket","Football","Movies","Badminton"}));
        Player player3 =solutionApp.createPlayer("UserF", Gender.MALE,33, Arrays.asList(new String[]{"Cricket","Football","Movies","Badminton"}));

        players.add(player2);
        players.add(player3);
        PreProcessingService preProcessingService = new PreProcessingServiceImpl(players);
        recommendationService = new RecommendationServiceImpl(preProcessingService);
        List<Player> playerList =recommendationService.fetchTopMatchingPlayers(player1, 2);
        for (Player player : playerList) {
            System.out.println(player.name);
        }
    }



    private Player createPlayer(String name, Gender gender, int age, List<String> interests){
        Player player = new Player();
        player.name = name;
        player.age = age;
        player.gender = gender;
        player.interests = interests;

        return player;
    }
}
