package sinlin.string_facade;

import sinlin.Util;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 10/4/15
 * Time: 7:41 PM
 */
public class StringFacadeBuilder {
    public static StringFacadeIF create(String string) {
        string = Util.replaceAll(string);
        if (SplitString.test(string)) {
            return SplitString.create(string);
        } else {
            return new FlatString(string);
        }
    }

    public static StringFacadeIF createVCEF(String string) {
        if (CycleString.test(string)) {
            return new CycleString(string);
        }
        if (VarString.test(string)) {
            return new VarString(string);
        }
        if (Expr.test(string)) {
            return new Expr(string);
        }
        return new Fn(string);
    }
}
