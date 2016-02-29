package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/28/16
 * Time: 9:43 PM
 */
public class CycleStringTest {
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertEquals(10, new CycleString("; ; 10; ").getSize());
        Assert.assertEquals(5, new CycleString("5; ; 10; ").getSize());
        Assert.assertEquals(5, new CycleString("; 2; 10; ").getSize());
        Assert.assertEquals(3, new CycleString("5; 2; 10; ").getSize());
        Assert.assertEquals(4, new CycleString("; ; ; 4").getSize());
        Assert.assertEquals(4, new CycleString("5; ; ; 4").getSize());
        Assert.assertEquals(4, new CycleString("; 2; ; 4").getSize());
        Assert.assertEquals(4, new CycleString("5; 2; ; 4").getSize());
        Assert.assertEquals(4, new CycleString("; ; 10; 4").getSize());
        Assert.assertEquals(4, new CycleString("5; ; 10; 4").getSize());
        Assert.assertEquals(4, new CycleString("; 2; 10; 4").getSize());
        Assert.assertEquals(3, new CycleString("5; 2; 10; 4").getSize());

    }

    @Test
    public void testGetValue() throws Exception {
        Assert.assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]",
                allCycle(new CycleString("; ; 10; ")));
        Assert.assertEquals("[5, 6, 7, 8, 9]",
                allCycle(new CycleString("5; ; 10; ")));
        Assert.assertEquals("[0, 2, 4, 6, 8]",
                allCycle(new CycleString("; 2; 10; ")));
        Assert.assertEquals("[5, 7, 9]",
                allCycle(new CycleString("5; 2; 10; ")));
        Assert.assertEquals("[0, 1, 2, 3]",
                allCycle(new CycleString("; ; ; 4")));
        Assert.assertEquals("[5, 6, 7, 8]",
                allCycle(new CycleString("5; ; ; 4")));
        Assert.assertEquals("[0, 2, 4, 6]",
                allCycle(new CycleString("; 2; ; 4")));
        Assert.assertEquals("[5, 7, 9, 11]",
                allCycle(new CycleString("5; 2; ; 4")));
        Assert.assertEquals("[0, 2.5, 5, 7.5]",
                allCycle(new CycleString("; ; 10; 4")));
        Assert.assertEquals("[5, 6.25, 7.5, 8.75]",
                allCycle(new CycleString("5; ; 10; 4")));
        Assert.assertEquals("[2, 4, 6, 8]",
                allCycle(new CycleString("; 2; 10; 4")));
        Assert.assertEquals("[5, 7, 9]",
                allCycle(new CycleString("5; 2; 10; 4")));

    }

    @Test
    public void testTest() throws Exception {
        Assert.assertTrue(CycleString.test(";"));
        Assert.assertFalse(CycleString.test(""));
        Assert.assertFalse(CycleString.test("q"));
        Assert.assertFalse(CycleString.test("["));
        Assert.assertFalse(CycleString.test("]"));
    }

    @Test
    public void testTestBrackets1() throws Exception {
        //CycleString.test(";]");
        //Assert.fail();
    }

    @Test
    public void testTestBrackets2() throws Exception {
        //CycleString.test(";[");
        //Assert.fail();
    }

    private String allCycle(CycleString cycleString) {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < cycleString.getSize(); i++) {
            s.append(cycleString.getValue(null, i));
            if (i < cycleString.getSize() - 1) {
                s.append(", ");
            }
        }
        s.append("]");
        return s.toString();
    }
}
