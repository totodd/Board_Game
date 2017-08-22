package comp1110.ass2;

public enum PieceCC {
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

    private final int[] maskstatus;

    PieceCC(int[] maskstatus){
        this.maskstatus=maskstatus;
    }

    int[] getMaskPlacement(){
        return maskstatus;
    }






















}
