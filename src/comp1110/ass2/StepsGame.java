package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class provides the text interface for the Steps Game
 *
 * The game is based directly on Smart Games' IQ-Steps game
 * (http://www.smartgames.eu/en/smartgames/iq-steps)
 */
public class StepsGame {
//    private static final int StartPieceNum = 4;
    public static final String BOARD = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxy";

    private static final int ROW_LENGTH = 10;
    private static final int BOARD_LENGTH = 50;
    public static int[] BOARD_STATUS = new int[BOARD_LENGTH]; // 0 means available, 1 means unavailable to put pieces

    /**
     *
     * @param placement etc: "ABj"
     * @return if within the board
     */
    static boolean outOfBoard(String placement){
        boolean withInLeftRight = false;
        boolean withInUpDown = false;

        return (withInLeftRight
                &&
                withInUpDown);
    }

    /**
     *
     * @param wholePlacement 3*3 placement array for a piece
     * @return updated board status [50]
     */
    static int[] pieceOnBoard(Place place){
        int[] array = place.getArray();


        return transPieceToBoard(place.getExpandedArray());
    }

    /**
     *
     * @param piece (3x3)
     * @return 3x3 -> [50] to add on BOARD
     *
     */

    static int[] transPieceToBoard(int[] piece, int[] position){
        int[] TEMP_BSTATUS = BOARD_STATUS;

        for(int i = 0; i < 9; i++){
            TEMP_BSTATUS[position[i]] = piece[i];
        }

        return TEMP_BSTATUS;
    }


    /**
     *
     * @param position (string)
     * @return indices on board
     *
     *      x-11    x-10    x-9
     *      x-1     x       x+1
     *      x+9     x+10    x+11
     */

    static int[] getOnBoardIndex(String position){
        int CENTER_INDEX = BOARD.indexOf(position);
        int[] Index_ONBoard = {CENTER_INDEX-11,CENTER_INDEX-10,CENTER_INDEX-9,
                CENTER_INDEX-1,CENTER_INDEX, CENTER_INDEX+1,
                CENTER_INDEX+9,CENTER_INDEX+10,CENTER_INDEX+11};

        return Index_ONBoard;
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



        return (first >
                && second >
                && third >
                );
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
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed

        return false;
    }

    /**
     * Determine whether a placement sequence is valid.  To be valid, the placement
     * sequence must be well-formed and each piece placement must be a valid placement
     * (with the pieces ordered according to the order in which they are played).
     *
     * @param placement A placement sequence string
     * @return True if the placement sequence is valid
     */
    static boolean isPlacementSequenceValid(String placement) {
        // FIXME Task 5: determine whether a placement sequence is valid

        return false;
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
            res.add(e.substring(placement.length(), placement.length()+2))
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
