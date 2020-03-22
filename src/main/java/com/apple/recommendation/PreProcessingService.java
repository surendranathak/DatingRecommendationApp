package com.apple.recommendation;

import com.apple.recommendation.tree.TreeNode;
import com.apple.recommendation.util.Gender;

import java.util.List;

public interface PreProcessingService {

     TreeNode preProcessSortByAge(List<Player> players);
     List<Player> preProcessByGender(List<Player> players, Gender genderFilter);

     List<Player> getFemalePlayers();
     List<Player> getMalePlayers();
     TreeNode getMaleRootNode();
     TreeNode getFemaleRootNode();
}
