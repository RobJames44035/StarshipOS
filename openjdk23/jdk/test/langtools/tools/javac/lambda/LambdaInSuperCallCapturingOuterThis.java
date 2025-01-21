/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8184989
 * @summary Incorrect class file created when passing lambda in inner class constructor and outer is subclass
 * @run main LambdaInSuperCallCapturingOuterThis
 */

class A8184989 {
    public boolean test() {
        return true;
    }

    class AA {
        public AA(Condition8184989<AA> condition) {
        }
    }
}

interface Condition8184989<T> {
    boolean check(T t);
}

public class LambdaInSuperCallCapturingOuterThis extends A8184989 {

    public LambdaInSuperCallCapturingOuterThis() {
        new BA();
    }
    public class BA extends AA {
        public BA() {
            super(o -> test());
        }
    }
    public static void main(String[] args) {
        LambdaInSuperCallCapturingOuterThis b = new LambdaInSuperCallCapturingOuterThis();
    }
}
