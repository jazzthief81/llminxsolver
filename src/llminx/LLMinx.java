package llminx;

/**
 *
 */
public class LLMinx implements Comparable<LLMinx> {

  public static final byte UC1_POSITION = 0;
  public static final byte UC2_POSITION = 1;
  public static final byte UC3_POSITION = 2;
  public static final byte UC4_POSITION = 3;
  public static final byte UC5_POSITION = 4;
  public static final byte RC1_POSITION = 5;
  public static final byte RC5_POSITION = 6;
  public static final byte FC5_POSITION = 7;
  public static final byte FC1_POSITION = 8;
  public static final byte FC2_POSITION = 9;
  public static final byte LC1_POSITION = 10;
  public static final byte LC2_POSITION = 11;
  public static final byte BC1_POSITION = 12;
  public static final byte BC2_POSITION = 13;
  public static final byte UE1_POSITION = 0;
  public static final byte UE2_POSITION = 1;
  public static final byte UE3_POSITION = 2;
  public static final byte UE4_POSITION = 3;
  public static final byte UE5_POSITION = 4;
  public static final byte RE2_POSITION = 5;
  public static final byte RE3_POSITION = 6;
  public static final byte RE4_POSITION = 7;
  public static final byte FE2_POSITION = 8;
  public static final byte FE3_POSITION = 9;
  public static final byte FE4_POSITION = 10;
  public static final byte FE5_POSITION = 11;
  public static final byte LE3_POSITION = 12;
  public static final byte LE4_POSITION = 13;
  public static final byte LE5_POSITION = 14;
  public static final byte BE3_POSITION = 15;
  public static final byte BE4_POSITION = 16;
  public static final byte BE5_POSITION = 17;

  public static final byte NEUTRAL_ORIENTATION = 0;
  public static final byte POSITIVE_ORIENTATION = 1;
  public static final byte NEGATIVE_ORIENTATION = 2;
  public static final byte IGNORE_ORIENTATION = 3;

  public static final byte R_MOVE = 0;
  public static final byte Ri_MOVE = 1;
  public static final byte R2_MOVE = 2;
  public static final byte R2i_MOVE = 3;
  public static final byte L_MOVE = 4;
  public static final byte Li_MOVE = 5;
  public static final byte L2_MOVE = 6;
  public static final byte L2i_MOVE = 7;
  public static final byte U_MOVE = 8;
  public static final byte Ui_MOVE = 9;
  public static final byte U2_MOVE = 10;
  public static final byte U2i_MOVE = 11;
  public static final byte F_MOVE = 12;
  public static final byte Fi_MOVE = 13;
  public static final byte F2_MOVE = 14;
  public static final byte F2i_MOVE = 15;
  public static final byte B_MOVE = 16;
  public static final byte Bi_MOVE = 17;
  public static final byte B2_MOVE = 18;
  public static final byte B2i_MOVE = 19;

  private static byte[] CORNER_ORIENTATION_CW = new byte[]{
    POSITIVE_ORIENTATION,
    NEGATIVE_ORIENTATION,
    NEUTRAL_ORIENTATION,
    IGNORE_ORIENTATION,
  };

  private static byte[] CORNER_ORIENTATION_ANTI_CW = new byte[]{
    NEGATIVE_ORIENTATION,
    NEUTRAL_ORIENTATION,
    POSITIVE_ORIENTATION,
    IGNORE_ORIENTATION
  };

  public static byte[] INVERSE_MOVES = new byte[]{
    Ri_MOVE,
    R_MOVE,
    R2i_MOVE,
    R2_MOVE,
    Li_MOVE,
    L_MOVE,
    L2i_MOVE,
    L2_MOVE,
    Ui_MOVE,
    U_MOVE,
    U2i_MOVE,
    U2_MOVE,
    Fi_MOVE,
    F_MOVE,
    F2i_MOVE,
    F2_MOVE,
    Bi_MOVE,
    B_MOVE,
    B2i_MOVE,
    B2_MOVE
  };

  public static final String[] POSITION_STRINGS = {};
  public static final String[] MOVE_STRINGS = {
    "R   ",
    "R\'  ",
    "R2  ",
    "R2\' ",
    "L   ",
    "L\'  ",
    "L2  ",
    "L2\' ",
    "U   ",
    "U\'  ",
    "U2  ",
    "U2\' ",
    "F   ",
    "F\'  ",
    "F2  ",
    "F2\' ",
    "B   ",
    "B\'  ",
    "B2  ",
    "B2\' ",
  };

  private static int sMaxDepth = 100;
  private static boolean sKeepMoves = true;

  private byte[] fCornerPositions;
  private boolean[] fIgnoreCornerPositions;
  private byte[] fEdgePositions;
  private boolean[] fIgnoreEdgePositions;
  private int fCornerOrientations;
  private boolean[] fIgnoreCornerOrientations;
  private int fEdgeOrientations;
  private boolean[] fIgnoreEdgeOrientations;
  private byte[] fMoves;
  private int fDepth;
  private byte fLastMove = -1;

  public LLMinx() {
    fCornerPositions = new byte[14];
    fIgnoreCornerPositions = new boolean[14];
    fIgnoreCornerOrientations = new boolean[14];
    for (int i = 0; i < fCornerPositions.length; i++) {
      fCornerPositions[i] = (byte) i;
      fIgnoreCornerPositions[i] = false;
      fIgnoreCornerOrientations[i] = false;
    }
    fEdgePositions = new byte[18];
    fIgnoreEdgePositions = new boolean[18];
    fIgnoreEdgeOrientations = new boolean[18];
    for (int i = 0; i < fEdgePositions.length; i++) {
      fEdgePositions[i] = (byte) i;
      fIgnoreEdgePositions[i] = false;
      fIgnoreEdgeOrientations[i] = false;
    }
    fCornerOrientations = 0;
    fEdgeOrientations = 0;
    fMoves = new byte[sMaxDepth];
    fDepth = 0;
  }

