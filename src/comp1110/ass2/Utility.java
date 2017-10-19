// This code and its idea are created and own by the following authors:
// Chen Chen (u6032167).
// All the responsibility are preserved by the authors.


package comp1110.ass2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Utility {
    /**
     * Author: Chen Chen
     * write a string into a file
     * @param string The string need to write into the file.
     * @param filename the filename of the file,including the relative directory
     * @param append whether write the string in a new file
     * @throws Exception
     */
    public static void writeString(String string,String filename,boolean append) throws Exception {
        try {
            File file = new File(filename);
            boolean fvar = file.createNewFile();
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
        FileWriter fileWriter = new FileWriter(filename,append);
        fileWriter.write(string + "\n");
        fileWriter.close();
    }
    public static void writeString(String strings,String filename) throws Exception {
        writeString(strings, filename,false);
    }

    /**
     * Author: Chen Chen
     * write a string into a file
     * @param strings The strings array need to write into the file.
     * @param filename the filename of the file,including the relative directory
     * @param append whether write the string in a new file
     * @throws Exception
     */
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
    /**Author: Chen Chen
     * write a string into a file
     * method overloading with append=false
     */
    public static void writeStringArray(String strings[],String filename) throws Exception {
        writeStringArray(strings,filename,false);
    }

    /**Author: Chen Chen
     * read a string array from a file
     * @param filename the filename of the file,including the relative directory
     * @throws Exception
     */
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

    /**Author: Chen Chen
     * merge two string array into one, which can also deduplicate
     * @param input1 string array need to merge
     * @param input2 string array need to merge
     */
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
    /**Author: Chen Chen
     * determine if a array is empty
     * return true if empty
     * @param input array to determine
     */
    public static boolean isArrayEmpty(Object[] input){
        if(input.length==0){
            return true;
        }
        return false;
    }
}
