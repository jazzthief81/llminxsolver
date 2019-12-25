package llminx.solver;

import llminx.LLMinx;
import util.CoordinateUtil;

/**
 *
 */
public class CoordinateUtilTest {

  public static void main(String[] args) {

    LLMinx start = new LLMinx(
      new byte[]{
        LLMinx.UC1_POSITION,
          LLMinx.UC2_POSITION,
          LLMinx.UC3_POSITION,
          LLMinx.UC4_POSITION,
        LLMinx.UC5_POSITION,
        LLMinx.RC1_POSITION,
        LLMinx.RC5_POSITION,
        LLMinx.FC5_POSITION,
        LLMinx.FC1_POSITION,
        LLMinx.FC2_POSITION,
        LLMinx.LC1_POSITION,
        LLMinx.LC2_POSITION,
        LLMinx.BC1_POSITION,
        LLMinx.BC2_POSITION
      },
      new byte[]{
          LLMinx.UE1_POSITION,
          LLMinx.UE2_POSITION,
          LLMinx.UE3_POSITION,
          LLMinx.UE4_POSITION,
          LLMinx.UE5_POSITION,
        LLMinx.RE2_POSITION,
        LLMinx.RE3_POSITION,
        LLMinx.RE4_POSITION,
        LLMinx.FE2_POSITION,
        LLMinx.FE3_POSITION,
        LLMinx.FE4_POSITION,
        LLMinx.FE5_POSITION,
        LLMinx.LE3_POSITION,
        LLMinx.LE4_POSITION,
        LLMinx.LE5_POSITION,
        LLMinx.BE3_POSITION,
        LLMinx.BE4_POSITION,
        LLMinx.BE5_POSITION
      },
//      (LLMinx.NEGATIVE_ORIENTATION << (LLMinx.UC4_POSITION * 2)) +
//        (LLMinx.POSITIVE_ORIENTATION << (LLMinx.FC2_POSITION * 2)),
//        (LLMinx.POSITIVE_ORIENTATION << (LLMinx.UC2_POSITION * 2)),
//        (LLMinx.IGNORE_ORIENTATION << (LLMinx.UC4_POSITION * 2)) +
//        (LLMinx.IGNORE_ORIENTATION << (LLMinx.UC5_POSITION * 2)),
      0,
      0,
      new boolean[14],
      new boolean[18],
      new boolean[14],
      new boolean[18],
      new byte[25],
      (byte) 0
    );
    byte[] cubies = new byte[]{
      LLMinx.UE5_POSITION,
      LLMinx.UE4_POSITION,
      LLMinx.UE3_POSITION,
    };
    int permutation = CoordinateUtil.getPermutationCoordinate(start.getEdgePositions(), cubies);
    System.out.println( permutation );
    byte[] edge_positions = start.getEdgePositions();
    for (int i = 0; i < edge_positions.length; i++) {
      System.out.print(edge_positions[i] + " ");
    }
    CoordinateUtil.getPermutation(permutation, edge_positions, cubies);
    System.out.println();
    for (int i = 0; i < edge_positions.length; i++) {
      System.out.print(edge_positions[i] + " ");
    }
    System.out.println();
  }
}
