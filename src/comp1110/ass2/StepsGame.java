// This code and its idea are created and own by the following authors:
// Tao Chen (u6074544),
// Sheng Xu (u5538588),
// Chen Chen (u6032167).
// All the responsibility are preserved by the authors.


package comp1110.ass2;


import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.Set;

/**
 * This class provides the text interface for the Steps Game
 * <p>
 * The game is based directly on Smart Games' IQ-Steps game
 * (http://www.smartgames.eu/en/smartgames/iq-steps)
 */
public class StepsGame implements Serializable{
    //    private static final int StartPieceNum = 4;
    public static final int ROW_LENGTH = 10;
    private static final int BOARD_LENGTH = 50;

    public static final String BOARD_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxy";
    public static boolean[] BOARD_STATUS = new boolean[BOARD_LENGTH]; // false means available, true means occupied
    private static final String BOARD_UPPER = "BDFHJKMOQSVXacefhjlnqsuwy";
    private static final String BOARD_LOWER = "ACEGILNPRTUWYbdgikmoprtvx";
    private static final String LEFT_EDGE = "AKUfp";
    private static final String RIGHT_EDGE = "JTeoy";
    private static final String PIECES = "ABCDEFGH";
    public static final ArrayList<String> viableSingleSolutions = new ArrayList<>();
    private static ArrayList<String> finalSolutions = new ArrayList<>();
    private static ArrayList<ArrayList<String>> temp = new ArrayList<>();


    /**
     * Author: Tao Chen
     * check if the placement is within the board
     * @param placement (Place)  -> int[3*3] + position
     * @return if within the board
     */
    public static boolean withInBoard(Place placement) {
        int[] position = placement.getBoardIdx();
        int[] val = placement.getValue();
        boolean withInLeftRight = false;
        boolean withInUpDown = true;


        int mid = position[4];
        int left;
        int right;
        if (val[0] > 0
                | val[3] > 0
                | val[6] > 0)
            left = mid - 1;
        else left = mid;

        if (val[2] > 0
                | val[5] > 0
                | val[8] > 0)
            right = mid + 1;
        else right = mid;

        int left_col = left % 10;
        int mid_col = mid % 10;
        int right_col = right % 10;

        if (((mid_col - left_col) <= 1)
                & ((mid_col - left_col) >= 0)
                & (((right_col - mid_col) <= 1))
                & (((right_col - mid_col) >= 0)))
            withInLeftRight = true;

        for (int i = 0; i < placement.getBoardIdx().length; i++) {
            if (((placement.getBoardIdx()[i] < 0) & (placement.getValue()[i] > 0))) {
                withInUpDown = false;
                break;
            }

            if (((placement.getBoardIdx()[i] >= 50) & (placement.getValue()[i] > 0))) {
                withInUpDown = false;
                break;
            }
        }


        return (withInLeftRight
                &&
                withInUpDown);
    }

