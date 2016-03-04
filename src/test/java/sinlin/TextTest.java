package sinlin;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 3/3/16
 * Time: 5:04 PM
 */
public class TextTest {
    Exporter exporter;

    TagHandler tagHandler;

    @Before
    public void setUp() throws Exception {
        tagHandler = new TagHandler();
        exporter = new Exporter();
    }

    @After
    public void tearDown() throws Exception {
        tagHandler = null;
        exporter = null;

    }

    @Test
    public void pathTest() throws Exception {
        SaxParsing.parse(tagHandler,
                "src/test/resources/textTest.svg");
        Assert.assertEquals("text1", tagHandler.getRootTag().getNodes().pop().getText().getName());
        Assert.assertEquals("text2", tagHandler.getRootTag().getNodes().pop().getText().getName());
        Assert.assertEquals("text3", tagHandler.getRootTag().getNodes().pop().getText().getName());
        Assert.assertEquals("text4", tagHandler.getRootTag().getNodes().pop().getText().getName());
        Assert.assertEquals("te xt5", tagHandler.getRootTag().getNodes().pop().getText().getName());
        //works, but Idea replace in textTest.svg te\nxt6 by te xt6
        //Assert.assertEquals("te\txt6", tagHandler.getRootTag().getNodes().pop().getText().getName());//fixme
        //(not fixme) Assert.assertEquals("te \n\txt7", tagHandler.getRootTag().getNodes().pop().getText().getName());//fixme
    }
}
