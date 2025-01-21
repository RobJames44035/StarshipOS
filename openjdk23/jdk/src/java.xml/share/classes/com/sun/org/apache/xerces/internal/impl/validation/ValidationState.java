/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.impl.validation;

import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of ValidationContext inteface. Used to establish an
 * environment for simple type validation.
 *
 * @xerces.internal
 *
 * @author Elena Litani, IBM
 * @LastModified: Oct 2017
 */
public class ValidationState implements ValidationContext {

    //
    // private data
    //
    private boolean fExtraChecking              = true;
    private boolean fFacetChecking              = true;
    private boolean fNormalize                  = true;
    private boolean fNamespaces                 = true;

    private EntityState fEntityState            = null;
    private NamespaceContext fNamespaceContext  = null;
    private SymbolTable fSymbolTable            = null;
    private Locale fLocale                      = null;

    private HashSet<String> fIds;
    private List<String> fIdRefList;

    //
    // public methods
    //
    public void setExtraChecking(boolean newValue) {
        fExtraChecking = newValue;
    }

    public void setFacetChecking(boolean newValue) {
        fFacetChecking = newValue;
    }

    public void setNormalizationRequired (boolean newValue) {
          fNormalize = newValue;
    }

    public void setUsingNamespaces (boolean newValue) {
          fNamespaces = newValue;
    }

    public void setEntityState(EntityState state) {
        fEntityState = state;
    }

    public void setNamespaceSupport(NamespaceContext namespace) {
        fNamespaceContext = namespace;
    }

    public void setSymbolTable(SymbolTable sTable) {
        fSymbolTable = sTable;
    }

    /**
     * return null if all IDREF values have a corresponding ID value;
     * otherwise return an iterator for all the IDREF values without
     * a matching ID value.
     */
    public Iterator<String> checkIDRefID () {
        HashSet<String> missingIDs = null;
        if (fIdRefList != null) {
            String key;
            for (int i = 0; i < fIdRefList.size(); i++) {
                key = fIdRefList.get(i);
                if (fIds == null || !fIds.contains(key)) {
                    if (missingIDs == null) {
                        missingIDs = new HashSet<>();
                    }
                    missingIDs.add(key);
                }
            }
        }
        return (missingIDs != null) ? missingIDs.iterator() : null;
    }

    public void reset () {
        fExtraChecking = true;
        fFacetChecking = true;
        fNamespaces = true;
        fIds = null;
        fIdRefList = null;
        fEntityState = null;
        fNamespaceContext = null;
        fSymbolTable = null;
    }

    /**
     * The same validation state can be used to validate more than one (schema)
     * validation roots. Entity/Namespace/Symbol are shared, but each validation
     * root needs its own id/idref tables. So we need this method to reset only
     * the two tables.
     */
    public void resetIDTables() {
        fIds = null;
        fIdRefList = null;
    }

    //
    // implementation of ValidationContext methods
    //

    // whether to do extra id/idref/entity checking
    public boolean needExtraChecking() {
        return fExtraChecking;
    }

    // whether to validate against facets
    public boolean needFacetChecking() {
        return fFacetChecking;
    }

    public boolean needToNormalize (){
        return fNormalize;
    }

    public boolean useNamespaces() {
        return fNamespaces;
    }

    // entity
    public boolean isEntityDeclared (String name) {
        if (fEntityState !=null) {
            return fEntityState.isEntityDeclared(getSymbol(name));
        }
        return false;
    }
    public boolean isEntityUnparsed (String name) {
        if (fEntityState !=null) {
            return fEntityState.isEntityUnparsed(getSymbol(name));
        }
        return false;
    }

    // id
    public boolean isIdDeclared(String name) {
        return fIds != null && fIds.contains(name);
    }
    public void addId(String name) {
        if (fIds == null) fIds = new HashSet<>();
        fIds.add(name);
    }

    // idref
    public void addIdRef(String name) {
        if (fIdRefList == null) fIdRefList = new ArrayList<>();
        fIdRefList.add(name);
    }
    // get symbols

    public String getSymbol (String symbol) {
        if (fSymbolTable != null)
            return fSymbolTable.addSymbol(symbol);
        // if there is no symbol table, we return java-internalized string,
        // because symbol table strings are also java-internalzied.
        // this guarantees that the returned string from this method can be
        // compared by reference with other symbol table string. -SG
        return symbol.intern();
    }
    // qname, notation
    public String getURI(String prefix) {
        if (fNamespaceContext !=null) {
            return fNamespaceContext.getURI(prefix);
        }
        return null;
    }

    // Locale

    public void setLocale(Locale locale) {
        fLocale = locale;
    }

    public Locale getLocale() {
        return fLocale;
    }
}
