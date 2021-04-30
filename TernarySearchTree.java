import java.io.File;
import java.util.Scanner;

public class TernarySearchTree {
    public static void main(String[] args) {
    }

    TernarySearchTree(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
        } catch(Exception error) {
            System.out.println("Its broken.. : " + error.toString());
        }
    }
}