package cs3500.pa04.jsonmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * record FleetJson that is used when handling set up
 *
 * @param shipJson a list of shipJson
 */
public record FleetJson(

    @JsonProperty("fleet") List<ShipJson> shipJson) {
}
