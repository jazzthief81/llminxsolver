package llminx;

/**
 *
 */

public class LLMinxProfiler {

  private static int MOVE_COUNT = 10000000;

  public static void main(String[] args) {
    LLMinx.setMaxDepth(MOVE_COUNT);
    LLMinx.setKeepMoves(false );
    LLMinx minx = new LLMinx();
    long time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveF();
    }
    System.out.println("Performed " + MOVE_COUNT + " F moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveFi();
    }
    System.out.println("Performed " + MOVE_COUNT + " Fi moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveF2();
    }
    System.out.println("Performed " + MOVE_COUNT + " F2 moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveF2i();
    }
    System.out.println("Performed " + MOVE_COUNT + " F2i moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveR();
    }
    System.out.println("Performed " + MOVE_COUNT + " R moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveRi();
    }
    System.out.println("Performed " + MOVE_COUNT + " Ri moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveR2();
    }
    System.out.println("Performed " + MOVE_COUNT + " R2 moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveR2i();
    }
    System.out.println("Performed " + MOVE_COUNT + " R2i moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveU();
    }
    System.out.println("Performed " + MOVE_COUNT + " U moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveUi();
    }
    System.out.println("Performed " + MOVE_COUNT + " Ui moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveU2();
    }
    System.out.println("Performed " + MOVE_COUNT + " U2 moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveU2i();
    }
    System.out.println("Performed " + MOVE_COUNT + " U2i moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveL();
    }
    System.out.println("Performed " + MOVE_COUNT + " L moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveLi();
    }
    System.out.println("Performed " + MOVE_COUNT + " Li moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveL2();
    }
    System.out.println("Performed " + MOVE_COUNT + " L2 moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveL2i();
    }
    System.out.println("Performed " + MOVE_COUNT + " L2i moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveB();
    }
    System.out.println("Performed " + MOVE_COUNT + " B moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveBi();
    }
    System.out.println("Performed " + MOVE_COUNT + " Bi moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveB2();
    }
    System.out.println("Performed " + MOVE_COUNT + " B2 moves in " + (System.currentTimeMillis() - time) + "ms");
    minx = new LLMinx();
    time = System.currentTimeMillis();
    for (int i = 0; i < MOVE_COUNT; i++) {
      minx.moveB2i();
    }
    System.out.println("Performed " + MOVE_COUNT + " B2i moves in " + (System.currentTimeMillis() - time) + "ms");

    for (byte i = 0; i < LLMinx.MOVE_STRINGS.length; i++) {
      minx = new LLMinx();
      time = System.currentTimeMillis();
      for (int move = 0; move < MOVE_COUNT; move++) {
        minx.move(i);
      }
      System.out.println("Performed " + MOVE_COUNT + " " + LLMinx.MOVE_STRINGS[i] + " moves using switch in " + (System.currentTimeMillis() - time) + "ms");
    }

  }

}
