package sinlin;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

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
        Util.splitSquareBrackets("qwe1[qwe2]qwe3");
    }
}
