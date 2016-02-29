package sinlin;

import java.util.OptionalInt;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/21/16
 * Time: 3:20 PM
 */
public class Util {
    public static int mapElementsSizes(IntStream intStream) {
        OptionalInt n = intStream.reduce((a, b) ->
                (a == -1) ? -1 : (b == 1) ? a : (a == b) ? a : (a == 1) ? b : -1
                //          reduce() return -1
                //                          no changes of a
                //                                         no changes of a
                //                                                        change to b
                //                                                             change to -1
        );
        return n.isPresent() ? n.getAsInt() : 1;
    }

    public static int oneOrEqual(int a, int b) {
        if (a < 0 || b < 0) {
            return -1;
        }

        if (a == b) {
            return a;
        } else {
            if (a != 1 && b != 1) {
                return -1;
            } else {
                return a > b ? a : b;
            }
        }
    }

    public static String deleteForbiddenSymbols(String string) {
        if (string != null) {
            return string.replaceAll("[\\-\\[\\]\\{\\}\\?\\'\\\"]", "");
        }
        return null;
    }

    public static void printErrorInPath() {
        System.out.print("Error in path: ");
        Exporter.getPath().forEach((s) -> {
            System.out.print("<" + s.getNameWithAttr() + ">");
        });
        System.out.println();
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
