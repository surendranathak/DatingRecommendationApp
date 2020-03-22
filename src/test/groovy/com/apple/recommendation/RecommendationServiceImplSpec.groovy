package com.apple.recommendation

import com.apple.recommendation.exception.InValidDataException
import com.apple.recommendation.tree.BinarySearchTree
import com.apple.recommendation.tree.TreeNode
import com.apple.recommendation.util.Gender
import spock.lang.Specification

class RecommendationServiceImplSpec extends Specification {
    List<Player> players = new ArrayList<>();
    Player playerA
    Player playerB
    Player playerC
    Player playerD
    Player playerE
    PreProcessingService preProcessingService
    RecommendationService recommendationService
    void setup(){

        playerA = PlayerFixture.createPlayer([name:  "UserA", gender:  Gender.FEMALE,age:  25, interests:["Cricket", "Tennis"]])
        players.add(playerA);
        playerB = PlayerFixture.createPlayer([name:  "UserB", gender:  Gender.MALE,age:  27, interests:["Cricket","Football","Movies"]]);
        players.add(playerB);
        playerC = PlayerFixture.createPlayer([name:  "UserC", gender:  Gender.MALE,age:  26, interests:["Movies","Tennis","Football","Cricket"]]);
        playerD = PlayerFixture.createPlayer([name:  "UserD",gender:  Gender.FEMALE,age:  24, interests:["Tennis","Football","Badminton"]]);
        playerE =PlayerFixture.createPlayer([name:  "UserE", gender:  Gender.FEMALE,age:  32, interests:["Cricket","Football","Movies","Badminton"]]);
        players.add(playerC);
        players.add(playerD);
        players.add(playerE);
        preProcessingService = new PreProcessingServiceImpl(players)
        recommendationService= new RecommendationServiceImpl(preProcessingService)
    }
    def "test fetchTopMatching Players for Player B"(){
        when:
        List<Player> players= recommendationService.fetchTopMatchingPlayers(playerB,2)
        then:
        players.size() == 2
        players*.name == ["UserA","UserD"]
    }
    def "test fetchTopMatching get Opposite Gender"(){
        when:
        List<Player> players= recommendationService.fetchTopMatchingPlayers(playerD,2)
        then:
        players.size() == 2
        playerD.gender == Gender.FEMALE
        players*.name == ["UserC","UserB"]
        players*.gender == [Gender.MALE,Gender.MALE]
    }

    def "test all players has same age"(){
        given:
        players=[]
        playerA = PlayerFixture.createPlayer([name:  "UserA", gender:  Gender.FEMALE,age:  25, interests:["Cricket", "Tennis"]])
        players.add(playerA);
        playerB = PlayerFixture.createPlayer([name:  "UserB", gender:  Gender.MALE,age:  25, interests:["Cricket","Football"]]);
        players.add(playerB);
        playerC = PlayerFixture.createPlayer([name:  "UserC", gender:  Gender.MALE,age:  25, interests:["Movies","Tennis","Football","Cricket"]]);
        playerD = PlayerFixture.createPlayer([name:  "UserD",gender:  Gender.FEMALE,age:  25, interests:["Tennis","Football","Badminton"]]);
        playerE =PlayerFixture.createPlayer([name:  "UserE", gender:  Gender.FEMALE,age:  25, interests:["Cricket","Football","Movies","Badminton"]]);
        players.add(playerC);
        players.add(playerD);
        players.add(playerE);
        preProcessingService = new PreProcessingServiceImpl(players)
        recommendationService= new RecommendationServiceImpl(preProcessingService)
        when:
        List<Player> players= recommendationService.fetchTopMatchingPlayers(playerE,2)
        then:
        players.size() == 2
        playerD.gender == Gender.FEMALE
        players*.name == ["UserC","UserB"]
        players*.gender == [Gender.MALE,Gender.MALE]
    }

