package llminx.solver.searchmode;

import java.io.*;

/**
 *
 */
public abstract class AbstractLLMinxPruner implements LLMinxPruner {

  private static final String FILE_EXTENSION = ".prn";
  private String fName;
  private String fTablePath;

  public AbstractLLMinxPruner( String name, String tablePath ) {
    fName = name;
    fTablePath = tablePath;
  }

  public String getName() {
    return fName;
  }

  public boolean isPrecomputed( LLMinxMetric aMetric ) {
    return getTableFile( aMetric ).exists();
  }

  public byte[] loadTable( LLMinxMetric aMetric ) {
    try {
      BufferedInputStream prun_in = new BufferedInputStream( new FileInputStream( getTableFile( aMetric ) ), 1 << 20 );
      byte[] table = new byte[getTableSize()];
      for ( int i = 0; i < table.length; i++ ) {
        table[i] = (byte) prun_in.read();
      }
      prun_in.read( table );
      prun_in.close();
      return table;
    }
    catch ( IOException e ) {
      return null;
    }
  }

  public void saveTable( byte[] aTable, LLMinxMetric aMetric ) {
    try {
      BufferedOutputStream prun_out = new BufferedOutputStream( new FileOutputStream( getTableFile( aMetric ) ), 1 << 22 );
      for ( int i = 0; i < aTable.length; i++ ) {
        prun_out.write( aTable[i] );
      }
      prun_out.close();
    }
    catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  private File getTableFile( LLMinxMetric aMetric ) {
    return new File( fTablePath + aMetric.name() + FILE_EXTENSION );
  }

}
