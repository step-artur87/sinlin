package sinlin.string_facade;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/5/15
 * Time: 10:43 AM
 */
public class Expr extends StringFacadeAbstract
        implements StringFacadeIF {
    private Map<String, StringFacadeIF> map = new HashMap<>();
    private Expression expression;

    public Expr(String string) {
        int n = 0;
        name = string;
        String exprString = "";
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, "[]", true);
        while (stringTokenizer.hasMoreElements()) {
            tokens.add(stringTokenizer.nextToken());
        }
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.size() > i + 1
                    && tokens.get(i + 1).equals("]")) {
                exprString = exprString.concat("var" + n);
                map.put("var" + n,
                        StringFacadeBuilder.createVCEF(
                                tokens.get(i)));
                n++;
            } else if (!tokens.get(i).equals("[") && !tokens.get(i).equals("]")) {
                exprString = exprString.concat(tokens.get(i));
            }
        }
        try {
            expression = (new ExpressionBuilder(exprString)
                    .variables(map.keySet())).build();
        } catch (IllegalArgumentException e) {
            handleException(e);
        }
    }

    @Override
    public int getSize() {
        //todo new map putAll, putAll
        int m = -1;
        if (map.isEmpty()) {
            return 0;
        }
        OptionalInt min = map.values().stream()
                .mapToInt(StringFacadeIF::getSize)
                .filter((i) -> i > 1)
                .min();
        OptionalInt max = map.values().stream()
                .mapToInt(StringFacadeIF::getSize)
                .filter((i) -> i > 1)
                .max();

        if (!min.isPresent() || !max.isPresent()) {
            m = 1;
        } else if (min.equals(max)) {
            m = min.getAsInt();
        }

        if (m < 0) {
            System.out.println("In " + this.getClass().getSimpleName() + " \""
                    + this.name + "\" attributes have not same sizes:");
            map.forEach((s, sf) -> System.out.println(sf.getClass().getSimpleName() + " " + sf.getName() + " (" + sf.getSize() + ")"));
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        StringFacadeIF stringFacadeIF = null;
        String value = null;
        try {
            for (String s : map.keySet()) {
                stringFacadeIF = map.get(s);
                value = stringFacadeIF.getValue(keyMap, n);
                expression.setVariable(s, Double.parseDouble(
                        value));
            }
            value = Double.toString(expression.evaluate());
            if (value.endsWith(".0")) {
                value = value.replace(".0", "");
            }
            return value;
        } catch (NumberFormatException e) {
            handleException(e);
        } catch (IllegalArgumentException e) {
            handleException(e);
        }

        return null;
    }
}
