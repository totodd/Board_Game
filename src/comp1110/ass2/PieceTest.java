package comp1110.ass2;

import java.util.Arrays;

public class PieceTest {

    static int turnRightSingle(int original){
        int j = original % 3;
        int i = (original - j)/3;
        int tempi = j;
        int tempj = 2-i;
        int output = tempi*3+tempj;
        return output;
    }

    static int[] turnRightArray(int[] originalArray){
        int[] outputArray = new int[9];
        for (int i=0;i<=8;i++){
            int i2 = turnRightSingle(i);
            outputArray[i2]=originalArray[i];
        }
        return outputArray;
    }

    static int[] rotateArray(int[]originalArray,int rotateTimes){
        int[] outputArray = originalArray;
        for (int i=1;i<=rotateTimes;i++){
            outputArray = turnRightArray(outputArray);
        }
        return outputArray;
    }

    public static void main(String[] args) {

        int [] originalArray = PieceCC.A.getMaskPlacement();
        System.out.println("Original.A");
        System.out.println(Arrays.toString(originalArray));

        int[] outputArray = turnRightArray(originalArray);

        System.out.println("A.Output,turn,right x1");
        System.out.println(Arrays.toString(outputArray));

        outputArray = turnRightArray(outputArray);
        System.out.println("A.Output,turn,right x2");
        System.out.println(Arrays.toString(outputArray));

        outputArray = turnRightArray(outputArray);
        int[]rotateArray3=rotateArray(originalArray,3);
        System.out.println("A.Output,turn,right x3");
        System.out.println(Arrays.toString(outputArray));
        System.out.println(Arrays.toString(rotateArray3));


    }
}
