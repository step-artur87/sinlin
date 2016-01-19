package sinlin;

import sinlin.string_facade.StringFacadeIF;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * Date: 10/7/15
 * Time: 6:34 PM
 */
public class Exporter {
    private static final int WARNING_QUANTITY
            = 100;//if export more files then it - print question
    private static final int yCharCode
            = 121;//charCode for "y"
    private static int limit
            = -1;//number from option -m
    private XMLStreamWriter last
            = null;//when getXmlStreamWriter(), for last (if exist)
    //writeEndDocument() an close()

    public static void setLimit(int limit) {
        if (limit > 0) {
            Exporter.limit = limit;
        } else {
            System.out.println("Limit (" + limit + ") must be > 0.");
        }
    }

    /**
     * Writes all exemplar of this tag
     * and all his nodes recursively to files.
     * One file to exemplar
     * (exemplar is text, representing tag
     * with attribute - fn, replaced by fn value)
     *
     * @param tag - written tag
     */
    public void writeAllXml(
            Tag tag,
            String prefix,
            boolean toOutStream) {
        XMLStreamWriter current;
        int n = tag.sizes();
        String extension = tag.getName();
        if (limit >= 0 && limit < n) {
            n = limit;
        }
        if (n > WARNING_QUANTITY) {
            System.out.println("Write " + n + " files? y/n");
            try {
                int yn = System.in.read();
                if (yn != yCharCode) {
                    System.out.println("Writing canceled. Exit.");
                    System.exit(0);
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        if (n == 0) {
            if (toOutStream) {
                writeExemplarXML(
                        getXmlStreamWriter(null),
                        tag, 0, 0);
            } else {
                current = getXmlStreamWriter(
                        prefix + "." + extension);
                writeExemplarXML(
                        current,
                        tag, 0, 0);
                writeVersion(current);
            }
        } else {
            if (toOutStream) {
                for (int i = 0; i < n; i++) {
                    writeExemplarXML(
                            getXmlStreamWriter(null),
                            tag, i, 0);
                }
            } else {
                for (int i = 0; i < n; i++) {
                    current = getXmlStreamWriter(
                            prefix
                                    + " "
                                    + Integer.toString(i) + "." + extension);
                    writeExemplarXML(
                            current,
                            tag, i, 0);
                    writeVersion(current);
                }
            }
        }
    }

    private void writeAllXml(
            XMLStreamWriter xmlStreamWriter,
            Tag tag,
            int tabs) {
        int n = tag.sizes();
        if (limit >= 0 && limit < n) {
            n = limit;
        }
        for (int i = 0; i < n; i++) {
            writeExemplarXML(xmlStreamWriter, tag, i, tabs);
        }
        if (n == 0) {
            writeExemplarXML(xmlStreamWriter, tag, 0, tabs);
        }
    }

    private void writeExemplarXML(
            XMLStreamWriter xmlStreamWriter,
            Tag tag,
            int n, int tabs) {
        ArrayList<Tag> alt = new ArrayList(tag.getTagArrayDeque());
        String string;
        Map<String, StringFacadeIF> map;
        if (tag.isExemplarWritten(n)) {
            map = tag.getStringFacadeMap();
            try {
                tabs(xmlStreamWriter, tabs);
                xmlStreamWriter.writeStartElement(
                        tag.getName());
                for (String s : tag.getAttributeNames()) {
                    if (s.endsWith("...")) {
                        string = s.replace("...", "");
                        if (tag.getAttributeNames().contains(string)) {
                            catchingWriteAttribute(xmlStreamWriter, string,
                                    map.get(s).getValue(null, n));
                        } else {
                            System.out.println("\"" + s + "\" has not pair. Exit");
                            System.exit(1);
                        }
                    } else {
                        string = s.concat("...");
                        if (tag.getAttributeNames().contains(string)) {
                            catchingWriteAttribute(xmlStreamWriter, string,
                                    map.get(s).getValue(null, n));
                        } else {
                            catchingWriteAttribute(xmlStreamWriter, s,
                                    map.get(s).getValue(null, n));
                        }
                    }
                }

                if (!tag.isEmpty()) {
                    xmlStreamWriter.writeCharacters("\n");
                }

                if (!tag.oneNode()) {
                    tag.getTagArrayDeque().stream().forEach(
                            t -> writeAllXml(xmlStreamWriter, t, tabs + 1));
                } else {
                    if (tag.getTagArrayDeque().stream().filter(
                            (t) -> t.sizes() != tag.sizes())
                            .count() == 0) {
                        tag.getTagArrayDeque().stream().forEach(
                                t -> writeExemplarXML(xmlStreamWriter, t, n, tabs + 1));
                    } else {
                        System.out.println("Tag \""
                                + tag.getName()
                                + "\" ("
                                + tag.sizes()
                                + ") has node(s) with different sizes:");
                        tag.getTagArrayDeque().forEach((t) -> {
                            System.out.println(t.getName()
                                    + " (" + t.sizes() + ")");
                        });
                        System.out.println("Exit");
                        System.exit(1);
                    }
                }

                if (tag.getText() != null) {
                    xmlStreamWriter.writeCharacters(
                            tag.getText().getValue(null, n));
                }
                if (!tag.isEmpty()) {
                    tabs(xmlStreamWriter, tabs);
                }
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeCharacters("\n");
            } catch (XMLStreamException e) {
                System.out.println(e.toString());
                System.exit(1);
            }
        }
    }

    private static void tabs(
            XMLStreamWriter xmlStreamWriter,
            int tabs) throws XMLStreamException {
        for (int i = 0; i < tabs; i++) {
            xmlStreamWriter.writeCharacters("\t");
        }
    }

    private void catchingWriteAttribute(
            XMLStreamWriter xmlStreamWriter,
            String k, String v) {
        try {
            xmlStreamWriter.writeAttribute(k, v);
        } catch (XMLStreamException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }

    private void writeVersion(XMLStreamWriter current) {
        try {
            current.writeComment(Main.version);
        } catch (XMLStreamException e) {
            System.out.println(e.toString());
        }
    }

    private XMLStreamWriter getXmlStreamWriter(
            String fileName) {
        try {
            if (last != null) {
                last.writeEndDocument();
                last.close();
            }

            if (fileName == null) {
                last = XMLOutputFactory.newInstance()
                        .createXMLStreamWriter(System.out);
            } else {
                last = XMLOutputFactory.newInstance()
                        .createXMLStreamWriter(
                                new FileOutputStream(
                                        fileName));
            }

            last.writeStartDocument();
        } catch (XMLStreamException | FileNotFoundException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
        return last;
    }
}
