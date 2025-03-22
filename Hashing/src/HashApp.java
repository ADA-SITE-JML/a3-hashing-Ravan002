import java.util.Scanner;

public class HashApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: hash_app <n> <s>");
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
            System.out.println("Error: "+e.getMessage());
        }


        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("Please enter the string: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue; // Skip empty input
            }
            long hash = Math.abs(input.hashCode());
            int bucketIndex = (int) (hash % n); // Bucket index (1 to n)
            bucketIndex= bucketIndex==0 ? n : bucketIndex;
            String bucketFileName = bucketIndex + ".txt";
            System.out.println(hash+" bucket:"+bucketIndex+"  filename: "+bucketFileName);
        }
    }
}