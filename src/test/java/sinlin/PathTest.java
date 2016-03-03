package sinlin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 3/3/16
 * Time: 5:04 PM
 */
public class PathTest {
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
                "src/test/resources/pathTest.svg");
        exporter.writeAllXml(tagHandler.getRootTag(), null, true);
    }
}
