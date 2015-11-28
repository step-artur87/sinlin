package sinlin.data.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
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
 * Date: 8/26/15
 * Time: 8:17 PM
 */
public class DataSourceHandler extends DefaultHandler {
    public static final String DATA = "xmlData";
    public static final String COLUMN = "column";
    public static final String ROW = "row";
    public static final String ID = "id";

    private Map<String, ArrayList<String>> dataMap;
    private ArrayList<String> currentColumn;
    private String string;

    /**
     * Creates new sinlin.data.xml.DataSourceHandler.
     *
     * @param xmlData is receiver for parsed xmlData
     */
    public DataSourceHandler(XmlData xmlData) {
        dataMap = xmlData.getDataMap();
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes)
            throws SAXException {
        switch (qName) {
            case DATA:
                break;
            case COLUMN:
                if (dataMap.put(attributes.getValue(ID),
                        new ArrayList<>()) != null) {
                    System.out.println("Warning. Column \""
                            + attributes.getValue(ID) + "\" was replaced");
                }
                currentColumn = dataMap.get(attributes.getValue(ID));
                break;
            case ROW:
                break;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName)
            throws SAXException {
        switch (qName) {
            case DATA:
                break;
            case COLUMN:
                break;
            case ROW:
                currentColumn.add(string);
                break;
        }
    }

    @Override
    public void characters(char[] ch,
                           int start,
                           int length)
            throws SAXException {
        string = new String(ch, start, length);
    }
}
