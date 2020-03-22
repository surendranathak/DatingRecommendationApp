package com.apple.recommendation

import com.apple.recommendation.util.Gender

class PlayerFixture {

    public static Player createPlayer(Map overrideProperties =[:]){
        Map defaultProperties = [
            name : "userA",
            age : 24,
            gender : Gender.FEMALE,
            interests : ["Cricket", "Tennis"]
        ]
        return new Player(defaultProperties + overrideProperties);
    }
}
