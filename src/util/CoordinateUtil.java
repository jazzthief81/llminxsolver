package util;

import java.util.Arrays;

/**
 *
 */
public class CoordinateUtil {

  public static final int[] POWERS_OF_TWO_MINUS_ONE = new int[30];
  public static final int[] POWERS_OF_TWO = new int[30];
  public static final int[] POWERS_OF_THREE = new int[20];
  public static final int[] FAC = new int[13];
  public static final int[][] CKN;

  static {
    for ( int i = 0; i < POWERS_OF_TWO_MINUS_ONE.length; i++ ) {
      POWERS_OF_TWO[i] = 1 << i;
      POWERS_OF_TWO_MINUS_ONE[i] = ( 1 << i ) - 1;
    }
    POWERS_OF_THREE[0] = 1;
    for ( int i = 1; i < POWERS_OF_THREE.length; i++ ) {
      POWERS_OF_THREE[i] = POWERS_OF_THREE[i - 1] * 3;
    }
    FAC[0] = 1;
    for ( int i = 1; i < FAC.length; i++ ) {
      FAC[i] = FAC[i - 1] * i;
    }
    CKN = new int[20][9];
    for ( int i = 0; i < CKN.length; i++ ) {
      int[] ckns = CKN[i];
      for ( int j = 0; j < ckns.length; j++ ) {
        ckns[j] = ( int ) c( i, j );
      }
    }
  }

  public static int getPermutationCoordinate( byte aPermutation[], byte aCubies[] ) {
    int coordinate = 0;
    byte[] locations = new byte[aCubies.length];
    System.arraycopy( aCubies, 0, locations, 0, aCubies.length );
    for ( int i = 0; i < aCubies.length - 2; i++ ) {
      int piece = aCubies[i];
      for ( int j = 0; j + i < locations.length; j++ ) {
        if ( aPermutation[locations[j]] == piece ) {
          coordinate *= ( locations.length - i );
          coordinate += j;
          for ( int k = j; k + i < locations.length - 1; k++ ) {
            locations[k] = locations[k + 1];
          }
          break;
        }
      }
    }
    return coordinate;
  }

  public static void getPermutation( int aCoordinate, byte aPermutation[], byte aCubies[] ) {
    int[] indices = new int[aCubies.length];
    byte[] locations = new byte[aCubies.length];
    System.arraycopy( aCubies, 0, locations, 0, aCubies.length );
    int factor = 3;
    int sum = 0;
    for ( int i = indices.length - 3; i >= 0; i-- ) {
      indices[i] = ( aCoordinate % factor );
      sum += indices[i];
      aCoordinate /= factor;
      factor++;
    }
    indices[indices.length - 2] = sum % 2;
    indices[indices.length - 1] = 0;
    for ( int i = 0; i < aCubies.length; i++ ) {
      int index = indices[i];
      aPermutation[locations[index]] = aCubies[i];
      for ( int k = index; k + i < locations.length - 1; k++ ) {
        locations[k] = locations[k + 1];
      }
    }
  }

//  public static int getPermutationCoordinate(byte aPermutation[], byte aCubies[]) {
//    int coordinate = 0;
//    for (int i = 1; i < aCubies.length; i++) {
//      int count = 0;
//      for (int j = 0; j < i; j++)
//        if (aPermutation[aCubies[j]] > aPermutation[aCubies[i]])
//          count++;
//
//      coordinate += FAC[i] * count;
//    }
//
//    return coordinate;
//  }
//
//  public static void getPermutation(int aCoordinate, byte aPermutation[], byte aCubies[]) {
//    byte work_cubies[] = new byte[aCubies.length];
//    System.arraycopy(aCubies, 0, work_cubies, 0, aCubies.length);
//    for (int i = work_cubies.length - 1; i > 0; i--) {
//      int cubie = aCoordinate / FAC[i];
//      aPermutation[aCubies[i]] = work_cubies[i - cubie];
//      if (cubie > 0)
//        System.arraycopy(work_cubies, (i + 1) - cubie, work_cubies, i - cubie, cubie);
//      aCoordinate -= FAC[i] * cubie;
//    }
//  }

  public static int getSeparationCoordinate( byte aPermutation[], byte aCubies[] ) {
    int coordinate = 0;
    int count = 1;
    for ( int i = 0; i < aPermutation.length; i++ ) {
      byte aPosition = aPermutation[i];
      int j;
      for ( j = 0; j < aCubies.length && aCubies[j] != aPosition; j++ ) ;
      if ( j != aCubies.length ) {
        coordinate += CKN[i][count];
        count++;
      }
    }

    return coordinate;
  }

  public static void getSeparation( int aCoordinate, byte aPermutation[], byte aCubies[] ) {
    Arrays.fill( aPermutation, ( byte ) -1 );
    for ( int count = aCubies.length; count > 0; count-- ) {
      int position_left;
      for ( position_left = aPermutation.length - 1; aCoordinate < CKN[position_left][count]; position_left-- ) ;
      aPermutation[position_left] = aCubies[count - 1];
      aCoordinate -= CKN[position_left][count];
    }

  }

  public static int getEdgeOrientationCoordinate( int aEdgeOrientation, int aEdgeCount ) {
    return POWERS_OF_TWO_MINUS_ONE[aEdgeCount - 1] & aEdgeOrientation;
  }

  public static int getEdgeOrientation( int aEdgeCoordinate, int aEdgeCount ) {
    aEdgeCoordinate = POWERS_OF_TWO_MINUS_ONE[aEdgeCount - 1] & aEdgeCoordinate;
    return aEdgeCoordinate | getParity( aEdgeCoordinate ) << aEdgeCount - 1;
  }

  public static int getCornerOrientationCoordinate( int aCornerOrientation, byte aCubies[] ) {
    int coordinate = 0;
    for ( int i = 0; i < aCubies.length - 1; i++ )
      coordinate += POWERS_OF_THREE[i] * ( aCornerOrientation >> aCubies[i] * 2 & 3 );

    return coordinate;
  }

  public static int getCornerOrientation( int aCornerOrientation, byte aCubies[] ) {
    int orientation = 0;
    int sum_orientation = 0;
    int i;
    for ( i = 0; i < aCubies.length - 1; i++ ) {
      int cubie_orientation = aCornerOrientation % 3;
      sum_orientation += cubie_orientation;
      orientation += POWERS_OF_TWO[aCubies[i] * 2] * cubie_orientation;
      aCornerOrientation /= 3;
    }

    orientation += POWERS_OF_TWO[aCubies[i] * 2] * ( ( 3 - sum_orientation % 3 ) % 3 );
    return orientation;
  }

  public static int getParity( int aInt ) {
    int x = aInt;
    x = x ^ ( x >> 16 );
    x = x ^ ( x >> 8 );
    x = x ^ ( x >> 4 );
    x = x ^ ( x >> 2 );
    x = x ^ ( x >> 1 );
    return x & 1;
  }

  public static long c( int aN, int aK ) {
    if ( aN < aK ) return 0;
    long result = 1;
    for ( int i = aN; i > aK; i-- ) {
      result *= i;
    }
    for ( int i = 1; i <= aN - aK; i++ ) {
      result /= i;
    }
    return result;
  }

}
