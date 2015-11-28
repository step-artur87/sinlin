package sinlin.string_facade;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/4/15
 * Time: 7:41 PM
 */
public class StringFacadeBuilder {
    private static final String OPERATORS = ".*[\\+\\-\\*/%^!#§&:~<>|=].*";

    public static StringFacadeIF create(String string) {
        string = replaceAll(string);
        if (!string.contains(StringFacadeIF.DELIM)) {
            return new FlatString(string);
        } else if (string.split("\\$").length != 1) {
            return new SplitedString(string);
        }
        string = string.replace("$", "");
        return createVCEF(string);
    }

    public static StringFacadeIF createVCEF(String string) {
        assert !string.contains(StringFacadeIF.DELIM);

        if (string.matches(OPERATORS)) {
            return new Expr(string);
        }
        if (string.contains(StringFacadeIF.DELIM_VAR)) {
            return new VarString(string);
        }
        if (string.contains(StringFacadeIF.DELIM_CYCLE)) {
            return new CycleString(string);
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
}
