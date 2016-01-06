package sinlin;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import sinlin.data.Data;
import sinlin.data.odt.OdfData;
import sinlin.string_facade.Fn;
import sinlin.string_facade.StringFacadeBuilder;
import sinlin.string_facade.StringFacadeIF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static String version = "sinlin v0.1.1";

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        BufferedReader bufferedReader;
        String line;
        StringFacadeIF stringFacadeIF;
        String[] d;

        String versionWithLicense = version + "\n" +
                "License GPLv3: GNU GPL version 3 <http://gnu.org/licenses/gpl.html>.\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extent permitted by law.\n\n" +
                "Written by Artur Stepankevich.";
/*
                Copyright (C) 2015
                Written by , see <http://git.  /tree/AUTHORS>.
*/

        String help = "SYNOPSIS\n\n" +
                "java -jar sinlin.jar [options].\n\n" +
                "OPTIONS\n" +
                "\n" +
                "-i file\n\tPath to input file (svg or xml).\n\n" +
                "-d file\n\tPath to data file (ods).\n\n" +
                "-o file\n\tPath and begin of name of output file." +
                "\n\t\"_out\" will add to end of file" +
                "\n\tOutput file extension will the same, that root tag in source file." +
                "\n\tIf this option is absent, then input file path " +
                "\n\tand data filename (if present) and _out is used.\n\n" +
                "-m number\n" +
                "\tExport only number of first exemplars for each tag if one has more.\n\n" +
                "-g\t" +
                "\n\tGenerate data sequence from string." +
                "\n\tWith option -d can get data from ods files.\n\n" +
                "-V" +
                "\n\tPrint version.\n\n" +
                "-h" +
                "\n\tPrint help.\n\n" +
                "KNOWN BUGS\n\n" +
                "1.From .ods files all data is taken only from first sheet.\n" +
                "\nSee code at <https://github.com/step-artur87/sinlin> (Java, GPLv3),\n" +
                "wiki at <https://github.com/step-artur87/sinlin/wiki> \n" +
                "and binaries at <http://sourceforge.net/projects/sinlin/>.\n" +
                "Twitter <https://twitter.com/sinlinSVG>.";

        CommandLine commandLine;

        String prefix;

        Data data;
        ArrayDeque<Tag> rootTagExoskeleton = new ArrayDeque<>();
        Options options = new Options();
        options.addOption("i", true, "input");
        options.addOption("d", true, "data");
        options.addOption("o", true, "output");
        options.addOption("V", false, "version");
        options.addOption("h", false, "help");
        options.addOption("g", false, "generate");
        options.addOption("m", true, "limit");

        //there order of ifs is significant
        try {
            commandLine = (new DefaultParser()).parse(options, args);

            if (commandLine.getOptions().length == 0) {
                System.out.println(help);
                System.exit(0);
            }

            if (commandLine.hasOption("V")) {
                System.out.println(versionWithLicense);
                System.exit(0);
            }

            if (commandLine.hasOption("h")) {
                System.out.println(help);
                System.exit(0);
            }

            if (commandLine.hasOption("d")) {
                System.out.println("Before data received time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                data = new OdfData(commandLine.getOptionValue("d"));
                Fn.setData(data);
                System.out.println("After data received time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
            }

            if (commandLine.hasOption("g")) {
                try {
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(System.in));

                    System.out.println("Write string (blank string for exit):");
                    line = bufferedReader.readLine();
                    while (line.length() > 0) {
                        stringFacadeIF = StringFacadeBuilder.create(line);
                        System.out.println(stringFacadeIF.getSize() + " element(s):");
                        System.out.println("[");
                        for (int i = 0; i < stringFacadeIF.getSize(); i++) {
                            System.out.println(stringFacadeIF.getValue(null, i));
                        }
                        System.out.println("]");
                        line = bufferedReader.readLine();
                    }
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
                System.exit(0);
            }

            if (commandLine.hasOption("i")) {
                System.out.println("Before parsing time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                SaxParsing.parse(new TagHandler(rootTagExoskeleton),
                        commandLine.getOptionValue("i"));
                System.out.println("After parsing time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");

                if (commandLine.hasOption("o")) {
                    //!!if no _out added and no filename (only path) - not works
                    prefix = commandLine.getOptionValue("o") + "_out";
                } else {
                    if (commandLine.hasOption("d")) {
                        d = commandLine.getOptionValue("d").split("/");
                        prefix = commandLine.getOptionValue("i") + "__"
                                + d[d.length - 1] + "__"
                                + "out";
                    } else {
                        prefix = commandLine.getOptionValue("i") + "__"
                                + "out";
                    }
                }

                if (commandLine.hasOption("m")) {//todo change -o if nas -m
                    Exporter.setLimit(Integer.parseInt(commandLine.getOptionValue("m")));
                }

                System.out.println("Before export time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                Exporter exporter = new Exporter();
                Tag r = rootTagExoskeleton.getFirst();
                exporter.writeAllXml(r, prefix, false);
                System.out.println("After export time  = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                System.exit(0);
            }

            System.out.println(help);

        } catch (ParseException | NumberFormatException e) {
            System.out.println(e.toString());
        }
    }
}