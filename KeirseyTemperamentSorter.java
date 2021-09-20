/*
 *  Authors: Alexander Romans, Derly Buentello
 *  Date: September 16, 2021
 *  Class: Computer Science II K
 *  Name: KeirseyTemperamentSorter.java
 */

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class KeirseyTemperamentSorter{
    
    /**
     * Converts the percentage of answers to the overall personality type. 
     * If for each personality type the percentage is 50%, it returns "X" for that personality trait.
     * If there was no data inputted for a personality type, it returns "-" for that personality type.
     * 
     * @param list the list of percentages of each personality type
     * @return the overall personality type, abbreviated as a {@code String} object.
     */
    private String percentToType(int[] list){
        String output = "";
        for(int i=0; i<list.length;i++){
            switch(i){
                case 0: output += list[i]==100||list[i]>50&&list[i]<100?"I":list[i]>=0&&list[i]<50?"E":list[i]==50?"X":"-"; break;
                case 1: output += list[i]==100||list[i]>50&&list[i]<100?"N":list[i]>=0&&list[i]<50?"S":list[i]==50?"X":"-"; break;
                case 2: output += list[i]==100||list[i]>50&&list[i]<100?"F":list[i]>=0&&list[i]<50?"T":list[i]==50?"X":"-"; break;
                case 3: output += list[i]==100||list[i]>50&&list[i]<100?"P":list[i]>=0&&list[i]<50?"J":list[i]==50?"X":"-"; break;
            }    
        }return output;
    }
    /**
     * Encodes the answers corresponding to the {@code name} and returns 
     * the data of their answers and corresponding personality combination.
     * 
     * @param name the identification of the person
     * @param list the array of the documented answers
     * @return the data of their personality level
     */
    public String encode(String name, String[] list){
        
        int[] aCount = {0, 0, 0, 0}; /* Extrovert, Sensation, Thinking, Judging */
        int[] bCount = {0, 0, 0, 0}; /* Introvert, iNtuitive, Feeling, Perceiving */
        int[] bPercent = {0, 0, 0, 0};
        
        for(int i = 0; i<list.length;i++){
            if(i%7==0){
                switch(list[i].toLowerCase()){
                    case "a" : aCount[0]++; break;
                    case "b" : bCount[0]++; break;
                }
            }else if(i%7==1||i%7==2){
                switch(list[i].toLowerCase()){
                    case "a" : aCount[1]++; break;
                    case "b" : bCount[1]++; break;
                }
            }else if(i%7==3||i%7==4){
                switch(list[i].toLowerCase()){
                    case "a" : aCount[2]++; break;
                    case "b" : bCount[2]++; break;
                }
            }else if(i%7==5||i%7==6){
                switch(list[i].toLowerCase()){
                    case "a" : aCount[3]++; break;
                    case "b" : bCount[3]++; break;
                }
            }        
        }for(int a = 0; a<bPercent.length; a++){
            try{bPercent[a] = (int)((bCount[a]*100.0)/(bCount[a]+aCount[a]));}
            catch(ArithmeticException ae){bPercent[a]=-1;}
        }
        return String.format(name + ":\n    %dA-%dB %dA-%dB %dA-%dB %dA-%dB\n    [%d%%, %d%%, %d%%, %d%%] = "+percentToType(bPercent)+"\n", aCount[0], bCount[0], aCount[1], bCount[1], aCount[2], bCount[2], aCount[3], bCount[3], bPercent[0], bPercent[1], bPercent[2], bPercent[3]);
    }
    
    public static void main(String[] args) throws IOException{
        
        /* The personality and output files are directed from my drive, cutrrently.  *
         * I don't know how to fix this problem yet.                                 */
        File personality = new File("personality.txt");
        File output = new File("output.txt");
        
        Scanner data = new Scanner(personality);
        PrintWriter out = new PrintWriter(new FileWriter(output));
        String name;
        KeirseyTemperamentSorter kts = new KeirseyTemperamentSorter();
        while(data.hasNextLine()){
            name = data.nextLine();
            if(data.hasNextLine())out.println(kts.encode(name, data.nextLine().split("")));
        }
        data.close(); // Closes the personality data
        out.close();  // Closes the PrintWriter
    }
}
