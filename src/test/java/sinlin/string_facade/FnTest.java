package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sinlin.data.odt.OdfData;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/28/16
 * Time: 9:44 PM
 */
public class FnTest {
    OdfData odfData;
    Fn fn;

    @Before
    public void setUp() throws Exception {
        odfData = new OdfData(
                "src/test/resources/fn_test.ods");
        Fn.setData(odfData);
        fn = new Fn("ten");
    }

    @After
    public void tearDown() throws Exception {
        odfData = null;
        fn = null;
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertEquals(
                10,
                fn.getSize());
    }

    @Test
    public void testGetValue() throws Exception {
        Assert.assertEquals(
                "ten1",
                fn.getValue(null, 0));
        Assert.assertEquals(
                "ten10",
                fn.getValue(null, 9));
    }

}
