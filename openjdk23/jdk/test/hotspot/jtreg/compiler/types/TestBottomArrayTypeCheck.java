/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8308583
 * @summary SIGSEGV in GraphKit::gen_checkcast
 * @run main/othervm -Xbatch -XX:-TieredCompilation -XX:CompileCommand=compileonly,TestBottomArrayTypeCheck::test TestBottomArrayTypeCheck
 */

public class TestBottomArrayTypeCheck {

    interface WordBase {
    }

    interface RelocatedPointer {
    }

    static final class Word implements WordBase {
    }

    static Object[] staticObjectFields;

    static byte[] staticPrimitiveFields;


    interface SnippetReflection {
        Object forObject(Object o);
    }

    static class BaseSnippetReflection implements SnippetReflection {
        public Object forObject(Object o) {
            return null;
        }

    }
    static class SubSnippetReflection extends BaseSnippetReflection {
        public Object forObject(Object object) {
            if (object instanceof WordBase word && !(object instanceof RelocatedPointer)) {
                return word;
            }
            return super.forObject(object);
        }
    }

    public static void main(String[] args) {
        t1();
        for (int i = 0; i < 10; i++) {
            t2();
        }
    }

    static void t1() {
        Word w = new Word();
        SnippetReflection base = new BaseSnippetReflection();
        SnippetReflection sub = new SubSnippetReflection();
        for (int i = 0; i < 10000; i++) {
            invoke(base, w);
            invoke(sub, w);
        }
    }

    static void t2() {
        SnippetReflection base = new BaseSnippetReflection();
        SnippetReflection sub = new SubSnippetReflection();
        for (int i = 0; i < 10000; i++) {
            test(base, i % 2 == 0);
            test(sub, i % 2 == 0);
            test(sub, i % 2 == 0);
        }
    }

    static Object test(SnippetReflection s, boolean b) {
        return s.forObject(b ? staticObjectFields : staticPrimitiveFields);
    }


    static Object invoke(SnippetReflection s, Object o) {
        return s.forObject(o);
    }
}
