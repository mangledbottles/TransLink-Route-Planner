import java.io.File;
import java.util.Scanner;

public class TernarySearchTree {
    private static File file;
    private static Scanner scanner;
    private int tstCount;

    private int n;              // size
    private Node root;

    private static class Node {
        private char c;                     // character
        private Node left, mid, right;      // left, middle, and right subtries
        private Stop val;                   // value associated with string
    }
    // public static void main(String[] args) {
    //     TernarySearchTree TST = new TernarySearchTree("./inputs/stops.txt");

    //     System.out.println(TST.n);
    //     for(String key : TST.keysWithPrefix("HWY")){
    //         System.out.println(key);
    //     }
    // }

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

                    Stop currentStop = new Stop(stopId, stopName, stopDesc);
                    put(stopName, currentStop);

                    System.out.println(stopId + ", " + stopName + ", " + stopDesc);
                }
            }
            scanner.close();
        } catch(Exception error) {
            System.out.println("Its broken.. : " + error.toString());
        }
    }
    
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }
    
    public Stop get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }
    
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }
     

    public void put(String key, Stop val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key)) n++;
        else if(val == null) n--;       // delete existing key
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Stop val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)               x.left  = put(x.left,  key, val, d);
        else if (c > x.c)               x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1);
        else                            x.val   = val;
        return x;
    }


    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix, queue);
        if (x.val != null) queue.enqueue(prefix.toString() + x.c);
        collect(x.mid,   prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }
}



class Stop {
    public int stopId;
    public String stopName, stopDesc;

    public Stop(int stopId, String stopName, String stopDesc) {
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
        this.stopDesc = stopDesc;

    }
}
