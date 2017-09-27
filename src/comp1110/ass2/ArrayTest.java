package comp1110.ass2;

import org.junit.Test;

import static comp1110.ass2.StepsGame.isArrayEmpty;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ArrayTest {
    @Test
    public void isEmptyArray(){
        Integer[] test = {};
        Integer[] test2 = {1};
        assertTrue(isArrayEmpty(test));
        assertFalse(isArrayEmpty(test2));
    }

    @Test
    public void merge(){
        String a[]={"aa","bb"};
        String b[]={"cc","dd"};
        String c[]={"aa","bb","cc","dd"};
        assertTrue(StepsGame.mergeStringArray(a,b).length==4);
    }

    @Test
    public void deduplicate(){
        String a[]={"aa","bb"};
        String b[]={"bb","dd"};
        String c[]={"aa","bb","dd"};
        assertTrue(StepsGame.deduplicateStringArray(StepsGame.mergeStringArray(a,b)).length==3);
    }
}
