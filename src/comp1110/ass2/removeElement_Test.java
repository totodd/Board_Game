package comp1110.ass2;
import java.util.Arrays;

public class removeElement_Test {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 0, 9, 10, 0, 13, 15};

        System.out.println(Arrays.toString(a)+" "+a.length);


        for (int i = 0; i < a.length; i++) {
            if(a[i] == 0){
                a = removeElement(a, i);
                System.out.println(Arrays.toString(a)+" "+a.length);
            }
        }
    }

    public static int[] removeElement(int[] original, int del) {
        int[] lessened = new int[original.length-1];
        System.arraycopy(original,del+1,original,del,original.length-1-del);
        lessened = Arrays.copyOf(original,original.length-1);
        return lessened;
    }
}

