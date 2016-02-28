package sinlin.string_facade;

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
        String[] strings = string.split(",");
        Collections.addAll(varString, strings);
    }

    //can return 0
    @Override
    public int getSize() {
        return varString.size();
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        return varString.get(n);
    }

    public static boolean test(String string) {
        String s = string.replaceAll("\\[.*\\]", "");
        return s.split("\\,").length > s.split("\\\\\\,").length;

    }
}
