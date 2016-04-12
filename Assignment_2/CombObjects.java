import java.util.*;
public class CombObjects {
    public static ArrayList<String> getGrayCode(int n) {
        ArrayList<String> codes = new ArrayList<String>();
        //base case representing subset of a set with a single item
        if (n == 1) {
            //adds the two strings, 0 and 1
            codes.add("0");
            codes.add("1");
        //but otherwise
        } else {
            //take all subsets of the n - 1 items
            ArrayList<String> sets = getGrayCode(n - 1);
            //prepend a 0 to create a subset of n items without n-th item
            for (int i = 0; i < sets.size(); i++) {
                codes.add("0" + sets.get(i));
            }
            //reverses the list of subsets of n - 1 items
            for (int i = sets.size() - 1; i >= 0; i--) {
                //prepends a 1 to them to create subsets of items that have 
                //their n-th item
                codes.add("1" + sets.get(i));
            }
        }
        //return all the gray codes
        return codes;    
    }
    public static ArrayList<String> getLexPerm(String str) {
        //creates arraylist of strings that we want to add to and later return
        ArrayList<String> strings = new ArrayList<String>();
        //if the string is empty, return it
        if (str.length() == 0) {
            //add the empty string
            strings.add(str);
            //and return the arraylist containing it
            return strings;
        }
        //but if its not empty, look through all character positions
        //of the string containing the characters to be permuted
        for (int i = 0; i < str.length(); i++) {
            //form a simpler word by removing the character
            String newStr = str.substring(0, i) + str.substring(i + 1);
            //recursively generates all permutations of the simpler word
            ArrayList<String> perms = getLexPerm(newStr);
            //add the removed character to the front of each permutation
            for (String s: perms) {
                //and add it to the list to be returned
                strings.add(str.charAt(i) + s);
            }
        }
        //return the list of permutations
        return strings;
    }
    public static ArrayList<String> getMinChgPerm(String str) {
        //declares arraylist of strings we will add to and later return
        ArrayList<String> strings = new ArrayList<String>();
        //if the string is empty, then return it
        if (str.length() == 0) {
            strings.add(str);
            return strings;
        }
        //remove the last character of the string, and call it x
        String x = "" + str.charAt(str.length() - 1);
        //get the resulting string
        String newStr = str.substring(0, str.length() - 1);
        //generate all permutations of the simpler word
        ArrayList<String> perms = getMinChgPerm(newStr);
        //lets us know when to start from the right or left
        boolean startFromRight = true;
        //go through the returned permutations
        for (String s: perms) {
            //insert the removed character into all positions moving
            //right to left
            if (startFromRight){
                //goes through each character position
                for (int i = s.length(); i >= 0; i--) {
                    //gets the string left of the insertion spot
                    String leftOfBreak = s.substring(0, i);
                    //gets the string right of the insertion spot
                    String rightOfBreak = s.substring(i, s.length());
                    //concatenate them all together and add result
                    strings.add(leftOfBreak + x + rightOfBreak);
                }
                //start from the left next time
                startFromRight = false;
            //otherwise, starting from the left
            } else {
                //go through each character from left to right
                for (int i = 0; i <= s.length(); i++) {
                    //gets the string left of the insertion spot
                    String leftOfBreak = s.substring(0, i);
                    //gets the string right of the insertion spot
                    String rightOfBreak = s.substring(i, s.length());
                    //concatenates them all together and adds result
                    strings.add(leftOfBreak + x + rightOfBreak);
                }
                //start from the right next time
                startFromRight = true;
            }
        }
        //return all the permutations
        return strings;
    }
}