    /**
     * Author: Tao Chen
     * Check if the center of the piece is on the right level, etc: lower level center should be at lower level board
     *
     * @param placement (Place)
     * @return if on right level
     */
    public static boolean onRightLevel(Place placement) {
        switch (placement.getCenter()) {
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
     * Author: Tao Chen
     * put the placement on the board the return the board index occupied
     * @param placement (Place)
     * @return 3x3 -> [50] to add on BOARD_STRING, then update the conflict UPPER area on [50]
     */

    static boolean[] putOnBoard(Place placement, boolean[] temp_Status) {
        if (temp_Status.length == BOARD_STATUS.length) {
            for (int i = 0; i < placement.getBoardIdx().length; i++) {
                int boardIdx = placement.getBoardIdx()[i];
                if (boardIdx < 50 & boardIdx >= 0 & placement.getValue()[i] > 0) {
                    temp_Status[boardIdx] = (placement.getValue()[i] > 0);
                }
                if (placement.getValue()[i] == 2) {
                    if (boardIdx - ROW_LENGTH > 0)
                        temp_Status[boardIdx - ROW_LENGTH] = true;
                    if (!LEFT_EDGE.contains(String.valueOf(BOARD_STRING.charAt(boardIdx))))
                        temp_Status[boardIdx - 1] = true;
                    if (!RIGHT_EDGE.contains(String.valueOf(BOARD_STRING.charAt(boardIdx))))
                        temp_Status[boardIdx + 1] = true;
                    if (boardIdx + ROW_LENGTH < 50)
                        temp_Status[boardIdx + ROW_LENGTH] = true;
                }
            }
        }

        return temp_Status;
    }


    /**
     * Author: Chen Chen
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
        if (piecePlacement.length() != 3) {
            return false;
        }
        char first = piecePlacement.charAt(0);
        char second = piecePlacement.charAt(1);
        char third = piecePlacement.charAt(2);
        if ((int) first < 65 || (int) first > 72) {
            return false;
        }
        if ((int) second < 65 || (int) second > 72) {
            return false;
        }
        if ((int) third < 65 || (int) third > 121) {
            return false;
        } else if ((int) third > 89 && (int) third < 97) {
            return false;
        }
        return true;
    }


    /**
     * Author: Chen Chen
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N three-character piece placements (where N = 1 .. 8);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        if (placement == null) {
            return false;
        }
        if (placement.isEmpty()) {
            return false;
        }
        if (placement.length() % 3 != 0) {
            return false;
        }
        String[] piecePlacementArray = placement.split("(?<=\\G.{3})");
        int[] pieceUsed = {0, 0, 0, 0, 0, 0, 0, 0};
        //                A,B,C,D,E,F,G,H
        //                65,66,67,68,69,70,71,72
        for (String i : piecePlacementArray) {
            if (!isPiecePlacementWellFormed(i)) {
                return false;
            }
            if (pieceUsed[(int) i.charAt(0) - 65] == 0) {
                pieceUsed[(int) i.charAt(0) - 65] = 1;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     *  finished and responsible by Tao Chen u6074544

     * Recursion to check if the sequence is valid, try to put on board piece by piece and check the conflict
     * @param placement placement to put on board
     * @param boardStatus if the board is occupied or not
     * @return true if all pieces have no conflict
     */
    private static boolean sequenceValid(List<Place> placement, boolean[] boardStatus) {
        if (onRightLevel(placement.get(0)) & withInBoard(placement.get(0))) {
            int[] board = placement.get(0).getBoardIdx();
            for (int i = 0; i < placement.get(0).getBoardIdx().length; i++) {
                int newIdx = placement.get(0).getBoardIdx()[i];
                if (newIdx < 50 & newIdx >= 0) {
                    if (boardStatus[newIdx] & (placement.get(0).getValue()[i] > 0)) {
                        return false;
                    }
                }
            }
            boardStatus = putOnBoard(placement.get(0), boardStatus);
        } else {
            return false;
        }

        if (placement.size() <= 1) {
            return true;
        }
        placement.remove(0);
//        boardStatus = putOnBoard(placement.get(0),boardStatus);
        return sequenceValid(placement, boardStatus);
    }

    public static List<Place> turnToPlace(String placement) {
        List<Place> places = new ArrayList<Place>();
        for (int i = 0; i < placement.length(); i = i + 3) {
            places.add(new Place(
                    Pieces.valueOf(String.valueOf(placement.charAt(i))), placement.charAt(i + 1), placement.charAt(i + 2)));
        }
        return places;
    }



    /**
     *    finished and responsible by Tao Chen u6074544
     *
     * Determine whether a placement sequence is valid.  To be valid, the placement
     * sequence must be well-formed and each piece placement must be a valid placement
     * (with the pieces ordered according to the order in which they are played).
     *
     * @param placement A placement sequence string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementSequenceValid(String placement) {
        // FIXME Task 5: determine whether a placement sequence is valid
        if (isPlacementWellFormed(placement)) {
            List<Place> places = turnToPlace(placement);
            boolean[] tempStatus = new boolean[BOARD_STATUS.length];
            return sequenceValid(places, tempStatus);
        } else {
            return false;
        }
    }




    /**
     *   finished and responsible by Tao Chen u6074544

     *
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
    public static Set<String> getViablePiecePlacements(String placement, String objective) {
        // FIXME Task 6: determine the correct order of piece placements
        List<Place> placed = turnToPlace(placement);
        List<Place> toPlace = turnToPlace(objective);
        List<List<Place>> sol = new ArrayList<>();


        sol = findTail(placed, toPlace, sol);
        Set<Place> nextPlace = new HashSet<>();
        Set<String> res = new HashSet<>();
        if(placed.size()<8){
            for(List<Place> s : sol) {
                if (!nextPlace.contains(s.get(placed.size())))
                    nextPlace.add(s.get(placed.size()));
            }
        }
        for(Place p : nextPlace){
            res.add(p.toString());
        }

        return res;
    }

    @Test
    public void isValidTale(){
        String placement = "";
        String objective = "EEfCHSAHQFDNGBLDAiHFlBDx";
        List<Place> placed = turnToPlace(placement);
        List<Place> toPlace = turnToPlace(objective);
        List<List<Place>> sol = new ArrayList<>();
        sol = findTail(placed, toPlace, sol);
        Set<Place> nextPlace = new HashSet<>();
        for(List<Place> s : sol) {
            if (!nextPlace.contains(s.get(placed.size())))
                nextPlace.add(s.get(placed.size()));
        }
        System.out.println(nextPlace);
    }

    /**
     *   finished and responsible by Tao Chen u6074544
     *
     * Recursion to get all solutions of Task 6, viable placements
     *
     * @param placed Initial placement
     * @param toPlace the placements haven't placed on board
     * @param sol the variable to save all solutions in recursion
     * @return full solutions
     */
    private static List<List<Place>> findTail(List<Place> placed, List<Place>toPlace, List<List<Place>> sol){
        String res = "";
        for (Place s : placed) res = res + s;

        if(placed.size() == 0 | isPlacementSequenceValid(res)) {
            if (placed.size() == 8) {
                sol.add(placed);
            }
            for(Place p : toPlace){
                List<Place> temp = new LinkedList<>(placed);
                temp.add(p);
                List<Place> tempToPlace = new LinkedList<>(toPlace);
                tempToPlace.remove(p);
                findTail(temp, tempToPlace, sol);
            }
        }
        return sol;
    }

    /**
     * Author: Sheng Xu
     * Given a string of placement, the function will return all the unavailable
     * positions on board based on the shape of each single used piece
     * @param placement
     * @return a char array contains all occupied positions on board
     */
    public static char[] occupiedPositions(String placement) {
        String occupiedPos = "";
        List<Place> pieces = turnToPlace(placement);
        for (Place p : pieces) {
            int[] circleStatus = p.getValue();
            int[] boardIdx = p.getBoardIdx();
            for (int ind = 0; ind < boardIdx.length; ind++) {
                if (boardIdx[ind] >= 0 && circleStatus[ind] > 0) {
                    String temp = String.valueOf(BOARD_STRING.charAt(boardIdx[ind]));
                    occupiedPos += temp;
                }
            }
        }
        return occupiedPos.toCharArray();
    }

    /**
     * Author: Sheng Xu
     * Before the start of the game, the function will find out all viable piece-placements
     */
    public static void viableSinglePlacement() {
        String pcs = PIECES;
        String viablePositions = BOARD_STRING;
        for (int i = 0; i < pcs.length(); i++) {
            for (int j = 0; j < PIECES.length(); j++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (isPlacementSequenceValid(newPiece)) {
                        if (!viableSingleSolutions.contains(newPiece))
                            viableSingleSolutions.add(newPiece);
                    }
                }
            }
        }
    }

    static ArrayList viableSinglePlacement2() {
        ArrayList<String> single = new ArrayList<>();
        String pcs = PIECES;
        String viablePositions = BOARD_STRING;
        for (int i = 0; i < pcs.length(); i++) {
            for (int j = 0; j < PIECES.length(); j++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (isPlacementSequenceValid(newPiece)) {
                        if (!single.contains(newPiece))
                            single.add(newPiece);
                    }
                }
            }
        }
        return single;
    }


