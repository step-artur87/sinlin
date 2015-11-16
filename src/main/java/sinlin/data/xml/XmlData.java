package sinlin.data.xml;

import sinlin.data.Data;

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
 * Date: 9/2/15
 * Time: 9:41 PM
 */
public class XmlData implements Data {
    private Map<String, ArrayList<String>> dataMap = new HashMap<>();

    @Override
    public ArrayList<String> getRow(String name) {
        if (!dataMap.containsKey(name)) {
            System.out.print("XmlData has not cell range with name "
                    + name
                    + ". Exit.");
            System.exit(1);
        }
        return dataMap.get(name);
    }

    public Map<String, ArrayList<String>> getDataMap() {
        return dataMap;
    }
}
