import java.util.*;
import java.lang.*;

public class Powerset {
    public static ArrayList<String> subsetGen(String s) {
        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> temp;
        if (s.length() > 0) {
            temp = subsetGen(s.substring(0,s.length() - 1));
            for (int i = 0; i < temp.size(); i++) {
                a.add(temp.get(i));
                a.add(temp.get(i) + " " + s.substring(s.length()-1));
            }
            return a;
        }
        else {
            a.add("");
            return a;
        }
    }
    public static void main(String [] argv) {
        ArrayList<String> a = subsetGen("abc");
        for (String s : a) {
            System.out.println(s);
        }
    }
}
