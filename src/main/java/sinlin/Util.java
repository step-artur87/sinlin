package sinlin;

import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/21/16
 * Time: 3:20 PM
 */
public class Util {
    public static int mapElementsSizes(IntStream intStream) {
        OptionalInt n = intStream.reduce((a, b)
                -> (a == -1) ? -1 : (b == 1) ? a : (a == b) ? a : -1);
        return n.isPresent() ? n.getAsInt() : 1;
    }
}
