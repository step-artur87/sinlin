package sinlin;

import java.util.ArrayList;
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
        int begin = 0;
        int end = s.length();//todo error when [qwe, qwe]
        if (s.startsWith("[")) {
            begin++;
        }
        if (s.endsWith("]")) {
            end--;
        }
        return s.substring(begin, end);
    }

    public static ArrayList<String> splitSquareBrackets(String s) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        String s1;
        StringBuilder buffer = new StringBuilder();
        int level = 0;
        StringTokenizer stringTokenizer = new StringTokenizer(s, "[]", true);
        while (stringTokenizer.hasMoreElements()) {
            s1 = stringTokenizer.nextToken();
            switch (s1) {
                case "[":
                    level++;
                    buffer.append(s1);
                    break;
                case "]":
                    level--;
                    buffer.append(s1);
                    break;
                default:
                    if (level == 0) {
                        if (buffer.length() > 0) {
                            stringArrayList.add(buffer.toString());
                            buffer = new StringBuilder();
                        }
                        stringArrayList.add(s1);
                    } else if (level > 0) {
                        buffer.append(s1);
                    } else {
                        System.out.println("Bad " + s + ". Exit.");
                        System.exit(0);
                    }
                    break;
            }
        }
        if (buffer.length() > 0) {
            stringArrayList.add(buffer.toString());
            buffer = new StringBuilder();
        }
        if (level != 0) {
            System.out.println("Bad " + s + ". Exit.");
            System.exit(0);
        }
        return stringArrayList;
    }
}
