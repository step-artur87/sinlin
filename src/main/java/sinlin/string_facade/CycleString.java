package sinlin.string_facade;

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
    private Double d = null;//count, todo m.b. integer

    public CycleString(String string) {
        name = string;
        String[] strings = string
                .replace(" ", "")
                .split(";");
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
                System.out.println("Cycle "
                        + name
                        + " was not parsed. Exit"
                );
                System.exit(1);
            }
            //todo as for(;;)
        } catch (NumberFormatException e) {
            System.out.println("In cycle \""
                    + name
                    + "\":"
            );
            System.out.println(e.toString());
            System.out.println("Exit");
            System.exit(1);
        }

        if (c == null && d == null) {
            System.out.println("Cycle "
                    + name
                    + " has nor end element nor element count. Exit"
            );
            System.exit(1);
        }

        if (d != null && d == 0) {
            System.out.println("Cycle "
                    + name
                    + " has zero count. Exit"
            );
            System.exit(1);
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
            System.out.println("Cycle "
                    + name
                    + " has zero step and has no count. Exit"
            );
            System.exit(1);
        }

        if (d == null) {
            d = Math.ceil((c - a) / b);
        } else if (c != null) {
            d = Math.min(d, Math.ceil((c - a) / b));
        }

        if (d.isInfinite() || d.isNaN() || (d <= 0)) {
            System.out.println("Cycle "
                    + name
                    + " count = " +
                    d +
                    ". Exit"
            );
            System.exit(1);
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
}
