package comp1110.ass2.test;

import comp1110.ass2.Place;
import comp1110.ass2.StepsGame;
import org.junit.Test;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.util.List;



public class T5test {

    @Test
    public void onRightLevel(){
        String s = "Ehu";
        List<Place> p = StepsGame.turnToPlace(s);
        assertTrue(StepsGame.onRightLevel(p.get(0)));
    }

    @Test
    public void onWrongLevel(){
        String s = "AAh";
        List<Place> p = StepsGame.turnToPlace(s);
        assertFalse(s + "is not on Right level",StepsGame.onRightLevel( p.get(0)));
    }

    @Test
    public void isSequenceValid(){
        String s = "DFOGGQEDIBAkFHn";
        assertTrue(s + "is sequence valid", StepsGame.isPlacementSequenceValid(s));
    }

    @Test
    public void isNotSequenceValid(){
        String s = "AAWBBi";
        assertFalse(s + "is not sequence valid", StepsGame.isPlacementSequenceValid(s));
    }

    @Test
    public void isNotWithinBoard(){
        String s = "AAA";
        assertFalse(StepsGame.isPlacementSequenceValid(s));
    }

    @Test
    public void isWithinBoard(){
        String s = "AAW";
        assertTrue(StepsGame.isPlacementSequenceValid(s));
    }



}
