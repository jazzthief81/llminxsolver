package llminx.solver;

import llminx.LLMinx;

import java.util.Vector;
import java.util.List;

/**
 *
 */
public class LLMinxSearchProfiler {

  private static final int DEPTH = 5;

  public static void main(String[] args) {
    Vector<LLMinx> queue = new Vector<LLMinx>();
    queue.add(new LLMinx());
    int nodes = 0;
    long time = System.currentTimeMillis();
    while (!queue.isEmpty()) {
      LLMinx minx = queue.remove(0);
      nodes++;

      // add children.
      if (minx.getDepth() < DEPTH) addChildren(minx, queue, true);
    }
    System.out.println("Visit " + nodes + " nodes performed in " + (System.currentTimeMillis() - time) / 1000 + "s");

    LLMinx start = new LLMinx();
    nodes = 0;
    time = System.currentTimeMillis();
    boolean stop = false;
    while (!stop) {
      nodes++;
      stop = nextNode(start);
    }
    System.out.println("Visit " + nodes + " nodes performed in " + (System.currentTimeMillis() - time) / 1000 + "s");
  }

  private static void addChildren(LLMinx aMinx, List aList, boolean aDepthFirst) {
    LLMinx child = aMinx.clone();
    child.moveR();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveRi();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveR2();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveR2i();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveL();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveLi();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveL2();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveL2i();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveU();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveUi();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveU2();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveU2i();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveF();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveFi();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveF2();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveF2i();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveB();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveBi();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveB2();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
    child = aMinx.clone();
    child.moveB2i();
    if (aDepthFirst)
      aList.add(0, child);
    else
      aList.add(child);
  }


  private static boolean nextNode(LLMinx aMinx) {
    if (aMinx.getDepth() < DEPTH) {
      aMinx.move((byte) 0);
      return false;
    }
    else {
      while (aMinx.getLastMove() == LLMinx.B2i_MOVE) {
        aMinx.undoMove();
      }
      if (aMinx.getLastMove() == -1) {
        return true;
      }
      else {
        aMinx.move((byte) (aMinx.undoMove() + 1));
        return false;
      }
    }
  }

}
