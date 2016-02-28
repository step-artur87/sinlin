package sinlin.string_facade;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/26/15
 * Time: 12:27 AM
 */
public interface StringFacadeIF {
    /**
     * Returns the name of this StringFacadeIF.
     * The name is equal string, result of parsing which
     * is this StringFacadeIF.
     *
     * @return the name of this StringFacadeIF.
     */
    public String getName();

    /**
     * Returns the size of this StringFacadeIF.
     * The size is equal to quantity of values
     * that can be returned by {@link #getValue(java.util.Map, int)}
     * while distinct integer argument of this method.
     *
     * @return the size of this StringFacadeIF.
     */
    public int getSize();

    /**
     * Returns the value of this StringFacadeIF,
     * defined by map of filters and number of needed value.
     *
     * @param keyMap map that only will be used in one
     *               of the far future version (if one will
     *               developed).
     * @param n      number of the needed value.
     * @return the value of this StringFacadeIF.
     */
    public String getValue(Map<String, String> keyMap, int n);
}
