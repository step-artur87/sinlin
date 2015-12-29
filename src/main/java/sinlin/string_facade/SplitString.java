package sinlin.string_facade;

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
    public int getSize() {
        int m = -1;
        if (nodes.isEmpty()) {
            return 1;
        }
        OptionalInt min = nodes.stream()
                .mapToInt(StringFacadeIF::getSize)
                .filter((i) -> i > 1)
                .min();
        OptionalInt max = nodes.stream()
                .mapToInt(StringFacadeIF::getSize)
                .filter((i) -> i > 1)
                .max();

        if (!min.isPresent() || !max.isPresent()) {
            m = 1;
        } else if (min.equals(max)) {
            m = min.getAsInt();
        }

        if (m < 0) {
            System.out.println("In " + this.getClass().getSimpleName() + " \""
                    + this.name + "\" attributes have not same sizes:");
            nodes.forEach((s) -> System.out.println(s.getClass().getSimpleName() + " " + s.getName() + " (" + s.getSize() + ")"));
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
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
