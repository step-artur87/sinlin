package sinlin.string_facade;

import java.util.Map;

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
        String[] strings = string.split("\\$");
        String buffer = null;
        boolean toFs = false;

        for (int i = 0; i < strings.length; i++) {
            if (buffer != null) {
                strings[i] = buffer.concat(strings[i]);
                buffer = null;
            }

            if (strings[i].endsWith("\\")) {
                buffer = strings[i].substring(0, strings[i].length() - 1);
                strings[i] = null;
            }
        }

        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                if (strings[i].length() > 0) {
                    if (toFs) {
                        nodes.add(StringFacadeBuilder.createVCEF(strings[i]));
                    } else {
                        nodes.add(new FlatString(strings[i]));
                    }
                }
                toFs = !toFs;
            }
        }
    }

    public static boolean test(String string) {
        return string.split("\\$").length > string.split("\\\\\\$").length;
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
