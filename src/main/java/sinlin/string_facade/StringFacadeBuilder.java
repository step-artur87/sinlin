package sinlin.string_facade;

import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/4/15
 * Time: 7:41 PM
 */
public class StringFacadeBuilder {
    public static StringFacadeIF create(String string) {
        string = replaceAll(string);
        if (SplitString.test(string)) {
            return new FlatString(string);
        } else {
            return SplitString.create(string);
        }
    }

    public static StringFacadeIF createVCEF(String string) {
        String clearedString = clearSquareBrackets(string);

        if (CycleString.test(clearedString)) {
            return new CycleString(string);
        }
        if (Expr.test(clearedString)) {
            return new Expr(string);
        }
//        if (VarString.test(clearedString)) {
//            return new VarString(string);
//        }
        return new Fn(string);
    }

    public static String replaceAll(String string) {
        string = string.replace(", ", ",")
                .replace(" ,", ",")
                .replace("/ ", "/")
                .replace(" /", "/")
                .replace("-> ", "->")
                .replace(" ->", "->");
        return string;
    }

    public static String clearSquareBrackets(String s) {
        int n = 0;
        String result = "";
        String s1;
        StringTokenizer stringTokenizer
                = new StringTokenizer(s, "[]", true);
        while (stringTokenizer.hasMoreElements()) {
            s1 = stringTokenizer.nextToken();
            if (s1.equals("[")) {
                n++;
            } else if (s1.equals("]")) {
                n--;
                if (n < 0) {
                    System.out.println("Error with [] in " + s);
                    System.exit(1);
                }
            } else if (n == 0) {
                result = result.concat(s1);
            }
        }
        if (n > 0) {
            System.out.println("Error with [] in " + s);
            System.exit(1);
        }
        return result;
    }
}
