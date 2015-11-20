package sinlin;

import org.apache.commons.cli.*;
import sinlin.data.Data;
import sinlin.data.odt.OdfData;
import sinlin.string_facade.Fn;

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
 * Date: 8/25/15
 * Time: 12:45 AM
 */
public class Main {
    public static String version = "sinlin 0.1.0";

    public static void main(String[] args) {
        String versionWithLicense = version + "\n" +
                "License GPLv3: GNU GPL versionWithLicense 3 <http://gnu.org/licenses/gpl.html>.\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extent permitted by law.\n\n" +
                "Written by Artur Stepankevich.";
/*
                Copyright (C) 2015
                Written by , see <http://git.  /tree/AUTHORS>.
*/
        CommandLine commandLine;

        long t = System.currentTimeMillis();

        String prefix = "out";

        Data data;
        ArrayDeque<Tag> rootTagKostyl = new ArrayDeque<>();
        Options options = new Options();
        options.addOption("s", true, "source");
        options.addOption("d", true, "data");
        options.addOption("p", true, "prefix");
//        options.addOption("c", false, "compile");
//        options.addOption("x", false, "copy source");
//        options.addOption("o", false, "copy data");
        options.addOption("V", false, "version");
//        options.addOption("v", false, "verbose");
//        options.addOption("f", false, "fact data");
//        options.addOption("b", false, "debug");
        options.addOption("m", false, "fast and uncompleted");

        try {
            commandLine = (new DefaultParser()).parse(options, args);

            if (commandLine.hasOption("V")) {
                System.out.println(versionWithLicense);
                System.exit(0);
            }

            if (!commandLine.hasOption("s")) {
                System.out.println("what the file?");
                System.exit(0);
            }

            if (commandLine.hasOption("m")) {
                Exporter.limit = 20;
            }

            for (Option o : commandLine.getOptions()) {
                switch (o.getOpt()) {
                    case "s":
                        System.out.println("Parse time before = "
                                + (System.currentTimeMillis() - t));
                        SaxParsing.parse(new TagHandler(rootTagKostyl),
                                o.getValue());
                        System.out.println("Parse time after = "
                                + (System.currentTimeMillis() - t));
                        break;
                    case "d":
                        System.out.println("Data time before = "
                                + (System.currentTimeMillis() - t));
                        data = new OdfData(o.getValue());
                        Fn.setData(data);
                        System.out.println("Data time after = "
                                + (System.currentTimeMillis() - t));
                        break;
                    case "p":
                        prefix = o.getValue();
                        break;
                    case "c"://for next versions
                        break;
                    case "x"://for next versions
                        break;
                    case "o"://for next versions
                        break;
                    case "v"://for next versions
                        break;
                    case "f"://for next versions
                        break;
                    case "b"://for next versions
                        break;
                }
            }

            if (!commandLine.hasOption("p")) {//todo other symbols
                prefix = commandLine.getOptionValue("s") + "__"
                        + (commandLine.hasOption("d")
                        ? (commandLine.getOptionValue("d") + "__")
                        : "")
                        + "out";
            }

            if (commandLine.getOptionValue("s").equals(prefix + ".svg")) {
                prefix = prefix.concat("_out");
            }

            System.out.println("Export time before = "
                    + (System.currentTimeMillis() - t));
            Exporter exporter = new Exporter();
            Tag r = rootTagKostyl.getFirst();
            exporter.writeAllXml(r, prefix, false);
            System.out.println("Export time after = "
                    + (System.currentTimeMillis() - t));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}