import net.openhft.hashing.LongHashFunction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Scanner;


public class HashApp {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java HashApp <number of buckets> <bucket size>");
            return;
        }
        int n=0;
        int s=0;
        try{
            n=Integer.parseInt(args[0]);
            s=Integer.parseInt(args[1]);
            if(s<2){
                System.out.println("Bucket size cannot be less than 2");
                return;
            }
        }
        catch (Exception e){
            System.out.println("Error occur1: "+e.getMessage());
        }
        deleteAllTextFiles();
        Scanner scanner = new Scanner(System.in);
        HashMap<Integer, Integer> overflowHandle= new HashMap<Integer, Integer>();

        while(true){
            try{
                System.out.print("Please enter the string: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue; // Skip empty input
                }
                if(input.length()>s){
                    System.out.println("Input size can't be greater than bucket size because it create infinity overflow file");
                    continue;
                }
                long hash = Math.abs(hash(input));
                int bucketIndex = (int) (hash % n); // Bucket index (1 to n-1)
                bucketIndex= bucketIndex==0 ? n : bucketIndex; // if bucket index is 0 replace with n

                overflowHandle.putIfAbsent(bucketIndex, 0);
                Integer overflowNumber = overflowHandle.get(bucketIndex);
                String bucketFileName = overflowNumber ==0 ? (bucketIndex + ".txt") : (bucketIndex+"_"+overflowNumber+".txt");

                boolean isEmpty=false;
                int lengthOfFile=lengthOfFile(bucketFileName);

                lengthOfFile=lengthOfFile == 0 ? lengthOfFile : (lengthOfFile+1);
                if(lengthOfFile==0){
                    isEmpty=true;
                }

                if((lengthOfFile+input.length())>s){
                    overflowNumber+=1;
                    overflowHandle.put(bucketIndex,overflowNumber);
                    bucketFileName=bucketIndex+"_"+overflowNumber+".txt";
                    isEmpty=true;
                }

                writeInputToFile(bucketFileName,input,isEmpty);
            }
            catch (Exception e){
                System.out.println("Error occur2: "+e.getMessage());
            }
        }
    }

    private static long hash(String input) {
        return (long) LongHashFunction.xx().hashChars(input);
    }
    private static void deleteAllTextFiles() {
        File folder = new File(".");

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null) {
            System.out.println("No files found or folder doesn't exist!");
            return;
        }

        for (File file : files) {
            if (file.delete()) {
                System.out.println("Deleted: " + file.getName());
            } else {
                System.out.println("Failed to delete: " + file.getName());
            }
        }
    }
    private static int lengthOfFile(String bucketFileName){
        int charCount = 0;
        File file = new File(bucketFileName);

        try {
            // If file does not exist, create it
            if (!file.exists()) {
                file.createNewFile();
                return 0;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    charCount += line.length(); // count characters in this line
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return charCount;
    }
    private static void writeInputToFile(String bucketFileName, String input, boolean isEmpty){
        try (FileWriter writer = new FileWriter(bucketFileName,true)){
            if (isEmpty) {
                writer.write(input);
            } else {
                writer.write(","+input);
            }
            System.out.println(input+" added to: "+bucketFileName);

        }
        catch (Exception e){
            System.out.println("Error occur in Writer "+e.getMessage());
        }
    }
}