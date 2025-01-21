/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Verifies security checks are performed before existence checks
 *          in pre-defined body processors APIs
 * @run testng/othervm SecurityBeforeFile
 */

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static java.lang.System.out;
import static java.nio.file.StandardOpenOption.*;
import static org.testng.Assert.*;

public class SecurityBeforeFile {

    @Test
    public void BodyPublishersOfFile() {
        Path p = Paths.get("doesNotExist.txt");
        if (Files.exists(p))
            throw new AssertionError("Unexpected " + p);

        try {
            BodyPublishers.ofFile(p);
            fail("UNEXPECTED, file " + p.toString() + " exists?");
        } catch (FileNotFoundException fnfe) {
            out.println("caught expected file not found exception: " + fnfe);
        }
    }

    @DataProvider(name = "handlerOpenOptions")
    public Object[][] handlerOpenOptions() {
        return new Object[][] {
                { new OpenOption[] {               } },
                { new OpenOption[] { CREATE        } },
                { new OpenOption[] { CREATE, WRITE } },
        };
    }

    @Test(dataProvider = "handlerOpenOptions")
    public void BodyHandlersOfFileDownload(OpenOption[] openOptions) {
        Path p = Paths.get("doesNotExistDir");
        if (Files.exists(p))
            throw new AssertionError("Unexpected " + p);

        try {
            BodyHandlers.ofFileDownload(p, openOptions);
            fail("UNEXPECTED, file " + p.toString() + " exists?");
        } catch (IllegalArgumentException iae) {
            out.println("caught expected illegal argument exception: " + iae);
        }
    }
}
