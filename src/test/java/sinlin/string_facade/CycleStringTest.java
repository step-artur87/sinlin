package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/12/15
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class CycleStringTest {
    CycleString cycleString;

    @Before
    public void setUp() throws Exception {
        cycleString = new CycleString("0;0.8;5");
    }

    @After
    public void tearDown() throws Exception {
        cycleString = null;
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertEquals(7, cycleString.getSize());
    }

    @Test
    public void testGetValue() throws Exception {
        Assert.assertEquals("0.0", cycleString.getValue(null, 0));
        Assert.assertEquals("0.8", cycleString.getValue(null, 1));
        Assert.assertEquals("1.6", cycleString.getValue(null, 2));
        Assert.assertEquals("2.4", cycleString.getValue(null, 3));
        Assert.assertEquals("3.2", cycleString.getValue(null, 4));
        Assert.assertEquals("4.0", cycleString.getValue(null, 5));
        Assert.assertEquals("4.8", cycleString.getValue(null, 6));
    }
}
