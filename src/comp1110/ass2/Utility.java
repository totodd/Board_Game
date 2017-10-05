package comp1110.ass2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Utility {

    public static void writeString(String strings,String filename,boolean append) throws Exception {
        try {
            File file = new File(filename);
            boolean fvar = file.createNewFile();
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
        FileWriter fileWriter = new FileWriter(filename,append);
        fileWriter.write(strings + "\n");
        fileWriter.close();
    }
    public static void writeString(String strings,String filename) throws Exception {
        writeString(strings, filename,false);
    }

    public static void writeStringArray(String strings[],String filename,boolean append) throws Exception {
        try {
            File file = new File(filename);
            boolean fvar = file.createNewFile();
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
        FileWriter fileWriter = new FileWriter(filename,append);
        for (int i = 0; i < strings.length; i++) {
            fileWriter.write(strings[i] + "\n");
        }
        fileWriter.close();
    }
    public static void writeStringArray(String strings[],String filename) throws Exception {
        writeStringArray(strings,filename,false);
    }


    public static String[] readFiletoStringArray(String filename) throws Exception {
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()) {
            Scanner sc = new Scanner(new java.io.File(filename));
            List<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            String[] strings = lines.toArray(new String[0]);
            return strings;
        }
        return new String[0];

    }

    public static String[] deduplicateStringArray(String input[]){
        HashMap<String,String> map=new HashMap<>();
        for (String i:input){
            map.put(i,i);
        }
        String[] output=map.values().toArray(new String[map.size()]);
        return output;
    }

    public static String[] mergeStringArray(String input1[],String input2[]){
        HashMap<String,String> map=new HashMap<>();
        if (!isArrayEmpty(input1)){
            for (String i:input1){
                map.put(i,i);
            }
        }
        if (!isArrayEmpty(input2)){
            for (String i:input2){
                map.put(i,i);
            }
        }
        String[] output=map.values().toArray(new String[map.size()]);
        return output;
    }

    public static boolean isArrayEmpty(Object[] input){
        if(input.length==0){
            return true;
        }
        return false;
    }
}
