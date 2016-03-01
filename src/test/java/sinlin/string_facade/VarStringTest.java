package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
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
 * Time: 9:41 PM
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
        Assert.assertEquals(1, new VarString("1").getSize());
        Assert.assertEquals(2, new VarString("1, 1").getSize());
        Assert.assertEquals(3, new VarString("1, , 1").getSize());
        //Assert.assertEquals(2, new VarString(" , ").getSize());
        //Assert.assertEquals(2, new VarString(",").getSize());//fixme
        Assert.assertEquals(1, new VarString("").getSize());
    }

    @Test
    public void testGetValue() throws Exception {
/*
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

*/
    }

    @Test
    public void testTest() throws Exception {
        Assert.assertTrue(VarString.test(","));
        Assert.assertFalse(VarString.test(""));
        Assert.assertFalse(VarString.test("q"));
        Assert.assertFalse(VarString.test("["));
        Assert.assertFalse(VarString.test("]"));
    }

    @Test
    public void testTestBrackets1() throws Exception {
        //VarString.test(",]");
        //Assert.fail("Must be System.exit(1)");
    }

    @Test
    public void testTestBrackets2() throws Exception {
        //VarString.test(",[");
        //Assert.fail("Must be System.exit(1)");
    }
}
