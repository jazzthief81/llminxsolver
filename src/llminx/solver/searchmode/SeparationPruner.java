package llminx.solver.searchmode;

import util.CoordinateUtil;
import llminx.LLMinx;

/**
 *
 */
public class SeparationPruner extends AbstractLLMinxPruner {

  private byte[] fCorners;
  private byte[] fEdges;

  public SeparationPruner(String aName, String aPath, byte[] aCorners, byte[] aEdges) {
    super(aName, aPath);
    fCorners = aCorners;
    fEdges = aEdges;
  }

  public int getTableSize() {
    return CoordinateUtil.CKN[14][fCorners.length] * CoordinateUtil.CKN[18][fEdges.length];
  }


  public int getCoordinate(LLMinx aMinx) {
    return CoordinateUtil.getSeparationCoordinate(aMinx.getCornerPositions(), fCorners) *
      CoordinateUtil.CKN[18][fEdges.length] +
      CoordinateUtil.getSeparationCoordinate(aMinx.getEdgePositions(), fEdges);
  }

  public void getMinx(int aCoordinate, LLMinx aMinx) {
    CoordinateUtil.getSeparation(aCoordinate % CoordinateUtil.CKN[18][fEdges.length],
      aMinx.getEdgePositions(),
      fEdges);
    CoordinateUtil.getSeparation(aCoordinate / CoordinateUtil.CKN[18][fEdges.length],
      aMinx.getCornerPositions(),
      fCorners);
  }

  public boolean usesCornerPermutation() {
    return fCorners.length > 1;
  }

  public boolean usesEdgePermutation() {
    return fEdges.length > 1;
  }

  public boolean usesCornerOrientation() {
    return false;
  }

  public boolean usesEdgeOrientation() {
    return false;
  }

}
