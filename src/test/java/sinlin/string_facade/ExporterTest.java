package sinlin.string_facade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sinlin.Exporter;
import sinlin.Tag;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/7/15
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExporterTest {
    Exporter exporter;
    Tag tag;

    @Before
    public void setUp() throws Exception {
        exporter = new Exporter();
        tag = new Tag("name", null);
        tag.addChildTag(new Tag("g", null));
        tag.setText("text");
    }

    @After
    public void tearDown() throws Exception {
        exporter = null;
    }

    @Test
    public void testWriteAllXml() throws Exception {
        exporter.writeAllXml(tag, "", true);
    }
}