    def "test all players has more age"(){
        given:
        players=[];
        playerA = PlayerFixture.createPlayer([name:  "UserA", gender:  Gender.FEMALE,age:  25, interests:["Cricket", "Tennis"]])
        players.add(playerA);
        playerB = PlayerFixture.createPlayer([name:  "UserB", gender:  Gender.MALE,age:  27, interests:["Cricket","Football"]]);
        players.add(playerB);
        playerC = PlayerFixture.createPlayer([name:  "UserC", gender:  Gender.MALE,age:  29, interests:["Movies","Tennis","Football","Cricket"]]);
        playerD = PlayerFixture.createPlayer([name:  "UserD",gender:  Gender.FEMALE,age:  30, interests:["Tennis","Football","Badminton"]]);
        playerE =PlayerFixture.createPlayer([name:  "UserE", gender:  Gender.FEMALE,age:  32, interests:["Cricket","Football","Movies","Badminton"]]);
        players.add(playerC);
        players.add(playerD);
        players.add(playerE);
        preProcessingService = new PreProcessingServiceImpl(players)
        recommendationService= new RecommendationServiceImpl(preProcessingService)
        when:
        List<Player> players= recommendationService.fetchTopMatchingPlayers(playerA,2)
        then:
        players.size() == 2
        playerD.gender == Gender.FEMALE
        players*.name == ["UserB","UserC"]
        players*.gender == [Gender.MALE,Gender.MALE]
    }

    def "test all players has less age"(){
        given:
        playerA = PlayerFixture.createPlayer([name:  "UserA", gender:  Gender.FEMALE,age:  40, interests:["Cricket", "Tennis"]])
        players.add(playerA);
        playerB = PlayerFixture.createPlayer([name:  "UserB", gender:  Gender.MALE,age:  27, interests:["Cricket","Football"]]);
        players.add(playerB);
        playerC = PlayerFixture.createPlayer([name:  "UserC", gender:  Gender.MALE,age:  29, interests:["Movies","Tennis","Football","Cricket"]]);
        playerD = PlayerFixture.createPlayer([name:  "UserD",gender:  Gender.FEMALE,age:  30, interests:["Tennis","Football","Badminton"]]);
        playerE =PlayerFixture.createPlayer([name:  "UserE", gender:  Gender.FEMALE,age:  32, interests:["Cricket","Football","Movies","Badminton"]]);
        players.add(playerC);
        players.add(playerD);
        players.add(playerE);
        preProcessingService = new PreProcessingServiceImpl(players)
        recommendationService= new RecommendationServiceImpl(preProcessingService)
        when:
        List<Player> players= recommendationService.fetchTopMatchingPlayers(playerA,2)
        then:
        players.size() == 2
        playerD.gender == Gender.FEMALE
        players*.name == ["UserC","UserB"]
        players*.gender == [Gender.MALE,Gender.MALE]
    }
    def "test fetchTopMatching Players include both genders"(){
        when:
        List<Player> players= recommendationService.fetchTopMatchingPlayers(playerB,5)
        then:
        players.size() == 4
        players*.name.flatten() == ["UserA","UserD","UserE", "UserC"]
    }

    def "test insert  Player to tree dynamically"(){
        given:
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        TreeNode treeNode = preProcessingService.femaleRootNode
        Player player = PlayerFixture.createPlayer([gender: Gender.FEMALE])
        when:
        TreeNode root = binarySearchTree.insertRec(treeNode,player.age,player)
        then:
        root !=null
    }

    def "test delete  Player to tree dynamically"(){
        given:
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        TreeNode treeNode = preProcessingService.femaleRootNode
        when:
        TreeNode root = binarySearchTree.deleteRec(treeNode,playerE.age)
        then:
        root !=null
    }

    def "test throw Exception when not match any records"(){
        given:
        players =[]
        playerC =PlayerFixture.createPlayer([name:  "UserE", gender:  Gender.FEMALE,age:  32, interests:["Cricket","Football","Movies","Badminton"]]);
        players.add(playerC);
        preProcessingService = new PreProcessingServiceImpl([])
        recommendationService= new RecommendationServiceImpl(preProcessingService)
        when:
        recommendationService.fetchTopMatchingPlayers(playerD,2)
        then:
        thrown(InValidDataException)
    }

    def "test throw Exception"(){
        given:
        preProcessingService = new PreProcessingServiceImpl([])
        recommendationService= new RecommendationServiceImpl(preProcessingService)
        when:
        recommendationService.fetchTopMatchingPlayers(playerB,2)
        then:
        thrown(InValidDataException)
    }


}
