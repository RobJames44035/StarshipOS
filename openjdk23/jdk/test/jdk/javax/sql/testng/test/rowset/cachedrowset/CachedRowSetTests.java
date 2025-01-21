/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package test.rowset.cachedrowset;

import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

public class CachedRowSetTests extends CommonCachedRowSetTests {

    @Override
    protected CachedRowSet newInstance() throws SQLException {
        return rsf.createCachedRowSet();
    }

}
