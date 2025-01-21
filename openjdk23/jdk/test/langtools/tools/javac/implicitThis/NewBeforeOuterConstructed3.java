/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4704371 6313120
 * @summary compiler generates unverifiable code for implicit reference to uninit'd this
 */

public class NewBeforeOuterConstructed3 {
    class Two extends NewBeforeOuterConstructed3 {
        {
            System.out.println(NewBeforeOuterConstructed3.this);
        }
    }
    class Three extends Two {
        {
            new Two();
        }
    }
    public static void main(String[] args) {
        NewBeforeOuterConstructed3 o = new NewBeforeOuterConstructed3();
        System.out.println(o + " " + o.new Three());
    }
}
