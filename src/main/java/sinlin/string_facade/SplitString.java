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
 */
public class SplitString extends StringFacadeAbstract
        implements StringFacadeIF {
    private ArrayList<StringFacadeIF> ifs
            = new ArrayList<>();

    public SplitString(String string) {
        name = string;
        string = StringFacadeBuilder.replaceAll(string);
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, StringFacadeIF.DELIM, true);
        String s;
        boolean toFs = false;
        while (stringTokenizer.hasMoreElements()) {
            s = stringTokenizer.nextToken();
            if (!s.equals(StringFacadeIF.DELIM)) {
                if (toFs) {
                    ifs.add(StringFacadeBuilder.createVCEF(s));
                } else {
                    ifs.add(new FlatString(s));
                }
            } else {
                toFs = !toFs;
            }
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
            ifs.forEach((s) -> System.out.println(s + "(" + s.getSize() + ")"));
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        String result = "";
        for (StringFacadeIF anIf : ifs) {
            result = result.concat(
                    anIf.getValue(keyMap, n));
        }
        return result;
    }
}
