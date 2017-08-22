package comp1110.ass2;

import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;

/**
 * all placement are saved in object Place
 */

public class Place {
    private Pieces piece;
    private String position;
    private int[] boardIdx;
    private int[] value;

    Place(Pieces piece, String position){
        this.piece = piece;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public int[] getValue() {
        value = piece.getMaskPlacement();
        return value;
    }

    public int[] getBoardIdx() {
        return boardIdx;
    }

    public void setValue(int[] value) {
        //TODO : set 3x3 value of piece
        this.value = value;
    }

    /**
     *      x-11    x-10    x-9
     *      x-1     x       x+1
     *      x+9     x+10    x+11
     */
    public void setBoardIdx(int[] boardIdx) {
        //TODO : set 3x3 index of BOARD according to the position and piece value

            int center = StepsGame.BOARD_STRING.indexOf(position);
            int boardRow = StepsGame.ROW_LENGTH;

            this.boardIdx = new int[]{center-boardRow-1, center-boardRow, center-boardRow+1,
                                        center-1, center, center+1,
                                        center+boardRow-1, center+boardRow, center+boardRow+1};
            //updated
            for(int i : boardIdx){
                if(i == 0)
                  boardIdx = removeElement(boardIdx,i);
            }
    }
    //updated
    public int[] removeElement(int[] a, int del) {
        System.arraycopy(a,del+1,a,del,a.length-1-del); //http://blog.csdn.net/kesalin/article/details/566354
        return a;
    }
}

