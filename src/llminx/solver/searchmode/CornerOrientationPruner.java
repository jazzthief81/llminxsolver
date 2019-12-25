package llminx.solver.searchmode;

import util.CoordinateUtil;
import llminx.LLMinx;

/**
 *
 */
public class CornerOrientationPruner extends AbstractLLMinxPruner {

  private byte[] fCorners;

  public CornerOrientationPruner(String aName, String aPath, byte[] aCorners) {
    super(aName, aPath);
    fCorners = aCorners;
  }

  public int getTableSize() {
    return CoordinateUtil.POWERS_OF_THREE[fCorners.length-1];
  }
  
  public int getCoordinate(LLMinx aMinx) {
    return CoordinateUtil.getCornerOrientationCoordinate(aMinx.getCornerOrientations(), fCorners);
  }

  public void getMinx(int aCoordinate, LLMinx aMinx) {
    aMinx.setCornerOrientations(CoordinateUtil.getCornerOrientation(aCoordinate, fCorners));
  }

  public boolean usesCornerPermutation() {
    return false;
  }

  public boolean usesEdgePermutation() {
    return false;
  }

  public boolean usesCornerOrientation() {
    return true;
  }

  public boolean usesEdgeOrientation() {
    return false;
  }

}
