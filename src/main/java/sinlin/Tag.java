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
    private static final String ZERO = "0";
    private static final String EXIST = "exist";
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
                if (qName.equals(EXIST) || qName.equals(ONENODE)) {//todo arrayList
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
            return 0;
        }
        OptionalInt min = conMap.values().stream()
                .mapToInt(StringFacadeIF::getSize)
                .filter((i) -> i > 1)
                .min();
        OptionalInt max = conMap.values().stream()
                .mapToInt(StringFacadeIF::getSize)
                .max();

        if (!min.isPresent()) {
            m = 0;
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
            System.out.println("In tag \""
                    + this.name + "\" attributes have not same sizes:");
            conMap.forEach((s, sf) -> System.out.println(s + " = " + sf.getName() + " (" + sf.getSize() + ")"));
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
            m = 0;
        } else if (min.equals(max) && (this.attrSizes() == min.getAsInt())) {
            m = min.getAsInt();
        }

        if (m < 0) {
            System.out.println("In tag \""
                    + this.name
                    + "\" (" + this.attrSizes() + ") nodes have not same sizes:");
            nodes.forEach((t) -> System.out.println(t.getName()
                    + " (" + t.attrSizes() + ")"));
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
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
        return !(attributesMapFnExt.containsKey(EXIST)
                && ZERO.equals(attributesMapFnExt.get(EXIST)
                .getValue(null, n)));
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
}
