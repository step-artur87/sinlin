package sinlin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 3/3/16
 * Time: 5:04 PM
 */
public class CommandLineTest {
    ArrayList<String[]> args;

    Exporter exporter;

    TagHandler tagHandler;

    @Before
    public void setUp() throws Exception {
        tagHandler = new TagHandler();
        exporter = new Exporter();
        args = new ArrayList<>();
        args.add(new String[]{"-g $1;1;10"});
        args.add(new String[]{"-i in.svg"});
        //fixme args.add(new String[]{"-p", "-d /home/art/Documents/sinlin/sinlin/src/test/resources/fn_test.ods"});
        //args.add(new String[]{"-V"});
        //args.add(new String[]{"-h"});
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void pathTest() throws Exception {
        args.forEach((t) -> {
            System.out.println("\t" + Arrays.toString(t));
            Main.main(t);
        });
    }
}
