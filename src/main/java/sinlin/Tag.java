package sinlin;

import org.xml.sax.Attributes;
import sinlin.string_facade.StringFacadeBuilder;
import sinlin.string_facade.StringFacadeIF;

import java.util.*;

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
                if (qName.equals(EXIST) || qName.equals(EXIST0) || qName.equals(ONENODE)) {//todo arrayList (m.b. slow)
                    attributesMapFnExt.put(qName, StringFacadeBuilder.create(value));
                } else {
                    attributesMapFn.put(qName, StringFacadeBuilder.create(value));
                    attributeNames.add(qName);
                }
                conMap.put(qName, StringFacadeBuilder.create(value));
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
    public int attrSizes() {//todo new map putAll, putAll
        int m = -1;
        if (conMap.isEmpty() && text == null) {
            //tag without attributes and text exported one time
            return 1;
        }
        OptionalInt min = conMap.values().stream()
                .mapToInt(StringFacadeIF::getSize)
                .filter((i) -> i > 1)
                .min();
        OptionalInt max = conMap.values().stream()
                .mapToInt(StringFacadeIF::getSize)
                .max();

        if (!min.isPresent()) {
            //tag without attributes and text exported one time
            m = 1;
        } else if (min.equals(max)) {
            m = min.getAsInt();
        }

        if (text != null && text.getSize() > 1) {//todo simplify
            if (m > 1 && text.getSize() != m) {
                m = -1;
            } else {
                m = text.getSize();
            }
        }
        if (m < 0) {
            printErrorInPath();
            System.out.println("In tag <"
                    + this.getNameWithAttr() + "> attributes have not same sizes:");//todo (n)
            conMap.forEach((s, sf) -> System.out.println("\t" + s + " = " + sf.getName() + " (" + sf.getSize() + ")"));
            if (this.text != null) {
                System.out.println(text.getName() + " (" + text.getSize() + ")");
            }
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
    }

    public int nodeSizes() {//todo new map putAll, putAll
        int m = -1;
        if (nodes.isEmpty()) {
            return attrSizes();
        }
        OptionalInt min = nodes.stream()
                .mapToInt(Tag::attrSizes)
                .filter((i) -> i > 1)
                .min();
        OptionalInt max = nodes.stream()
                .mapToInt(Tag::attrSizes)
                .max();

        if (!min.isPresent()) {
            //tag without nodes exported one time
            m = 1;
        } else if (min.equals(max) && (this.attrSizes() == min.getAsInt())) {
            m = min.getAsInt();
        }

        if (m < 0) {
            printErrorInPath();
            System.out.println("In tag <"
                    + this.getNameWithAttr()
                    + "> (" + this.attrSizes() + ") nodes have not same sizes:");
            nodes.forEach((t) -> System.out.println("\t<" + t.getNameWithAttr()
                    + "> (" + t.attrSizes() + ")"));
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
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

    public String getName() {//todo replage with getNameWithAttr
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
//                System.out.println("In tag " + this.getName() //todo or not todo
//                        + " " + EXIST0 + " value \"" + value
//                        + "\" was not parsed and then not 0.");
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
        if (!s.contains("\n")) {//fixme qwerty \n qwerty
            text = StringFacadeBuilder.create(s);
        }
    }

    public StringFacadeIF getText() {
        return text;
    }

    public boolean isEmpty() {
        return nodes.isEmpty() && text == null;
    }

    public String getNameWithAttr() {
        StringBuilder s = new StringBuilder(this.getName());
        for (String s1 : attributeNamesExt) {
            s.append(" ").append(s1).append("=\"").append(conMap.get(s1).getName()).append("\"");
        }
        return s.toString();
    }
}
