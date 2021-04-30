import java.io.File;
import java.util.Scanner;

public class TernarySearchTree {
    private static File file;
    private static Scanner scanner;
    private int tstCount;

    public static void main(String[] args) {
        new TernarySearchTree("./inputs/stops.txt");
    }


    TernarySearchTree(String fileName) {
        int stopId;
        String stopName, stopDesc;

        try {
            file = new File(fileName);
            scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            tstCount++;
            while(scanner.hasNext()) {
                if(scanner.hasNextInt()) {
                    tstCount++;

                    stopId = Integer.parseInt(scanner.next());
                    scanner.next();
                    stopName = scanner.next();
                    stopDesc = scanner.next();
                    scanner.nextLine();

                    System.out.println(stopId + ", " + stopName + ", " + stopDesc);
                }
            }

        } catch(Exception error) {
            System.out.println("Its broken.. : " + error.toString());
        }
    }
}