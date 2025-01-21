/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class TestDetectHeadless {
    public static void main(String[] args) throws Exception {
        Class.forName("javax.swing.plaf.basic.BasicInternalFrameTitlePane");
    }
}
