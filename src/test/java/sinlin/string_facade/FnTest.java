package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sinlin.data.odt.OdfData;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/12/15
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class FnTest {

    private Fn fn;

    @Before
    public void setUp() throws Exception {
        Fn.setData(new OdfData("test.ods"));
        fn = new Fn("x");
    }

    @After
    public void tearDown() throws Exception {
        fn = null;
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertEquals(6, fn.getSize());
    }

    @Test
    public void testGetValue() throws Exception {
        //fn.getValue(null, 100);
    }
}
