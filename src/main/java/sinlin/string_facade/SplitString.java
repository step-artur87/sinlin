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
