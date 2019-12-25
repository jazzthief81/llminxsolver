package llminx.solver.searchmode;

import llminx.LLMinx;
import util.CoordinateUtil;

/**
 *
 */
public class CornerPermutationPruner extends AbstractLLMinxPruner {

  private byte[] fCorners;

  public CornerPermutationPruner(String aName, String aPath, byte[] aCorners) {
    super(aName, aPath);
    fCorners = aCorners;
  }

  public int getTableSize() {
    return CoordinateUtil.FAC[fCorners.length]/2;
  }

  public int getCoordinate(LLMinx aMinx) {
    return CoordinateUtil.getPermutationCoordinate(aMinx.getCornerPositions(), fCorners);
  }

  public void getMinx(int aCoordinate, LLMinx aMinx) {
    CoordinateUtil.getPermutation(aCoordinate, aMinx.getCornerPositions(), fCorners);
  }

  public boolean usesCornerPermutation() {
    return true;
  }

  public boolean usesEdgePermutation() {
    return false;
  }

  public boolean usesCornerOrientation() {
    return false;
  }

  public boolean usesEdgeOrientation() {
    return false;
  }
        
}
