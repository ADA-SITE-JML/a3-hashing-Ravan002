import java.io.File;
import java.io.FileWriter;
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
            System.out.println("n: "+n+"  s: "+s);
        }
        catch (Exception e){
            System.out.println("Error occur1: "+e.getMessage());
        }
        deleteAllTextFiles();
        Scanner scanner = new Scanner(System.in);

        while(true){
            try{
                System.out.print("Please enter the string: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue; // Skip empty input
                }
                long hash = Math.abs(input.hashCode());
                int bucketIndex = (int) (hash % n); // Bucket index (1 to n-1)
                bucketIndex= bucketIndex==0 ? n : bucketIndex; // if bucket index is 0 replace with n
                String bucketFileName = bucketIndex + ".txt";
                System.out.println("HashCode: "+hash+"\nbucket:"+bucketIndex+"\nfilename: "+bucketFileName);

                try (FileWriter writer = new FileWriter(bucketFileName,true)){
                    writer.write(input + "\n");
                    System.out.println(input+"  added to:  "+bucketFileName);
                }
                catch (Exception e){
                    System.out.println("Error occur3: "+e.getMessage());
                }
            }
            catch (Exception e){
                System.out.println("Error occur2: "+e.getMessage());
            }
        }
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
}