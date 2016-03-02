package sinlin.string_facade;

import org.junit.After;
import org.junit.Assert;
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
    Map<String, String> testMap1 = new HashMap<>();

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

        //from svg
        testMap.put("15, 10 15, -5 20, 2.5", "[15, 10 15, -5 20, 2.5]");
        testMap.put("100;-20;-10", "[100, 80, 60, 40, 20, 0]");
        testMap.put("300;-20;190", "[300, 280, 260, 240, 220, 200]");
        testMap.put("[1;1;10] * 100", "[100, 200, 300, 400, 500, 600, 700, 800, 900]");
        testMap.put("100 + 200 * [1;1;10]", "[300, 500, 700, 900, 1100, 1300, 1500, 1700, 1900]");
        //testMap.put("[1;1;10] * 100 - 20 * [1;1;10]", "[80, 160, 240, 320, 400, 480, 560, 640, 720, 800]");
        //testMap.put("100 + 200 * [1;1;10] - 20 * [1;1;10]", "[280, 460, 640, 820, 1000, 1180, 1360, 1540, 1720]");


        //create
        //testMap1.put("translate($[1;1;10] * 100 - 20 * [1;1;10]$,$100 + 200 * [1;1;10] - 20 * [1;1;10]$", "[]");
        testMap1.put("$-[0;10;510]", "[-0, -10, -20, -30, -40, -50, -60, -70, -80, -90, -100, -110, -120, -130, -140, -150, -160, -170, -180, -190, -200, -210, -220, -230, -240, -250, -260, -270, -280, -290, -300, -310, -320, -330, -340, -350, -360, -370, -380, -390, -400, -410, -420, -430, -440, -450, -460, -470, -480, -490, -500]");
        testMap1.put("$-[50;50;550]+10", "[-40, -90, -140, -190, -240, -290, -340, -390, -440, -490]");
        testMap1.put("$[1;1;10] * (-50)", "[-50, -100, -150, -200, -250, -300, -350, -400, -450]");
        testMap1.put("$0;50;550", "[0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500]");
        testMap1.put("$[1;1;10] * 50", "[50, 100, 150, 200, 250, 300, 350, 400, 450]");
        testMap1.put("$red, darkorange, green, blue, darkviolet", "[red, darkorange, green, blue, darkviolet]");
        ///*SString-repl_all*/testMap1.put("translate($0;10;100$, 0)", "[translate(0, 0), translate(10, 0), translate(20, 0), translate(30, 0), translate(40, 0), translate(50, 0), translate(60, 0), translate(70, 0), translate(80, 0), translate(90, 0)]");
        testMap1.put("fill:rgb($255-[0;1;10]*25$,255,$[0;1;10]*25$)", "[fill:rgb(255,255,0), fill:rgb(230,255,25), fill:rgb(205,255,50), fill:rgb(180,255,75), fill:rgb(155,255,100), fill:rgb(130,255,125), fill:rgb(105,255,150), fill:rgb(80,255,175), fill:rgb(55,255,200), fill:rgb(30,255,225)]");
        //testMap1.put("$100-100*sin([0;1;21]/20*3.14)", "[]");

    }

    @After
    public void tearDown() throws Exception {
        testMap = null;
        testMap = null;
    }

    @Test
    public void testCreateVCEF() throws Exception {
        testMap.forEach((k, v) -> {
            //System.out.println("\"" + k + "\"\t\"" + v + "\"");
            Assert.assertEquals("\"" + k + "\"\t\"" + v + "\"", v,
                    allCycle(StringFacadeBuilder.createVCEF(k)));
        });

        testMap.forEach((k, v) -> {
            //System.out.println("\"" + k + "\"\t\"" + v + "\"");
            Assert.assertEquals("\"" + k + "\"\t\"" + v + "\"", v,
                    allCycle(StringFacadeBuilder.create("$" + k)));
        });

        testMap1.forEach((k, v) -> {
            //System.out.println("\"" + k + "\"\t\"" + v + "\"");
            Assert.assertEquals("\"" + k + "\"\t\"" + v + "\"", v,
                    allCycle(StringFacadeBuilder.create(k)));
        });

        //todo it and other StringFacadeBuilder.create("translate($[1;1;10] * 100 - 20 * [1;1;10]$,$100 + 200 * [1;1;10] - 20 * [1;1;10]$");

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
