package llminx.solver.searchmode;

import util.CoordinateUtil;
import llminx.LLMinx;

/**
 *
 */
public class EdgeOrientationPruner extends AbstractLLMinxPruner {

  private byte[] fEdges;

  public EdgeOrientationPruner(String aName, String aPath, byte[] aEdges) {
    super(aName, aPath);
    fEdges = aEdges;
  }

  public int getTableSize() {
    return CoordinateUtil.POWERS_OF_TWO[fEdges.length-1];
  }

  public int getCoordinate(LLMinx aMinx) {
    return CoordinateUtil.getEdgeOrientationCoordinate(aMinx.getEdgeOrientations(), fEdges.length);
  }

  public void getMinx(int aCoordinate, LLMinx aMinx) {
    aMinx.setEdgeOrientations(CoordinateUtil.getEdgeOrientation(aCoordinate, fEdges.length));
  }

  public boolean usesCornerPermutation() {
    return false;
  }

  public boolean usesEdgePermutation() {
    return false;
  }

  public boolean usesCornerOrientation() {
    return false;
  }

  public boolean usesEdgeOrientation() {
    return true;
  }

}
