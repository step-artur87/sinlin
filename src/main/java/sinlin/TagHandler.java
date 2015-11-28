package sinlin;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayDeque;

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

public class TagHandler extends DefaultHandler {
    private ArrayDeque<Tag> arrayDeque = new ArrayDeque<>();
    private ArrayDeque<Tag> rootTagExoskeleton;

    public TagHandler(ArrayDeque<Tag> rootTagExoskeleton) {
        this.rootTagExoskeleton = rootTagExoskeleton;
        this.rootTagExoskeleton.clear();
        arrayDeque.clear();
    }

    @Override
    public void startDocument() {
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes)
            throws SAXException {
        Tag tag = new Tag(qName, attributes);

        //addChild
        if (!arrayDeque.isEmpty()) {
            arrayDeque.peek().addChildTag(tag);
        }

        //push
        arrayDeque.push(tag);

    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName)
            throws SAXException {
        if (arrayDeque.size() == 1) {//todo if root again
            rootTagExoskeleton.push(arrayDeque.peek());
        }

        //poll
        arrayDeque.poll();
    }

    @Override
    public void characters(char[] ch,
                           int start,
                           int length)
            throws SAXException {
        arrayDeque.peek().setText(new String(ch, start, length));
        //fixme <g>20*[x]</g>
        //todo -/n only here
    }
}
