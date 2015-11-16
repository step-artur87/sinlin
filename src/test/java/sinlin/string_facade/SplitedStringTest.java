package sinlin.string_facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sinlin.data.odt.OdfData;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/6/15
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class SplitedStringTest {

    @Before
    public void setUp() throws Exception {
        Fn.setData(new OdfData("2.ods"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertTrue(
                1 == (new SplitedString("text")).getSize());
        Assert.assertTrue(
                2 == (new SplitedString("$text")).getSize());
        Assert.assertTrue(
                2 == (new SplitedString("1$text")).getSize());
        Assert.assertTrue(
                2 == (new SplitedString("$text$2")).getSize());
        Assert.assertTrue(
                2 == (new SplitedString("$text$2$")).getSize());
        Assert.assertTrue(
                2 == (new SplitedString("$text$$text")).getSize());//todo $$ between different Fn
/*
        Assert.assertTrue(
                3 == (new SplitedString("te$x$t")).getSize());
        Assert.assertTrue(
                3 == (new SplitedString("$te$x$t")).getSize());
*/

/*
        Assert.assertTrue(
                2 == (new SplitedString("text1, text2")).getSize());
*/
    }

    @Test
    public void testGetValue() throws Exception {
/*
        Assert.assertEquals("text",
                (new SplitedString("text")).getValue(null, 0));

        Assert.assertEquals("text",
                (new SplitedString("text , text1")).getValue(null, 0));
        Assert.assertEquals("text1",
                (new SplitedString("text , text1")).getValue(null, 1));

        Assert.assertEquals("text",
                (new SplitedString("text,text1")).getValue(null, 0));
        Assert.assertEquals("text1",
                (new SplitedString("text,text1")).getValue(null, 1));

*/
    }
}
