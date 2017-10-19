// This code and its idea are created and own by the following authors:
// Tao Chen (u6074544),
// Sheng Xu (u5538588),
// Chen Chen (u6032167).
// All the responsibility are preserved by the authors.

package comp1110.ass2;

    /**
     * This class is used to manipulate the enum class
     */
public class Place {
    private Pieces piece;
    private char position;
    private char rot;
    private int[] value;

    Place(Pieces p, char rot, char position){
        this.piece = p;
        this.position = position;
        this.rot = rot;
        this.value = rotate(p,rot);
    }

    public Pieces getPiece() {
        return piece;
    }

    public char getPosition() {
        return position;
    }

    public int[] getValue() {
        return value;
    }

    /** set 3x3 index of BOARD according to the position and piece value
     *      x-11    x-10    x-9
     *      x-1     x       x+1
     *      x+9     x+10    x+11
     */
    public int[] getBoardIdx() {
        int center = StepsGame.BOARD_STRING.indexOf(position);
        int boardRow = StepsGame.ROW_LENGTH;

        return new int[]{center - boardRow - 1, center - boardRow, center - boardRow + 1,
                center - 1, center, center + 1,
                center + boardRow - 1, center + boardRow, center + boardRow + 1};
    }

    public int getCenter(){
        return value[4];
    }
    public static int getRotateDegree (char rot){
        return rotateDegree(rot);
    }


    @Override
    public String toString() {
        return piece.name()+String.valueOf(rot)+String.valueOf(position);
    }

    /**
     * Author: Chen Chen
     * rotate the original piece positions according to the rotation
     * @param p Pieces
     * @param rot a char from 'A','B','C','D','E','F','G','H'
     * @return int array representing the nine space a piece after rotation
     */
    private int[] rotate(Pieces p, char rot){
        int rotate = (int)rot-65;
        int[] mask  = p.getMask();
        boolean flip = (rotate-3)>0;
        rotate = rotate%4;
        int[] maskTemp = flipArray(mask,flip);
        maskTemp = rotateArray(maskTemp,rotate);
        return maskTemp;
    }

    private static int rotateDegree(char rot){
        int rotate = (int)rot-65;
        rotate = rotate%4;
        return rotate*90;
    }


    private static int turnRightSingle(int original){
        int j = original % 3;
        int i = (original - j)/3;
        int tempi = j;
        int tempj = 2-i;
        int output = tempi*3+tempj;
        return output;
    }

    private static int[] turnRightArray(int[] originalArray){
        int[] outputArray = new int[9];
        for (int i=0;i<=8;i++){
            int i2 = turnRightSingle(i);
            outputArray[i2]=originalArray[i];
        }
        return outputArray;
    }

    /**
     * Author: Chen Chen
     * flip the original piece positions
     * @param originalArray original int array of 9 positions
     * @param rotateTimes how many time to rotate 90 degrees
     * @return int array representing the nine space a piece after rotation
     */
    private static int[] rotateArray(int[] originalArray, int rotateTimes){
        int[] outputArray = originalArray;
        for (int i=1;i<=rotateTimes;i++){
            outputArray = turnRightArray(outputArray);
        }
        return outputArray;
    }
    /**
     * Author: Chen Chen
     * flip the original piece positions
     * @param originalArray original int array of 9 positions
     * @param flip true to do the flip
     * @return int array representing the nine space a piece after flip
     */
    private static int[] flipArray(int[] originalArray, boolean flip){
        if(flip){
            int[] outputArray = new int[9];
            for(int i=0;i<9;i++){
                if (originalArray[i]==0){
                    outputArray[(i-i%3+2-i%3)]=0;
                }else if(originalArray[i]==1){
                    outputArray[(i-i%3+2-i%3)]=2;
                }else if (originalArray[i]==2){
                    outputArray[(i-i%3+2-i%3)]=1;
                }
            }
            return outputArray;
        }
        return originalArray;
    }
}

