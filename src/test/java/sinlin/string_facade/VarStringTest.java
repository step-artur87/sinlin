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
public class VarStringTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertTrue(
                1 == (new VarString("text")).getSize());
        Assert.assertTrue(
                2 == (new VarString("text1, text2")).getSize());
    }

    @Test
    public void testGetValue() throws Exception {
        Assert.assertEquals("text",
                (new VarString("text")).getValue(null, 0));

        Assert.assertEquals("text",
                (new VarString("text , text1")).getValue(null, 0));
        Assert.assertEquals("text1",
                (new VarString("text , text1")).getValue(null, 1));

        Assert.assertEquals("text",
                (new VarString("text,text1")).getValue(null, 0));
        Assert.assertEquals("text1",
                (new VarString("text,text1")).getValue(null, 1));

    }
}
