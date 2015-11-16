package sinlin.string_facade;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/26/15
 * Time: 12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlatString extends StringFacadeAbstract
        implements StringFacadeIF {
    private String flatString;

    public FlatString(String string) {
        name = string;
        this.flatString = string;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        return flatString;
    }
}
