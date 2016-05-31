import java.util.*;
import java.lang.*;
import java.io.*;

public class Knapsack {

  //brute force approach to knapsack problem
  public static void bruteForce(int capacity, int n, int [] values,
                                int [] weights) {
    int max = 0;
    int weight = 0;
    int curValue = 0;
    int curWeight = 0;
    String maxItems = "";
    //generates all binary strings of size n
    //this denotes which items we take and the ones we do not
    ArrayList<String> strings = binaryString(n, "", new ArrayList<String>());
    //for each binary string
    for (String s : strings) {
      //keep track of the value and weight
      curValue = 0;
      curWeight = 0;
      //go through each character in the binary string
      for (int i = 0; i < n; i++) {
        if (s.charAt(i) == '1') {
          curValue += values[i];
          curWeight += weights[i];
        }
      }
      //if it fits in the backpack and we found a more valuable configuration
      if (curWeight <= capacity && curValue > max) {
        max = curValue;
        weight = curWeight;
        maxItems = s;
      }
    }
    System.out.println("Using Brute force the best feasible solution found: " +
                        "Value " + max + ", Weight " + weight);
    for (int i = 0; i < n; i++) {
      if (maxItems.charAt(i) == '1') {
        System.out.print((i + 1) + " ");
      }
    }
    System.out.println();
  }

  //greedy method that does not return an optimal solution to find the most
  //valuable configuration of goods for the knapsack
  //the ranking is determined by value / weight, as it works for a not 0/1
  //knapsack problem. Also it gives a pretty good estimate for what the actual
  //answer is. Further, it is used in determining the upper bounds for the
  //branch and bound solution
  public static void greedy(int capacity, int n, int [] values,
                            int [] weights) {
    //create an array of items to make representing the goods easier
    Item [] items = new Item[n];
    for (int i = 0; i < n; i++) {
      items[i] = new Item(values[i], weights[i], i + 1);
    }
    //sorts them
    Arrays.sort(items, new ItemComparator());
    //keeps track of how much space we have left
    int curCapacity = capacity;
    //keeps track of the weight and value
    int weight = 0;
    int max = 0;
    //string to
    ArrayList<Integer> itemNumbers = new ArrayList<Integer>();
    //goes through each item
    for (Item item : items) {
      //if we can fit it in
      if (curCapacity > item.weight) {
        //put it in the knapsack
        curCapacity -= item.weight;
        max += item.value;
        weight += item.weight;
        itemNumbers.add(item.number);
      }
      //if we have no more space, don't look anymore
      if (curCapacity == 0) {
        break;
      }
    }
    //sort the arraylist
    Collections.sort(itemNumbers);
    //outputs what we found
    System.out.println("Greedy solution (not necessarily optimal): " +
                        "Value " + max + ", Weight " + weight);
    for (Integer item : itemNumbers) {
      System.out.print(item + " ");
    }
    System.out.println();
  }

