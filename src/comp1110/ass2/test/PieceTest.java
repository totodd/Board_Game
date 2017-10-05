package comp1110.ass2.test;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import comp1110.ass2.Pieces;
import comp1110.ass2.Place;
import comp1110.ass2.StepsGame;

import java.util.Arrays;
import java.util.List;

public class PieceTest {


    public static void main(String[] args) {
//Get int value of char
        System.out.println((int)'A');//65
        System.out.println((int)'^');//94
        List<Place> a = StepsGame.turnToPlace("AAWBBJ");

        System.out.println(a.toString());
//Test Duplicate placement
        String placement="CEQCEQ";
        String[] piecePlacementArray = placement.split("(?<=\\G.{3})");
        int[] pieceUsed= {0,0,0,0,0,0,0,0};
        //                A,B,C,D,E,F,G,H
        //                65,66,67,68,69,70,71,72
        for (String i:piecePlacementArray){
            if(pieceUsed[(int)i.charAt(0)-65]==0){
                pieceUsed[(int)i.charAt(0)-65]=1;
                System.out.println("Add");
            }else {
                System.out.println("Duplicate");
            }
            System.out.println(Arrays.toString(pieceUsed));
        }
//Piece Rotation/Flip

    }
}
