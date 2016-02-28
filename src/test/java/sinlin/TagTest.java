package sinlin;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/28/16
 * Time: 6:45 PM
 */
public class TagTest {
    Tag tag;

    @Before
    public void setUp() throws Exception {
        tag = new Tag("tag", null);//todo test null, null
    }

    @After
    public void tearDown() throws Exception {
        tag = null;
    }

    @Test
    public void testAttrSizes() throws Exception {

    }

    @Test
    public void testNodeSizes() throws Exception {

    }

    @Test
    public void testIsExemplarWritten() throws Exception {

    }


    @Test
    public void testsetTextConcat() throws Exception {
        tag.setTextConcat("s");
        Assert.assertEquals("s",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("\ns\n");
        Assert.assertEquals("s",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("\ts\t");
        Assert.assertEquals("s",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat(" s ");
        Assert.assertEquals("s",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("\n\ts\t\n");
        Assert.assertEquals("s",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("\ts\ts\t");
        Assert.assertEquals("s\ts",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("\ns\ns\n");
        Assert.assertEquals("s\ns",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("s");
        Assert.assertEquals("s",
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("\t\t");
        Assert.assertEquals(null,
                tag.getText().getName());
        tag.setTextConcat(null);
        tag.setTextConcat("\t\n\t\n");
        Assert.assertEquals(null,
                tag.getText().getName());
    }

    @Test
    public void testGetNameWithAttr() throws Exception {

    }

    @Test
    public void testGetText() throws Exception {

    }
}
