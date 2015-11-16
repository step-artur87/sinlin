package sinlin.string_facade;

import java.util.ArrayList;
import java.util.Map;
import java.util.OptionalInt;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/4/15
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class SplitedString extends StringFacadeAbstract
        implements StringFacadeIF {
    private ArrayList<StringFacadeIF> ifs
            = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();

    public SplitedString(String string) {
        name = string;
        string = StringFacadeBuilder.replaceAll(string);
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, StringFacadeIF.DELIM);
        String s;
        boolean toFs = string.startsWith(StringFacadeIF.DELIM);
        if (toFs) {
            strings.add("");
        }
        while (stringTokenizer.hasMoreElements()) {
            s = stringTokenizer.nextToken();
            if (toFs) {
                if (s.contains(",")) {
                    ifs.add(new VarString(s));
                } else {
                    ifs.add(StringFacadeBuilder.createVCEF(s));
                }
            } else {
                strings.add(s);
            }
            toFs = !toFs;
        }
    }

    @Override
    public int getSize() {
        int m = -1;
        if (ifs.isEmpty()) {
            return 1;
        }
        OptionalInt min = ifs.stream()
                .mapToInt(StringFacadeIF::getSize)
                .filter((i) -> i > 1)
                .min();
        OptionalInt max = ifs.stream()
                .mapToInt(StringFacadeIF::getSize)
                .max();

        if (min.equals(max)) {
            m = min.getAsInt();
        }

        if (m < 0) {
            System.out.println("Attr have not same sizes:");
            ifs.forEach((s) -> {
                System.out.println(s + "(" + s.getSize() + ")");
            });
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        String result = new String();
        for (int i = 0; i < ifs.size(); i++) {
            result = result.concat(strings.get(i));
            result = result.concat(
                    ifs.get(i).getValue(keyMap, n));
        }
        if (strings.size() > ifs.size()) {
            result = result.concat(strings.get(strings.size() - 1));
        }
        return result;
    }
}
