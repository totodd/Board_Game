package comp1110.ass2;

import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * all placement are saved in object Place
 */

public class Place {
    private Pieces piece;
    private char position;
    private char rot;

    private int[] value;


    Place(char pieceName, char rot, char position){
        for (Pieces p : Pieces.values()){
            if(pieceName == (p.name().charAt(0)))
                this.piece = p;
        }
        this.position = position;
        this.rot = rot;
        this.value = piece.getMaskPlacement(this.rot);
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

    public int getPieceCenter(){
        return piece.getCenter(this.rot);
    }

}

