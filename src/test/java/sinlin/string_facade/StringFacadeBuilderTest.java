package sinlin.string_facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sinlin.data.odt.OdfData;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/7/15
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringFacadeBuilderTest {
    StringFacadeIF sf;

    @Before
    public void setUp() throws Exception {
        Fn.setData(new OdfData("2.ods"));

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testCreateVSEF() throws Exception {
        sf = StringFacadeBuilder.createVCEF("text");
        Assert.assertEquals(
                Fn.class,
                sf.getClass());
        sf = StringFacadeBuilder.createVCEF("text,text1");
        Assert.assertEquals(
                VarString.class,
                sf.getClass());
        sf = StringFacadeBuilder.createVCEF("text+text1");
        Assert.assertEquals(
                Expr.class,
                sf.getClass());
    }
}
