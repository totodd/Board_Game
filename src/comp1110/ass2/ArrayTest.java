package comp1110.ass2;

import org.junit.Test;

import static comp1110.ass2.Utility.isArrayEmpty;
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
        assertTrue(Utility.mergeStringArray(a,b).length==4);
    }

    @Test
    public void deduplicate(){
        String a[]={"aa","bb"};
        String b[]={"bb","dd"};
        String c[]={"aa","bb","dd"};
        String d[]={"aa","aa","bb","bb"};
        assertTrue(Utility.deduplicateStringArray(Utility.mergeStringArray(a,b)).length==3);
        assertTrue(Utility.deduplicateStringArray(d).length==2);
    }
}
