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

    /**
     * Only needed when fn has links to sinlin.data
     *
     * @param data
     */
    public static void setData(Data data) {
        Fn.data = data;
    }

    public int getSize() {
        if (Fn.data == null) {
            System.out.println("Fn \"" + name + "\" has no sinlin.data. " +
                    "Use StringFacade.setData(sinlin.data.Data sinlin.data) to set it. Exit");
            System.exit(1);
        }

        return Fn.data.getRow(result).size();
    }

    public String getValue(Map<String, String> keyMap, int n) {
        if (Fn.data == null) {
            System.out.println("Fn \"" + name + "\" has no sinlin.data. " +
                    "Use StringFacade.setData(sinlin.data.Data sinlin.data) to set it. Exit");
            System.exit(1);
        }

        if (result == null) {
            System.out.println("This fn is empty. Exit");
            System.exit(1);
        }

        return Fn.data.getRow(result).get(n);
    }

}