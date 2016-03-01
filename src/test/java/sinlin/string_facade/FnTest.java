package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sinlin.data.odt.OdfData;

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
