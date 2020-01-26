package llminx.solver;

import llminx.LLMinx;
import llminx.solver.searchmode.LLMinxMetric;
import llminx.solver.searchmode.LLMinxPruner;
import llminx.solver.searchmode.LLMinxSearchMode;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class LLMinxSolver {

  public static boolean sDebug;
  private LLMinxSearchMode fSearchMode = LLMinxSearchMode.RU_MODE;
  private LLMinxSearchMode fLastSearchMode;
  private LLMinxMetric fMetric = LLMinxMetric.FIFTH;
  private LLMinxMetric fLastMetric;
  private int fMaxDepth = 12;
  private boolean fLimitDepth = false;
  private int fDepth = 12;
  private AtomicLong fNodes = new AtomicLong();
  private LLMinxPruner fPruner;
  private long[] pruned;
  private LLMinxPruner[] pruners;
  private LLMinxPruner[] fUsedPruners;
  private byte[] moves;
  private byte[][] next_siblings;
  private byte[] first_moves;
  private byte[][] tables;
  private byte[][] fUsedTables;
  private LLMinx fStart;
  private Vector<StatusListener> fEventListeners;
  private boolean fInterupted = false;
  private boolean fSearchStarted = false;
  private boolean fIgnoreCornerPositions = false;
  private boolean fIgnoreEdgePositions = false;
  private boolean fIgnoreCornerOrientations = false;
  private boolean fIgnoreEdgeOrientations = false;

  static {
    sDebug = "on".equals( System.getProperty( "llminx.solver.LLMinxSolver.debug" ) );
  }

  public static void main( String[] args ) {
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
        ( byte ) 0
    );
    LLMinxSolver solver = new LLMinxSolver();
    solver.setStart( start );
    solver.setMaxDepth( 16 );
    solver.addStatusListener( new SystemOutStatusListener() );
    solver.solve();
  }

  public LLMinxSolver() {
    this( LLMinxSearchMode.RU_MODE, 12 );
  }

  public LLMinxSolver( LLMinxSearchMode aSearchMode, int maxDepth ) {
    setSearchMode( aSearchMode );
    fMaxDepth = maxDepth;
    fEventListeners = new Vector<StatusListener>();
  }

  public LLMinxSearchMode getSearchMode() {
    return fSearchMode;
  }

  public void setSearchMode( LLMinxSearchMode searchMode ) {
    fSearchMode = searchMode;
  }

  public LLMinxMetric getMetric() {
    return fMetric;
  }

  public void setMetric( LLMinxMetric metric ) {
    fMetric = metric;
  }

  public int getMaxDepth() {
    return fMaxDepth;
  }

  public void setMaxDepth( int maxDepth ) {
    fMaxDepth = maxDepth;
  }


  public boolean isLimitDepth() {
    return fLimitDepth;
  }

  public void setLimitDepth( boolean aLimitDepth ) {
    fLimitDepth = aLimitDepth;
  }

  public LLMinx getStart() {
    return fStart;
  }

  public void setStart( LLMinx start ) {
    fStart = start;
  }

  public boolean isIgnoreCornerPositions() {
    return fIgnoreCornerPositions;
  }

  public void setIgnoreCornerPositions( boolean ignoreCornerPositions ) {
    fIgnoreCornerPositions = ignoreCornerPositions;
  }

  public boolean isIgnoreEdgePositions() {
    return fIgnoreEdgePositions;
  }

  public void setIgnoreEdgePositions( boolean ignoreEdgePositions ) {
    fIgnoreEdgePositions = ignoreEdgePositions;
  }

  public boolean isIgnoreCornerOrientations() {
    return fIgnoreCornerOrientations;
  }

  public void setIgnoreCornerOrientations( boolean aIgnoreCornerOrientations ) {
    fIgnoreCornerOrientations = aIgnoreCornerOrientations;
  }

  public boolean isIgnoreEdgeOrientations() {
    return fIgnoreEdgeOrientations;
  }

  public void setIgnoreEdgeOrientations( boolean aIgnoreEdgeOrientations ) {
    fIgnoreEdgeOrientations = aIgnoreEdgeOrientations;
  }

  public boolean solve() {
    final long time = System.currentTimeMillis();
    fSearchStarted = false;
    if ( fSearchMode != fLastSearchMode || fMetric != fLastMetric ) {
      // init moves table
      buildMovesTable();

      // init pruning table
      buildPruningTables();

      if ( !fInterupted ) {
        fLastSearchMode = fSearchMode;
        fLastMetric = fMetric;
      }
      else {
        fLastSearchMode = null;
        fLastMetric = null;
      }
    }
    filterPruningTables();

    // start search
    if ( !fInterupted ) {
      fSearchStarted = true;
      fireEvent( new StatusEvent( StatusEventType.MESSAGE, "Searching...", 0 ) );
      LLMinx.setKeepMoves( true );
      boolean stop;
      LLMinx goal = new LLMinx();
      ExecutorService executor = Executors.newFixedThreadPool(10);
      for ( fDepth = 1; ( !fLimitDepth || fDepth <= fMaxDepth ) && fDepth < pruned.length && !fInterupted; fDepth++ ) {
        Arrays.fill( pruned, 0 );
        fNodes.set(0);
        fireEvent( new StatusEvent( StatusEventType.START_DEPTH, "Searching depth " + fDepth + "...", 0 ) );
        LLMinx minx = fStart.clone();
        nextNode(minx);
        List<Future<Boolean>> list = new ArrayList<Future<Boolean>>();
        do {
          LLMinx clone = minx.clone();
          list.add(executor.submit(()->solveParallel(clone, goal)));
          stop = nextParallel(minx);
        } while (!stop);
        for(Future<Boolean> fut : list){
          try {
            //print the return value of Future, notice the output delay in console
            // because Future.get() waits for task to get completed
            System.out.println(new Date()+ "::"+fut.get());
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        }
        fireEvent( new StatusEvent( StatusEventType.END_DEPTH, "Searching depth " + fDepth + "...", 1 ) );
      }
    }

    boolean interrupted = fInterupted;
    fInterupted = false;
    String msg = ( interrupted ? "Search interrupted after " : "Search completed in " )
        + ( int ) ( ( System.currentTimeMillis() - time ) / 1000 ) + " seconds.";
    fireEvent( new StatusEvent( StatusEventType.MESSAGE, msg, 1 ) );
    return interrupted;
  }

  public boolean solveParallel(LLMinx minx, LLMinx goal) {
    boolean stop = false;
    while ( !stop && !fInterupted ) {
      fNodes.incrementAndGet();
      int levels_left = fDepth - minx.getDepth();
      if ( minx.equals( goal ) ) {
        if ( levels_left == 0 && checkOptimal( minx ) ) {
          String msg = minx.getGeneratingMoves() + " (" + minx.getHTMLength() + "," + minx.getQTMLength() + ")";
          fireEvent( new StatusEvent( StatusEventType.MESSAGE, msg, 0 )
          );
        }
        if ( levels_left > 0 ) {
          pruned[levels_left - 1]++;
        }
        stop = backTrack( minx );
      }
      else {
        if ( levels_left > 0 ) {
          int i;
          for ( i = 0; i < fUsedPruners.length; i++ ) {
            LLMinxPruner pruner = fUsedPruners[i];
            if ( fUsedTables[i][pruner.getCoordinate( minx )] > levels_left ) {
              pruned[levels_left - 1]++;
              break;
            }
          }
          // add children.
          if ( i == fUsedPruners.length ) {
            stop = nextNode( minx );
          }
          else {
            stop = backTrack( minx );
          }
        }
        else {
          stop = nextNode( minx );
        }
      }
    }
    return stop;
  }

  public void interrupt() {
    fInterupted = true;
  }

  private void buildMovesTable() {
    // filter out moves depending on selected metric.
    byte[] possible_moves = fSearchMode.getPossibleMoves();
    if ( fMetric == LLMinxMetric.FACE ) {
      moves = possible_moves;
    }
    else if ( fMetric == LLMinxMetric.FIFTH ) {
      moves = new byte[possible_moves.length / 2];
      int moves_left = 0;
      for ( int i = 0; i < possible_moves.length; i++ ) {
        if ( ( possible_moves[i] % 4 ) < 2 ) {
          moves[moves_left++] = possible_moves[i];
        }
      }
    }

    // intialize tables for first moves and sibling order.
    first_moves = new byte[LLMinx.B2i_MOVE + 2];
    next_siblings = new byte[LLMinx.B2i_MOVE + 2][LLMinx.B2i_MOVE + 1];
    int last_sibling;
    int last_move;
    Arrays.fill( first_moves, moves[0] );
    Arrays.fill( next_siblings[0], ( byte ) -1 );

    // fill out values for root node.
    first_moves[0] = moves[0];
    for ( last_sibling = 0; last_sibling < moves.length - 1; last_sibling++ ) {
      next_siblings[0][moves[last_sibling]] = moves[last_sibling + 1];
    }

    // fill out values for internal nodes.
    for ( last_move = 0; last_move < moves.length; last_move++ ) {
      int last_move_index = moves[last_move] + 1;

      // fill out first moves table.
      if ( !isAllowed( moves[last_move], moves[0] ) ) {
        int after_first_move = 0;
        while ( !isAllowed( moves[last_move], moves[after_first_move] ) ) {
          after_first_move++;
        }
        first_moves[last_move_index] = moves[after_first_move];
      }

      // fill out sibling order table
      Arrays.fill( next_siblings[last_move_index], ( byte ) -1 );
      for ( last_sibling = 0; last_sibling < moves.length - 1; last_sibling++ ) {
        if ( !isAllowed( moves[last_sibling], moves[last_move] ) ) {
          // we don't want two successive turns of the same face.
          continue;
        }
        else if ( !isAllowed( moves[last_sibling + 1], moves[last_move] ) ) {
          // we don't want two successive turns of the same face.
          int new_last_sibling = last_sibling + 1;
          while ( new_last_sibling < moves.length && !isAllowed( moves[new_last_sibling], moves[last_move] ) ) {
            new_last_sibling++;
          }
          if ( new_last_sibling < moves.length ) {
            next_siblings[last_move_index][moves[last_sibling]] = moves[new_last_sibling];
            last_sibling = new_last_sibling - 1;
          }
          else {
            break;
          }
        }
        else {
          next_siblings[last_move_index][moves[last_sibling]] = moves[last_sibling + 1];
        }
      }
    }

    if ( sDebug ) {
      printMoveTables();
    }
  }

  private void printMoveTables() {
    System.out.println( "First move" );
    System.out.println();
    System.out.println( "Root: " + LLMinx.MOVE_STRINGS[first_moves[0]] );
    for ( int i = 0; i < moves.length; i++ ) {
      byte move = moves[i];
      System.out.println( LLMinx.MOVE_STRINGS[move] + ": " + LLMinx.MOVE_STRINGS[first_moves[move + 1]] );
    }
    System.out.println();
    System.out.println( "Siblings" );
    System.out.println();
    System.out.print( "      " );
    for ( int i = 0; i < moves.length; i++ ) {
      byte move = moves[i];
      System.out.print( LLMinx.MOVE_STRINGS[move] );
    }
    System.out.println( "" );
    System.out.print( "Root: " );
    for ( int i = 0; i < moves.length; i++ ) {
      byte move = moves[i];
      byte next_sibling = next_siblings[0][move];
      if ( next_sibling == -1 ) {
        System.out.print( "--  " );
      }
      else {
        System.out.print( LLMinx.MOVE_STRINGS[next_sibling] );
      }
    }
    System.out.println( "" );
    for ( int j = 0; j < moves.length; j++ ) {
      byte last_move = moves[j];
      System.out.print( LLMinx.MOVE_STRINGS[last_move] + ": " );
      for ( int i = 0; i < moves.length; i++ ) {
        byte move = moves[i];
        byte next_sibling = next_siblings[last_move + 1][move];
        if ( next_sibling == -1 ) {
          System.out.print( "--  " );
        }
        else {
          System.out.print( LLMinx.MOVE_STRINGS[next_sibling] );
        }
      }
      System.out.println( "" );
    }
  }

  private boolean isAllowed( byte aPreviousMove, byte aMove ) {
    return fMetric == LLMinxMetric.FIFTH ?
        LLMinx.INVERSE_MOVES[aPreviousMove] != aMove :
        aPreviousMove / 4 != aMove / 4;
  }

  private void buildPruningTables() {
    fUsedTables = null;
    pruners = fSearchMode.getPruners();
    pruned = new long[Byte.MAX_VALUE + 1];
    tables = new byte[pruners.length][];
    for ( int i = 0; i < pruners.length && !fInterupted; i++ ) {
      fireEvent( new StatusEvent( StatusEventType.MESSAGE, "Initialzing pruning table " + pruners[i].getName() + "...", 0 ) );
      Arrays.fill( pruned, 0 );
      fPruner = pruners[i];
      if ( !fPruner.isPrecomputed( fMetric ) ) {
        // build pruning table.
        fireEvent( new StatusEvent( StatusEventType.MESSAGE, "Building pruning table...", 0 ) );
        LLMinx.setKeepMoves( false );
        tables[i] = buildPruningTable( fPruner );
        if ( fInterupted ) break;

        // writing table to disk.
        fireEvent( new StatusEvent( StatusEventType.MESSAGE, "Writing table to disk...", 0 ) );
        fPruner.saveTable( tables[i], fMetric );
      }
      else {
        // read table from disk.
        fireEvent( new StatusEvent( StatusEventType.MESSAGE, "Reading pruning table from disk...", 0 ) );
        tables[i] = fPruner.loadTable( fMetric );
        for ( int j = 0; j < tables[i].length; j++ ) {
          pruned[tables[i][j]]++;
        }
//        for (int j = 0; j < pruned.length && pruned[j] != 0; j++) {
//          long l = pruned[j];
//          System.out.println("Depth " + j + ": " + l);
//        }
      }
    }
  }

  private byte[] buildPruningTable( LLMinxPruner aPruner ) {
    byte[] table = new byte[aPruner.getTableSize()];
    Arrays.fill( table, Byte.MAX_VALUE );
    LLMinx minx = new LLMinx();
    int coordinate = aPruner.getCoordinate( minx );
    table[coordinate] = 0;
    fNodes.set(1);
    int previous_depth_length = 1;
    byte depth = 0;
    byte next_depth;
    boolean forward_search;
    fireEvent( new StatusEvent( StatusEventType.START_BUILDING_TABLE, "Building pruning table " + fPruner.getName() + "...", 0 ) );
    while ( previous_depth_length > 0 && !fInterupted ) {
      fireEvent( new StatusEvent( StatusEventType.MESSAGE, "Depth " + depth + ": " + previous_depth_length, 0 ) );
      forward_search = previous_depth_length < table.length - fNodes.get();
      previous_depth_length = 0;
      next_depth = ( byte ) ( depth + 1 );
      if ( forward_search ) {
        // iterate over all table entries from previous depth
        for ( int i = 0; i < table.length && !fInterupted; i++ ) {
          byte tcoordinate = table[i];
          if ( tcoordinate == depth ) {
            // recuperate situation.
            aPruner.getMinx( i, minx );

            // apply all moves on it.
            for ( int m = 0; m < moves.length; m++ ) {
              minx.move( moves[m] );
              coordinate = aPruner.getCoordinate( minx );
              // if it is a new situation, store depth in table.
              if ( table[coordinate] == Byte.MAX_VALUE ) {
                table[coordinate] = next_depth;
                fNodes.incrementAndGet();
                previous_depth_length++;
              }
              minx.undoMove();
            }
          }
        }
      }
      else {
        // iterate over all empty table entries
        for ( int i = 0; i < table.length && !fInterupted; i++ ) {
          byte tcoordinate = table[i];
          if ( tcoordinate == Byte.MAX_VALUE ) {
            // recuperate situation.
            aPruner.getMinx( i, minx );

            // apply all moves on it.
            for ( int m = 0; m < moves.length; m++ ) {
              minx.move( moves[m] );
              coordinate = aPruner.getCoordinate( minx );
              // if it is a situation, store depth in table.
              if ( table[i] == Byte.MAX_VALUE && table[coordinate] == depth ) {
                table[i] = next_depth;
                previous_depth_length++;
                fNodes.incrementAndGet();
              }
              minx.undoMove();
            }
          }
        }
      }
      depth++;
    }
    fireEvent( new StatusEvent( StatusEventType.END_BUILDING_TABLE, "Building pruning table " + fPruner.getName() + "...", 1 ) );
    return table;
  }

  private void filterPruningTables() {
    ArrayList<LLMinxPruner> used_pruners = new ArrayList<LLMinxPruner>();
    ArrayList<byte[]> used_tables = new ArrayList<byte[]>();
    for ( int i = 0; i < pruners.length; i++ ) {
      if ( !( pruners[i].usesCornerPermutation() && fIgnoreCornerPositions ) &&
          !( pruners[i].usesEdgePermutation() && fIgnoreEdgePositions ) &&
          !( pruners[i].usesCornerOrientation() && fIgnoreCornerOrientations ) &&
          !( pruners[i].usesEdgeOrientation() && fIgnoreEdgeOrientations ) ) {
        used_pruners.add( pruners[i] );
        used_tables.add( tables[i] );
      }
    }
    fUsedPruners = used_pruners.toArray( new LLMinxPruner[used_pruners.size()] );
    fUsedTables = used_tables.toArray( new byte[used_tables.size()][] );
  }

  private static boolean checkOptimal( LLMinx aMinx ) {
    byte[] moves = aMinx.getMoves();
    for ( int i = 1; i < aMinx.getDepth(); i++ ) {
      if ( i < aMinx.getDepth() - 1 && moves[i - 1] == moves[i] && moves[i] == moves[i + 1] ) {
        return false;
      }
      if ( i < aMinx.getDepth() - 1 && moves[i + 1] == moves[i - 1] ) {
        if ( ( moves[i] / 4 == LLMinx.L_MOVE / 4 && moves[i - 1] / 4 == LLMinx.R_MOVE / 4 )
            || ( moves[i] / 4 == LLMinx.R_MOVE / 4 && moves[i - 1] / 4 == LLMinx.L_MOVE / 4 )
            || ( moves[i] / 4 == LLMinx.B_MOVE / 4 && moves[i - 1] / 4 == LLMinx.F_MOVE / 4 )
            || ( moves[i] / 4 == LLMinx.F_MOVE / 4 && moves[i - 1] / 4 == LLMinx.B_MOVE / 4 ) ) {
          return false;
        }
      }
    }
    return true;
  }

  public double getProgress() {
    if ( fSearchStarted ) {
      long checked = fNodes.get();
      int branching_factor = 1;
      for ( int i = 0; i < next_siblings[1].length; i++ ) {
        if ( next_siblings[1][i] != -1 ) branching_factor++;
      }
      for ( int i = 0; i < pruned.length; i++ ) {
        long l = pruned[i];
        checked += ( ( long ) ( Math.pow( branching_factor, i + 1 ) - 1 ) / ( branching_factor - 1 ) ) * l * ( i + 1 == fDepth ? moves.length : branching_factor );
      }
      long total = 1 + ( moves.length ) * ( ( long ) ( Math.pow( branching_factor, fDepth ) - 1 ) / ( branching_factor - 1 ) );
      return ( ( double ) checked / total );
//      return "Depth " + fDepth + ", nodes checked: " + checked + " / " + total + " (" + (checked * 1000 / total) / 10d + "%)"
//      + " (" + ((total - nodes) * 100 / (double) total) + "% pruning table efficiency)"
//        ;
    }
    else if ( fPruner != null ) {
      return ( double ) fNodes.get() / fPruner.getTableSize();
    }
    else {
      return 0;
    }
  }

  private boolean nextNode( LLMinx aMinx ) {
    if ( aMinx.getDepth() < fDepth ) {
      aMinx.move( first_moves[aMinx.getLastMove() + 1] );
      return false;
    }
    else {
      return backTrack( aMinx );
    }
  }

  private boolean backTrack( LLMinx aMinx ) {
    if ( aMinx.getDepth() == 1 ) return true;
    byte sibling = aMinx.undoMove();
    byte last_move = aMinx.getLastMove();
    byte next_sibling = next_siblings[last_move + 1][sibling];
    while ( last_move != -1 && next_sibling == -1 ) {
      if ( aMinx.getDepth() == 1 ) return true;
      sibling = aMinx.undoMove();
      last_move = aMinx.getLastMove();
      next_sibling = next_siblings[last_move + 1][sibling];
    }
    if ( last_move == -1 && next_sibling == -1 ) {
      return true;
    }
    else {
      aMinx.move( next_sibling );
      return false;
    }
  }

  private boolean nextParallel(LLMinx aMinx ) {
    byte sibling = aMinx.undoMove();
    byte next_sibling = next_siblings[0][sibling];
    if ( next_sibling == -1 ) {
      return true;
    }
    else {
      aMinx.move( next_sibling );
      return false;
    }
  }

  public void addStatusListener( StatusListener aListener ) {
    fEventListeners.add( aListener );
  }

  public void removeEventListener( StatusListener aListener ) {
    fEventListeners.remove( aListener );
  }

  synchronized private void fireEvent( StatusEvent aStatusEvent ) {
    Iterator<StatusListener> listeners = fEventListeners.iterator();
    while ( listeners.hasNext() ) {
      StatusListener listener = listeners.next();
      listener.statusEvent( aStatusEvent );
    }
  }

}
