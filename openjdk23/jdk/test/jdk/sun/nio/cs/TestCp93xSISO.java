/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
 * @bug 4429358
 * @summary Remove illegal SI/SO char to byte mappings
 * @modules jdk.charsets
 */

public class TestCp93xSISO {
    public static void main ( String[] args) throws Exception {
        int exceptionCount = 0;
        String[] encName = {"Cp930", "Cp933", "Cp935", "Cp937", "Cp939" };

        String s = "\u000e\u000f" ;

        for ( int i=0; i < encName.length; i++) { // Test 2 converters.
            try {
                byte[] encoded = s.getBytes(encName[i]);
                for (int j=0 ; j<encoded.length; j++) {
                    if (encoded[j] != (byte)0x6f) // Expect to map to 0x6f
                        exceptionCount++;
                }
            } catch (Throwable t) {
                    System.err.println("error with converter " + encName[i]);
                    exceptionCount++;
            }
        }

        if (exceptionCount > 0)
           throw new Exception ("bug4429369: Cp93x SI/SO Ch->Byte mappings incorrect");
    }
}
