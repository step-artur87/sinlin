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
    private int limit
            = -1;//number from option -m
    private XMLStreamWriter last
            = null;//when getXmlStreamWriter(), for last (if exist)
    //writeEndDocument() an close()

    private static ArrayList<Tag> path
            = new ArrayList<>();//all parents of exported now tag

    public void setLimit(int limit) {
        if (limit > 0) {
            this.limit = limit;
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
        int n = tag.attrSizes();
        String extension = tag.getName();

        //if tag size > m, _trim it_
        if (limit >= 0 && limit < n) {
            n = limit;
        }

        //if tag size > WARNING_QUANTITY, ask user.
        //This tag is root tag of export, so quantity of files equals to its size
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

        //if must be one file, export it
        if (n == 0) {
            //if to out, print
            if (toOutStream) {
                writeExemplarXML(
                        getXmlStreamWriter(null),
                        tag, 0, 0);
            } else {
                //if to file, export
                current = getXmlStreamWriter(
                        prefix + "." + extension);
                writeExemplarXML(
                        current,
                        tag, 0, 0);
                writeVersion(current);
            }
            //if files many,
        } else {
            //if to out,print all
            if (toOutStream) {
                for (int i = 0; i < n; i++) {
                    writeExemplarXML(
                            getXmlStreamWriter(null),
                            tag, i, 0);
                }
            } else {
                //if to files, export all appended with __[№]
                for (int i = 0; i < n; i++) {
                    //todo not number, but generated attributes
                    current = getXmlStreamWriter(
                            prefix
                                    + "__"
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
        int n = tag.attrSizes();
        //if tag size > m, _trim it_

        if (limit >= 0 && limit < n) {
            n = limit;
        }

        //export i-th exemplar
        for (int i = 0; i < n; i++) {
            writeExemplarXML(xmlStreamWriter, tag, i, tabs);
        }
        if (n == 0) {//fixme tag.attrSizes() must be >=1
            writeExemplarXML(xmlStreamWriter, tag, 0, tabs);
        }
    }

    private void writeExemplarXML(
            XMLStreamWriter xmlStreamWriter,
            Tag tag,
            int n, int tabs) {
        String string;
        Map<String, StringFacadeIF> map;

        //add this tag to end of path
        path.add(tag);

        //filtr exemplar by EXIST, EXIST0, ONENODE (and m.b. something else in future)
        if (tag.isExemplarWritten(n)) {
            map = tag.getStringFacadeMap();

            try {
                //"\t" * tabs
                tabs(xmlStreamWriter, tabs);

                //<tag>
                xmlStreamWriter.writeStartElement(
                        tag.getName());

                //replace attr with "..." by same without dots d and vice versa
                //and export it
                for (String s : tag.getAttributeNames()) {
                    if (s.endsWith("...")) {
                        string = s.replace("...", "");
                        if (tag.getAttributeNames().contains(string)) {
                            xmlStreamWriter.writeAttribute(string,
                                    map.get(s).getValue(null, n));
                        } else {
                            System.out.println("\"" + s + "\" has not pair. Exit");
                            System.exit(1);
                        }
                    } else {
                        string = s.concat("...");
                        if (tag.getAttributeNames().contains(string)) {
                            xmlStreamWriter.writeAttribute(string,
                                    map.get(s).getValue(null, n));
                        } else {
                            xmlStreamWriter.writeAttribute(s,
                                    map.get(s).getValue(null, n));
                        }
                    }
                }

                //if not <tag></tag>, \n
                if (!tag.isEmpty()) {
                    xmlStreamWriter.writeCharacters("\n");
                }

                //export nodes with "\t" * (tabs + 1)
                if (!tag.oneNode()) {
                    tag.getNodes().stream().forEach(
                            t -> writeAllXml(xmlStreamWriter, t, tabs + 1));
                } else {
                    if (tag.nodeSizes() > -1) {//fixme, because tag.nodeSizes() can not return -1
                        tag.getNodes().stream().forEach(
                                t -> writeExemplarXML(
                                        xmlStreamWriter,
                                        t, n, tabs + 1));
                    }
                }

                //text
                if (tag.getText() != null) {
                    tabs(xmlStreamWriter, tabs + 1);
                    xmlStreamWriter.writeCharacters(
                            tag.getText().getValue(null, n));
                    xmlStreamWriter.writeCharacters("\n");
                }

                //if not <tag></tag>, "\t" * (tabs)
                if (!tag.isEmpty()) {
                    tabs(xmlStreamWriter, tabs);
                }
                //</tag>
                xmlStreamWriter.writeEndElement();

                //\n
                xmlStreamWriter.writeCharacters("\n");

                //print only exception name, not stackTrace (if catch this exception)
            } catch (XMLStreamException e) {
                System.out.println(e.toString());
                System.exit(1);
            }
        }

        //remove this tag from path
        getPath().remove(getPath().size() - 1);
    }

    private static void tabs(
            XMLStreamWriter xmlStreamWriter,
            int tabs) throws XMLStreamException {
        for (int i = 0; i < tabs; i++) {
            //write one tab
            xmlStreamWriter.writeCharacters("\t");
        }
    }

    private void writeVersion(XMLStreamWriter current) {
        try {
            //write "sinlin vx.x.x" in end of docement
            current.writeComment(Main.version);
        } catch (XMLStreamException e) {
            System.out.println(e.toString());
        }
    }

    private XMLStreamWriter getXmlStreamWriter(
            String fileName) {
        try {

            // if for some XMLStreamWriter was run writeStartDocument,
            //run writeEndDocument and close it
            if (last != null) {
                last.writeEndDocument();
                last.close();
            }

            //if..., create XMLStreamWriter to out
            if (fileName == null) {
                last = XMLOutputFactory.newInstance()
                        .createXMLStreamWriter(System.out);
                //else, create XMLStreamWriter to file
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

    public static ArrayList<Tag> getPath() {
        return path;
    }
}
