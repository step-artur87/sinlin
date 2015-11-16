package sinlin.string_facade;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 11/5/15
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Expr extends StringFacadeAbstract
        implements StringFacadeIF {
    private ExpressionBuilder expressionBuilder;
    private Map<String, StringFacadeIF> map = new HashMap<>();

    public Expr(String string) {
        name = string;
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, "[]", true);
        while (stringTokenizer.hasMoreElements()) {
            tokens.add(stringTokenizer.nextToken());
        }
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("]")) {
                map.put(tokens.get(i - 1),
                        StringFacadeBuilder.createVCEF(
                                tokens.get(i - 1)));
            }
        }
        expressionBuilder = new ExpressionBuilder(
                string.replace("[", "").replace("]", "")
        ).variables(map.keySet());
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
                .max();

        if (!min.isPresent()) {
            m = 1;
        } else if (min.equals(max)) {
            m = min.getAsInt();
        }

        if (m < 0) {
            System.out.println("In expression \""
                    + this.name + "\" fns have not same sizes:");
            map.forEach((s, sf) -> {
                System.out.println(s + "(" + sf.getSize() + ")");
            });
            System.out.println("Exit.");
            System.exit(1);
        }

        return m;
    }

    @Override
    public String getValue(Map<String, String> keyMap, int n) {
        Expression expression;
        StringFacadeIF stringFacadeIF = null;
        String value = null;
        try {
            expression = expressionBuilder.build();
            for (String s : map.keySet()) {
                stringFacadeIF = map.get(s);
                value = stringFacadeIF.getValue(keyMap, n);
                expression.setVariable(s, Double.parseDouble(
                        value));
            }
            return Double.toString(expression.evaluate());
        } catch (NumberFormatException e) {
//            if (stringFacadeIF != null && value!= null) {
//fixme                System.out.println("In \"" + stringFacadeIF.getName()
//                        + "\" value \"" + value + "\" is not number.");
//            }
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            if (stringFacadeIF != null && value != null) {
                System.out.println("In \"" + stringFacadeIF.getName()
                        + "\" value \"" + value + "\" is not number.");
            }
            System.out.println("In expression \"" + this.getName()
                    + "\" diapasons must be written within []");
            e.printStackTrace();
        }

        return null;
    }
}
