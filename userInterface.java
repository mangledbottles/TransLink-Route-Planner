import java.util.Scanner;
import java.util.ArrayList;

public class userInterface {

  public static final Scanner input = new Scanner(System.in);

  public static void main(String[] args) {

    boolean isRunning = true;
    while(isRunning) {
      isRunning = menu();
    }
    System.out.println("Program quit");
  }

  public static boolean menu() {
    System.out.println("Select an option below, or enter '0' to quit the program:\n"
    + "[1] find the shortest path between two bus stops \n"
    + "[2] find information regarding a specific stop \n"
    + "[3] find all trips with a desired arrival time \n"
    + "[0] exit program \n"
    );

    String stringIn = input.next();
    int choice = 0;

    try {
      choice = Integer.parseInt(stringIn);
    }
    catch (NumberFormatException e) {
      System.out.println("Invalid input provided.");
    }

    if(choice == 0) {
      return false;
    }

    else {
      if(choice >= 1 && choice <= 3) {

        // Option 1: Finding the shortest path between two bus stops //
        if (choice == 1) {
          int stopA = 0;
          int stopB = 0;
          System.out.println("Enter the first stop number: ");
          if(input.hasNextInt()) {
            stopA = input.nextInt();
          }
          else {
            System.out.println("Invalid stop number.");
          }
          System.out.println("Enter the second stop number: ");
          if(input.hasNextInt()) {
            stopB = input.nextInt();
          }
          else {
            System.out.println("Invalid stop number.");
          }
          findShortestPath(stopA, stopB);
        }

        // Option 2: Finding information regarding a specific bus stop //
        else if (choice == 2) {
          System.out.println("Enter the stop name: ");
          String stopIn = input.next().toUpperCase();
          findStopInformation(stopIn);
        }

        // Option 3: Finding all trips with a desired arrival time //
        else if (choice == 3) {
          System.out.println("Enter your arrival time [hh:mm:ss]: ");
          String timeIn = input.nextLine();
          findArrivalTime(timeIn);
          // TODO: error check time format
        }
        else {
          System.out.println("Invalid input provided.");
        }
      }
    }
    return true;
  }

  // Implementation of findShortestPath class
  private static void findShortestPath(int stopA, int stopB) {
    BusShortestPath shortest = new BusShortestPath();
    shortest.findShortDistance(stopA, stopB);
  }

  // Implementation of findStopInformation class
  private static void findStopInformation(String stopName) {
    TernarySearchTree TST = new TernarySearchTree("./inputs/stops.txt");
    Iterable<String> validStops = TST.keysWithPrefix(stopName);

    if(validStops != null) {
        for(String key : validStops){
            System.out.println("" + TST.get(key).printStopSingleLine());
        }
    } else {
        System.out.println("No matching stops were found");
    }
  }


  // Implementation of findArrivalTime class
  private static void findArrivalTime(String arrivalTime) {
    SearchArrivalTime arrTime = new SearchArrivalTime();
    arrTime.getlist(arrivalTime);
  }
}
