package comp1110.ass2;

public enum Pieces {
//    A(DU0UDUD00),
//    B(0U00DU0UD),
//    C(0U00DUDU0),
//    D(0U0UD00UD),
//    E(0U0UD0DU0)
//    F(00D0DUDU0),
//    G(0UD0DUDU0),
//    H(0UDUD00UD)

    A(new int[] {1,2,0,2,1,2,1,0,0}),
    B(new int[] {0,2,0,0,1,2,0,2,1}),
    C(new int[] {0,2,0,0,1,2,1,2,0}),
    D(new int[] {0,2,0,2,1,0,0,2,1}),
    E(new int[] {0,2,0,2,1,0,1,2,0}),
    F(new int[] {0,0,1,0,1,2,1,2,0}),
    G(new int[] {0,2,1,0,1,2,1,2,0}),
    H(new int[] {0,2,1,2,1,0,0,2,1});
    //down=1
    //up===2

    private int[] maskPos;

    Pieces(int[] maskPos){
        this.maskPos = maskPos;
    }

    public int[] getMaskPlacement(char rot){
        return rotate(rot);
    }

    int[] rotate(char rot){
        int rotate = (int)rot-65;
        boolean flip = (rotate-3)>0;
        rotate = rotate%4;
        maskPos = flipArray(maskPos,flip);
        maskPos = rotateArray(maskPos,rotate);


        //TODO : rotate maskPos according to rot
        return maskPos;
    }
    public int getRotateDegree (char rot){
        return rotateDegree(rot);
    }

    static int rotateDegree(char rot){
        int rotate = (int)rot-65;
        rotate = rotate%4;
        return rotate*90;
    }


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

    static int[] flipArray(int[] originalArray,boolean flip){
        if(flip){
            int[] outputArray = new int[9];
            for(int i=0;i<9;i++){
                if (originalArray[i]==0){
                    outputArray[(i-i%3+2-i%3)]=0;
                }else if(originalArray[i]==1){
                    outputArray[(i-i%3+2-i%3)]=2;
                }else if (originalArray[i]==2){
                    outputArray[(i-i%3+2-i%3)]=1;
                }
            }
            return outputArray;
        }
        return originalArray;
    }


    int getCenter(){
        return maskPos[4];
    }

}