    static String[] viableSinglePlacement2SeperateFile() throws Exception {
        String output[]=Utility.readFiletoStringArray("1/1.txt");

        for(int i=0;i<output.length;i++){
            char piece=output[i].charAt(0);
            String newFileName="1/"+String.valueOf(piece)+".txt";
            System.out.println(newFileName);
            Utility.writeString(output[i],newFileName,true);
        }
        return output;
    }


    /**
     * Author: Sheng Xu
     * Given an uncompleted placement (length less than 24), the function will
     * find out all next viable piece-placements and add them to the end of the
     * previous placement
     * @param placement
     * @return an Arraylist of string of all viable new placements
     */
    public static ArrayList<String> nextPlacement(String placement) {
        ArrayList<String> singleSolutions = new ArrayList<>();
        ArrayList<String> newPlacements = new ArrayList<>();
        singleSolutions.addAll(viableSingleSolutions);
        String[] tempSols = new String[singleSolutions.size()];
        tempSols = singleSolutions.toArray(tempSols);
        for (String s : tempSols) {
            for (int i = 0; i < placement.length(); i += 3) {
                if (s.charAt(0) == placement.charAt(i)) {
                    singleSolutions.remove(s);
                }
            }
        }
        char[] OP = occupiedPositions(placement);
        tempSols = new String[singleSolutions.size()];
        tempSols = singleSolutions.toArray(tempSols);
        for (String s : tempSols) {
            for (int i = 0; i < OP.length; i++) {
                if (s.charAt(2) == OP[i]) {
                    singleSolutions.remove(s);
                }
            }
        }

        for(String s : singleSolutions){
            if(isPlacementSequenceValid(placement+s)){
                newPlacements.add(placement+s);
            }
        }
        return newPlacements;

    }

