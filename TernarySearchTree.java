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

                    Stop currStop = new Stop(stopId, stopName, stopDesc);
                    put(currStop.stopName, currStop);

                    System.out.println(stopId + ", " + stopName + ", " + stopDesc);
                }
            }

        } catch(Exception error) {
            System.out.println("Its broken.. : " + error.toString());
        }
    }
}

class Stop {
    public int stopId;
    public String stopName, stopDesc;

    public Stop(int stopId, String stopName) {
        String stopNameTmp;
        switch(stopName.charAt(0)) {
            case 'W':
                stopNameTmp = stopName.substring(9);
                stopNameTmp += " WB";
                this.stopName = stopNameTmp;
            break;

            case 'E':
                stopNameTmp = stopName.substring(9);
                stopNameTmp += " EB";
                this.stopName = stopNameTmp;
            break;

            case 'N':
                stopNameTmp = stopName.substring(10);
                stopNameTmp += " NB";
                this.stopName = stopNameTmp;
            break;

            case 'S':
                stopNameTmp = stopName.substring(10);
                stopNameTmp += " SB";
                this.stopName = stopNameTmp;
            break;

            default:
                this.stopName = stopName;
            break;
        }

        this.stopId = stopId;

    }
}