  public LLMinx(byte[] aCornerPositions,
                byte[] aEdgePositions,
                int aCornerOrientations,
                int aEdgeOrientation,
                boolean[] aIgnoreCornerPieces,
                boolean[] aIgnoreEdgePieces,
                boolean[] aIgnoreCornerOrientations,
                boolean[] aIgnoreEdgeOrientations,
                byte[] aMoves,
                int aDepth) {
    fCornerPositions = aCornerPositions;
    fEdgePositions = aEdgePositions;
    fCornerOrientations = aCornerOrientations;
    fEdgeOrientations = aEdgeOrientation;
    fIgnoreCornerPositions = aIgnoreCornerPieces;
    fIgnoreEdgePositions = aIgnoreEdgePieces;
    fIgnoreCornerOrientations = aIgnoreCornerOrientations;
    fIgnoreEdgeOrientations = aIgnoreEdgeOrientations;
    fMoves = aMoves;
    fDepth = aDepth;
  }

  public static int getMaxDepth() {
    return sMaxDepth;
  }

  public static void setMaxDepth(int aMaxDepth) {
    LLMinx.sMaxDepth = aMaxDepth;
  }

  public static boolean isKeepMoves() {
    return sKeepMoves;
  }

  public static void setKeepMoves(boolean keepMoves) {
    sKeepMoves = keepMoves;
  }

  public byte[] getMoves() {
    return fMoves;
  }

  public String getGeneratingMoves() {
    StringBuffer move_string = new StringBuffer(fDepth * 4);
    byte[] moves = new byte[fDepth];
    System.arraycopy(fMoves, 0, moves, 0, fDepth);
    while (simplifyMoves(moves)) ;
    for (int i = 0; i < fDepth && moves[i] != -1; i++) {
      move_string.append(MOVE_STRINGS[moves[i]]);
    }
    return move_string.toString();
  }

  private boolean simplifyMoves(byte[] aMoves) {
    boolean simplified = false;
    for (int i = 1; i < aMoves.length && aMoves[i] != -1; i++) {
      if ((aMoves[i] == aMoves[i - 1]) && (aMoves[i] % 4 < 2)) {
        aMoves[i - 1] += 2;
        simplified = true;
      }
      if (simplified) {
        if (i < aMoves.length - 1) {
          System.arraycopy(aMoves, i + 1, aMoves, i, aMoves.length - i - 1);
        }
        aMoves[aMoves.length - 1] = -1;
        break;
      }
    }
    return simplified;
  }

  public String getSolvingMoves() {
    StringBuffer moves = new StringBuffer(fDepth * 4);
    for (int i = fDepth - 1; i >= 0; i--) {
      moves.append(MOVE_STRINGS[INVERSE_MOVES[fMoves[i]]]);
    }
    return moves.toString();
  }

  public byte getLastMove() {
    return fLastMove;
  }

  public int getDepth() {
    return fDepth;
  }

  public byte[] getCornerPositions() {
    return fCornerPositions;
  }

  public byte[] getEdgePositions() {
    return fEdgePositions;
  }

  public int getCornerOrientations() {
    return fCornerOrientations;
  }

  public int getCornerOrientation(byte aPiece) {
    return (fCornerOrientations >> (aPiece * 2)) & 3;
  }

  public void setCornerOrientation(byte aPiece, int aOrientation) {
    fCornerOrientations = (fCornerOrientations & (~(3 << (aPiece * 2)))) | (aOrientation << (aPiece * 2));
  }

  public int getEdgeOrientations() {
    return fEdgeOrientations;
  }

  public int getEdgeOrientation(byte aPiece) {
    return (fEdgeOrientations >> aPiece) & 1;
  }

  public void setEdgeOrientation(byte aPiece, int aOrientation) {
    fEdgeOrientations = (fEdgeOrientations & (~(1 << aPiece))) | (aOrientation << aPiece);
  }

  public void setCornerOrientations(int cornerOrientations) {
    fCornerOrientations = cornerOrientations;
  }

  public void setEdgeOrientations(int edgeOrientations) {
    fEdgeOrientations = edgeOrientations;
  }

  public boolean[] getIgnoreCornerPositions() {
    return fIgnoreCornerPositions;
  }

  public void setIgnoreCornerPositions(boolean[] ignoreCornerPieces) {
    fIgnoreCornerPositions = ignoreCornerPieces;
  }

  public boolean[] getIgnoreEdgePositions() {
    return fIgnoreEdgePositions;
  }

  public void setIgnoreEdgePositions(boolean[] ignoreEdgePieces) {
    fIgnoreEdgePositions = ignoreEdgePieces;
  }

  public boolean[] getIgnoreCornerOrientations() {
    return fIgnoreCornerOrientations;
  }

  public void setIgnoreCornerOrientations(boolean[] aIgnoreCornerOrientations) {
    fIgnoreCornerOrientations = aIgnoreCornerOrientations;
  }

  public boolean[] getIgnoreEdgeOrientations() {
    return fIgnoreEdgeOrientations;
  }

  public void setIgnoreEdgeOrientations(boolean[] aIgnoreEdgeOrientations) {
    fIgnoreEdgeOrientations = aIgnoreEdgeOrientations;
  }

  public void move(byte aMove) {
    switch (aMove) {
      case R_MOVE:
        moveR();
        break;
      case Ri_MOVE:
        moveRi();
        break;
      case R2_MOVE:
        moveR2();
        break;
      case R2i_MOVE:
        moveR2i();
        break;
      case L_MOVE:
        moveL();
        break;
      case Li_MOVE:
        moveLi();
        break;
      case L2_MOVE:
        moveL2();
        break;
      case L2i_MOVE:
        moveL2i();
        break;
      case U_MOVE:
        moveU();
        break;
      case Ui_MOVE:
        moveUi();
        break;
      case U2_MOVE:
        moveU2();
        break;
      case U2i_MOVE:
        moveU2i();
        break;
      case F_MOVE:
        moveF();
        break;
      case Fi_MOVE:
        moveFi();
        break;
      case F2_MOVE:
        moveF2();
        break;
      case F2i_MOVE:
        moveF2i();
        break;
      case B_MOVE:
        moveB();
        break;
      case Bi_MOVE:
        moveBi();
        break;
      case B2_MOVE:
        moveB2();
        break;
      case B2i_MOVE:
        moveB2i();
        break;
    }
  }

  public byte undoMove() {
    byte last_move = fLastMove;
    move(INVERSE_MOVES[fLastMove]);
    fDepth -= 2;
    if (fDepth > 0) {
      fLastMove = fMoves[fDepth - 1];
    }
    else {
      fLastMove = -1;
    }
    return last_move;
  }

  public int getQTMLength() {
    int length = 0;
    for (int i = 0; i < fDepth; i++) {
      length += (fMoves[i] % 4) / 2 + 1;

    }
    return length;
  }

