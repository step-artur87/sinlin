package sinlin.data.odt;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;
import sinlin.data.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class OdfData implements Data {//fixme one cell as range
    private SpreadsheetDocument document;
    private List<Table> tableList;//sheats
    private Map<String, ArrayList<String>> rows
            = new HashMap<>();//<diapason_name, diapason_values_arrayList>

    public OdfData(String fileName) {
        try {
            document = SpreadsheetDocument.loadDocument(
                    new File(fileName));
            tableList = document.getTableList();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }

    @Override
    public ArrayList<String> getRow(String name) {
        ArrayList<String> stringArrayList;
        if (!rows.containsKey(name)) {
            stringArrayList = new ArrayList<>();
            CellRange cellRange;
            int rowNumber;
            //Bug all <table>.cellRange get cells from <table>
            //if it has place on other
            cellRange = tableList.get(0).getCellRangeByName(name);
            if (cellRange == null) {
                System.out.print("File "
                        + document.getBaseURI()
                        + " has not cell range with name "
                        + name
                        + ". Exit.");
                System.exit(1);
            }
            rowNumber = cellRange.getRowNumber();
            for (int i = 0; i < rowNumber; i++) {
                stringArrayList.add(
                        cellRange.getCellByPosition(0, i)
                                .getDisplayText());
            }
            rows.put(name, stringArrayList);
        }
        return rows.get(name);
    }
}
