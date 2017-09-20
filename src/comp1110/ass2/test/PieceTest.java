package comp1110.ass2.test;

import comp1110.ass2.Pieces;

import java.util.Arrays;

public class PieceTest {


    public static void main(String[] args) {
//Get int value of char
        System.out.println((int)'A');//65
        System.out.println((int)'^');//94
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
