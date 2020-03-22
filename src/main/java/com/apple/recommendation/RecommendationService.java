package com.apple.recommendation;

import com.apple.recommendation.exception.InValidDataException;

import java.util.List;

public interface RecommendationService {

    List<Player> fetchTopMatchingPlayers(Player player, int topMatches) throws InValidDataException;
}
