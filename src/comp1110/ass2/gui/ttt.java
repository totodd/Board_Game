package comp1110.ass2.gui;

import comp1110.ass2.StepsGame;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ttt {
    public static void main(String[] args) {
//        char a = 65;
//        int b = 65;
//        for(int i = 0; i <100; i ++) {
//            b = 65 + i % 4;
//            a = (char) b;
//            System.out.println(a);
//        }


        String a = "BGSAHQEFBGCg";
        StepsGame.viableSinglePlacement();
        Set<String> nextPc = new HashSet<>();
        String [] fin;
        try {
            fin = StepsGame.getSolutions(a);
            for(String f : fin){
                Set<String> temp = StepsGame.getViablePiecePlacements(a,f);
                nextPc.addAll(temp);
            }
            System.out.println(nextPc);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Bad placement, not solution!");
        }catch (Exception e) {
            e.printStackTrace();
        }



    }
}
