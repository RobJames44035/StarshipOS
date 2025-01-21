/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8170251
 * @summary     Add javax.tools.Tool.name()
 * @modules jdk.compiler/com.sun.tools.javac.api
 */

import java.util.Optional;
import java.util.ServiceLoader;
import javax.tools.Tool;
import com.sun.tools.javac.api.JavacTool;

public class TestName {
    public static void main(String... args) throws Exception {
        new TestName().run();
    }

    public void run() throws Exception {
        Optional<Tool> opt = findFirst("javac");
        if (!opt.isPresent()) {
            throw new Exception("tool not found");
        }
        if (!(opt.get() instanceof JavacTool)) {
            throw new Exception("unexpected tool found");
        }
    }

    Optional<Tool> findFirst(String name) {
        getClass().getModule().addUses(Tool.class);
        for (Tool p : ServiceLoader.load(Tool.class,
                ClassLoader.getSystemClassLoader())) {
            if (p.name().equals(name)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
