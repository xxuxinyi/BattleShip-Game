package cs3500.pa04.jsonmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * record volleyJson that is used in handling take shot
 *
 * @param shots userShots
 */
public record VolleyJson(

    @JsonProperty("coordinates") List<CoordJson> shots) {

}
