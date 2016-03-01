package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

/*
sinlin - SVG preprocessor, that can add data from .ods files to SVG.
Copyright (C) 2015  Artur Stepankevich

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
        Assert.assertEquals("[5, 5, 5, 5]",
                allCycle(new CycleString("5; 0; ; 4")));
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
        //Assert.fail("Must be System.exit(1)");
    }

    @Test
    public void testTestBrackets2() throws Exception {
        //CycleString.test(";[");
        //Assert.fail("Must be System.exit(1)");
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
