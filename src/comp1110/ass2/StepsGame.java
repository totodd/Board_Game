package comp1110.ass2;

import java.util.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides the text interface for the Steps Game
 *
 * The game is based directly on Smart Games' IQ-Steps game
 * (http://www.smartgames.eu/en/smartgames/iq-steps)
 */
public class StepsGame {
//    private static final int StartPieceNum = 4;
    public static final int ROW_LENGTH = 10;
    private static final int BOARD_LENGTH = 50;

    public static final String BOARD_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxy";
    public static boolean[] BOARD_STATUS = new boolean[BOARD_LENGTH]; // false means available, true means occupied
    private static final String BOARD_UPPER = "BDFHJKMOQSVXacefhjlnqsuwy";
    private static final String BOARD_LOWER = "ACEGILNPRTUWYbdgikmoprtvx";
    private static final String LEFT_EDGE = "AKUfp";
    private static final String RIGHT_EDGE = "JTeoy";

    /**
     *
     * @param placement (Place)  -> int[3*3] + position
     * @return if within the board
     */
    public static boolean withInBoard(Place placement){
        //TODO: finish withInBoard
        int[] position = placement.getBoardIdx();
        int[] val = placement.getValue();
        boolean withInLeftRight = false;
        boolean withInUpDown = true;

        int mid = position[4];
        int left;
        int right;
        if(val[0] > 0
                | val[3]>0
                | val[6]>0)
            left = mid - 1;
        else left = mid;

        if(val[2] > 0
                | val[5]>0
                | val[8]>0)
            right = mid + 1;
        else right = mid;

        int left_col = left%10;
        int mid_col = mid%10;
        int right_col = right%10;

        if(((mid_col - left_col)<=1)
                & ((mid_col - left_col)>=0)
                & (((right_col - mid_col)<=1))
                & (((right_col - mid_col)>=0)))
            withInLeftRight = true;

        for (int i = 0; i< placement.getBoardIdx().length; i++){
            if (((placement.getBoardIdx()[i] < 0)&(placement.getValue()[i]>0))) {
                withInUpDown = false;
                break;
            }

            if(((placement.getBoardIdx()[i] >= 50)&(placement.getValue()[i]>0))){
                withInUpDown = false;
                break;
            }
        }


        return (withInLeftRight
                &&
                withInUpDown);
    }

    /**
     * Check if the center of the piece is on the right level, etc: lower level center should be at lower level board
     * @param placement (Place)
     * @return if on right level
     */
    public static boolean onRightLevel(Place placement){
        switch (placement.getPieceCenter()){
            case 1:
                boolean res = BOARD_LOWER.contains(String.valueOf(placement.getPosition()));
                return res;
            case 2:
                return BOARD_UPPER.contains(String.valueOf(placement.getPosition()));
            default:
                return false;
        }
    }


    /**
     *
     * @param placement (Place)
     * @return 3x3 -> [50] to add on BOARD_STRING, then update the conflict UPPER area on [50]
     *
     */

    static boolean [] putOnBoard(Place placement, boolean[]temp_Status){
        if(temp_Status.length == BOARD_STATUS.length) {
            for (int i = 0; i < placement.getBoardIdx().length; i++) {
                int boardIdx = placement.getBoardIdx()[i];
                if(boardIdx < 50 & boardIdx >= 0) {
                    temp_Status[boardIdx] = (placement.getValue()[i] > 0);
                }
                if (placement.getValue()[i] == 2) {
                    if(boardIdx - ROW_LENGTH > 0)
                        temp_Status[boardIdx - ROW_LENGTH] = true;
                    if(!LEFT_EDGE.contains(String.valueOf(BOARD_STRING.charAt(boardIdx))))
                        temp_Status[boardIdx - 1] = true;
                    if(!RIGHT_EDGE.contains(String.valueOf(BOARD_STRING.charAt(boardIdx))))
                        temp_Status[boardIdx + 1] = true;
                    if(boardIdx + ROW_LENGTH < 50)
                        temp_Status[boardIdx + ROW_LENGTH] = true;
                }
            }
        }

        return temp_Status;
    }


    /**
     * Determine whether a piece placement is well-formed according to the following:
     * - it consists of exactly three characters
     * - the first character is in the range A .. H (shapes)
     * - the second character is in the range A .. H (orientations)
     * - the third character is in the range A .. Y and a .. y (locations)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 2: determine whether a piece placement is well-formed
        char first = piecePlacement.charAt(0);
        char second = piecePlacement.charAt(1);
        char third = piecePlacement.charAt(2);
        if((int)first<65||(int)first>72){
            return false;
        }
        if((int)second<65||(int)second>72){
            return false;
        }
        if((int)third<65||(int)third>121){
            return false;
        }else if((int)third>89&&(int)third<97){
            return false;
        }
        return true;
    }

    /**
     * Determine whether a placement string is well-formed:
     *  - it consists of exactly N three-character piece placements (where N = 1 .. 8);
     *  - each piece placement is well-formed
     *  - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
     public static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        if (placement==null){
            return false;
        }
        if(placement.isEmpty()){
            return false;
        }
        if (placement.length()%3!=0){
            return false;
        }
        String[] piecePlacementArray = placement.split("(?<=\\G.{3})");
        int[] pieceUsed= {0,0,0,0,0,0,0,0};
        //                A,B,C,D,E,F,G,H
        //                65,66,67,68,69,70,71,72
        for (String i:piecePlacementArray){
            if(!isPiecePlacementWellFormed(i)){
                return false;
            }
            if(pieceUsed[(int)i.charAt(0)-65]==0){
                pieceUsed[(int)i.charAt(0)-65]=1;
            }else {
                return false;
            }
        }
        return true;
    }

    static boolean sequenceValid(List<Place> placement, boolean[] boardStatus){
        if(onRightLevel(placement.get(0)) & withInBoard(placement.get(0))){
            int[] board = placement.get(0).getBoardIdx();
            for(int i=0; i<placement.get(0).getBoardIdx().length;i++){
                int newIdx = placement.get(0).getBoardIdx()[i];
                if(newIdx<50 & newIdx>=0){
                    if(boardStatus[newIdx]&(placement.get(0).getValue()[i]>0)){
                        return false;
                    }
                }
            }
            boardStatus = putOnBoard(placement.get(0), boardStatus);
        }else{
            return false;
        }

        if(placement.size()<=1){
            return true;
        }
        placement.remove(0);
//        boardStatus = putOnBoard(placement.get(0),boardStatus);
        return sequenceValid(placement,boardStatus);
    }

    public static List<Place> turnToPlace(String placement){
        List<Place> places = new ArrayList<Place>();
        for(int i = 0 ; i < placement.length(); i = i+3){
            places.add(new Place(placement.charAt(i), placement.charAt(i+1), placement.charAt(i+2)));
        }
        return places;
    }

    /**
     * Determine whether a placement sequence is valid.  To be valid, the placement
     * sequence must be well-formed and each piece placement must be a valid placement
     * (with the pieces ordered according to the order in which they are played).
     *
     * @param placement A placement sequence string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementSequenceValid(String placement) {
        // FIXME Task 5: determine whether a placement sequence is valid
        if(isPlacementWellFormed(placement)) {
            List<Place> places = turnToPlace(placement);
            boolean[] tempStatus = new boolean[BOARD_STATUS.length];
            return sequenceValid(places, tempStatus);
        }else{
            return false;
        }
    }

    /**
     * Given a string describing a placement of pieces and a string describing
     * an (unordered) objective, return a set of all possible next viable
     * piece placements.   A viable piece placement must be a piece that is
     * not already placed (ie not in the placement string), and which will not
     * obstruct any other unplaced piece.
     *
     * @param placement A valid sequence of piece placements where each piece placement is drawn from the objective
     * @param objective A valid game objective, but not necessarily a valid placement string
     * @return An set of viable piece placements
     */
    static Set<String> getViablePiecePlacements(String placement, String objective) {
        // FIXME Task 6: determine the correct order of piece placements
        String[] sol = getSolutions(placement);
        Set<String> res = new HashSet<String>();

        for (String e : res){
            res.add(e.substring(placement.length(), placement.length()+2));
        }


        return res;
    }

    /**
     * Return an array of all solutions to the game, given a starting placement.
     *
     * @param placement  A valid piece placement string.
     * @return An array of strings, each describing a solution to the game given the
     * starting point provided by placement.
     */
    static String[] getSolutions(String placement) {
        // FIXME Task 9: determine all solutions to the game, given a particular starting placement
        return null;
    }
}
