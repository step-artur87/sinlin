package sinlin.string_facade;

import sinlin.Util;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/7/15
 * Time: 6:06 PM
 */
public abstract class StringFacadeAbstract implements StringFacadeIF {
    protected String name;
    protected ArrayList<StringFacadeIF> nodes
            = new ArrayList<>();

    @Override
    public int getSize() {
        StringBuffer stringBuffer = new StringBuffer();
        int n;
        if (nodes.isEmpty()) {
            return 1;
        }

        n = Util.mapElementsSizes(nodes.stream()
                .mapToInt(StringFacadeIF::getSize));

        if (n < 0) {
            nodes.forEach((s) -> {
                stringBuffer
                        .append(s.getClass().getSimpleName())
                        .append(" ")
                        .append(s.getName())
                        .append(" (")
                        .append(s.getSize())
                        .append(")\n");
            });
            Util.printErrorAndExit("In " + this.getClass().getSimpleName() + " \""
                    + this.name + "\" attributes have not same sizes:\n" + stringBuffer);
        }

        return n;
    }

    @Override
    public String getName() {
        return name;
    }

    protected void handleException(Exception e) {
        Util.printErrorInPath();
        System.err.println("In " + this.getClass().getSimpleName() + " \"" + this.getName() + "\" thrown exception:");
        System.err.println(e.toString());
        System.err.println("Exit.");
        System.exit(1);
    }

}
