/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filter;

import com.sun.hotspot.igv.data.ChangedListener;
import javax.swing.JComboBox;

/**
 *
 * @author Thomas Wuerthinger
 */
public interface FilterChainProvider {

    FilterChain getFilterChain();
    FilterChain getAllFiltersOrdered();

    FilterChain createNewCustomFilterChain();

    void setCustomFilterChain(FilterChain filterChain);

    void selectFilterChain(FilterChain filterChain);

    void setFilterChainSelectionChangedListener(ChangedListener<JComboBox<FilterChain>> listener);
}
