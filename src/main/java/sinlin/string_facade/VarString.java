package sinlin.string_facade;

import sinlin.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/26/15
 * Time: 12:36 AM
 */
public class VarString extends StringFacadeAbstract
        implements StringFacadeIF {
    private ArrayList<String> varString = new ArrayList<>();

    public VarString(String string) {
        name = string;
        String[] strings = Util.replaceAll(string).split(",");
        Collections.addAll(varString, strings);
    }

    @Override
    public int getSize() {
        return varString.size();
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        return varString.get(n);
    }

    public static boolean test(String string) {
/*
        For possible future releases, where can be cycle "[];[];[];[]"
        String s = string.replaceAll("\\[.*\\]", "");
        return s.split("\\,").length > s.split("\\\\\\,").length;
*/
        string = string.replaceAll("\\[.*\\]", "");//fixme it must be when [1, 1]*3, but not 1, [1]

        if (string.contains(StringFacadeIF.DELIM_VAR)) {
            if (string.contains("[") || string.contains("]")) {
                printErrorAndExit("Can not create list from \""
                        + string
                        + "\",\n" +
                        "because lists not support inner preprocessed strings ([]). Exit.");
            }

            return true;
        }
        return false;
    }

}
