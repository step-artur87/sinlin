package sinlin.string_facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/28/16
 * Time: 9:46 PM
 */
public class StringFacadeBuilderTest {
    Map<String, String> testMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        testMap.put("; ; 10; ", "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        testMap.put("5; ; 10; ", "[5, 6, 7, 8, 9]");
        testMap.put("; 2; 10; ", "[0, 2, 4, 6, 8]");
        testMap.put("5; 2; 10; ", "[5, 7, 9]");
        testMap.put("; ; ; 4", "[0, 1, 2, 3]");
        testMap.put("5; ; ; 4", "[5, 6, 7, 8]");
        testMap.put("; 2; ; 4", "[0, 2, 4, 6]");
        testMap.put("5; 2; ; 4", "[5, 7, 9, 11]");
        testMap.put("; ; 10; 4", "[0, 2.5, 5, 7.5]");
        testMap.put("5; ; 10; 4", "[5, 6.25, 7.5, 8.75]");
        testMap.put("; 2; 10; 4", "[2, 4, 6, 8]");
        testMap.put("5; 2; 10; 4", "[5, 7, 9]");
        testMap.put("5; 0; ; 4", "[5, 5, 5, 5]");

    }

    @After
    public void tearDown() throws Exception {
        testMap = null;
    }

    @Test
    public void testCreateVCEF() throws Exception {
        testMap.forEach((k, v) -> {
            Assert.assertEquals("\"" + k + "\"\t\"" + v + "\"", v,
                    allCycle(StringFacadeBuilder.createVCEF(k)));
        });
    }

    private String allCycle(StringFacadeIF stringFacadeIF) {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < stringFacadeIF.getSize(); i++) {
            s.append(stringFacadeIF.getValue(null, i));
            if (i < stringFacadeIF.getSize() - 1) {
                s.append(", ");
            }
        }
        s.append("]");
        return s.toString();
    }
}
