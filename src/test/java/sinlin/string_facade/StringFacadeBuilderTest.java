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
        //fixme testMap.put("1,", "[1, ]");
        //fixme testMap.put(",1", "[, 1]");
        testMap.put("1, , 1", "[1, , 1]");
        //fixme testMap.put(" , ", "[ , ]");
        //fixme testMap.put(",", "[,]");

        //expr
        testMap.put("[1, 2]*3", "[3, 6]");
        testMap.put("sqrt([4, 16])", "[2, 4]");
        testMap.put("[-1, -2]*3", "[-3, -6]");
        testMap.put("[1.1, 2.2]*3", "[3.3000000000000003, 6.6000000000000005]");
        testMap.put("[1;1;;2]*3", "[3, 6]");
        testMap.put("[-1;-1;;2]*3", "[-3, -6]");

        //todo fixme I do not know what to do
        testMap.put("[1.1;1.1;;2]*3", "[3.3000000000000003, 6.6000000000000005]");
        testMap.put("[100000000.1;100000000.1;;2]*3", "[3.0000000029999995E8, 6.000000005999999E8]");

        //from svg
        testMap.put("15, 10 15, -5 20, 2.5", "[15, 10 15, -5 20, 2.5]");
        testMap.put("100;-20;-10", "[100, 80, 60, 40, 20, 0]");
        testMap.put("300;-20;190", "[300, 280, 260, 240, 220, 200]");
        testMap.put("[1;1;10] * 100", "[100, 200, 300, 400, 500, 600, 700, 800, 900]");
        testMap.put("100 + 200 * [1;1;10]", "[300, 500, 700, 900, 1100, 1300, 1500, 1700, 1900]");
        testMap.put("[1;1;10] * 100 - 20 * [1;1;10]", "[80, 160, 240, 320, 400, 480, 560, 640, 720]");
        testMap.put("100 + 200 * [1;1;10] - 20 * [1;1;10]", "[280, 460, 640, 820, 1000, 1180, 1360, 1540, 1720]");


        //create
        //fixme testMap1.put("translate($[1;1;10] * 100 - 20 * [1;1;10]$,$100 + 200 * [1;1;10] - 20 * [1;1;10]$", "[]");
        testMap1.put("$-[0;10;510]", "[-0, -10, -20, -30, -40, -50, -60, -70, -80, -90, -100, -110, -120, -130, -140, -150, -160, -170, -180, -190, -200, -210, -220, -230, -240, -250, -260, -270, -280, -290, -300, -310, -320, -330, -340, -350, -360, -370, -380, -390, -400, -410, -420, -430, -440, -450, -460, -470, -480, -490, -500]");
        testMap1.put("$-[50;50;550]+10", "[-40, -90, -140, -190, -240, -290, -340, -390, -440, -490]");
        testMap1.put("$[1;1;10] * (-50)", "[-50, -100, -150, -200, -250, -300, -350, -400, -450]");
        testMap1.put("$0;50;550", "[0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500]");
        testMap1.put("$[1;1;10] * 50", "[50, 100, 150, 200, 250, 300, 350, 400, 450]");
        testMap1.put("$one, two, three, four, five", "[one, two, three, four, five]");
        //fixme testMap1.put("translate($0;10;100$, 0)", "[translate(0, 0), translate(10, 0), translate(20, 0), translate(30, 0), translate(40, 0), translate(50, 0), translate(60, 0), translate(70, 0), translate(80, 0), translate(90, 0)]");
        testMap1.put("fill:rgb($255-[0;1;10]*25$,255,$[0;1;10]*25$)", "[fill:rgb(255,255,0), fill:rgb(230,255,25), fill:rgb(205,255,50), fill:rgb(180,255,75), fill:rgb(155,255,100), fill:rgb(130,255,125), fill:rgb(105,255,150), fill:rgb(80,255,175), fill:rgb(55,255,200), fill:rgb(30,255,225)]");
        testMap1.put("$100-100*sin([0;1;21]/20*3.14)", "[100, 84.36441877247522, 69.11344799010678, 54.62223729244549, 41.2472474286108, 29.31748188946341, 19.12639394468698, 10.924668158803371, 4.914053949353004, 1.2424028719077285, 3.170681654296459E-5, 1.2175008099690956, 4.86486237661714, 10.852397109768702, 19.032821172283576, 29.204909135156782, 41.11844380322051, 54.48037116042034, 68.96200903279583, 84.20713214751335, 99.84073470835132]");
        testMap1.put("$10,20,30,40,60,70,80,90,110,120,130,140", "[10, 20, 30, 40, 60, 70, 80, 90, 110, 120, 130, 140]");
        testMap1.put("$5/s, 42/s, 3/s, 2/s, 1/s", "[5/s, 42/s, 3/s, 2/s, 1/s]");
        testMap1.put("$300-[1;1;10]*50", "[250, 200, 150, 100, 50, 0, -50, -100, -150]");
        testMap1.put("$[1;1;10]/4", "[0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2, 2.25]");
        testMap1.put("$-150;-150;;4", "[-150, -300, -450, -600]");
        testMap1.put("$0;-10;-600", "[0, -10, -20, -30, -40, -50, -60, -70, -80, -90, -100, -110, -120, -130, -140, -150, -160, -170, -180, -190, -200, -210, -220, -230, -240, -250, -260, -270, -280, -290, -300, -310, -320, -330, -340, -350, -360, -370, -380, -390, -400, -410, -420, -430, -440, -450, -460, -470, -480, -490, -500, -510, -520, -530, -540, -550, -560, -570, -580, -590]");
        testMap1.put("$0.25;0.25;;4", "[0.25, 0.5, 0.75, 1]");
        testMap1.put("$4, 2, 1.33, 1", "[4, 2, 1.33, 1]");
        testMap1.put("translate(\n" +
                "\t\t\t\t\t$([1;1;10] / 100) $,\n" +
                "\t\t\t\t\t$-([1;1;10] / 100) $)", "[translate(\n" +
                "\t\t\t\t\t0.01,\n" +
                "\t\t\t\t\t-0.01), translate(\n" +
                "\t\t\t\t\t0.02,\n" +
                "\t\t\t\t\t-0.02), translate(\n" +
                "\t\t\t\t\t0.03,\n" +
                "\t\t\t\t\t-0.03), translate(\n" +
                "\t\t\t\t\t0.04,\n" +
                "\t\t\t\t\t-0.04), translate(\n" +
                "\t\t\t\t\t0.05,\n" +
                "\t\t\t\t\t-0.05), translate(\n" +
                "\t\t\t\t\t0.06,\n" +
                "\t\t\t\t\t-0.06), translate(\n" +
                "\t\t\t\t\t0.07,\n" +
                "\t\t\t\t\t-0.07), translate(\n" +
                "\t\t\t\t\t0.08,\n" +
                "\t\t\t\t\t-0.08), translate(\n" +
                "\t\t\t\t\t0.09,\n" +
                "\t\t\t\t\t-0.09)]");
        testMap1.put("$([1;1;10] / 10000)  / 10", "[1.0E-5, 2.0E-5, 2.9999999999999997E-5, 4.0E-5, 5.0E-5, 5.9999999999999995E-5, 7.0E-5, 8.0E-5, 8.999999999999999E-5]");
        testMap1.put("translate(0, $0, 100, 200$)", "[translate(0,0), translate(0,100), translate(0,200)]");
        testMap1.put("stroke:url(#grad_$x, y1, z1$)", "[stroke:url(#grad_x), stroke:url(#grad_y1), stroke:url(#grad_z1)]");
        testMap1.put("$([1;1;10] / 100)  - ([1;1;10] / 10000 /2) $,\n" +
                "\t\t$-([1;1;10] / 100)  + ([1;1;10] / 10000 / 2) $)\"", "[0.00995,\n" +
                "\t\t-0.00995)\", 0.0199,\n" +
                "\t\t-0.0199)\", 0.029849999999999998,\n" +
                "\t\t-0.029849999999999998)\", 0.0398,\n" +
                "\t\t-0.0398)\", 0.04975,\n" +
                "\t\t-0.04975)\", 0.059699999999999996,\n" +
                "\t\t-0.059699999999999996)\", 0.06965,\n" +
                "\t\t-0.06965)\", 0.0796,\n" +
                "\t\t-0.0796)\", 0.08954999999999999,\n" +
                "\t\t-0.08954999999999999)\"]");
        testMap1.put("stroke-width:4;stroke:rgb($[1;1;10] * 255 / 50000$, \n" +
                "\t\t$[1;1;10] * 255 / 50000$, \n" +
                "\t\t$[1;1;10] * 255 / 5000000$)", "[stroke-width:4;stroke:rgb(0.0051,\n" +
                "\t\t0.0051,\n" +
                "\t\t5.1E-5), stroke-width:4;stroke:rgb(0.0102,\n" +
                "\t\t0.0102,\n" +
                "\t\t1.02E-4), stroke-width:4;stroke:rgb(0.0153,\n" +
                "\t\t0.0153,\n" +
                "\t\t1.53E-4), stroke-width:4;stroke:rgb(0.0204,\n" +
                "\t\t0.0204,\n" +
                "\t\t2.04E-4), stroke-width:4;stroke:rgb(0.0255,\n" +
                "\t\t0.0255,\n" +
                "\t\t2.55E-4), stroke-width:4;stroke:rgb(0.0306,\n" +
                "\t\t0.0306,\n" +
                "\t\t3.06E-4), stroke-width:4;stroke:rgb(0.0357,\n" +
                "\t\t0.0357,\n" +
                "\t\t3.57E-4), stroke-width:4;stroke:rgb(0.0408,\n" +
                "\t\t0.0408,\n" +
                "\t\t4.08E-4), stroke-width:4;stroke:rgb(0.0459,\n" +
                "\t\t0.0459,\n" +
                "\t\t4.59E-4)]");
        testMap1.put("$-([1;1;10] / 100)  / 10", "[-0.001, -0.002, -0.003, -0.004, -0.005, -0.006, -0.007000000000000001, -0.008, -0.009]");
        testMap1.put("$([1;1;10] / 10000)  * (1 - sqrt(2) / 10) / 2", "[4.292893218813453E-5, 8.585786437626906E-5, 1.2878679656440356E-4, 1.7171572875253812E-4, 2.1464466094067263E-4, 2.575735931288071E-4, 3.005025253169417E-4, 3.4343145750507625E-4, 3.8636038969321073E-4]");
        testMap1.put("$-([1;1;10] / 10000)  * (1 + sqrt(2) / 10) / 2", "[-5.7071067811865474E-5, -1.1414213562373095E-4, -1.7121320343559641E-4, -2.282842712474619E-4, -2.853553390593274E-4, -3.4242640687119283E-4, -3.994974746830583E-4, -4.565685424949238E-4, -5.136396103067892E-4]");
        testMap1.put("$1000 - [1;1;10] / 2 - 30 * [1;1;10]", "[969.5, 939, 908.5, 878, 847.5, 817, 786.5, 756, 725.5]");
        testMap1.put("$[1;1;10] * 10 - 30 * [1;1;10]", "[-20, -40, -60, -80, -100, -120, -140, -160, -180]");
        testMap1.put("$1000 - [1;1;10] / 2", "[999.5, 999, 998.5, 998, 997.5, 997, 996.5, 996, 995.5]");
        testMap1.put("$1000 - [1;1;10] / 2 -[1;1;10] * 12 / 2", "[993.5, 987, 980.5, 974, 967.5, 961, 954.5, 948, 941.5]");
        testMap1.put("fill:rgb(255,225,$0;10;170$)", "[fill:rgb(255,225,0), fill:rgb(255,225,10), fill:rgb(255,225,20), fill:rgb(255,225,30), fill:rgb(255,225,40), fill:rgb(255,225,50), fill:rgb(255,225,60), fill:rgb(255,225,70), fill:rgb(255,225,80), fill:rgb(255,225,90), fill:rgb(255,225,100), fill:rgb(255,225,110), fill:rgb(255,225,120), fill:rgb(255,225,130), fill:rgb(255,225,140), fill:rgb(255,225,150), fill:rgb(255,225,160)]");
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
            //System.out.println(k + " " + allCycle(StringFacadeBuilder.create(k)));
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
