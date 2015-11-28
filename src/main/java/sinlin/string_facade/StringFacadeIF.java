package sinlin.string_facade;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/26/15
 * Time: 12:27 AM
 */
public interface StringFacadeIF {
    public static final String DELIM = "$";//todo other constants
    public static final String DELIM_CYCLE = ";";
    String DELIM_VAR = ",";

    public String getName();

    public int getSize();

    public String getValue(Map<String, String> keyMap, int n);
}
