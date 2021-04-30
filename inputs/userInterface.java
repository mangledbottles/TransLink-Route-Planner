import java.util.Scanner;
import java.util.ArrayList;

public class userInterface {
  public static final Scanner input = new Scanner(System.in);
  public static void main(String[] args) {

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

  }


  private static void findArrivalTime(String arrivalTime) {
    SearchArrivalTime arrTime = new SearchArrivalTime();
    arrTime.getlist(arrivalTime);
  }
}
