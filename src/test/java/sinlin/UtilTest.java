package sinlin;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    }

    @Test
    public void testOneOrEqual() throws Exception {

    }

    @Test
    public void testDeleteForbiddenSymbols() throws Exception {
        Assert.assertTrue(
                Util.deleteForbiddenSymbols("-[]{}?'\"").isEmpty());
    }

    @Test
    public void testPrintErrorInPath() throws Exception {

    }
}
