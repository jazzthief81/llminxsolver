package llminx.solver.searchmode;

import llminx.LLMinx;

/**
 *
 */
public enum LLMinxSearchMode {

  RU_MODE(
    "RU",
    new byte[]{
      LLMinx.R_MOVE, LLMinx.Ri_MOVE, LLMinx.R2_MOVE, LLMinx.R2i_MOVE,
      LLMinx.U_MOVE, LLMinx.Ui_MOVE, LLMinx.U2_MOVE, LLMinx.U2i_MOVE
    },
    new LLMinxPruner[]{
      new EdgePermutationPruner("Edge permutations RU", "ruedgepermutations",
        new byte[]{
          LLMinx.UE1_POSITION,
          LLMinx.UE2_POSITION,
          LLMinx.UE3_POSITION,
          LLMinx.UE4_POSITION,
          LLMinx.UE5_POSITION,
          LLMinx.RE2_POSITION,
          LLMinx.RE3_POSITION,
          LLMinx.RE4_POSITION,
          LLMinx.FE2_POSITION
        }
      ),
      new CompositePruner("Corners RU", "rucorners",
        new CornerPermutationPruner("Corner permutations RU", "rucornerpermutations",
          new byte[]{
            LLMinx.UC1_POSITION,
            LLMinx.UC2_POSITION,
            LLMinx.UC3_POSITION,
            LLMinx.UC4_POSITION,
            LLMinx.UC5_POSITION,
            LLMinx.RC1_POSITION,
            LLMinx.RC5_POSITION,
            LLMinx.FC5_POSITION
          }
        ),
        new CornerOrientationPruner("Corner orientations RU", "rucornerorientations",
          new byte[]{
            LLMinx.UC1_POSITION,
            LLMinx.UC2_POSITION,
            LLMinx.UC3_POSITION,
            LLMinx.UC4_POSITION,
            LLMinx.UC5_POSITION,
            LLMinx.RC1_POSITION,
            LLMinx.RC5_POSITION,
            LLMinx.FC5_POSITION
          }
        )
      )
    }
  ),
  RUF_MODE(
    "RUF",
    new byte[]{
      LLMinx.R_MOVE, LLMinx.Ri_MOVE, LLMinx.R2_MOVE, LLMinx.R2i_MOVE,
      LLMinx.U_MOVE, LLMinx.Ui_MOVE, LLMinx.U2_MOVE, LLMinx.U2i_MOVE,
      LLMinx.F_MOVE, LLMinx.Fi_MOVE, LLMinx.F2_MOVE, LLMinx.F2i_MOVE
    },
    new LLMinxPruner[]{
      new CornerPermutationPruner("Corner permutations RUF", "rufcornerpermutations",
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
          LLMinx.FC2_POSITION
        }
      ),
      new EdgePermutationPruner("Edge permutations RUF", "rufedgepermutations",
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
          LLMinx.FE5_POSITION
        }
      ),
      new CompositePruner("Orientations RUF", "ruforientations",
        new CornerOrientationPruner("Corner orientations RUF", "rufcornerorientations",
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
            LLMinx.FC2_POSITION
          }
        ),
        new EdgeOrientationPruner("Edge orientations RUF", "rufedgeorientations",
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
            LLMinx.FE5_POSITION
          }
        )
      )
    }),
  RUL_MODE(
    "RUL",
    new byte[]{
      LLMinx.R_MOVE, LLMinx.Ri_MOVE, LLMinx.R2_MOVE, LLMinx.R2i_MOVE,
      LLMinx.U_MOVE, LLMinx.Ui_MOVE, LLMinx.U2_MOVE, LLMinx.U2i_MOVE,
      LLMinx.L_MOVE, LLMinx.Li_MOVE, LLMinx.L2_MOVE, LLMinx.L2i_MOVE
    },
    new LLMinxPruner[]{
      new CornerOrientationPruner("Corner orientations RUL", "rulcornerorientations",
        new byte[]{
          LLMinx.UC1_POSITION,
          LLMinx.UC2_POSITION,
          LLMinx.UC3_POSITION,
          LLMinx.UC4_POSITION,
          LLMinx.UC5_POSITION,
          LLMinx.RC1_POSITION,
          LLMinx.RC5_POSITION,
          LLMinx.FC5_POSITION,
          LLMinx.FC2_POSITION,
          LLMinx.LC1_POSITION,
          LLMinx.LC2_POSITION
        }
      ),
      new CornerPermutationPruner("Corner permutations RUL", "rulcornerpermutations",
        new byte[]{
          LLMinx.UC1_POSITION,
          LLMinx.UC2_POSITION,
          LLMinx.UC3_POSITION,
          LLMinx.UC4_POSITION,
          LLMinx.UC5_POSITION,
          LLMinx.RC1_POSITION,
          LLMinx.RC5_POSITION,
          LLMinx.FC5_POSITION,
          LLMinx.FC2_POSITION,
          LLMinx.LC1_POSITION,
          LLMinx.LC2_POSITION
        }
      )
    }),
  RUFL_MODE(
    "RUFL",
    new byte[]{
      LLMinx.R_MOVE, LLMinx.Ri_MOVE, LLMinx.R2_MOVE, LLMinx.R2i_MOVE,
      LLMinx.U_MOVE, LLMinx.Ui_MOVE, LLMinx.U2_MOVE, LLMinx.U2i_MOVE,
      LLMinx.F_MOVE, LLMinx.Fi_MOVE, LLMinx.F2_MOVE, LLMinx.F2i_MOVE,
      LLMinx.L_MOVE, LLMinx.Li_MOVE, LLMinx.L2_MOVE, LLMinx.L2i_MOVE
    },
    new LLMinxPruner[]{
      new EdgeOrientationPruner("Edge orientations RUFL", "rufledgeorientations",
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
          LLMinx.LE5_POSITION
        }
      ),
      new CornerOrientationPruner("Corner orientations RUFL", "ruflcornerorientations",
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
          LLMinx.LC2_POSITION
        }
      ),
      new CornerPermutationPruner("Corner permutations RUFL", "ruflcornerpermutations",
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
          LLMinx.LC2_POSITION
        }
      )
    }),
  RUFLB_MODE(
    "RUFLB",
    new byte[]{
      LLMinx.R_MOVE, LLMinx.Ri_MOVE, LLMinx.R2_MOVE, LLMinx.R2i_MOVE,
      LLMinx.U_MOVE, LLMinx.Ui_MOVE, LLMinx.U2_MOVE, LLMinx.U2i_MOVE,
      LLMinx.F_MOVE, LLMinx.Fi_MOVE, LLMinx.F2_MOVE, LLMinx.F2i_MOVE,
      LLMinx.L_MOVE, LLMinx.Li_MOVE, LLMinx.L2_MOVE, LLMinx.L2i_MOVE,
      LLMinx.B_MOVE, LLMinx.Bi_MOVE, LLMinx.B2_MOVE, LLMinx.B2i_MOVE
    },
    new LLMinxPruner[]{
      new EdgeOrientationPruner("Edge orientations RUFLB", "ruflbedgeorientations",
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
        }
      ),
      new CornerOrientationPruner("Corner orientations RUFLB", "ruflbcornerorientations",
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
        }
      ),
      new CompositePruner("Edge orientations / Corner separations RUFLB", "ruflbedgeorientationscornerseparations",
        new EdgeOrientationPruner("Edge orientations RUFLB", "ruflbedgeorientations",
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
          }
        ),
        new SeparationPruner("Corner separations U RUFLB", "ruflbcornerseparationsu",
          new byte[]{
            LLMinx.UC1_POSITION,
            LLMinx.UC2_POSITION,
            LLMinx.UC3_POSITION,
            LLMinx.UC4_POSITION,
            LLMinx.UC5_POSITION,
          },
          new byte[]{
          }
        )
      ),
      new SeparationPruner("Separations R RUFLB", "ruflbseparationsr",
        new byte[]{
          LLMinx.RC1_POSITION,
          LLMinx.FC5_POSITION,
          LLMinx.UC3_POSITION,
          LLMinx.UC2_POSITION,
          LLMinx.RC5_POSITION
        },
        new byte[]{
          LLMinx.FE2_POSITION,
          LLMinx.RE2_POSITION,
          LLMinx.RE3_POSITION,
          LLMinx.RE4_POSITION,
          LLMinx.UE5_POSITION
        }
      ),
      new SeparationPruner("Separations L RUFLB", "ruflbseparationsl",
        new byte[]{
          LLMinx.LC1_POSITION,
          LLMinx.LC2_POSITION,
          LLMinx.FC2_POSITION,
          LLMinx.UC4_POSITION,
          LLMinx.UC5_POSITION
        },
        new byte[]{
          LLMinx.FE5_POSITION,
          LLMinx.UE2_POSITION,
          LLMinx.LE3_POSITION,
          LLMinx.LE4_POSITION,
          LLMinx.LE5_POSITION
        }
      ),
      new SeparationPruner("Separations F RUFLB", "ruflbseparationsf",
        new byte[]{
          LLMinx.FC5_POSITION,
          LLMinx.FC2_POSITION,
          LLMinx.FC1_POSITION,
          LLMinx.UC4_POSITION,
          LLMinx.UC3_POSITION
        },
        new byte[]{
          LLMinx.UE1_POSITION,
          LLMinx.FE2_POSITION,
          LLMinx.FE3_POSITION,
          LLMinx.FE4_POSITION,
          LLMinx.FE5_POSITION
        }
      ),
      new SeparationPruner("Separations B RUFLB", "ruflbseparationsb",
        new byte[]{
          LLMinx.LC2_POSITION,
          LLMinx.BC1_POSITION,
          LLMinx.BC2_POSITION,
          LLMinx.UC1_POSITION,
          LLMinx.UC5_POSITION
        },
        new byte[]{
          LLMinx.LE5_POSITION,
          LLMinx.BE3_POSITION,
          LLMinx.BE4_POSITION,
          LLMinx.BE5_POSITION,
          LLMinx.UE3_POSITION
        }
      ),
//      new SeparationPruner("Separations U RUFLB", "ruflbseparationsu",
//        new byte[]{
//          LLMinx.UC1_POSITION,
//          LLMinx.UC2_POSITION,
//          LLMinx.UC3_POSITION,
//          LLMinx.UC4_POSITION,
//          LLMinx.UC5_POSITION,
//        },
//        new byte[]{
//          LLMinx.UE1_POSITION,
//          LLMinx.UE2_POSITION,
//          LLMinx.UE3_POSITION,
//          LLMinx.UE4_POSITION,
//          LLMinx.UE5_POSITION
//        }
//      )
    }
  );

  private String fDescription;
  private LLMinxPruner[] fPruners;
  private byte[] fPossibleMoves;
  boolean fAllowSuccessiveFaceTurns;

  LLMinxSearchMode(String description, byte[] possibleMoves, LLMinxPruner[] pruners) {
    fDescription = description;
    fPruners = pruners;
    fPossibleMoves = possibleMoves;
  }

  public String getDescription() {
    return fDescription;
  }

  public LLMinxPruner[] getPruners() {
    return fPruners;
  }

  public byte[] getPossibleMoves() {
    return fPossibleMoves;
  }


  public String toString() {
    return fDescription;
  }                     }
