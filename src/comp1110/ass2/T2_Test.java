package comp1110.ass2;

import org.junit.Test;

import static comp1110.ass2.StepsGame.isPiecePlacementWellFormed;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class T2_Test {

    @Test
    public void test_2char_false() {
        assertFalse(isPiecePlacementWellFormed("AB"));
        //assertTrue();
    }
    @Test
    public void test_3char_true() {
        assertTrue(isPiecePlacementWellFormed("ABJ"));
        //assertTrue();
    }

}
