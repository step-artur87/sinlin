package sinlin.data.odt;

import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.spreadsheet.Range;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import sinlin.data.Data;

import java.io.File;
import java.util.ArrayList;
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
 * Date: 10/10/15
 * Time: 2:14 AM
 */

//Get all rangenames of document is impossible
//Get range with one cell impossible (error)
//Get range on not first sheet impossible
public class OdfData implements Data {
    private Map<String, ArrayList<String>> rows
            = new HashMap<>();//<diapason_name, diapason_values_arrayList>
    private ODPackage odPackage;
    private SpreadSheet spreadSheet;

    public OdfData(String fileName) {
        try {
            odPackage = new ODPackage(new File("test.ods"));
            spreadSheet = odPackage
                    .getSpreadSheet();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }

    @Override
    public ArrayList<String> getRow(String name) {
        ArrayList<String> stringArrayList;
        Range range;
        if (!rows.containsKey(name)) {
            stringArrayList = new ArrayList<>();
            int rowNumber;
            int columnNumber;
            //Bug all <table>.cellRange get cells from <table>
            //if it has place on other
            range = spreadSheet.getRange(name);
            if (range == null) {
                System.out.print("File "
                        + odPackage.getFile().getName()
                        + " has not cell range with name "
                        + name
                        + "\n" +
                        "or (if you see ArrayIndexOutOfBoundsException) cell range has one cell\n" +
                        "and can not be taken, because bug in odftoolkit. Exit.");
                System.exit(1);
            }
            int strartX = range.getStartPoint().x;
            int strartY = range.getStartPoint().y;
            int endX = (int) range.getEndPoint().getX();
            int endY = (int) range.getEndPoint().getY();
            String startS = range.getStartSheet();

            for (int i = strartX; i <= endX; i++) {
                for (int j = strartY; j <= endY; j++) {
                    stringArrayList.add(
                            spreadSheet
                                    .getSheet(startS)
                                    .getCellAt(i, j).getTextValue());
                }
            }
            rows.put(name, stringArrayList);
        }
        return rows.get(name);
    }
}
