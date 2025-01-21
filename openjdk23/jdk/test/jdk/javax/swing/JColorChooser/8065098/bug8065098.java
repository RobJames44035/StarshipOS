/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8065098
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary JColorChooser no longer supports drag and drop
 *     between two JVM instances
 * @run main/manual bug8065098
 */

public class bug8065098 {
    private static final String INSTRUCTIONS = """
            1. Compile the java test JColorChooserDnDTest.java:
                > <path-to-the-tested-jdk>/bin/javac JColorChooserDnDTest.java
            2. Run the first instance of the java test:
                > <path-to-the-tested-jdk>/bin/java JColorChooserDnDTest
            3. Select a color in the color chooser.
            4. Run the second instance of the java test:
                > <path-to-the-tested-jdk>/bin/java JColorChooserDnDTest
            5. Drag and drop the selected color from the first color chooser
                preview panel to the second color chooser preview panel
            6. If the color is dragged to the second color chooser, then the
                test passes. Otherwise, the test fails.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("bug8065098 Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .build()
                .awaitAndCheck();
    }
}
