// This code and its idea are created and own by the following authors:
// Chen Chen (u6032167).
// All the responsibility are preserved by the authors.

package comp1110.ass2;

import java.util.ArrayList;
/**
 * Author: Chen Chen
 * This is another attempt to generate all solutions
 */
public class Generate2 {
    static final String BOARD_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxy";
    static final String PIECES = "ABCDEFGH";
    static final ArrayList<String> viableSingleSolutions = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        String start[]=Utility.readFiletoStringArray("4/DABC.txt");
        for(int i=0;i<start.length;i++){
            System.out.println(start[i]);
            StepsGame.getSolutions(start[i]);
        }
    }
        //StepsGame.getSolutions("EGOCGQGGS");
    }