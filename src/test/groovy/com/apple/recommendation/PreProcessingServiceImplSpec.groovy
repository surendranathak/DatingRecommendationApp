package com.apple.recommendation

import com.apple.recommendation.tree.TreeNode
import com.apple.recommendation.util.Gender
import spock.lang.Specification

class PreProcessingServiceImplSpec extends Specification {
    List<Player> players = new ArrayList<>();
    Player playerA
    Player playerB
    Player playerE
    Player playerF
    Player playerG
    PreProcessingService preProcessingService
    def setup(){

        playerA = PlayerFixture.createPlayer([name:  "UserA", gender:  Gender.FEMALE,age:  25, interests:["Cricket","Tennis"]])
        players.add(playerA);
        playerB = PlayerFixture.createPlayer([name:  "UserB", gender:  Gender.MALE,age:  27, interests:["Cricket","Football","Movies"]]);
        players.add(playerB);
        players.add(PlayerFixture.createPlayer([name:  "UserC", gender:  Gender.MALE,age:  26, interests:["Movies","Tennis","Football","Cricket"]]));
        players.add(PlayerFixture.createPlayer([name:  "UserD", gender:  Gender.FEMALE,age:  24, interests:["Tennis","Football","Badminton"]]));
        playerE =PlayerFixture.createPlayer([name:  "UserE", gender:  Gender.FEMALE,age:  32, interests:["Cricket","Football","Movies","Badminton"]]);
        playerF =PlayerFixture.createPlayer([name:  "UserF", gender:  Gender.MALE,age:  33, interests:["Cricket","Football","Movies","Badminton"]]);
        players.add(playerE);
        players.add(playerF);
        playerG = PlayerFixture.createPlayer([name:  "UserG", gender:  Gender.FEMALE,age:  50, interests:["Cricket","Football"]])
        players.add(playerG);
        preProcessingService = new PreProcessingServiceImpl(players)
    }
    def "test getFemale players"(){
        when:
        List<Player> femalePlayers= preProcessingService.getFemalePlayers()
        then:
        femalePlayers.size() == 4
        femalePlayers.findAll {it -> it.gender== Gender.MALE} == []
    }

    def "test getMale players"(){
        when:
        List<Player> malePlayers= preProcessingService.getMalePlayers()
        then:
        malePlayers.size() == 3
        malePlayers.findAll {it -> it.gender== Gender.FEMALE} == []
    }

    def "test getMale Root Node"(){
        when:
        TreeNode treeNode= preProcessingService.getMaleRootNode()
        then:
        treeNode  !=null
    }

    def "test getFemale Root Node"(){
        when:
        TreeNode treeNode= preProcessingService.getFemaleRootNode()
        then:
        treeNode  !=null
    }

    def "test empty Players "(){
        given:
        players =[]
        preProcessingService = new PreProcessingServiceImpl(players)
        when:
        List<Player> malePlayers = preProcessingService.getMalePlayers()
        then:
        malePlayers  == []
    }

}
