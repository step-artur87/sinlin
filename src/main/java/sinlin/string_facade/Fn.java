package sinlin.string_facade;

import sinlin.Util;
import sinlin.data.Data;

import java.util.Map;

public class Fn extends StringFacadeAbstract
        implements StringFacadeIF {
    private static Data data = null;
    private String result = null;

    public Fn(String string) {
        name = string;
        result = string;
    }

    public static void setData(Data data) {
        Fn.data = data;
    }

    //can return 0
    public int getSize() {
        if (Fn.data == null) {
            Util.printErrorAndExit("Data of diapason \"" + name + "\" is unavailable. \n" +
                    "Use command-line argument \"-d\" to define .ods file " +
                    "with needed data.\n" +
                    "Exit");
        }

        return Fn.data.getRow(result).size();
    }

    public String getValue(Map<String, String> keyMap, int n) {
        if (Fn.data == null) {
            Util.printErrorAndExit("Data of diapason \"" + name + "\" is unavailable. \n" +
                    "Use command-line argument \"-d\" to define .ods file " +
                    "with needed data.\n" +
                    "Exit");
        }

        if (result == null) {
            Util.printErrorAndExit("This fn is empty. Exit");
        }

        return Fn.data.getRow(result).get(n);
    }

}