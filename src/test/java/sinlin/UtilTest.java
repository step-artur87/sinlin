package sinlin;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

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
 * Time: 6:12 PM
 */
public class UtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMapElementsSizes() throws Exception {
        Assert.assertEquals(10,
                Util.mapElementsSizes(IntStream.of(1, 10, 1, 10, 1)));
        Assert.assertEquals(-1,
                Util.mapElementsSizes(IntStream.of(1, 10, 1, 9, 1)));
        Assert.assertEquals(10,
                Util.mapElementsSizes(IntStream.of(10, 10, 1, 10, 1)));
        Assert.assertEquals(-1,
                Util.mapElementsSizes(IntStream.of(10, 10, 1, 9, 1)));
        Assert.assertEquals(10,
                Util.mapElementsSizes(IntStream.of(1, 10, 1, 10, 10)));
        Assert.assertEquals(-1,
                Util.mapElementsSizes(IntStream.of(1, 10, 1, 9, 10)));
        Assert.assertEquals(10,
                Util.mapElementsSizes(IntStream.of(10, 10, 10, 10, 10)));
        Assert.assertEquals(-1,
                Util.mapElementsSizes(IntStream.of(9, 10, 10, 10, 10)));
        Assert.assertEquals(-1,
                Util.mapElementsSizes(IntStream.of(10, 10, 9, 10, 10)));
        Assert.assertEquals(-1,
                Util.mapElementsSizes(IntStream.of(10, 10, 10, 10, 9)));
        Assert.assertEquals(-1,
                Util.mapElementsSizes(IntStream.of(10, 9, 9, 10, 10)));
        Assert.assertEquals(1,
                Util.mapElementsSizes(IntStream.of(1, 1, 1, 1, 1)));
    }

    @Test
    public void testOneOrEqual() throws Exception {
        Assert.assertEquals(-1,
                Util.oneOrEqual(-10, 10));
        Assert.assertEquals(-1,
                Util.oneOrEqual(10, -10));
        Assert.assertEquals(10,
                Util.oneOrEqual(10, 10));
        Assert.assertEquals(-1,
                Util.oneOrEqual(9, 10));
        Assert.assertEquals(-1,
                Util.oneOrEqual(10, 9));
        Assert.assertEquals(10,
                Util.oneOrEqual(1, 10));
        Assert.assertEquals(10,
                Util.oneOrEqual(10, 1));
    }

    @Test
    public void testDeleteForbiddenSymbols() throws Exception {
        Assert.assertTrue(
                Util.deleteForbiddenSymbols("-[]{}?'\"").isEmpty());
    }

    @Test
    public void testPrintErrorInPath() throws Exception {

    }

    @Test
    public void testClearSquareBrackets() throws Exception {
        Assert.assertEquals("qwe", Util.clearSquareBrackets("qwe"));
        Assert.assertEquals("qwe", Util.clearSquareBrackets("[qwe]"));
        Assert.assertEquals("", Util.clearSquareBrackets(""));
        Assert.assertEquals("", Util.clearSquareBrackets("[]"));
    }

    @Test
    public void testSplitSquareBrackets() throws Exception {
        Assert.assertEquals("[qwe]",
                Util.splitSquareBrackets("qwe").toString());
        Assert.assertEquals("[qwe, [qwe]]",
                Util.splitSquareBrackets("qwe[qwe]").toString());
        Assert.assertEquals("[[qwe], qwe]",
                Util.splitSquareBrackets("[qwe]qwe").toString());
        Assert.assertEquals("[qwe, [qwe1], qwe2]",
                Util.splitSquareBrackets("qwe[qwe1]qwe2").toString());
        Assert.assertEquals("[qwe, [q[we]1], qwe2]",
                Util.splitSquareBrackets("qwe[q[we]1]qwe2").toString());
        Assert.assertEquals("[qwe, [qwe1][qwe2], qwe3]",
                Util.splitSquareBrackets("qwe[qwe1][qwe2]qwe3").toString());
    }
}
