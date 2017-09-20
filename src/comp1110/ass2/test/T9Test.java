package comp1110.ass2.test;

import comp1110.ass2.Pieces;
import comp1110.ass2.StepsGame;

import java.util.ArrayList;
import java.util.Arrays;

public class T9Test {
    public static void main(String[] args) {

        boolean[] origBoardStatus = new boolean[StepsGame.BOARD_STATUS.length];

        ArrayList<Character> boardLeft = new ArrayList<>();
        for(int i = 0; i<origBoardStatus.length; i++){
            if(origBoardStatus[i])
                boardLeft.add(StepsGame.BOARD_STRING.charAt(i));
        }
        char[] boardLeftChar = boardLeft.toString().toCharArray();
//        StepsGame.getFullSolution(StepsGame.BOARD_STATUS, Arrays.asList(Pieces.values()), null, boardLeft);
    }
}
