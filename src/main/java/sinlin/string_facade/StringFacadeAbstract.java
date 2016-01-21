package sinlin.string_facade;

import java.util.ArrayList;
import java.util.OptionalInt;

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
    public String getName() {
        return name;
    }

    protected void handleException(Exception e) {
        System.out.println("In " + this.getClass().getSimpleName() + " \"" + this.getName() + "\" thrown exception:");
        System.out.println(e.toString());
        System.out.println("Exit.");
        System.exit(1);
    }
}
