package sinlin.string_facade;

import sinlin.data.Data;

import java.util.Map;

public class Fn extends StringFacadeAbstract
        implements StringFacadeIF {
    private static Data data = null;
    private String result = null;

    public Fn(String string) {
        name = string;
        string = StringFacadeBuilder.replaceAll(string);
        result = string;
    }

    public static void setData(Data data) {
        Fn.data = data;
    }

    //can return 0
    public int getSize() {
        if (Fn.data == null) {
            System.out.println("Data of diapason \"" + name + "\" is unavailable. \n" +
                    "Use command-line argument \"-d\" to define .ods file " +
                    "with needed data.\n" +
                    "Exit");
            System.exit(1);
        }

        return Fn.data.getRow(result).size();//todo rows * columns
    }

    public String getValue(Map<String, String> keyMap, int n) {
        if (Fn.data == null) {
            System.out.println("Data of diapason \"" + name + "\" is unavailable. \n" +
                    "Use command-line argument \"-d\" to define .ods file " +
                    "with needed data.\n" +
                    "Exit");
            System.exit(1);
        }

        if (result == null) {
            System.out.println("This fn is empty. Exit");
            System.exit(1);
        }

        return Fn.data.getRow(result).get(n);
    }

}