  public int getHTMLength() {
    int length = fDepth;
    for (int i = 1; i < fDepth; i++) {
      if ((fMoves[i] / 4) == (fMoves[i - 1] / 4)) {
        length--;
      }

    }
    return length;
  }

  public void moveU() {
    byte old_uc1_pos = fCornerPositions[UC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_uc1_or = getCornerOrientation(UC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[UC1_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = old_uc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[UE5_POSITION];
    fEdgePositions[UE5_POSITION] = fEdgePositions[UE4_POSITION];
    fEdgePositions[UE4_POSITION] = fEdgePositions[UE3_POSITION];
    fEdgePositions[UE3_POSITION] = fEdgePositions[UE2_POSITION];
    fEdgePositions[UE2_POSITION] = old_ue1_pos;
    setCornerOrientation(UC1_POSITION, getCornerOrientation(UC5_POSITION));
    setCornerOrientation(UC5_POSITION, getCornerOrientation(UC4_POSITION));
    setCornerOrientation(UC4_POSITION, getCornerOrientation(UC3_POSITION));
    setCornerOrientation(UC3_POSITION, getCornerOrientation(UC2_POSITION));
    setCornerOrientation(UC2_POSITION, old_uc1_or);
    setEdgeOrientation(UE1_POSITION, getEdgeOrientation(UE5_POSITION));
    setEdgeOrientation(UE5_POSITION, getEdgeOrientation(UE4_POSITION));
    setEdgeOrientation(UE4_POSITION, getEdgeOrientation(UE3_POSITION));
    setEdgeOrientation(UE3_POSITION, getEdgeOrientation(UE2_POSITION));
    setEdgeOrientation(UE2_POSITION, old_ue1_or);
    if (sKeepMoves) fMoves[fDepth] = U_MOVE;
    fDepth++;
    fLastMove = U_MOVE;
  }

  public void moveUi() {
    byte old_uc1_pos = fCornerPositions[UC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_uc1_or = getCornerOrientation(UC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[UC1_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = old_uc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[UE2_POSITION];
    fEdgePositions[UE2_POSITION] = fEdgePositions[UE3_POSITION];
    fEdgePositions[UE3_POSITION] = fEdgePositions[UE4_POSITION];
    fEdgePositions[UE4_POSITION] = fEdgePositions[UE5_POSITION];
    fEdgePositions[UE5_POSITION] = old_ue1_pos;
    setCornerOrientation(UC1_POSITION, getCornerOrientation(UC2_POSITION));
    setCornerOrientation(UC2_POSITION, getCornerOrientation(UC3_POSITION));
    setCornerOrientation(UC3_POSITION, getCornerOrientation(UC4_POSITION));
    setCornerOrientation(UC4_POSITION, getCornerOrientation(UC5_POSITION));
    setCornerOrientation(UC5_POSITION, old_uc1_or);
    setEdgeOrientation(UE1_POSITION, getEdgeOrientation(UE2_POSITION));
    setEdgeOrientation(UE2_POSITION, getEdgeOrientation(UE3_POSITION));
    setEdgeOrientation(UE3_POSITION, getEdgeOrientation(UE4_POSITION));
    setEdgeOrientation(UE4_POSITION, getEdgeOrientation(UE5_POSITION));
    setEdgeOrientation(UE5_POSITION, old_ue1_or);
    if (sKeepMoves) fMoves[fDepth] = Ui_MOVE;
    fDepth++;
    fLastMove = Ui_MOVE;
  }

  public void moveU2() {
    byte old_uc1_pos = fCornerPositions[UC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_uc1_or = getCornerOrientation(UC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[UC1_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = old_uc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[UE4_POSITION];
    fEdgePositions[UE4_POSITION] = fEdgePositions[UE2_POSITION];
    fEdgePositions[UE2_POSITION] = fEdgePositions[UE5_POSITION];
    fEdgePositions[UE5_POSITION] = fEdgePositions[UE3_POSITION];
    fEdgePositions[UE3_POSITION] = old_ue1_pos;
    setCornerOrientation(UC1_POSITION, getCornerOrientation(UC4_POSITION));
    setCornerOrientation(UC4_POSITION, getCornerOrientation(UC2_POSITION));
    setCornerOrientation(UC2_POSITION, getCornerOrientation(UC5_POSITION));
    setCornerOrientation(UC5_POSITION, getCornerOrientation(UC3_POSITION));
    setCornerOrientation(UC3_POSITION, old_uc1_or);
    setEdgeOrientation(UE1_POSITION, getEdgeOrientation(UE4_POSITION));
    setEdgeOrientation(UE4_POSITION, getEdgeOrientation(UE2_POSITION));
    setEdgeOrientation(UE2_POSITION, getEdgeOrientation(UE5_POSITION));
    setEdgeOrientation(UE5_POSITION, getEdgeOrientation(UE3_POSITION));
    setEdgeOrientation(UE3_POSITION, old_ue1_or);
    if (sKeepMoves) fMoves[fDepth] = U2_MOVE;
    fDepth++;
    fLastMove = U2_MOVE;
  }

  public void moveU2i() {
    byte old_uc1_pos = fCornerPositions[UC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_uc1_or = getCornerOrientation(UC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[UC1_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = old_uc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[UE3_POSITION];
    fEdgePositions[UE3_POSITION] = fEdgePositions[UE5_POSITION];
    fEdgePositions[UE5_POSITION] = fEdgePositions[UE2_POSITION];
    fEdgePositions[UE2_POSITION] = fEdgePositions[UE4_POSITION];
    fEdgePositions[UE4_POSITION] = old_ue1_pos;
    setCornerOrientation(UC1_POSITION, getCornerOrientation(UC3_POSITION));
    setCornerOrientation(UC3_POSITION, getCornerOrientation(UC5_POSITION));
    setCornerOrientation(UC5_POSITION, getCornerOrientation(UC2_POSITION));
    setCornerOrientation(UC2_POSITION, getCornerOrientation(UC4_POSITION));
    setCornerOrientation(UC4_POSITION, old_uc1_or);
    setEdgeOrientation(UE1_POSITION, getEdgeOrientation(UE3_POSITION));
    setEdgeOrientation(UE3_POSITION, getEdgeOrientation(UE5_POSITION));
    setEdgeOrientation(UE5_POSITION, getEdgeOrientation(UE2_POSITION));
    setEdgeOrientation(UE2_POSITION, getEdgeOrientation(UE4_POSITION));
    setEdgeOrientation(UE4_POSITION, old_ue1_or);
    if (sKeepMoves) fMoves[fDepth] = U2i_MOVE;
    fDepth++;
    fLastMove = U2i_MOVE;
  }

  public void moveL() {
    byte old_lc1_pos = fCornerPositions[LC1_POSITION];
    byte old_ue2 = fEdgePositions[UE2_POSITION];
    int old_lc1_or = getCornerOrientation(LC1_POSITION);
    int old_ue2_or = getEdgeOrientation(UE2_POSITION);
    fCornerPositions[LC1_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = old_lc1_pos;
    fEdgePositions[UE2_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = fEdgePositions[LE4_POSITION];
    fEdgePositions[LE4_POSITION] = fEdgePositions[LE3_POSITION];
    fEdgePositions[LE3_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = old_ue2;
    setCornerOrientation(LC1_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(FC2_POSITION)]);
    setCornerOrientation(FC2_POSITION, getCornerOrientation(UC4_POSITION));
    setCornerOrientation(UC4_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC5_POSITION)]);
    setCornerOrientation(UC5_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(LC2_POSITION)]);
    setCornerOrientation(LC2_POSITION, old_lc1_or);
    setEdgeOrientation(UE2_POSITION, getEdgeOrientation(LE5_POSITION));
    setEdgeOrientation(LE5_POSITION, getEdgeOrientation(LE4_POSITION));
    setEdgeOrientation(LE4_POSITION, getEdgeOrientation(LE3_POSITION));
    setEdgeOrientation(LE3_POSITION, getEdgeOrientation(FE5_POSITION));
    setEdgeOrientation(FE5_POSITION, old_ue2_or);
    if (sKeepMoves) fMoves[fDepth] = L_MOVE;
    fDepth++;
    fLastMove = L_MOVE;
  }

  public void moveLi() {
    byte old_lc1_pos = fCornerPositions[LC1_POSITION];
    byte old_ue2 = fEdgePositions[UE2_POSITION];
    int old_lc1_or = getCornerOrientation(LC1_POSITION);
    int old_ue2_or = getEdgeOrientation(UE2_POSITION);
    fCornerPositions[LC1_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = old_lc1_pos;
    fEdgePositions[UE2_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = fEdgePositions[LE3_POSITION];
    fEdgePositions[LE3_POSITION] = fEdgePositions[LE4_POSITION];
    fEdgePositions[LE4_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = old_ue2;
    setCornerOrientation(LC1_POSITION, getCornerOrientation(LC2_POSITION));
    setCornerOrientation(LC2_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC5_POSITION)]);
    setCornerOrientation(UC5_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC4_POSITION)]);
    setCornerOrientation(UC4_POSITION, getCornerOrientation(FC2_POSITION));
    setCornerOrientation(FC2_POSITION, CORNER_ORIENTATION_ANTI_CW[old_lc1_or]);
    setEdgeOrientation(UE2_POSITION, getEdgeOrientation(FE5_POSITION));
    setEdgeOrientation(FE5_POSITION, getEdgeOrientation(LE3_POSITION));
    setEdgeOrientation(LE3_POSITION, getEdgeOrientation(LE4_POSITION));
    setEdgeOrientation(LE4_POSITION, getEdgeOrientation(LE5_POSITION));
    setEdgeOrientation(LE5_POSITION, old_ue2_or);
    if (sKeepMoves) fMoves[fDepth] = Li_MOVE;
    fDepth++;
    fLastMove = Li_MOVE;
  }

  public void moveL2() {
    byte old_lc1_pos = fCornerPositions[LC1_POSITION];
    byte old_ue2 = fEdgePositions[UE2_POSITION];
    int old_lc1_or = getCornerOrientation(LC1_POSITION);
    int old_ue2_or = getEdgeOrientation(UE2_POSITION);
    fCornerPositions[LC1_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = old_lc1_pos;
    fEdgePositions[UE2_POSITION] = fEdgePositions[LE4_POSITION];
    fEdgePositions[LE4_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = fEdgePositions[LE3_POSITION];
    fEdgePositions[LE3_POSITION] = old_ue2;
    setCornerOrientation(LC1_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC4_POSITION)]);
    setCornerOrientation(UC4_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(LC2_POSITION)]);
    setCornerOrientation(LC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(FC2_POSITION)]);
    setCornerOrientation(FC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC5_POSITION)]);
    setCornerOrientation(UC5_POSITION, CORNER_ORIENTATION_CW[old_lc1_or]);
    setEdgeOrientation(UE2_POSITION, getEdgeOrientation(LE4_POSITION));
    setEdgeOrientation(LE4_POSITION, getEdgeOrientation(FE5_POSITION));
    setEdgeOrientation(FE5_POSITION, getEdgeOrientation(LE5_POSITION));
    setEdgeOrientation(LE5_POSITION, getEdgeOrientation(LE3_POSITION));
    setEdgeOrientation(LE3_POSITION, old_ue2_or);
    if (sKeepMoves) fMoves[fDepth] = L2_MOVE;
    fDepth++;
    fLastMove = L2_MOVE;
  }

  public void moveL2i() {
    byte old_lc1_pos = fCornerPositions[LC1_POSITION];
    byte old_ue2 = fEdgePositions[UE2_POSITION];
    int old_lc1_or = getCornerOrientation(LC1_POSITION);
    int old_ue2_or = getEdgeOrientation(UE2_POSITION);
    fCornerPositions[LC1_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = old_lc1_pos;
    fEdgePositions[UE2_POSITION] = fEdgePositions[LE3_POSITION];
    fEdgePositions[LE3_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = fEdgePositions[LE4_POSITION];
    fEdgePositions[LE4_POSITION] = old_ue2;
    setCornerOrientation(LC1_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC5_POSITION)]);
    setCornerOrientation(UC5_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(FC2_POSITION)]);
    setCornerOrientation(FC2_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(LC2_POSITION)]);
    setCornerOrientation(LC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC4_POSITION)]);
    setCornerOrientation(UC4_POSITION, CORNER_ORIENTATION_ANTI_CW[old_lc1_or]);
    setEdgeOrientation(UE2_POSITION, getEdgeOrientation(LE3_POSITION));
    setEdgeOrientation(LE3_POSITION, getEdgeOrientation(LE5_POSITION));
    setEdgeOrientation(LE5_POSITION, getEdgeOrientation(FE5_POSITION));
    setEdgeOrientation(FE5_POSITION, getEdgeOrientation(LE4_POSITION));
    setEdgeOrientation(LE4_POSITION, old_ue2_or);
    if (sKeepMoves) fMoves[fDepth] = L2i_MOVE;
    fDepth++;
    fLastMove = L2i_MOVE;
  }

  public void moveR() {
    byte old_rc1_pos = fCornerPositions[RC1_POSITION];
    byte old_eu5_pos = fEdgePositions[UE5_POSITION];
    int old_rc1_or = getCornerOrientation(RC1_POSITION);
    int old_ue5_or = getEdgeOrientation(UE5_POSITION);
    fCornerPositions[RC1_POSITION] = fCornerPositions[RC5_POSITION];
    fCornerPositions[RC5_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = old_rc1_pos;
    fEdgePositions[UE5_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = fEdgePositions[RE4_POSITION];
    fEdgePositions[RE4_POSITION] = fEdgePositions[RE3_POSITION];
    fEdgePositions[RE3_POSITION] = fEdgePositions[RE2_POSITION];
    fEdgePositions[RE2_POSITION] = old_eu5_pos;
    setCornerOrientation(RC1_POSITION, getCornerOrientation(RC5_POSITION));
    setCornerOrientation(RC5_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC2_POSITION)]);
    setCornerOrientation(UC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC3_POSITION)]);
    setCornerOrientation(UC3_POSITION, getCornerOrientation(FC5_POSITION));
    setCornerOrientation(FC5_POSITION, CORNER_ORIENTATION_CW[old_rc1_or]);
    setEdgeOrientation(UE5_POSITION, getEdgeOrientation(FE2_POSITION));
    setEdgeOrientation(FE2_POSITION, getEdgeOrientation(RE4_POSITION));
    setEdgeOrientation(RE4_POSITION, getEdgeOrientation(RE3_POSITION));
    setEdgeOrientation(RE3_POSITION, getEdgeOrientation(RE2_POSITION));
    setEdgeOrientation(RE2_POSITION, old_ue5_or);
    if (sKeepMoves) fMoves[fDepth] = R_MOVE;
    fDepth++;
    fLastMove = R_MOVE;
  }

