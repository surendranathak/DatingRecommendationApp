"# DatingRecommendationApp"
# prerequisite to run App
 "Jdk version > JDK 1.8"
 
"Used maven pom.xml for dependecies added"

"for test cases used spoc testing framework(http://spockframework.org/spock/docs/1.3/all_in_one.html) and test cases are written in groovy code(similar to java)
source code written in java code."


"Note: if you are unable to execute tests, please mark test directory as "Test Resources Root".

# How to Run App.
  use  SolutionApp.java to run application.
  
  PreProcessingServiceImpl.java: has   pre prossing logic on player list  like maintaing two lists, one for male player list and other is female player list and also sorted players using binary search
  
  RecommendationServiceImpl.java: has actual logic to find closet players 
  
  RecommendationServiceImplSpec.java : most of the use cases covered to find closet players  
  
  
  
  

