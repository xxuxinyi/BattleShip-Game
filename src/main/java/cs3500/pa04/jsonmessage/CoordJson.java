package cs3500.pa04.jsonmessage;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * record Coordinate as json
 *
 * @param x col coordinate
 * @param y row coordinate
 */

public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

}


