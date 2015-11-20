package sinlin;

import sinlin.string_facade.StringFacadeIF;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final int WARNING_QANTITY = 100;
    private static final int y = 121;
    public static int limit = -1;
    private XMLStreamWriter last = null;

    /**
     * Writes all exemplar of this tag
     * and all his childTags recursively to files.
     * One file to exemplar
     * (exemplar is text, representing tag
     * with attribute - fn, replaced by fn value)
     *
     * @param tag - written tag
     */
    public void writeAllXml(Tag tag, String prefix, boolean toOutStream) {
        int n = tag.sizes();
        if (limit >= 0 && limit < n) {
            n = limit;
        }
        if (n > WARNING_QANTITY) {
            System.out.print("Write " + n + " files? y/n");
            try {
                int yn = System.in.read();
                if (yn != y) {
                    System.out.println("Writing canceled. Exit.");
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (n == 0) {
            if (toOutStream) {
                writeExemplarXML(
                        getXmlStreamWriter(null),
                        tag, 0, 0);
            } else {
                writeExemplarXML(
                        getXmlStreamWriter(
                                prefix
                                        + ".svg"),
                        tag, 0, 0);
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
                    writeExemplarXML(
                            getXmlStreamWriter(
                                    prefix
                                            + " "
                                            + Integer.toString(i) + ".svg"),
                            tag, i, 0);
                }
            }
        }
    }

    private void writeAllXml(XMLStreamWriter xmlStreamWriter,
                             Tag tag, int tabs) {
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
        Map<String, StringFacadeIF> map;
        int m;
        if (tag.isExemplarWrited(n)) {
            map = tag.getStringFacadeMap();
            m = tag.getTagArrayDeque().size();
            if (limit >= 0 && limit < m) {
                m = limit;
            }
            try {
                tabs(xmlStreamWriter, tabs);
                xmlStreamWriter.writeStartElement(
                        tag.getName());
                for (String s : tag.getAttributeNames()) {
                    catchingWriteAttribute(xmlStreamWriter, s,
                            map.get(s).getValue(null, n));
                }

                if (!tag.isEmpty()) {
                    xmlStreamWriter.writeCharacters("\n");
                }

                tag.getTagArrayDeque().stream().limit(m).forEach(
                        t -> writeAllXml(xmlStreamWriter, t, tabs + 1));

                if (tag.getText() != null) {
                    xmlStreamWriter.writeCharacters(
                            tag.getText().getValue(null, n));
                }
                if (!tag.isEmpty()) {
                    tabs(xmlStreamWriter, tabs);
                }
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeCharacters("\n");
                System.out.println();//because bug
            } catch (XMLStreamException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static void tabs(XMLStreamWriter xmlStreamWriter, int tabs) throws XMLStreamException {
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
            e.printStackTrace();
            System.exit(1);
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
            e.printStackTrace();
            System.exit(1);
        }
        return last;
    }
}
