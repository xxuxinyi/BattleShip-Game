# change the constructor for Player
  - decrease the number of parameter in constructor, so we can initialize the player in driver class
# change the method placeAdjacent in Coord
  - this stronger the AI when AI choose shots. 
  - I break the situation into more detail case so that AI wouldn't miss some Coord
# change the name method in AIPLayer
  - instead of return fix name, it returns the name field for this player
# add the interface controller
  - we need to make sure we have method round for every controller
# change take shot for AIPlayer
  - which take all the Coord that opponent board have left if we have more Ship count remain
  - to make the server didn't determine as invalid vally
# move the allPossibleH and  allPossibleV method to ShipType from Ship
  - make this method can be used for calculated the weight for each Coord for AI player