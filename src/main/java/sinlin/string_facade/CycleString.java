package sinlin.string_facade;

import sinlin.Util;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/10/15
 * Time: 9:51 AM
 */
public class CycleString extends StringFacadeAbstract
        implements StringFacadeIF {
    private Double a = null;//begin
    private Double b = null;//step
    private Double c = null;//end
    private Double d = null;//count

    public CycleString(String string) {
        name = string;

        String[] strings
                = Util.replaceAll(string).split(";");
        try {
            if (strings.length == 3 || strings.length == 4) {
                a = (strings[0].length() > 0)
                        ? Double.parseDouble(strings[0])
                        : null;
                b = (strings[1].length() > 0)
                        ? Double.parseDouble(strings[1]) : null;
                c = (strings[2].length() > 0)
                        ? Double.parseDouble(strings[2])
                        : null;
                if (strings.length == 4) {
                    d = (strings[3].length() > 0)
                            ? Double.parseDouble(strings[3])
                            : null;
                }
            } else {
                Util.printErrorAndExit("Cycle " + name
                        + " was not parsed. Exit");
            }
            //todo as for(;;)
        } catch (NumberFormatException e) {
            handleException(e);
        }

        if (c == null && d == null) {
            Util.printErrorAndExit("Cycle " + name
                    + " has nor end element nor element count");
        }

        if (d != null && d == 0) {
            Util.printErrorAndExit("Cycle " + name
                    + " has zero count. Exit");
        }

        if (a == null) {
            if (b != null && c != null && d != null) {
                a = c - b * d;
            } else {
                a = 0.;
            }
        }

        if (b == null) {
            if (c != null && d != null) {
                b = (c - a) / d;
            } else if (d != null) {
                b = 1.;

            } else {
                b = Math.signum(c - a);
            }
        }

        if (b == 0 && d == null) {
            Util.printErrorAndExit("Cycle " + name
                    + " has zero step and has no count. Exit");
        }

        if (d == null) {
            d = Math.ceil((c - a) / b);
        } else if (c != null) {
            d = Math.min(d, Math.ceil((c - a) / b));
        }

        if (d.isInfinite() || d.isNaN() || (d <= 0)) {
            Util.printErrorAndExit("Cycle " + name
                    + " count = " + d);
        }
    }

    @Override
    public int getSize() {
        return d.intValue();
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        String value;
        if (n > d || n < 0) {
            System.out.println("Cycle "
                    + this.name
                    + " have not value with number "
                    + n
                    + ".Exit");
            System.exit(1);
        }

        value = Double.toString(a + b * n);
        if (value.endsWith(".0")) {
            value = value.replace(".0", "");
        }
        return value;
    }

    public static boolean test(String string) {
        /*
        For possible future releases, where can be cycle "[];[];[];[]"
        String s = string.replaceAll("\\[.*\\]", "");
        return s.split("\\;").length > s.split("\\\\\\;").length;
        */
        string = string.replaceAll("\\[.*\\]", "");//fixme it must be when [1;1;10]*3, but not 1;[1];10

        if (string.contains(StringFacadeIF.DELIM_CYCLE)) {
            if (string.contains("[") || string.contains("]")) {
                Util.printErrorAndExit("Can not create cycle from \""
                        + string
                        + "\",\n" +
                        "because cycles not support inner preprocessed strings ([]). Exit.");
            }

            return true;
        }
        return false;
    }
}