  //dynamic programming solution, using a bottom up approach
  public static void dynamic(int capacity, int n, int [] values,
                            int [] weights) {
    //creates a table to keep track of values
    int [][] table = new int [n + 1][capacity + 1];
    //starting at item 1, and going through each of the weights
    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= capacity; j++) {
        //if we can fit an item in
        if (weights[i - 1] <= j) {
          //check to see if we should put it in the bag
          //puts in the bag if the configuration of that weight is better
          //than not putting it in
          table[i][j] = Math.max(table[i - 1][j],
                        table[i - 1][j - weights[i - 1]] + values[i - 1]);
        } else {
          //otherwise keep that value and go to the next weight
          table[i][j] = table[i - 1][j];
        }
      }
    }
    //find which items we stuffed in the knapsack
    traceback(capacity, n, values, weights, table);
  }

  //traceback method for the dynamic programming solution
  public static void traceback(int capacity, int n, int [] values,
                              int [] weights, int [] [] table) {
    //start at last item with a full bag
    int i = n;
    int j = capacity;
    //keep track of which items we add, as well as the values and weights
    int value = 0;
    int weight = 0;
    ArrayList<Integer> itemsTaken = new ArrayList<Integer>();
    //while there are still items to take and we aren't empty
    while (i > 0 && j >= 0) {
      //if we did take an item
      if (table[i][j] != table[i-1][j]) {
        //keep track of that item
        itemsTaken.add(i--);
        //add the value and weight of that
        value += values[i];
        weight += weights[i];
        //take the item out of the bag
        j -= weights[i];
      } else {
        //otherwise, go back an item
        i--;
      }
    }
    //prints out the results
    System.out.println("Dynamic Programming solution: Value "
                      + value + ", Weight " + weight);
    Collections.sort(itemsTaken);
    for (int item : itemsTaken) {
      System.out.print((item) + " ");
    }
    System.out.println();
  }

  //method to generate binary strings of length n
  //uses a list as we can pass it through each recursive call
  private static ArrayList<String> binaryString(int n, String s,
                                                ArrayList<String> list) {
    //when we reach the desired length, add it in to the list
    if (n == 0) {
      list.add(s);
    } else {
      //utilizes a tree-like structure found in branch and bound solutions
      binaryString(n - 1, s + "0", list);
      binaryString(n - 1, s + "1", list);
    }
    return list;
  }

  //private class representing an item to be put in the knapsack
  private static class Item {
    int weight;
    int value;
    int number;
    //constructor for the item
    Item(int value, int weight, int number) {
      this.value = value;
      this.weight = weight;
      this.number = number;
    }
  }

  //comparator for the item class to be used with the Arrays sort method
  public static class ItemComparator implements Comparator<Object> {
    //compares two items
    public int compare(Object o1, Object o2) {
      //casts the objects into items
      Item obj1 = (Item) o1;
      Item obj2 = (Item) o2;
      //orders the items by their value/weight ratio
      double ratio1 = (obj1.value * 1.0) / obj1.weight;
      double ratio2 = (obj2.value * 1.0) / obj2.weight;
      if (ratio1 < ratio2) {
        return 1;
      } else if (ratio1 > ratio2) {
        return -1;
      } else {
        return 0;
      }
    }
    //method is needed to fulfill the comparator interface
    public boolean compare(Object obj)
    {
 	    if (obj == null)
 	      return false;
 	    if (!obj.getClass().equals(this.getClass()))
 	      return false;
 	    return true;
    }
  }

  //class for the node in branch and bound method
  public static class Node implements Comparable<Node> {
    int level;
    int weight;
    int value;
    double upperbound;
    ArrayList<Integer> items;

    //default constructor
    Node() {
      level = 0;
      weight = 0;
      value = 0;
      upperbound = 0;
      items = new ArrayList<Integer>();
    }

    //builds a node using another node
    //also adds all the items from the other node into this node
    Node(Node other) {
      this.level = other.level;
      this.weight = other.weight;
      this.value = other.value;
      this.upperbound = 0;
      items = new ArrayList<Integer>();
      items.addAll(other.items);
    }

    void findUpperBound(int capacity, int n, Item [] items, int [] values, int [] weights) {
      //get the item we are on
      int item = level;
      //keep track of current weight
      int tempWeight = weight;
      //give the node a starting upper bound
      upperbound = value;
      //while there are still items to add and if there is space
      //this gives a much more realistic, efficient, and tighter upper bound
      //than compared to the textbook
      while (item < n && tempWeight + items[item].weight <= capacity) {
        //add the item
        tempWeight += items[item].weight;
        //increase the upperbound
        upperbound += items[item].value;
        //move onto the next item
        item++;
      }
      //add the partial item to the upper bound
      upperbound += (capacity - tempWeight) * (1.0 * items[item].value )
                    / items[item].weight;
    }

    //method to compare two nodes; only included to fulfill the Comparable
    //interface
    public int compareTo(Node other) {
      return (int) (other.upperbound - upperbound);
    }
  }

  public static void branchAndBound(int capacity, int n, int [] values,
                                    int [] weights, int timelimit) {
    //start the timer
    long start = System.currentTimeMillis();
    //keeps track of whether or not the timer went off
    boolean finished = false;
    //creates an array of items to make orgnizing the data easier
    Item [] items = new Item [n];
    for (int i = 0; i < n; i++) {
      items[i] = new Item(values[i], weights[i], i + 1);
    }
    //sorts the items for the branch and bound solution
    Arrays.sort(items, new ItemComparator());
    //keeps track of the best solution so far
    Node best = new Node();
    //create the starting point
    Node root = new Node();
    root.findUpperBound(capacity, n, items, values, weights);
    //creates the priority queue
    PriorityQueue<Node> queue = new PriorityQueue<Node>();
    queue.offer(root);
    //while the queue has items in it, and theres still time
    while (!queue.isEmpty() && !finished) {
      //get the latest item
      Node node = queue.poll();
      //check to see if there is time left
      if (System.currentTimeMillis() - start > timelimit * 60 * 1000) {
        //if there isn't, we'll stop the loop and output that time is up
        finished = true;
        System.out.println("Reached time limit of " + timelimit + " minutes.");
      }
      //if the node potentially has a better value
      else if (node.upperbound > best.value) {
        //take the item
        Node left = new Node(node);
        //keep track that we took the item
        int itemNumber = node.level;
        //make the node go down one level
        left.level++;
        //add the item's weight to it
        left.weight += items[itemNumber].weight;
        //if we didn't go over capacity
        if (left.weight <= capacity) {
          //add that item to its list
          left.items.add(items[itemNumber].number);
          //add the value
          left.value += items[itemNumber].value;
          //if we have more items left
          if (left.level < n) {
            //calculate the upper bound
            left.findUpperBound(capacity, n, items, values, weights);
          }
          //if its the best we have so far, then update it
          if (left.value > best.value) {
            best = left;
          }
          //if it has potential, let's explore more
          if (left.upperbound > best.value && left.level < n) {
            queue.offer(left);
          }
        }
        //but if we didn't take the item
        Node right = new Node(node);//System.out.println(filescanner.nextLine().split(" ")[0]);
      //int itemCount = Integer.parseInt(filescanner.nextLine().split(" ")[0]);
        //go down a level as well
        right.level++;
        //find the upper bound, and go down the tree
        if (right.level < n) {
          right.findUpperBound(capacity, n, items, values, weights);
          if (right.upperbound > best.value) {
            queue.offer(right);
          }
        }
      }
    }

    System.out.println("Using Branch and Bound "
                        + "the best feasible solution found: Value "
                        + best.value + " Weight " + best.weight);
    Collections.sort(best.items);
    for (int item : best.items) {
      System.out.print(item + " ");
    }
    System.out.println();
  }

  public static void main(String [] argv) throws FileNotFoundException {
    //checks to see if the user gave us a file
    if (argv.length == 0) {
      System.out.println("Missing input file...");
    } else {
      //if they did, start the process
      Scanner filescanner = new Scanner(new File(argv[0]));
      //get the item count
      int itemCount = Integer.parseInt(filescanner.next());
      //clear the scanner's buffer
      filescanner.nextLine();
      //initialize arrays to hold the values and weights of the file
      int [] values = new int [itemCount];
      int [] weights = new int [itemCount];
      //for each item
      for (int i = 1; i <= itemCount; i++) {
        //replace tabs with spaces, and split by spaces
        String [] line = filescanner.nextLine().replace("\t", " ").split(" ");
        //counter to keep track of what value we are given (ie. a value)
        int counter = 0;
        for (String s : line) {
          //since we may get spaces when we split, we'll act on when we get a
          //number
          if (!s.equals("")) {
            //if the counter is 0, we are given an item with that number
            if (counter == 0) {
              //but we don't need the number, so increase the counter
              counter++;
              //if the counter is 1, then we are given a value
            } else if (counter == 1) {
              //so get that value, and increment the counter
              values[i - 1] = Integer.parseInt(s);
              counter++;
              //otherwise, we are given a weight
            } else {
              weights[i - 1] = Integer.parseInt(s);
            }
          }
        }
      }
      //get the capacity manually as it is the last part of the file
      int capacity = Integer.parseInt(filescanner.next());
      //give a default time limit of 5 minutes
      int timelimit = 5;
      //if there is a time limit given to us, use it
      if (argv.length > 1) {
        timelimit = Integer.parseInt(argv[1]);
      }
      //use bruteforce if we do not have too many items
      if (itemCount <= 25) {
        bruteForce(capacity, itemCount, values, weights);
      }
      //call the methods
      greedy(capacity, itemCount, values, weights);
      dynamic(capacity, itemCount, values, weights);
      branchAndBound(capacity, itemCount, values, weights, timelimit);
    }
  }
}
