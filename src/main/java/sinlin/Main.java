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

/*
Other bugs:
2. "$1,  , 1, , " not works
*/

public class Main {
    public static String version = "sinlin v0.3.0";

    public static void main(String[] args) {
        boolean toOutStream = false;//use System.out if true and file otherwise
        boolean silent = false;//no times out
        long t = System.currentTimeMillis();
        BufferedReader bufferedReader;
        String line;
        StringFacadeIF stringFacadeIF;
        TagHandler tagHandler = new TagHandler();
        Exporter exporter = new Exporter();

        String[] d;

        String versionWithLicense = version + "\n" +
                "License GPLv3: GNU GPL version 3 <http://gnu.org/licenses/gpl.html>.\n" +
                "This is free software: you are free to change and redistribute it.\n" +
                "There is NO WARRANTY, to the extent permitted by law.\n\n" +
                "Written by Artur Stepankevich.";

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
                "-p\n" +
                "--print\n\tExport to System.out. Option -o ignored\n" +
                "\tTimes not prints.\n\n" +
                "-m number\n" +
                "\tExport only [number] of first exemplars for each tag if one has more.\n\n" +
                "-g\t" +
                "\n\tGenerate data sequence from string." +
                "\n\tSymbol \"$\" must be replaced by \"\\$\"." +
                "\n\tWith option -d can get data from ods files.\n\n" +
                "-r" +
                "\n\tPrint names of all cell ranges in ods file, defined with option -d.\n\n" +
                "-V" +
                "\n\tPrint version.\n\n" +
                "-h" +
                "\n\tPrint help.\n\n" +
                "-b" +
                "\n\tDebug. Exports also sinlin attributes (exist, onenode, etc).\n\n" +
                "BIGGEST KNOWN BUGS\n\n" +  //program has littler bugs
                "1.Error when get range with one cell from .ods files.\n" +
                "\nSee code at <https://github.com/step-artur87/sinlin> (Java, GPLv3),\n" +
                "wiki at <https://github.com/step-artur87/sinlin/wiki> \n" +
                "and binaries at <http://sourceforge.net/projects/sinlin/>.\n" +
                "Twitter <https://twitter.com/sinlinSVG>.";

        CommandLine commandLine;

        String prefix;

        Data data;

        //define options
        Options options = new Options();
        options.addOption("i", true, "input");
        options.addOption("d", true, "data");
        options.addOption("o", true, "output");
        options.addOption("V", false, "version");
        options.addOption("h", false, "help");
        options.addOption("g", true, "generate");
        options.addOption("r", false, "ranges");
        options.addOption("m", true, "limit");
        options.addOption("b", false, "debug");
        options.addOption("p", "print", false, "print");

        //there order of ifs is significant
        try {
            commandLine = (new DefaultParser()).parse(options, args);

            //if no argument, print help and exit
            if (commandLine.getOptions().length == 0) {
                System.out.println(help);
                System.exit(0);
            }

            //print version and exit
            if (commandLine.hasOption("V")) {
                System.out.println(versionWithLicense);
                System.exit(0);
            }

            //print help and exit
            if (commandLine.hasOption("h")) {
                System.out.println(help);
                System.exit(0);
            }

            //set output stream to System.out
            if (commandLine.hasOption("p")) {
                toOutStream = true;
                silent = true;
            }

            //if defined data file, load it
            if (commandLine.hasOption("d")) {
                if (!silent) System.out.println("Before data load time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                data = new OdfData(commandLine.getOptionValue("d"));
                Fn.setData(data);
                if (!silent) System.out.println("After data load time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                if (commandLine.hasOption("r")) {
                    ((OdfData) data).printRanges();
                    System.exit(0);
                }
            }

            //if generate mode, print needed and exit
            if (commandLine.hasOption("g")) {
                stringFacadeIF = StringFacadeBuilder.create(
                        commandLine.getOptionValue("g"));
                for (int i = 0; i < stringFacadeIF.getSize(); i++) {
                    //no concat all with "\n" because Windows EOL diffs
                    System.out.println(stringFacadeIF.getValue(null, i));
                }
                System.exit(0);
            }

            //if normal mode, do needed
            if (commandLine.hasOption("i")) {

                //if debug, set it
                if (commandLine.hasOption("b")) {
                    Tag.setDebug(true);
                }

                //parse input file
                if (!silent) System.out.println("Before parsing time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                SaxParsing.parse(tagHandler,
                        commandLine.getOptionValue("i"));
                if (!silent) System.out.println("After parsing time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");

                //define prefix
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

                //if defined max export exemplar count , set it
                if (commandLine.hasOption("m")) {
                    exporter.setLimit(Integer.parseInt(commandLine.getOptionValue("m")));
                }

                //export and exit
                if (!silent) System.out.println("Before export time = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                Tag r = tagHandler.getRootTag();
                exporter.writeAllXml(r, prefix, toOutStream);
                if (!silent) System.out.println("After export time  = "
                        + ((System.currentTimeMillis() - t)) / 1000. + " s");
                System.exit(0);
            }

            //if undefined argument combination print help (end of main)
            System.out.println(help);

            //print only exception name, not stackTrace (if these exception)
        } catch (ParseException | NumberFormatException e) {
            Util.handleException(e);
        }
    }
}