  public void moveRi() {
    byte old_rc1_pos = fCornerPositions[RC1_POSITION];
    byte old_eu5_pos = fEdgePositions[UE5_POSITION];
    int old_rc1_or = getCornerOrientation(RC1_POSITION);
    int old_ue5_or = getEdgeOrientation(UE5_POSITION);
    fCornerPositions[RC1_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = fCornerPositions[RC5_POSITION];
    fCornerPositions[RC5_POSITION] = old_rc1_pos;
    fEdgePositions[UE5_POSITION] = fEdgePositions[RE2_POSITION];
    fEdgePositions[RE2_POSITION] = fEdgePositions[RE3_POSITION];
    fEdgePositions[RE3_POSITION] = fEdgePositions[RE4_POSITION];
    fEdgePositions[RE4_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = old_eu5_pos;
    setCornerOrientation(RC1_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(FC5_POSITION)]);
    setCornerOrientation(FC5_POSITION, getCornerOrientation(UC3_POSITION));
    setCornerOrientation(UC3_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC2_POSITION)]);
    setCornerOrientation(UC2_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(RC5_POSITION)]);
    setCornerOrientation(RC5_POSITION, old_rc1_or);
    setEdgeOrientation(UE5_POSITION, getEdgeOrientation(RE2_POSITION));
    setEdgeOrientation(RE2_POSITION, getEdgeOrientation(RE3_POSITION));
    setEdgeOrientation(RE3_POSITION, getEdgeOrientation(RE4_POSITION));
    setEdgeOrientation(RE4_POSITION, getEdgeOrientation(FE2_POSITION));
    setEdgeOrientation(FE2_POSITION, old_ue5_or);
    if (sKeepMoves) fMoves[fDepth] = Ri_MOVE;
    fDepth++;
    fLastMove = Ri_MOVE;
  }

  public void moveR2() {
    byte old_rc1_pos = fCornerPositions[RC1_POSITION];
    byte old_eu5_pos = fEdgePositions[UE5_POSITION];
    int old_rc1_or = getCornerOrientation(RC1_POSITION);
    int old_ue5_or = getEdgeOrientation(UE5_POSITION);
    fCornerPositions[RC1_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = fCornerPositions[RC5_POSITION];
    fCornerPositions[RC5_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = old_rc1_pos;
    fEdgePositions[UE5_POSITION] = fEdgePositions[RE4_POSITION];
    fEdgePositions[RE4_POSITION] = fEdgePositions[RE2_POSITION];
    fEdgePositions[RE2_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = fEdgePositions[RE3_POSITION];
    fEdgePositions[RE3_POSITION] = old_eu5_pos;
    setCornerOrientation(RC1_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC2_POSITION)]);
    setCornerOrientation(UC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(FC5_POSITION)]);
    setCornerOrientation(FC5_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(RC5_POSITION)]);
    setCornerOrientation(RC5_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC3_POSITION)]);
    setCornerOrientation(UC3_POSITION, CORNER_ORIENTATION_CW[old_rc1_or]);
    setEdgeOrientation(UE5_POSITION, getEdgeOrientation(RE4_POSITION));
    setEdgeOrientation(RE4_POSITION, getEdgeOrientation(RE2_POSITION));
    setEdgeOrientation(RE2_POSITION, getEdgeOrientation(FE2_POSITION));
    setEdgeOrientation(FE2_POSITION, getEdgeOrientation(RE3_POSITION));
    setEdgeOrientation(RE3_POSITION, old_ue5_or);
    if (sKeepMoves) fMoves[fDepth] = R2_MOVE;
    fDepth++;
    fLastMove = R2_MOVE;
  }

  public void moveR2i() {
    byte old_rc1_pos = fCornerPositions[RC1_POSITION];
    byte old_eu5_pos = fEdgePositions[UE5_POSITION];
    int old_rc1_or = getCornerOrientation(RC1_POSITION);
    int old_ue5_or = getEdgeOrientation(UE5_POSITION);
    fCornerPositions[RC1_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[RC5_POSITION];
    fCornerPositions[RC5_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = fCornerPositions[UC2_POSITION];
    fCornerPositions[UC2_POSITION] = old_rc1_pos;
    fEdgePositions[UE5_POSITION] = fEdgePositions[RE3_POSITION];
    fEdgePositions[RE3_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = fEdgePositions[RE2_POSITION];
    fEdgePositions[RE2_POSITION] = fEdgePositions[RE4_POSITION];
    fEdgePositions[RE4_POSITION] = old_eu5_pos;
    setCornerOrientation(RC1_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC3_POSITION)]);
    setCornerOrientation(UC3_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(RC5_POSITION)]);
    setCornerOrientation(RC5_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(FC5_POSITION)]);
    setCornerOrientation(FC5_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC2_POSITION)]);
    setCornerOrientation(UC2_POSITION, CORNER_ORIENTATION_ANTI_CW[old_rc1_or]);
    setEdgeOrientation(UE5_POSITION, getEdgeOrientation(RE3_POSITION));
    setEdgeOrientation(RE3_POSITION, getEdgeOrientation(FE2_POSITION));
    setEdgeOrientation(FE2_POSITION, getEdgeOrientation(RE2_POSITION));
    setEdgeOrientation(RE2_POSITION, getEdgeOrientation(RE4_POSITION));
    setEdgeOrientation(RE4_POSITION, old_ue5_or);
    if (sKeepMoves) fMoves[fDepth] = R2i_MOVE;
    fDepth++;
    fLastMove = R2i_MOVE;
  }

  public void moveF() {
    byte old_fc1_pos = fCornerPositions[FC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_fc1_or = getCornerOrientation(FC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[FC1_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = old_fc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = fEdgePositions[FE4_POSITION];
    fEdgePositions[FE4_POSITION] = fEdgePositions[FE3_POSITION];
    fEdgePositions[FE3_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = old_ue1_pos;
    setCornerOrientation(FC1_POSITION, getCornerOrientation(FC5_POSITION));
    setCornerOrientation(FC5_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC3_POSITION)]);
    setCornerOrientation(UC3_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC4_POSITION)]);
    setCornerOrientation(UC4_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(FC2_POSITION)]);
    setCornerOrientation(FC2_POSITION, old_fc1_or);
    setEdgeOrientation(UE1_POSITION, (~getEdgeOrientation(FE5_POSITION)) & 1);
    setEdgeOrientation(FE5_POSITION, getEdgeOrientation(FE4_POSITION));
    setEdgeOrientation(FE4_POSITION, getEdgeOrientation(FE3_POSITION));
    setEdgeOrientation(FE3_POSITION, getEdgeOrientation(FE2_POSITION));
    setEdgeOrientation(FE2_POSITION, (~old_ue1_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = F_MOVE;
    fDepth++;
    fLastMove = F_MOVE;
  }

  public void moveFi() {
    byte old_fc1_pos = fCornerPositions[FC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_fc1_or = getCornerOrientation(FC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[FC1_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = old_fc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = fEdgePositions[FE3_POSITION];
    fEdgePositions[FE3_POSITION] = fEdgePositions[FE4_POSITION];
    fEdgePositions[FE4_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = old_ue1_pos;
    setCornerOrientation(FC1_POSITION, getCornerOrientation(FC2_POSITION));
    setCornerOrientation(FC2_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC4_POSITION)]);
    setCornerOrientation(UC4_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC3_POSITION)]);
    setCornerOrientation(UC3_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(FC5_POSITION)]);
    setCornerOrientation(FC5_POSITION, old_fc1_or);
    setEdgeOrientation(UE1_POSITION, (~getEdgeOrientation(FE2_POSITION)) & 1);
    setEdgeOrientation(FE2_POSITION, getEdgeOrientation(FE3_POSITION));
    setEdgeOrientation(FE3_POSITION, getEdgeOrientation(FE4_POSITION));
    setEdgeOrientation(FE4_POSITION, getEdgeOrientation(FE5_POSITION));
    setEdgeOrientation(FE5_POSITION, (~old_ue1_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = Fi_MOVE;
    fDepth++;
    fLastMove = Fi_MOVE;
  }

  public void moveF2() {
    byte old_fc1_pos = fCornerPositions[FC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_fc1_or = getCornerOrientation(FC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[FC1_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = old_fc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[FE4_POSITION];
    fEdgePositions[FE4_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = fEdgePositions[FE3_POSITION];
    fEdgePositions[FE3_POSITION] = old_ue1_pos;
    setCornerOrientation(FC1_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC3_POSITION)]);
    setCornerOrientation(UC3_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(FC2_POSITION)]);
    setCornerOrientation(FC2_POSITION, getCornerOrientation(FC5_POSITION));
    setCornerOrientation(FC5_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC4_POSITION)]);
    setCornerOrientation(UC4_POSITION, CORNER_ORIENTATION_CW[old_fc1_or]);
    setEdgeOrientation(UE1_POSITION, (~getEdgeOrientation(FE4_POSITION)) & 1);
    setEdgeOrientation(FE4_POSITION, getEdgeOrientation(FE2_POSITION));
    setEdgeOrientation(FE2_POSITION, getEdgeOrientation(FE5_POSITION));
    setEdgeOrientation(FE5_POSITION, getEdgeOrientation(FE3_POSITION));
    setEdgeOrientation(FE3_POSITION, (~old_ue1_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = F2_MOVE;
    fDepth++;
    fLastMove = F2_MOVE;
  }

  public void moveF2i() {
    byte old_fc1_pos = fCornerPositions[FC1_POSITION];
    byte old_ue1_pos = fEdgePositions[UE1_POSITION];
    int old_fc1_or = getCornerOrientation(FC1_POSITION);
    int old_ue1_or = getEdgeOrientation(UE1_POSITION);
    fCornerPositions[FC1_POSITION] = fCornerPositions[UC4_POSITION];
    fCornerPositions[UC4_POSITION] = fCornerPositions[FC5_POSITION];
    fCornerPositions[FC5_POSITION] = fCornerPositions[FC2_POSITION];
    fCornerPositions[FC2_POSITION] = fCornerPositions[UC3_POSITION];
    fCornerPositions[UC3_POSITION] = old_fc1_pos;
    fEdgePositions[UE1_POSITION] = fEdgePositions[FE3_POSITION];
    fEdgePositions[FE3_POSITION] = fEdgePositions[FE5_POSITION];
    fEdgePositions[FE5_POSITION] = fEdgePositions[FE2_POSITION];
    fEdgePositions[FE2_POSITION] = fEdgePositions[FE4_POSITION];
    fEdgePositions[FE4_POSITION] = old_ue1_pos;
    setCornerOrientation(FC1_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC4_POSITION)]);
    setCornerOrientation(UC4_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(FC5_POSITION)]);
    setCornerOrientation(FC5_POSITION, getCornerOrientation(FC2_POSITION));
    setCornerOrientation(FC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC3_POSITION)]);
    setCornerOrientation(UC3_POSITION, CORNER_ORIENTATION_ANTI_CW[old_fc1_or]);
    setEdgeOrientation(UE1_POSITION, (~getEdgeOrientation(FE3_POSITION)) & 1);
    setEdgeOrientation(FE3_POSITION, getEdgeOrientation(FE5_POSITION));
    setEdgeOrientation(FE5_POSITION, getEdgeOrientation(FE2_POSITION));
    setEdgeOrientation(FE2_POSITION, getEdgeOrientation(FE4_POSITION));
    setEdgeOrientation(FE4_POSITION, (~old_ue1_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = F2i_MOVE;
    fDepth++;
    fLastMove = F2i_MOVE;
  }

  public void moveB() {
    byte old_bc1_pos = fCornerPositions[BC1_POSITION];
    byte old_ue3_pos = fEdgePositions[UE3_POSITION];
    int old_bc1_or = getCornerOrientation(BC1_POSITION);
    int old_ue3_or = getEdgeOrientation(UE3_POSITION);
    fCornerPositions[BC1_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[UC1_POSITION];
    fCornerPositions[UC1_POSITION] = fCornerPositions[BC2_POSITION];
    fCornerPositions[BC2_POSITION] = old_bc1_pos;
    fEdgePositions[UE3_POSITION] = fEdgePositions[BE5_POSITION];
    fEdgePositions[BE5_POSITION] = fEdgePositions[BE4_POSITION];
    fEdgePositions[BE4_POSITION] = fEdgePositions[BE3_POSITION];
    fEdgePositions[BE3_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = old_ue3_pos;
    setCornerOrientation(BC1_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(LC2_POSITION)]);
    setCornerOrientation(LC2_POSITION, getCornerOrientation(UC5_POSITION));
    setCornerOrientation(UC5_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC1_POSITION)]);
    setCornerOrientation(UC1_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(BC2_POSITION)]);
    setCornerOrientation(BC2_POSITION, old_bc1_or);
    setEdgeOrientation(UE3_POSITION, (~getEdgeOrientation(BE5_POSITION)) & 1);
    setEdgeOrientation(BE5_POSITION, getEdgeOrientation(BE4_POSITION));
    setEdgeOrientation(BE4_POSITION, getEdgeOrientation(BE3_POSITION));
    setEdgeOrientation(BE3_POSITION, getEdgeOrientation(LE5_POSITION));
    setEdgeOrientation(LE5_POSITION, (~old_ue3_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = B_MOVE;
    fDepth++;
    fLastMove = B_MOVE;
  }

  public void moveBi() {
    byte old_bc1_pos = fCornerPositions[BC1_POSITION];
    byte old_ue3_pos = fEdgePositions[UE3_POSITION];
    int old_bc1_or = getCornerOrientation(BC1_POSITION);
    int old_ue3_or = getEdgeOrientation(UE3_POSITION);
    fCornerPositions[BC1_POSITION] = fCornerPositions[BC2_POSITION];
    fCornerPositions[BC2_POSITION] = fCornerPositions[UC1_POSITION];
    fCornerPositions[UC1_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = old_bc1_pos;
    fEdgePositions[UE3_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = fEdgePositions[BE3_POSITION];
    fEdgePositions[BE3_POSITION] = fEdgePositions[BE4_POSITION];
    fEdgePositions[BE4_POSITION] = fEdgePositions[BE5_POSITION];
    fEdgePositions[BE5_POSITION] = old_ue3_pos;
    setCornerOrientation(BC1_POSITION, getCornerOrientation(BC2_POSITION));
    setCornerOrientation(BC2_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC1_POSITION)]);
    setCornerOrientation(UC1_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC5_POSITION)]);
    setCornerOrientation(UC5_POSITION, getCornerOrientation(LC2_POSITION));
    setCornerOrientation(LC2_POSITION, CORNER_ORIENTATION_ANTI_CW[old_bc1_or]);
    setEdgeOrientation(UE3_POSITION, (~getEdgeOrientation(LE5_POSITION)) & 1);
    setEdgeOrientation(LE5_POSITION, getEdgeOrientation(BE3_POSITION));
    setEdgeOrientation(BE3_POSITION, getEdgeOrientation(BE4_POSITION));
    setEdgeOrientation(BE4_POSITION, getEdgeOrientation(BE5_POSITION));
    setEdgeOrientation(BE5_POSITION, (~old_ue3_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = Bi_MOVE;
    fDepth++;
    fLastMove = Bi_MOVE;
  }

  public void moveB2() {
    byte old_bc1_pos = fCornerPositions[BC1_POSITION];
    byte old_ue3_pos = fEdgePositions[UE3_POSITION];
    int old_bc1_or = getCornerOrientation(BC1_POSITION);
    int old_ue3_or = getEdgeOrientation(UE3_POSITION);
    fCornerPositions[BC1_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = fCornerPositions[BC2_POSITION];
    fCornerPositions[BC2_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = fCornerPositions[UC1_POSITION];
    fCornerPositions[UC1_POSITION] = old_bc1_pos;
    fEdgePositions[UE3_POSITION] = fEdgePositions[BE4_POSITION];
    fEdgePositions[BE4_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = fEdgePositions[BE5_POSITION];
    fEdgePositions[BE5_POSITION] = fEdgePositions[BE3_POSITION];
    fEdgePositions[BE3_POSITION] = old_ue3_pos;
    setCornerOrientation(BC1_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC5_POSITION)]);
    setCornerOrientation(UC5_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(BC2_POSITION)]);
    setCornerOrientation(BC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(LC2_POSITION)]);
    setCornerOrientation(LC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC1_POSITION)]);
    setCornerOrientation(UC1_POSITION, CORNER_ORIENTATION_CW[old_bc1_or]);
    setEdgeOrientation(UE3_POSITION, (~getEdgeOrientation(BE4_POSITION)) & 1);
    setEdgeOrientation(BE4_POSITION, getEdgeOrientation(LE5_POSITION));
    setEdgeOrientation(LE5_POSITION, getEdgeOrientation(BE5_POSITION));
    setEdgeOrientation(BE5_POSITION, getEdgeOrientation(BE3_POSITION));
    setEdgeOrientation(BE3_POSITION, (~old_ue3_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = B2_MOVE;
    fDepth++;
    fLastMove = B2_MOVE;
  }

  public void moveB2i() {
    byte old_bc1_pos = fCornerPositions[BC1_POSITION];
    byte old_ue3_pos = fEdgePositions[UE3_POSITION];
    int old_bc1_or = getCornerOrientation(BC1_POSITION);
    int old_ue3_or = getEdgeOrientation(UE3_POSITION);
    fCornerPositions[BC1_POSITION] = fCornerPositions[UC1_POSITION];
    fCornerPositions[UC1_POSITION] = fCornerPositions[LC2_POSITION];
    fCornerPositions[LC2_POSITION] = fCornerPositions[BC2_POSITION];
    fCornerPositions[BC2_POSITION] = fCornerPositions[UC5_POSITION];
    fCornerPositions[UC5_POSITION] = old_bc1_pos;
    fEdgePositions[UE3_POSITION] = fEdgePositions[BE3_POSITION];
    fEdgePositions[BE3_POSITION] = fEdgePositions[BE5_POSITION];
    fEdgePositions[BE5_POSITION] = fEdgePositions[LE5_POSITION];
    fEdgePositions[LE5_POSITION] = fEdgePositions[BE4_POSITION];
    fEdgePositions[BE4_POSITION] = old_ue3_pos;
    setCornerOrientation(BC1_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(UC1_POSITION)]);
    setCornerOrientation(UC1_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(LC2_POSITION)]);
    setCornerOrientation(LC2_POSITION, CORNER_ORIENTATION_ANTI_CW[getCornerOrientation(BC2_POSITION)]);
    setCornerOrientation(BC2_POSITION, CORNER_ORIENTATION_CW[getCornerOrientation(UC5_POSITION)]);
    setCornerOrientation(UC5_POSITION, CORNER_ORIENTATION_ANTI_CW[old_bc1_or]);
    setEdgeOrientation(UE3_POSITION, (~getEdgeOrientation(BE3_POSITION)) & 1);
    setEdgeOrientation(BE3_POSITION, getEdgeOrientation(BE5_POSITION));
    setEdgeOrientation(BE5_POSITION, getEdgeOrientation(LE5_POSITION));
    setEdgeOrientation(LE5_POSITION, getEdgeOrientation(BE4_POSITION));
    setEdgeOrientation(BE4_POSITION, (~old_ue3_or) & 1);
    if (sKeepMoves) fMoves[fDepth] = B2i_MOVE;
    fDepth++;
    fLastMove = B2i_MOVE;
  }

  public boolean equals(Object aObject) {
    LLMinx minx = (LLMinx) aObject;
    byte piece;
    for (byte i = 0; i < fCornerPositions.length; i++) {
      piece = fCornerPositions[i];
      if (fCornerPositions[i] != minx.fCornerPositions[i] && !fIgnoreCornerPositions[piece]) return false;
      if (getCornerOrientation(i) != minx.getCornerOrientation(i) && !fIgnoreCornerOrientations[piece]) return false;
    }
    for (byte i = 0; i < fEdgePositions.length; i++) {
      piece = fEdgePositions[i];
      if (fEdgePositions[i] != minx.fEdgePositions[i] && !fIgnoreEdgePositions[piece]) return false;
      if (getEdgeOrientation(i) != minx.getEdgeOrientation(i) && !fIgnoreEdgeOrientations[piece]) return false;
    }
    return true;
//    return (this.fEdgeOrientations == minx.fEdgeOrientations) &&
//      (this.fCornerOrientations == minx.fCornerOrientations);
  }

  public LLMinx clone() {
    byte[] corner_positions = new byte[fCornerPositions.length];
    boolean[] ignore_corner_positions = new boolean[fIgnoreCornerPositions.length];
    boolean[] ignore_corner_orientations = new boolean[fIgnoreCornerOrientations.length];
    byte[] edge_positions = new byte[fEdgePositions.length];
    boolean[] ignore_edge_positions = new boolean[fIgnoreEdgePositions.length];
    boolean[] ignore_edge_orientations = new boolean[fIgnoreEdgeOrientations.length];
    byte[] moves = new byte[fMoves.length];
    System.arraycopy(fCornerPositions, 0, corner_positions, 0, fCornerPositions.length);
    System.arraycopy(fIgnoreCornerPositions, 0, ignore_corner_positions, 0, fIgnoreCornerPositions.length);
    System.arraycopy(fIgnoreCornerOrientations, 0, ignore_corner_orientations, 0, fIgnoreCornerOrientations.length);
    System.arraycopy(fEdgePositions, 0, edge_positions, 0, fEdgePositions.length);
    System.arraycopy(fIgnoreEdgePositions, 0, ignore_edge_positions, 0, fIgnoreEdgePositions.length);
    System.arraycopy(fIgnoreEdgeOrientations, 0, ignore_edge_orientations, 0, fIgnoreEdgeOrientations.length);
    System.arraycopy(fMoves, 0, moves, 0, fMoves.length);
    return new LLMinx(
      corner_positions,
      edge_positions,
      fCornerOrientations,
      fEdgeOrientations,
      ignore_corner_positions,
      ignore_edge_positions,
      ignore_corner_orientations,
      ignore_edge_orientations,
      moves,
      fDepth
    );
  }

  public int compareTo(LLMinx aObject) {
    int diff = fCornerOrientations - aObject.fCornerOrientations;
    if (diff != 0) {
      return diff;
    }
    else {
      diff = fEdgeOrientations - aObject.fEdgeOrientations;
      if (diff != 0) {
        for (int i = 0; i < fCornerPositions.length; i++) {
          diff = fCornerPositions[i] - aObject.fCornerPositions[i];
          if (diff != 0) {
            return diff;
          }
        }
        for (int i = 0; i < fEdgePositions.length; i++) {
          diff = fEdgePositions[i] - aObject.fEdgePositions[i];
          if (diff != 0) {
            return diff;
          }
        }
      }
    }
    return 0;
  }

}