    /**
     * Author: Sheng Xu
     * After a set of new placements were figured out, solutions for each new placement will be generated, and
     * set as the new placement results; Apart from that, solutions with same piece-placements but different orders
     * would be recorded once only (unique solutions)
     */
    static void findSolutions() {
        ArrayList<String> tempSols = new ArrayList<>();
        ArrayList<String> norm = new ArrayList<>();
        int pl = finalSolutions.get(0).length()/3;
        temp.clear();

        for(String s: finalSolutions){
            temp.add(nextPlacement(s));
        }

        finalSolutions.clear();
        for(int i = 0; i<temp.size(); i ++)
            tempSols.addAll(temp.get(i));
        for(String s : tempSols){
            String nm = normalize(s);
            if(!norm.contains(nm)){
                norm.add(nm);
                finalSolutions.add(s);
            }
        }

        pl++;
        if(pl<8){
            findSolutions();
        }
    }

    /**
     * Credict to Steve
     * Sort the pieces in the placement based on the alphabetically
     * @param placement string of a valid placement
     * @return Unique placement (without order)
     */
    static String normalize(String placement) {
        String[] pp = new String[8];
        boolean flip = false;
        for (int i = 0; i < placement.length(); i += 3) {
            int idx = placement.charAt(i) - 'A';
            pp[idx] = placement.substring(i, i + 3);
        }
        String norm = "";
        for (int i = 0; i < pp.length; i++) {
            if (pp[i] != null) norm += pp[i];
        }
        return norm;
    }




    /**
     * Author: Sheng Xu
     * Return an array of all unique (unordered) solutions to the game, given a
     * starting placement.   A given unique solution may have more than one than
     * one placement sequence, however, only a single (unordered) solution should
     * be returned for each such case.
     *
     * @param placement A valid piece placement string.
     * @return An array of strings, each describing a unique unordered solution to
     * the game given the starting point provided by placement.
     */
    public static String[] getSolutions(String placement) throws Exception {
        // FIXME Task 9: determine all solutions to the game, given a particular starting placement
        int pl = placement.length()/3;
        viableSinglePlacement();
        finalSolutions = nextPlacement(placement);
        ArrayList<String> tempSols = new ArrayList<>();
        ArrayList<String> norm = new ArrayList<>();
        tempSols.addAll(finalSolutions);
        finalSolutions.clear();
        for(String s : tempSols){
            String nm = normalize(s);
            if(!norm.contains(nm)){
                norm.add(nm);
                finalSolutions.add(s);
            }
        }

        pl++;
        if(pl<8)
            findSolutions();
        String[] Sols = new String[finalSolutions.size()];
        Sols = finalSolutions.toArray(Sols);
//        String tmp[] = readFiletoStringArray("FullSol.txt");
//        writeStringArray(deduplicateStringArray(mergeStringArray(tmp,Sols)),"FullSol.txt");
        return Sols;
    }
}
