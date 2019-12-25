package llminx.solver.searchmode;

import util.CoordinateUtil;
import llminx.LLMinx;

/**
 *
 */
public class EdgePermutationPruner extends AbstractLLMinxPruner {

  private byte[] fEdges;

  public EdgePermutationPruner(String aName, String aPath, byte[] aEdges) {
    super(aName, aPath);
    fEdges = aEdges;
  }

  public int getTableSize() {
    return CoordinateUtil.FAC[fEdges.length]/2;
  }

  public int getCoordinate(LLMinx aMinx) {
    return CoordinateUtil.getPermutationCoordinate(aMinx.getEdgePositions(), fEdges);
  }

  public void getMinx(int aCoordinate, LLMinx aMinx) {
    CoordinateUtil.getPermutation(aCoordinate, aMinx.getEdgePositions(), fEdges);
  }

  public boolean usesCornerPermutation() {
    return false;
  }

  public boolean usesEdgePermutation() {
    return true;
  }

  public boolean usesCornerOrientation() {
    return false;
  }

  public boolean usesEdgeOrientation() {
    return false;
  }

}
