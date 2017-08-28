package comp1110.ass2;

import org.junit.Test;

import static comp1110.ass2.StepsGame.isPlacementWellFormed;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class T3_Test {
    @Test
    public void testT3_empty_false(){
        assertFalse(isPlacementWellFormed(""));
    }
    @Test
    public void testT3_null_false(){
        String temp = null;
        assertFalse(isPlacementWellFormed(temp));
    }
    @Test
    public void testT3_duplicate_false(){
        assertFalse(isPlacementWellFormed("ABCABC"));
    }
    @Test
    public void testT3_3char_true(){
        assertTrue(isPlacementWellFormed("ABC"));
    }
    @Test
    public void testT3_6char_true(){
        assertTrue(isPlacementWellFormed("ABNBBd"));
    }
    @Test
    public void testT3_5char_false(){
        assertFalse(isPlacementWellFormed("ABNBB"));
    }
}
