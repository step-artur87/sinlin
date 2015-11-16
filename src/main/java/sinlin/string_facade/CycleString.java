package sinlin.string_facade;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/10/15
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class CycleString extends StringFacadeAbstract
        implements StringFacadeIF {
    Double begin = 0.;
    Double step = 1.;
    Double end = null;

    public CycleString(String string) {
        name = string;
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, DELIM_CYCLE);
        try {
            if (stringTokenizer.countTokens() == 3) {
                begin = Double.parseDouble(stringTokenizer.nextToken());
                step = Double.parseDouble(stringTokenizer.nextToken());
                end = Double.parseDouble(stringTokenizer.nextToken());
            } else if (stringTokenizer.countTokens() == 2) {
                step = Double.parseDouble(stringTokenizer.nextToken());
                end = Double.parseDouble(stringTokenizer.nextToken());
            } else if (stringTokenizer.countTokens() == 2) {
                end = Double.parseDouble(stringTokenizer.nextToken());
            } else {
                System.out.println("Cycle "
                        + name
                        + " was not parsed. Exit"
                );
                System.exit(1);
            }
            //todo as for(;;)
        } catch (NumberFormatException e) {
            System.out.println("In cycle "
                    + name
                    + " some double was not parsed. Exit"
            );
            System.exit(1);
        }
    }

    @Override
    public int getSize() {
        return (int) ((end - begin) / step);
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        double result = begin + n * step;
        if (result > end) {
            System.out.println("Cycle "
                    + this.name
                    + " have not value with number "
                    + n
                    + ".Exit");
            System.exit(1);
        }

        return Double.toString(result);
    }
}
