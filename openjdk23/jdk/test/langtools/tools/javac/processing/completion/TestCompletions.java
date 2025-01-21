/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6341177
 * @summary Some simple tests of the methods in Completions
 * @author Joseph D. Darcy
 * @modules java.compiler
 *          jdk.compiler
 */
import javax.annotation.processing.Completion;
import static javax.annotation.processing.Completions.*;

public class TestCompletions {
    public static void main(String... argv) {
        String value = "value";
        String message = "message";

        Completion c = of(value, message);
        if (!value.equals(c.getValue()) ||
            !message.equals(c.getMessage()))
            throw new RuntimeException("Bad full completion" + c);

        c = of(value);
        if (!value.equals(c.getValue()) ||
            !"".equals(c.getMessage()))
            throw new RuntimeException("Bad value completion" + c);
    }
}
