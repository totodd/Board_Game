package comp1110.ass2.test;

import comp1110.ass2.Place;
import comp1110.ass2.StepsGame;

import java.util.List;



public class T5test {
    public static void main(String[] args) {

        String s = "CHSAHQ";

        List<Place> p = StepsGame.turnToPlace(s);


//        for (int i=0;i< p.get(0).getBoardIdx().length;i++){
//            System.out.println(((p.get(0).getBoardIdx()[i] >= 50)&(p.get(0).getValue()[i]>0)));
//        }
//        for (int i=0;i< p.get(0).getBoardIdx().length;i++){
//            System.out.println(((p.get(0).getBoardIdx()[i] < 0)&(p.get(0).getValue()[i]>0)));
//        }
//        for (int i=0;i< p.get(0).getBoardIdx().length;i++){
//            System.out.println(((p.get(0).getBoardIdx()[i] < 0)&(p.get(0).getValue()[i]>0))
//                    | ((p.get(0).getBoardIdx()[i] >= 50)&(p.get(0).getValue()[i]>0)));
//        }

        System.out.println("On right level: "+ StepsGame.onRightLevel(p.get(0)));
        System.out.println("center: " + p.get(0).getPieceCenter());
        System.out.println("Within board: " + StepsGame.withInBoard(p.get(0)));
//        System.out.println();
//        for (int i : p.get(0).getValue()) {
//            System.out.println(i);
//        }

        System.out.println( StepsGame.isPlacementSequenceValid(s));

    }
}
