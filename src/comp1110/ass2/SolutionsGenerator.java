package comp1110.ass2;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolutionsGenerator {
    private static final String PIECES = "ABCDEFGH";
    private static ArrayList<String> finalSolutions = new ArrayList<>();
    private static ArrayList<String> firstPieces = new ArrayList<>();
    private static ArrayList<String> secondPieces = new ArrayList<>();
    private static ArrayList<String> thirdPieces = new ArrayList<>();
    private static ArrayList<String> fouthPieces = new ArrayList<>();
    private static ArrayList<String> fifthPieces = new ArrayList<>();
    private static ArrayList<String> sixthPieces = new ArrayList<>();
    private static ArrayList<String> seventhPieces = new ArrayList<>();
    private static ArrayList<String> finalPieces = new ArrayList<>();

    public static String setViablePositions(String placement) {
        String viablePositions = StepsGame.BOARD_STRING;
        List<Place> places = StepsGame.turnToPlace(placement);
        for (Place p : places) {
            int[] a = p.getValue();
            int[] b = p.getBoardIdx();
            for (int ind = 0; ind < b.length; ind++) {
                if (b[ind] >= 0 && a[ind] > 0) {
                    String temp = String.valueOf(StepsGame.BOARD_STRING.charAt(b[ind]));
                    viablePositions = viablePositions.replace(temp, "");
                }
            }
        }
        return viablePositions;
    }

    //    static void getSolutions(String placement) {
//        ArrayList<String> sols = new ArrayList<>();
//         String newPlacement = placement;
//         pcs = PIECES;
//         viablePositions = "";
//
//        if (placement.length() != 0) {
//            viablePositions = setViablePositions(placement);
//            for (int i = 0; i < placement.length(); i += 3) {
//                pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
//            }
//        } else
//            viablePositions = StepsGame.BOARD_STRING;
//
//        for (int j = 0; j < PIECES.length(); j++) {
//            for (int i = 0; i < pcs.length(); i++) {
//                for (int k = 0; k < viablePositions.length(); k++) {
//                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
//                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
//                        newPlacement = placement + newPiece;
//                        if (!sols.contains(newPlacement))
//                            sols.add(newPlacement);
//                    }
//                }
//            }
//        }
//
//        for (String s : sols) {
//
//            if (s.length() != 24) {
//                getSolutions(s);
//            }
//            else{
//                finalSolutions.add(s);
//            }
//        }
//    }
    static void putFirstPiece(String placement) {
        ArrayList<String> sols1 = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = StepsGame.BOARD_STRING;
        for (int i = 0; i < pcs.length(); i++) {
            for (int j = 0; j < PIECES.length(); j++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!sols1.contains(newPlacement))
                            sols1.add(newPlacement);
                    }
                }
            }
        }
        for (String s : sols1)
            firstPieces.add(s);
    }

    static void putSecondPiece(String placement) {
        ArrayList<String> sols2 = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = setViablePositions(placement);
        for (int i = 0; i < placement.length(); i += 3) {
            pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
        }

        for (int j = 0; j < PIECES.length(); j++) {
            for (int i = 0; i < pcs.length(); i++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!sols2.contains(newPlacement))
                            sols2.add(newPlacement);
                    }
                }
            }
        }
        for (String s : sols2)
            secondPieces.add(s);
    }

    static void putThirdPiece(String placement) {
        ArrayList<String> sols3 = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = setViablePositions(placement);
        for (int i = 0; i < placement.length(); i += 3) {
            pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
        }

        for (int j = 0; j < PIECES.length(); j++) {
            for (int i = 0; i < pcs.length(); i++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!sols3.contains(newPlacement))
                            sols3.add(newPlacement);
                    }
                }
            }
        }
        for (String s : sols3)
            thirdPieces.add(s);
    }

    static void putFouthPiece(String placement) {
        ArrayList<String> sols4 = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = setViablePositions(placement);
        for (int i = 0; i < placement.length(); i += 3) {
            pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
        }

        for (int j = 0; j < PIECES.length(); j++) {
            for (int i = 0; i < pcs.length(); i++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!sols4.contains(newPlacement))
                            sols4.add(newPlacement);
                    }
                }
            }
        }
        for (String s : sols4)
            fouthPieces.add(s);
    }

    static void putFifthPiece(String placement) {
        ArrayList<String> sols5 = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = setViablePositions(placement);
        for (int i = 0; i < placement.length(); i += 3) {
            pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
        }

        for (int j = 0; j < PIECES.length(); j++) {
            for (int i = 0; i < pcs.length(); i++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!sols5.contains(newPlacement))
                            sols5.add(newPlacement);
                    }
                }
            }
        }
        for (String s : sols5)
            fifthPieces.add(s);
    }

    static void putSixthPiece(String placement) {
        ArrayList<String> sols6 = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = setViablePositions(placement);
        for (int i = 0; i < placement.length(); i += 3) {
            pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
        }

        for (int j = 0; j < PIECES.length(); j++) {
            for (int i = 0; i < pcs.length(); i++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!sols6.contains(newPlacement))
                            sols6.add(newPlacement);
                    }
                }
            }
        }
        for (String s : sols6)
            sixthPieces.add(s);
    }

    static void putSeventhPiece(String placement) {
        ArrayList<String> sols7 = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = setViablePositions(placement);
        for (int i = 0; i < placement.length(); i += 3) {
            pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
        }

        for (int j = 0; j < PIECES.length(); j++) {
            for (int i = 0; i < pcs.length(); i++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!sols7.contains(newPlacement))
                            sols7.add(newPlacement);
                    }
                }
            }
        }
        for (String s : sols7)
            seventhPieces.add(s);
    }

    static void putFinalPieces(String placement) {
        ArrayList<String> solsF = new ArrayList<>();
        String newPlacement = placement;
        String pcs = PIECES;
        String viablePositions = setViablePositions(placement);
        for (int i = 0; i < placement.length(); i += 3) {
            pcs = pcs.replace(String.valueOf(placement.charAt(i)), "");
        }

        for (int j = 0; j < PIECES.length(); j++) {
            for (int i = 0; i < pcs.length(); i++) {
                for (int k = 0; k < viablePositions.length(); k++) {
                    String newPiece = String.valueOf(pcs.charAt(i)) + String.valueOf(PIECES.charAt(j)) + String.valueOf(viablePositions.charAt(k));
                    if (StepsGame.isPlacementSequenceValid(placement + newPiece)) {
                        newPlacement = placement + newPiece;
                        if (!solsF.contains(newPlacement))
                            solsF.add(newPlacement);
                    }
                }
            }
        }
        for (String s : solsF)
            finalPieces.add(s);
    }

    public static void main(String[] args) throws IOException {
        putFirstPiece("");
        System.out.println(firstPieces.size() + " solutions of Single piece placements generated!");

        File newTextFile = new File("src/comp1110/ass2/solutions1.txt");
        FileWriter fw = new FileWriter(newTextFile);
        for (String s : firstPieces) {
            fw.write(s + "\n");
            putSecondPiece(s);
        }
        fw.close();
        System.out.println(secondPieces.size() + " solutions of Two pieces placement generated!");

        newTextFile = new File("src/comp1110/ass2/solutions2.txt");
        fw = new FileWriter(newTextFile);
        for (String s : secondPieces) {
            fw.write(s + "\n");
            putThirdPiece(s);
        }
        fw.close();
        System.out.println(thirdPieces.size() + " solutions of Three pieces placement generated!");

        newTextFile = new File("src/comp1110/ass2/solutions3.txt");
        fw = new FileWriter(newTextFile);
        for (String s : thirdPieces) {
            fw.write(s + "\n");
            putFouthPiece(s);
        }
        fw.close();
        System.out.println(fouthPieces.size() + " solutions of Four pieces placement generated!");

        newTextFile = new File("src/comp1110/ass2/solutions4.txt");
        fw = new FileWriter(newTextFile);
        for (String s : fouthPieces) {
            fw.write(s + "\n");
            putFifthPiece(s);
        }
        fw.close();
        System.out.println(fifthPieces.size() + " solutions of Five pieces placement generated!");

        newTextFile = new File("src/comp1110/ass2/solutions5.txt");
        fw = new FileWriter(newTextFile);
        for (String s : fifthPieces) {
            fw.write(s + "\n");
            putSixthPiece(s);
        }
        fw.close();
        System.out.println(sixthPieces.size() + " solutions of Six pieces placement generated!");

        newTextFile = new File("src/comp1110/ass2/solutions6.txt");
        fw = new FileWriter(newTextFile);
        for (String s : sixthPieces) {
            fw.write(s + "\n");
            putSeventhPiece(s);
        }
        fw.close();
        System.out.println(sixthPieces.size() + " solutions of Seven pieces placement generated!");

        newTextFile = new File("src/comp1110/ass2/solutions7.txt");
        fw = new FileWriter(newTextFile);
        for (String s : seventhPieces) {
            fw.write(s + "\n");
            putFinalPieces(s);
        }
        fw.close();


//        getSolutions("");
//        System.out.println(finalSolutions.size());

        File newTextFile1 = new File("src/comp1110/ass2/Finalsolutions.txt");
        FileWriter fw1 = new FileWriter(newTextFile1);
        for (String s : finalPieces) {
            fw1.write(s + "\n");
        }
        fw1.close();
    }
}
