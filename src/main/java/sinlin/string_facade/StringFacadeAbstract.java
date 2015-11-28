package sinlin.string_facade;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/7/15
 * Time: 6:06 PM
 */
public abstract class StringFacadeAbstract implements StringFacadeIF {
    protected String name;

    @Override
    public String getName() {
        return name;
    }
}
