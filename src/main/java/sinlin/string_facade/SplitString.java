package sinlin.string_facade;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/4/15
 * Time: 11:40 AM
 */
public class SplitString extends StringFacadeAbstract
        implements StringFacadeIF {
    public SplitString(String string) {
        name = string;
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, StringFacadeIF.DELIM, true);
        String s;
        boolean toFs = false;
        while (stringTokenizer.hasMoreElements()) {
            s = stringTokenizer.nextToken();
            if (!s.equals(StringFacadeIF.DELIM)) {
                if (toFs) {
                    nodes.add(StringFacadeBuilder.createVCEF(s));
                } else {
                    nodes.add(new FlatString(s));
                }
            } else {
                toFs = !toFs;
            }
        }
    }

    public static StringFacadeIF create(String string) {
        SplitString splitString = new SplitString(string);
        if (splitString.nodes.size() == 1) {
            return splitString.nodes.get(0);
        }
        return splitString;
    }

    public static boolean test(String string) {
        return string.contains(StringFacadeIF.DELIM);
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        String result = "";
        for (StringFacadeIF anIf : nodes) {
            result = result.concat(
                    anIf.getValue(keyMap, n));
        }
        return result;
    }
}
