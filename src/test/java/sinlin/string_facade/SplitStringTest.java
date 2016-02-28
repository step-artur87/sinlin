package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/28/16
 * Time: 9:17 PM
 */
public class SplitStringTest {


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {
        Assert.assertEquals("a, b1c, d3",
                SplitString.create("a, b$1,2$c, d$3, 4").getValue(null, 0));
        Assert.assertEquals("a, bc, de, f",
                SplitString.create("a, b$$c, d$$e, f").getValue(null, 0));
        Assert.assertEquals("135",
                SplitString.create("$1, 2$$3, 4$$5, 6").getValue(null, 0));
        Assert.assertEquals("1",
                SplitString.create("$1, 2").getValue(null, 0));
        Assert.assertEquals(SplitString.class,
                SplitString.create("a$1, 2").getClass());
        Assert.assertEquals(VarString.class,
                SplitString.create("$1, 2").getClass());
    }

    @Test
    public void testGetValue() throws Exception {

    }

    @Test
    public void testGetSize() throws Exception {

    }
}
