/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 *
 * @bug 4408997 8130181
 * Used by GetInstance test.
 */
import java.security.Provider;

public class StubProvider extends Provider {
    public StubProvider() {
        super( "StubProvider", "1.1", "No Info");
        put("CertPathBuilder.PKIX", "StubProviderImpl");
    }
}
