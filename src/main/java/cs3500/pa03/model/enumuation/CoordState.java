package cs3500.pa03.model.enumuation;

/**
 * represent the state for a coord
 */
public enum CoordState {
  MISSED('M'), HIT('H'), DEFAULT('.'), SHIP('S');
  public final char present;

  CoordState(char present) {
    this.present = present;
  }
}
