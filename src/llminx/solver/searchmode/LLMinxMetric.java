package llminx.solver.searchmode;

/**
 *
 */
public enum LLMinxMetric {

  FIFTH("Fifth turn metric"), FACE("Face turn metric");

  private String fDescription;

  LLMinxMetric(String description) {
    fDescription = description;
  }

  public String getDescription() {
    return fDescription;
  }

  public String toString() {
    return fDescription;
  }

}
