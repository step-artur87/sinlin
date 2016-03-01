package sinlin;

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
    public void testSetTextConcat() throws Exception {
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
                tag.getText());
        tag.setTextConcat(null);
        tag.setTextConcat("\t\n\t\n");
        Assert.assertEquals(null,
                tag.getText());
        tag.setTextConcat(null);
        tag.setTextConcat("");
        tag.setTextConcat(null);
        Assert.assertEquals(null,
                tag.getText());
        Assert.assertEquals(null,
                tag.getText());
        tag.setTextConcat(null);
        tag.setTextConcat("a");
        tag.setTextConcat("b");
        Assert.assertEquals("ab",
                tag.getText().getName());
    }

    @Test
    public void testGetNameWithAttr() throws Exception {

    }

    @Test
    public void testGetText() throws Exception {

    }
}
