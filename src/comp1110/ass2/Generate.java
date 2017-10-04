package comp1110.ass2;


import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Generate {


    public static void main(String[] args) throws Exception {
        for(int i=1;i<9;i++){
            File dir = new File(String.valueOf(i));
            boolean successful = dir.mkdir();
        }
        generateBase();
        generateSeq("",2);
        /*
        * This will generate from the Base point to the depth
        * Example Base = "" depth 1
        * will give you A,B,C,D,E,F,G,H
        *
        * Base "A",depth 1
        * will give you
        * "AB","AC","AD',"AE","AF","AG","AH"
        *
        * */

    }

    static void generateSeq(String base,int depth) throws Exception {
        if (depth!=0){
            String base2[]=nextSeq(base);
            for (String i:base2){
                //System.out.println(i);
                generateSeq(i,depth-1);
            }
        }
    }

    static String[] nextSeq(String base) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


        String add[]={"A","B","C","D","E","F","G","H"};
        char baseChar[]=base.toCharArray();
        ArrayList<Character> baseList = new ArrayList<>();
        ArrayList<String> output=new ArrayList<>();
        for (char a:baseChar){
            baseList.add(a);
        }
        for (int i=0;i<add.length;i++){
            if (!baseList.contains(add[i].charAt(0))){
                String next=base+add[i];
                LocalDateTime now = LocalDateTime.now();
                System.out.println(base+add[i]+"  "+"Current Length:"+next.length()+"  "+dtf.format(now));
                output.add(next);
                File file = new File((base.length()+1)+"/"+base+add[i]+".txt");
                if (!file.exists()){
                    addPiece(base,add[i]);
                }else {
                    System.out.println(base+add[i]+"Exist");
                }

            }
        }
        String outputString[]=output.toArray(new String[output.size()]);
        return outputString;
    }

    static void addPiece(String base,String add) throws Exception {
        String startbase[] = StepsGame.readFiletoStringArray(base.length()+"/"+base+".txt");
        String startadd[] = StepsGame.readFiletoStringArray("1/"+add+".txt");
        String placement;
        int[] pieceUsed = {0, 0, 0, 0, 0, 0, 0, 0};
        //                A,B,C,D,E,F,G,H
        //                65,66,67,68,69,70,71,72
        int count =0;
        for (int a = 0; a < startbase.length; a++) {
            for (int b = 0; b < startadd.length; b++) {
                placement = startbase[a] + startadd[b];
                if (StepsGame.isPlacementSequenceValid(placement)) {
                    count++;
                    //System.out.println(base+add+" "+count+" "+placement);
                    StepsGame.writeString(placement,(base.length()+1)+"/"+base+add+".txt",true);
                }
            }
        }

    }

    static void generateBase() throws Exception {
        ArrayList<String> single = StepsGame.viableSinglePlacement2();
        String[] start = single.toArray(new String[single.size()]);
        StepsGame.writeStringArray(start,"1/1.txt");
        StepsGame.viableSinglePlacement2SeperateFile();
    }
}
