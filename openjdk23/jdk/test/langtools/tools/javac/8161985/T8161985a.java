/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T8161985 {
    public static void meth() {
        T8161985 t = new T8161985();
        t.getClass();

    }
    public void getClass() {
        Fred1 f = new Fred1();
        System.out.println( "fred classname: " + f.getClassName());
    }


    abstract class Fred {
        public String getClassName() {
            return this.getClass().getSimpleName();
        }
    }

    class Fred1 extends Fred {
    }
}
