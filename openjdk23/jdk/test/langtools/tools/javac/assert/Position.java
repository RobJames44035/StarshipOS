/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */


public class Position {
    static public void main(String[] args) {
        Position.class.getClassLoader().setClassAssertionStatus("U", true);
        new U().main();
    }
}


class U {
    void main() {
        try {
            assert false; // line 20
        } catch (Throwable t) {
            if (t.getStackTrace()[0].getLineNumber() == 20) {
                return; // passed
            }
        }
        throw new Error("failed 4469737");
    }
}
