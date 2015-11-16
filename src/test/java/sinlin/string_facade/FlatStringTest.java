package sinlin.string_facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/6/15
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlatStringTest {
    FlatString stringFacade;

    @Before
    public void setUp() throws Exception {
        stringFacade = new FlatString("text");
    }

    @After
    public void tearDown() throws Exception {
        stringFacade = null;
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertTrue(1 == stringFacade.getSize());
    }

    @Test
    public void testGetValue() throws Exception {
        Assert.assertTrue(stringFacade.getValue(null, 0)
                .equals("text"));
    }
}
