package cs3500.pa04.jsonmessage;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * record ShipJson that is used when hadndling set up
 *
 * @param start the start coord for place a ship
 * @param shipLength the size of a ship
 * @param direction the direction of a ship
 */
public record ShipJson(

    @JsonProperty("coord") CoordJson start,
    @JsonProperty("length") int shipLength,
    @JsonProperty("direction") String direction){
}
