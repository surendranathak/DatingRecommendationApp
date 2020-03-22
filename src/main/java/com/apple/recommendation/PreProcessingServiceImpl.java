package com.apple.recommendation;

import com.apple.recommendation.tree.BinarySearchTree;
import com.apple.recommendation.tree.TreeNode;
import com.apple.recommendation.util.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PreProcessingServiceImpl implements PreProcessingService {
    public List<Player> femalePlayers = new ArrayList<>();
    public List<Player> malePlayers = new ArrayList<>();
    public TreeNode femaleRootNode = null;
    public TreeNode maleRootNode = null;

    PreProcessingServiceImpl(List<Player> players) {
        femalePlayers = preProcessByGender(players,Gender.FEMALE);
        malePlayers = preProcessByGender(players,Gender.MALE);
        femaleRootNode = preProcessSortByAge(femalePlayers);
        maleRootNode = preProcessSortByAge(malePlayers);
    }
    public TreeNode preProcessSortByAge(List<Player> players ){
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        for (Player player : players){
            binarySearchTree.insert(player.age,player);
        }
        return binarySearchTree.root;
    }
    public List<Player> preProcessByGender(List<Player> players , Gender genderFilter){
        Predicate<Player> byGender = player -> player.gender == genderFilter;
        List<Player> resultPlayers = players.stream().filter(byGender).collect(Collectors.toList());
        return resultPlayers;
    }

    public List<Player> getFemalePlayers() {
        return femalePlayers;
    }

    public List<Player> getMalePlayers() {
        return malePlayers;
    }

    public TreeNode getFemaleRootNode() {
        return femaleRootNode;
    }

    public TreeNode getMaleRootNode() {
        return maleRootNode;
    }

}
