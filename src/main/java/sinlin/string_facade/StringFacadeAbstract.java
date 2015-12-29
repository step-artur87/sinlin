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

    protected void handleException(Exception e) {
        System.out.println("In " + this.getClass().getSimpleName() + " \"" + this.getName() + "\" thrown exception:");
        System.out.println(e.toString());
        System.out.println("Exit.");
        System.exit(0);
    }
}
