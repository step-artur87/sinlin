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
        int n;
        if (nodes.isEmpty()) {
            return 1;
        }

        n = Util.mapElementsSizes(nodes.stream()
                .mapToInt(StringFacadeIF::getSize));

        if (n < 0) {
            System.out.println("In " + this.getClass().getSimpleName() + " \""
                    + this.name + "\" attributes have not same sizes:");
            nodes.forEach((s) -> System.out.println(s.getClass().getSimpleName() + " " + s.getName() + " (" + s.getSize() + ")"));
            System.out.println("Exit.");
            System.exit(1);
        }

        return n;
    }

    @Override
    public String getName() {
        return name;
    }

    protected void handleException(Exception e) {
        Util.printErrorInPath();
        System.out.println("In " + this.getClass().getSimpleName() + " \"" + this.getName() + "\" thrown exception:");
        System.out.println(e.toString());
        System.out.println("Exit.");
        System.exit(1);
    }

    protected String unScreen(String string) {
        return string
                .replace("\\$", "$")
                .replace("\\[", "]")
                .replace("\\;", ";")
                .replace("\\,", ",");//todo for all getValue
    }
}
