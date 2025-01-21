/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5097250
 * @summary Finalize methods on enums must be compile time error
 * @author Peter von der Ah\u00e9
 */

public enum OkFinal {
    A {
        protected void finalize(Void nil) {
            System.out.println("FISK");
        }
    };

    public static void main(String[] args) {
        System.out.println("HEST");
    }
}
