package cs3500.pa04.jsonmessage;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * record GameTypeJson that is used when handling join in
 *
 * @param playerName a player's name
 * @param gameType a game type (single or multi)
 */
public record GameTypeJson(

    @JsonProperty("name") String playerName,
    @JsonProperty("game-type") String gameType) {
}
