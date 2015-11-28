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
    private static final boolean DEBUG = false;//write map..Ext
    private static final String ZERO = "0";
    private static final String EXIST = "exist";
    private String name;
    private StringFacadeIF text = null;
    private Map<String, StringFacadeIF> attributesMapFn = new HashMap<>();
    private Map<String, StringFacadeIF> attributesMapFnExt = new HashMap<>();
    private Map<String, StringFacadeIF> conMap = new HashMap<>();
    private ArrayDeque<Tag> tagArrayDeque = new ArrayDeque<>();
    private ArrayList<String> attributeNames = new ArrayList<>();

    public Tag(String name, Attributes attributes) {
        this.name = name;
        String qName;
        String value;
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                value = attributes.getValue(i);
                qName = attributes.getQName(i);
                if (qName.equals(EXIST)) {//todo arrayList
                    attributesMapFnExt.put(qName, StringFacadeBuilder.create(value));
                } else {
                    attributesMapFn.put(qName, StringFacadeBuilder.create(value));
                    attributeNames.add(qName);
                }
                conMap.put(qName, StringFacadeBuilder.create(value));
            }
        }
    }

    /**
     * Returns size of attributesMapFn values if ones are same,
     * and exit program otherwise.
     *
     * @return size of attributesMapFn values.
     */
    public int sizes() {//todo new map putAll, putAll
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

    public void addChildTag(Tag tag) {
        tagArrayDeque.add(tag);
    }

    public String getName() {
        return name;
    }

    public Map<String, StringFacadeIF> getStringFacadeMap() {
        if (DEBUG) {
            Map<String, StringFacadeIF> map = new HashMap<>();
            map.putAll(attributesMapFn);
            map.putAll(attributesMapFnExt);
            return map;
        }
        return attributesMapFn;
    }

    public ArrayDeque<Tag> getTagArrayDeque() {
        return tagArrayDeque;
    }

    /**
     * Return false if this tag has attribute EXIST
     * and its value equals ZERO or true otherwise.
     *
     * @param n
     * @return
     */
    public boolean isExemplarWrited(int n) {
        return !(attributesMapFnExt.containsKey(EXIST)
                && ZERO.equals(attributesMapFnExt.get(EXIST)
                .getValue(null, n)));
    }

    public ArrayList<String> getAttributeNames() {
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
        return tagArrayDeque.isEmpty() && text == null;
    }
}
