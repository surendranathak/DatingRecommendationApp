package com.apple.recommendation;

import com.apple.recommendation.exception.InValidDataException;
import com.apple.recommendation.tree.TreeNode;
import com.apple.recommendation.util.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class RecommendationServiceImpl implements RecommendationService{
    PreProcessingService preProcessingService;

    RecommendationServiceImpl(PreProcessingService preProcessingService){
        this.preProcessingService = preProcessingService;
    }

    @Override
    public List<Player> fetchTopMatchingPlayers(Player player, int topMatches) throws InValidDataException {
        List<Player> players = new ArrayList<>();
             Gender oppositeGender = player.gender == Gender.MALE? Gender.FEMALE: Gender.MALE;
            TreeNode rootNode = getRootNodeByGender(oppositeGender);
            players = closestTopKValues(rootNode, player, topMatches);

            if(topMatches - players.size()>0) {
                rootNode = getRootNodeByGender(player.gender);
                players.addAll(closestTopKValues(rootNode, player, topMatches - players.size()));
            }

        return players;
    }

    public TreeNode getRootNodeByGender(Gender gender){
        if(gender== Gender.MALE){
            return preProcessingService.getMaleRootNode();
        }

        return preProcessingService.getFemaleRootNode();
    }

    private List<Player> closestTopKValues(TreeNode root, Player target, int k) throws InValidDataException {
        List<Player> result = new ArrayList<Player>();
        if (root == null) {
             throw new InValidDataException("Data Not Found ");
        }

        Stack<Player> precedessor = new Stack<Player>();
        Stack<Player> successor = new Stack<Player>();

        getPredecessor(root, target, precedessor);
        getSuccessor(root, target, successor);

        for (int i = 0; i < k; i++) {
            if (precedessor.isEmpty() && !successor.isEmpty()) {
                addMatchingRecords(successor.pop(),target,result);
            } else if (successor.isEmpty() && !precedessor.isEmpty()) {
                addMatchingRecords(precedessor.pop(),target,result);
            } else if ((!precedessor.isEmpty() && !successor.isEmpty()) && Math.abs( precedessor.peek().age - target.age) < Math.abs( successor.peek().age - target.age)) {
                addMatchingRecords(precedessor.pop(),target,result);
            } else if(!successor.isEmpty()){
                addMatchingRecords(successor.pop(),target,result);
            }else {
                if(result.isEmpty()){
                    throw new InValidDataException("Not Found Any close Match Records");
                }
                break;
            }
        }

        return result;
    }

    private  void addMatchingRecords(Player currentPlayer, Player target, List<Player> results){
        if(currentPlayer.name != target.name){
            results.add(currentPlayer);
        }
    }
    private void getPredecessor(TreeNode root, Player target, Stack<Player> precedessor) {
        if (root == null) {
            return;
        }

        getPredecessor(root.left, target, precedessor);

        if (root.key > target.age) {
            return;
        }
        if (!precedessor.isEmpty() && precedessor.peek().age == target.age) {
            int previousCount= intersectNoOfInterests(precedessor.peek().interests, target.interests);
            int latestCount = intersectNoOfInterests(root.player.interests,target.interests);
            if(previousCount > latestCount){
             Player tempPlayer =   precedessor.pop();
                precedessor.push(root.player);
                precedessor.push(tempPlayer);

            }else{
                precedessor.push(root.player);
            }
        }else {
            precedessor.push(root.player);
        }

        getPredecessor(root.right, target, precedessor);
    }

    private void getSuccessor(TreeNode root, Player target, Stack<Player> successor) {
        if (root == null) {
            return;
        }

        getSuccessor(root.right, target, successor);

        if (root.key <= target.age) {
            return;
        }

        successor.push(root.player);

        getSuccessor(root.left, target, successor);
    }

    private int intersectNoOfInterests(List<String> exitingValues, List<String> newValues){
        List<String> intersects = new ArrayList<>();
        intersects = exitingValues.stream()
                .filter(newValues::contains)
                .collect(Collectors.toList());
        return intersects.size();
    }
}
