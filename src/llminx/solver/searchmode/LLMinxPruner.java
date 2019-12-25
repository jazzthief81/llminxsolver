package llminx.solver.searchmode;

import llminx.LLMinx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 */
public interface LLMinxPruner {

  public String getName();

  public boolean isPrecomputed(LLMinxMetric aMetric);

  public byte[] loadTable(LLMinxMetric aMetric);

  public void saveTable(byte[] aTable, LLMinxMetric aMetric);

  public int getTableSize();

  public int getCoordinate(LLMinx aMinx);

  public void getMinx(int aCoordinate, LLMinx aMinx);

  public boolean usesCornerPermutation();

  public boolean usesEdgePermutation();

  public boolean usesCornerOrientation();

  public boolean usesEdgeOrientation();

}
