package comp1110.ass2;

public enum Pieces {
    A(new int[] {1,2,0,2,1,2,1,0,0}),
    B(new int[] {0,2,0,0,1,2,0,2,1}),
    C(new int[] {0,2,0,0,1,2,1,2,0}),
    D(new int[] {0,2,0,2,1,0,0,2,1}),
    E(new int[] {0,2,0,2,1,0,1,2,0}),
    F(new int[] {0,0,1,0,1,2,1,2,0}),
    G(new int[] {0,2,1,0,1,2,1,2,0}),
    H(new int[] {0,2,1,2,1,0,0,2,1});


    private final int[] maskPos;

    Pieces(int[] maskPos){
        this.maskPos = maskPos;
    }

    public int[] getMask(){
        return maskPos;
    }

    int getCenter(char rot){
        return maskPos[4];
    }

}
