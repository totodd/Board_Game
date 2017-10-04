package comp1110.ass2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class T9_Test {
    @Test
    public void test_getFullSolution(){
    }
    @Test
    public void test_SinglePlacement(){
        StepsGame.viableSinglePlacement();
        assertTrue("The total number of viable single placement should be 812!",StepsGame.viableSingleSolutions.size()==812);
    }
    @Test
    public void test_occupiedPositions(){
        assertTrue("No positions should be occupied for initial board status!",StepsGame.occupiedPositions("").length==0);

        char[] tOP = StepsGame.occupiedPositions("DFOGGQEDIBAkFHnHCiAALCAg");
        assertTrue("There should be 43 positions occupied!",tOP.length==43);
        String top = Arrays.toString(tOP);
        assertFalse(top.contains("C")||top.contains("E")||top.contains("d")||top.contains("f")
                ||top.contains("m")||top.contains("t")||top.contains("y"));

        tOP = StepsGame.occupiedPositions("BGKFCNCFlAFnHHSGAiECP");
        assertTrue(tOP.length==38);
        top = Arrays.toString(tOP);
        assertFalse(top.contains("C")||top.contains("H")||top.contains("J")||top.contains("O")
                ||top.contains("V")||top.contains("f")||top.contains("g")||top.contains("h")||top.contains("p")
                ||top.contains("q")||top.contains("t")||top.contains("v"));
    }
    @Test
    public void test_NextPlacement(){
        StepsGame.viableSinglePlacement();
        ArrayList<String > tNP = StepsGame.nextPlacement("DFOCGQGDLADgHFjBGSFHl");
        assertTrue(tNP.contains("DFOCGQGDLADgHFjBGSFHlEAo"));
        tNP = StepsGame.nextPlacement("CEQEHu");
        assertTrue(tNP.contains("CEQEHuGEO"));
    }
}
