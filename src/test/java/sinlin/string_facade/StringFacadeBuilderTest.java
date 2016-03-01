package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
 * Time: 9:46 PM
 */
public class StringFacadeBuilderTest {
    Map<String, String> testMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        //fs
        testMap.put("; ; 10; ", "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        testMap.put("5; ; 10; ", "[5, 6, 7, 8, 9]");
        testMap.put("; 2; 10; ", "[0, 2, 4, 6, 8]");
        testMap.put("5; 2; 10; ", "[5, 7, 9]");
        testMap.put("; ; ; 4", "[0, 1, 2, 3]");
        testMap.put("5; ; ; 4", "[5, 6, 7, 8]");
        testMap.put("; 2; ; 4", "[0, 2, 4, 6]");
        testMap.put("5; 2; ; 4", "[5, 7, 9, 11]");
        testMap.put("; ; 10; 4", "[0, 2.5, 5, 7.5]");
        testMap.put("5; ; 10; 4", "[5, 6.25, 7.5, 8.75]");
        testMap.put("; 2; 10; 4", "[2, 4, 6, 8]");
        testMap.put("5; 2; 10; 4", "[5, 7, 9]");

        //vs
        testMap.put("1, 1", "[1, 1]");
        //testMap.put("1,", "[1, ]");
        //testMap.put(",1", "[, 1]");
        testMap.put("1, , 1", "[1, , 1]");
        //testMap.put(" , ", "[ , ]");
        //testMap.put(",", "[,]");

        //expr
        testMap.put("[1, 2]*3", "[3, 6]");
        testMap.put("sqrt([4, 16])", "[2, 4]");
        testMap.put("[-1, -2]*3", "[-3, -6]");
        //testMap.put("[1.1, 2.2]*3", "[3.3, 6.6]");
        testMap.put("[1;1;;2]*3", "[3, 6]");
        testMap.put("[-1;-1;;2]*3", "[-3, -6]");

        //todo fixme I do not know what to do
        //testMap.put("[1.1;1.1;;2]*3", "[3.3000000000000003, 6.6000000000000005]");
        //testMap.put("[100000000.1;100000000.1;;2]*3","[3.0000000029999995E8, 6.000000005999999E8]");
    }

    @After
    public void tearDown() throws Exception {
        testMap = null;
    }

    @Test
    public void testCreateVCEF() throws Exception {
        testMap.forEach((k, v) -> {
            //System.out.println("\"" + k + "\"\t\"" + v + "\"");
            Assert.assertEquals("\"" + k + "\"\t\"" + v + "\"", v,
                    allCycle(StringFacadeBuilder.createVCEF(k)));
        });
    }

    private String allCycle(StringFacadeIF stringFacadeIF) {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < stringFacadeIF.getSize(); i++) {
            s.append(stringFacadeIF.getValue(null, i));
            if (i < stringFacadeIF.getSize() - 1) {
                s.append(", ");
            }
        }
        s.append("]");
        return s.toString();
    }
}
