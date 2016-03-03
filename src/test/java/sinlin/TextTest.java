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
        Assert.assertEquals("text", tagHandler.getRootTag().getNodes().peek().getText().getName());
        Assert.assertEquals("text", tagHandler.getRootTag().getNodes().peek().getText().getName());
        Assert.assertEquals("text", tagHandler.getRootTag().getNodes().peek().getText().getName());
        Assert.assertEquals(/*"te xt"*/"text", tagHandler.getRootTag().getNodes().peek().getText().getName());//fixme
        Assert.assertEquals(/*"te\txt"*/"text", tagHandler.getRootTag().getNodes().peek().getText().getName());//fixme
        Assert.assertEquals(/*"te    \n\t\t    xt"*/"text", tagHandler.getRootTag().getNodes().peek().getText().getName());//fixme
    }
}
