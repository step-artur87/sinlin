package sinlin.string_facade;

import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/4/15
 * Time: 7:41 PM
 */
public class StringFacadeBuilder {
    private static final String OPERATORS
            = ".*[\\+\\-\\*/%^!#§&:~<>|=].*";//expression detection
    private static final String FUNC
            = ".*(abs\\(" +
            "|acos\\(" +
            "|asin\\(" +
            "|atan\\(" +
            "|cbrt\\(" +
            "|ceil\\(" +
            "|cos\\(" +
            "|cosh\\(" +
            "|exp\\(" +
            "|floor\\(" +
            "|log\\(" +
            "|log10\\(" +
            "|log2\\(" +
            "|sin\\(" +
            "|sinh\\(" +
            "|sqrt\\(" +
            "|tan\\(" +
            "|tanh).*";

    public static StringFacadeIF create(String string) {
        string = replaceAll(string);
        if (!string.contains(StringFacadeIF.DELIM)) {
            return new FlatString(string);
        } else if (string.split("\\$").length != 1) {
            return new SplitString(string);
        }
        string = string.replace("$", "");
        return createVCEF(string);
    }

    public static StringFacadeIF createVCEF(String string) {
        assert !string.contains(StringFacadeIF.DELIM);
        String clearedString = clearSquareBrackets(string);

        if (clearedString.contains(StringFacadeIF.DELIM_CYCLE)) {
            return new CycleString(string);
        }
        if (clearedString.matches(OPERATORS) || clearedString.matches(FUNC)) {
            return new Expr(string);
        }
        if (clearedString.contains(StringFacadeIF.DELIM_VAR)) {
            return new VarString(string);
        }
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
                if (n < 0) {//todo sout
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
