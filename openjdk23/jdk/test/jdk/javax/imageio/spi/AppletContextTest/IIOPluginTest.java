/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import javax.imageio.ImageIO;
import java.util.ServiceConfigurationError;

public class IIOPluginTest {

    public static String[] dummyformatNames = {"test_5076692", "TEST_5076692"};
    public static String[] dummymimeType = {"image/test_5076692"};

    public static void main(String[] args) {
        try {
            String formatNames[] = ImageIO.getReaderFormatNames();
            String readerMimeTypes[] = ImageIO.getReaderMIMETypes();
            if (!isPresent(dummyformatNames, formatNames) ||
                !isPresent(dummymimeType, readerMimeTypes)) {
                throw new RuntimeException("No test plugin available!");
            }
        } catch (ServiceConfigurationError sce) {
            System.out.println("Expected ServiceConfigurationError \n" + sce);
            System.out.println("Test Passed");
        }
    }

    public static boolean isPresent(String[] t, String[] r) {
        for (int i=0; i<t.length; i++) {
            if (!isPresent(t[i], r)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPresent(String s, String[] a) {
        for (int i=0; i<a.length; i++) {
            System.out.println(a[i] + " ");
            if (s.equals(a[i])) {
                return true;
            }
        }
        return false;
    }
}
