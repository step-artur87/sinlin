package sinlin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sinlin.string_facade.Expr;
import sinlin.string_facade.StringFacadeBuilder;

import java.util.ArrayList;

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
public class ErrorTest {
    ArrayList<String> stringArrayList;

    @Before
    public void setUp() throws Exception {
        stringArrayList = new ArrayList<>();

        //CycleStringTest
        stringArrayList.add("1;");
        stringArrayList.add("1;1");
        stringArrayList.add("1;1;1;1;1");
        stringArrayList.add("1;1;;");
        stringArrayList.add("1;1;1;0;");
        stringArrayList.add("1;1;-1;;");
        stringArrayList.add("1;;1;;");
        stringArrayList.add("1;1;1;-1;");
        stringArrayList.add("1;[1;1;1;");
        stringArrayList.add("a;b;c;d;");
        stringArrayList.add("1;0;1;");

        //ExprTest
        stringArrayList.add("1+++1");
    }

    @After
    public void tearDown() throws Exception {
        stringArrayList = null;

    }

    @Test
    public void errorTest() throws Exception {
        stringArrayList.forEach((s) -> {
            try {
                System.out.println("Test for \"" + s + "\"");
                StringFacadeBuilder.createVCEF(s);
            } catch (Exception e) {
                System.out.println();
            }
        });

        try {
            //CycleStringTest
            System.out.println("Test for \"" + "have not value" + "\"");
            StringFacadeBuilder.createVCEF("1;1;10").getValue(null, 20);

            //ExprTest
            System.out.println("Test for \"" + "empty expr" + "\"");
            System.out.println(new Expr("").getValue(null, 0));
        } catch (Exception e) {
        }

    }
}
