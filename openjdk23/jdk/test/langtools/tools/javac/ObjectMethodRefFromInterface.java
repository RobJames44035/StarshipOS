/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4283170
 * @summary Verify that access to member of java.lang.Object via qualification of
 * object of an interface type is handled successfully.
 * @author maddox
 */

public class ObjectMethodRefFromInterface {

    public interface JunkInterface {
        public void blahBlahBlah(int j);
    }

    public static class JunkClass implements JunkInterface {
        public void blahBlahBlah(int j) {
            return;
        }
    }

    public void doReference(JunkInterface o) {
        Class c = o.getClass();
    }

    public static void main(String args[]) {
        JunkInterface o = new JunkClass();
        ObjectMethodRefFromInterface cnt = new ObjectMethodRefFromInterface();
        cnt.doReference(o);
    }
}
