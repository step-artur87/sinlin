package sinlin;

import org.xml.sax.Attributes;
import sinlin.string_facade.StringFacadeBuilder;
import sinlin.string_facade.StringFacadeIF;

import java.util.ArrayDeque;
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
 * Date: 8/26/15
 * Time: 6:55 PM
 */
public class Tag {
    private static boolean debug = false;//write map..Ext
    private static final String EXIST = "exist";
    private static final String EXIST0 = "exist0";
    private static final String ONENODE = "onenode";
    private String name;
    private String sText = "";
    private StringFacadeIF text = null;//text of xml tag
    private Map<String, StringFacadeIF> attributesMapFn
            = new HashMap<>();//<attribute_name, attribute_value>
    private Map<String, StringFacadeIF> attributesMapFnExt
            = new HashMap<>();//<hidden: attribute_name, attribute_value>
    private Map<String, StringFacadeIF> conMap
            = new HashMap<>();//attributesMapFn + attributesMapFnExt
    private ArrayDeque<Tag> nodes = new ArrayDeque<>();//<nodes>
    private ArrayList<String> attributeNames
            = new ArrayList<>();//used for save attr order
    private ArrayList<String> attributeNamesExt
            = new ArrayList<>();//shown and hidden attributes

    public Tag(String name, Attributes attributes) {
        this.name = name;
        String qName;
        String value;
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                value = attributes.getValue(i);
                qName = attributes.getQName(i);
                StringFacadeIF stringFacadeIF = StringFacadeBuilder.create(value);
                if (qName.equals(EXIST) || qName.equals(EXIST0) || qName.equals(ONENODE)) {
                    attributesMapFnExt.put(qName, stringFacadeIF);
                } else {
                    attributesMapFn.put(qName, stringFacadeIF);
                    attributeNames.add(qName);
                }
                conMap.put(qName, stringFacadeIF);
                attributeNamesExt.add(qName);
            }
        }
    }

    public static void setDebug(boolean debug) {
        Tag.debug = debug;
    }

    /**
     * Returns size of attributesMapFn values if ones are same,
     * and exit program otherwise.
     *
     * @return size of attributesMapFn values.
     */
    public int attrSizes() {
        int n = 1;
        int t = 1;

        if (!conMap.isEmpty()) {
            n = Util.mapElementsSizes(conMap.values().stream()
                    .mapToInt(StringFacadeIF::getSize));
        }

        if (getText() != null) {
            t = getText().getSize();
        }

        n = Util.oneOrEqual(n, t);

        if (n < 0) {
            printErrorInPath();
            System.out.println("In tag <"
                    + this.getNameWithAttr() + "> attributes have not same sizes:");
            conMap.forEach((s, sf) -> System.out.println("\t" + s + " = " + sf.getName() + " (" + sf.getSize() + ")"));
            if (this.getText() != null) {
                System.out.println(getText().getName() + " (" + getText().getSize() + ")");
            }
            System.out.println("Exit.");
            System.exit(1);
        }

        return n;
    }

    public int nodeSizes() {
        int a = attrSizes();
        int n;

        if (nodes.isEmpty()) {
            return a;
        }

        n = Util.mapElementsSizes(nodes.stream()
                .mapToInt(Tag::attrSizes));

        n = Util.oneOrEqual(n, a);

        if (n < 0) {
            printErrorInPath();
            System.out.println("In tag <"
                    + this.getNameWithAttr()
                    + "> (" + this.attrSizes() + ") nodes have not same sizes:");
            nodes.forEach((t) -> System.out.println("\t<" + t.getNameWithAttr()
                    + "> (" + t.attrSizes() + ")"));
            System.out.println("Exit.");
            System.exit(1);
        }

        return n;
    }

    private void printErrorInPath() {
        System.out.print("Error in path: ");
        Exporter.getPath().forEach((s) -> {
            System.out.print("<" + s.getNameWithAttr() + ">");
        });
        System.out.println();
    }

    public void addNodeTag(Tag tag) {
        nodes.add(tag);
    }

    public String getName() {
        return name;
    }

    public Map<String, StringFacadeIF> getStringFacadeMap() {
        if (debug) {
            return conMap;
        }
        return attributesMapFn;
    }

    public ArrayDeque<Tag> getNodes() {
        return nodes;
    }

    /**
     * Return false if this tag has attribute EXIST
     * and its value equals ZERO or true otherwise.
     *
     * @param n number of exemplar
     * @return true if this exemplar of tag has to be written
     */
    public boolean isExemplarWritten(int n) {
        if (attributesMapFnExt.containsKey(EXIST0)) {
            double d = 1;
            String value = attributesMapFnExt.get(EXIST0)
                    .getValue(null, n);
            try {
                d = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                d = 1;
            }
            return !(d == 0);
        }

        if (attributesMapFnExt.containsKey(EXIST)) {
            return !(attributesMapFnExt.get(EXIST)
                    .getValue(null, n).length() == 0);
        }

        return true;
    }

    public boolean oneNode() {
        return attributesMapFnExt.containsKey(ONENODE);
    }

    public ArrayList<String> getAttributeNames() {
        if (debug) {
            return attributeNamesExt;
        }
        return attributeNames;
    }

    public void setText(String s) {
        if (s.trim().length() > 0) {
            sText = sText.concat(s);
        }
    }

    public boolean isEmpty() {
        return nodes.isEmpty() && getText() == null;
    }

    public String getNameWithAttr() {
        StringBuilder s = new StringBuilder(this.getName());
        for (String s1 : attributeNamesExt) {
            s.append(" ").append(s1).append("=\"").append(conMap.get(s1).getName()).append("\"");
        }
        return s.toString();
    }

    public String getNameWithAttr(int n) {
        StringBuilder s = new StringBuilder(this.getName());
        for (String s1 : attributeNamesExt) {
            s.append(" ").append(s1).append("=\"").append(conMap.get(s1).getValue(null, n)).append("\"");
        }
        return s.toString();
    }

    public StringFacadeIF getText() {
        if (text == null && sText.length() > 0) {
            text = StringFacadeBuilder.create(sText);
        }

        return text;
    }
}
