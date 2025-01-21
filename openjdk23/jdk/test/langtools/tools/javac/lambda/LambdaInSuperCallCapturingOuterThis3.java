/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8184989
 * @summary Incorrect class file created when passing lambda in inner class constructor and outer is subclass
 * @run main LambdaInSuperCallCapturingOuterThis3
 */

interface I8184989_3 {
    public default boolean test(){
        return true;
    }
}

class A8184989_3 implements I8184989_3 {
    class AA {
        public AA(Condition8184989_3<AA> condition) {
            if (condition.check(this) != true) {
                throw new AssertionError("Incorrect output");
            }
        }
    }
}

interface Condition8184989_3<T> {
    boolean check(T t);
}

public class LambdaInSuperCallCapturingOuterThis3 extends A8184989_3 {
    public boolean test() {return false;}
    public void b() {}

    class C extends A8184989_3 {
        public class BA extends AA {
            public BA() {
                super(o -> {b(); return test();});
            }
        }
    }
    public static void main(String[] args) {
        new LambdaInSuperCallCapturingOuterThis3().new C().new BA();
    }
}
