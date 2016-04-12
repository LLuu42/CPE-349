import java.util.*;
public class CombDriver {
    public static void main(String [] argv) {
        ArrayList<String> perm = CombObjects.getLexPerm("abcd");
        ArrayList<String> minChgPerm = CombObjects.getMinChgPerm("abcd");
        ArrayList<String> grayCodes = CombObjects.getGrayCode(2);
        ArrayList<String> grayCodes2 = CombObjects.getGrayCode(3);
        System.out.println("Lex Perms");
        for (String s : perm) {
            System.out.println(s);
        }
        System.out.println("\nMinimum Change Perms");
        for (String s : minChgPerm) {
            System.out.println(s);
        }
        System.out.println("\nGray codes for n = 2");
        for (String s : grayCodes) {
            System.out.println(s);
        }
        System.out.println("\nGray codes for n = 3");
        for (String s: grayCodes2) {
            System.out.println(s);
        }
    }
}
