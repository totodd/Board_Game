// This code and its idea are created and own by the following authors:
// Tao Chen (u6074544),
// Sheng Xu (u5538588),
// Chen Chen (u6032167).
// All the responsibility are preserved by the authors.

package comp1110.ass2;
/**
 * Author: Chen Chen
 * a enum class containing all the piece information
 * int array with size 9 representing the nine space a piece can occupy
 * 0 means empty
 * 1 means on lower layer
 * 2 means on top layer
 */
public enum Pieces {
    A(new int[] {1,2,0,2,1,2,1,0,0}),
    B(new int[] {0,2,0,0,1,2,0,2,1}),
    C(new int[] {0,2,0,0,1,2,1,2,0}),
    D(new int[] {0,2,0,2,1,0,0,2,1}),
    E(new int[] {0,2,0,2,1,0,1,2,0}),
    F(new int[] {0,0,1,0,1,2,1,2,0}),
    G(new int[] {0,2,1,0,1,2,1,2,0}),
    H(new int[] {0,2,1,2,1,0,0,2,1});


    private final int[] maskPos;

    Pieces(int[] maskPos){
        this.maskPos = maskPos;
    }

    public int[] getMask(){
        return maskPos;
    }

    int getCenter(char rot){
        return maskPos[4];
    }

}
