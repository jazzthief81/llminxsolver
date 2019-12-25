package llminx.solver.searchmode;

import llminx.LLMinx;

/**
 *
 */
public class CompositePruner extends AbstractLLMinxPruner {

  private LLMinxPruner fPrunerA;
  private LLMinxPruner fPrunerB;

  public CompositePruner(String name, String tablePath, LLMinxPruner prunerA, LLMinxPruner prunerB) {
    super(name, tablePath);
    fPrunerA = prunerA;
    fPrunerB = prunerB;
  }


  public int getTableSize() {
    return fPrunerA.getTableSize() * fPrunerB.getTableSize();
  }

  public int getCoordinate(LLMinx aMinx) {
    return fPrunerA.getCoordinate(aMinx) * fPrunerB.getTableSize() + fPrunerB.getCoordinate(aMinx);
  }

  public void getMinx(int aCoordinate, LLMinx aMinx) {
    fPrunerB.getMinx(aCoordinate % fPrunerB.getTableSize(), aMinx);
    fPrunerA.getMinx(aCoordinate / fPrunerB.getTableSize(), aMinx);
  }

  public boolean usesCornerPermutation() {
    return fPrunerA.usesCornerPermutation() || fPrunerB.usesCornerPermutation();
  }

  public boolean usesEdgePermutation() {
    return fPrunerA.usesCornerPermutation() || fPrunerB.usesCornerPermutation();
  }

  public boolean usesCornerOrientation() {
    return fPrunerA.usesCornerOrientation() || fPrunerB.usesCornerOrientation();
  }

  public boolean usesEdgeOrientation() {
    return fPrunerA.usesEdgeOrientation() || fPrunerB.usesEdgeOrientation();
  }

}
