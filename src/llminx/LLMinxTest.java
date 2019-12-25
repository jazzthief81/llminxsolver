package llminx;

/**
 *
 */

public class LLMinxTest {

  public static void main(String[] args) {
    int move_count = 5;
    LLMinx solved = new LLMinx();
    LLMinx minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveF();
    }
    System.out.println("Move F implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveFi();
    }
    System.out.println("Move Fi implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveF2();
    }
    System.out.println("Move F2 implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveF2i();
    }
    System.out.println("Move F2i implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveR();
    }
    System.out.println("Move R implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveRi();
    }
    System.out.println("Move Ri implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveR2();
    }
    System.out.println("Move R2 implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveR2i();
    }
    System.out.println("Move R2i implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveU();
    }
    System.out.println("Move U implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveUi();
    }
    System.out.println("Move Ui implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveU2();
    }
    System.out.println("Move U2 implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveU2i();
    }
    System.out.println("Move U2i implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveL();
    }
    System.out.println("Move L implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveLi();
    }
    System.out.println("Move Li implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveL2();
    }
    System.out.println("Move L2 implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveL2i();
    }
    System.out.println("Move L2i implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveB();
    }
    System.out.println("Move B implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveBi();
    }
    System.out.println("Move Bi implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveB2();
    }
    System.out.println("Move B2 implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    for (int i = 0; i < move_count; i++) {
      minx.moveB2i();
    }
    System.out.println("Move B2i implemented " + (solved.equals(minx) ? "correctly." : "wrongly."));
    minx = new LLMinx();
    minx.moveF();
    minx.moveFi();
    System.out.println("Move F and Fi " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveFi();
    minx.moveF();
    System.out.println("Move Fi and F " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveF2();
    minx.moveF2i();
    System.out.println("Move F2 and F2i " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveF2i();
    minx.moveF2();
    System.out.println("Move F2i and F2 " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveR();
    minx.moveRi();
    System.out.println("Move R and Ri " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveRi();
    minx.moveR();
    System.out.println("Move Ri and R " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveR2();
    minx.moveR2i();
    System.out.println("Move R2 and R2i " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveR2i();
    minx.moveR2();
    System.out.println("Move R2i and R2 " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveL();
    minx.moveLi();
    System.out.println("Move L and Li " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveLi();
    minx.moveL();
    System.out.println("Move Li and L " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveL2();
    minx.moveL2i();
    System.out.println("Move L2 and L2i " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveL2i();
    minx.moveL2();
    System.out.println("Move L2i and L2 " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveU();
    minx.moveUi();
    System.out.println("Move U and Ui " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveUi();
    minx.moveU();
    System.out.println("Move Ui and U " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveU2();
    minx.moveU2i();
    System.out.println("Move U2 and U2i " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveU2i();
    minx.moveU2();
    System.out.println("Move U2i and U2 " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveB();
    minx.moveBi();
    System.out.println("Move B and Bi " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveBi();
    minx.moveB();
    System.out.println("Move Bi and B " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveB2();
    minx.moveB2i();
    System.out.println("Move B2 and B2i " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
    minx = new LLMinx();
    minx.moveB2i();
    minx.moveB2();
    System.out.println("Move B2i and B2 " + (solved.equals(minx) ? "cancel out." : "don't cancel out."));
  }

}
