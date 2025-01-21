/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filterwindow;

import com.sun.hotspot.igv.data.ChangedListener;
import com.sun.hotspot.igv.filter.FilterChain;
import com.sun.hotspot.igv.filter.FilterChainProvider;
import javax.swing.JComboBox;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas Wuerthinger
 */
@ServiceProvider(service=FilterChainProvider.class)
public class FilterChainProviderImplementation implements FilterChainProvider {

    @Override
    public FilterChain getFilterChain() {
        return FilterTopComponent.findInstance().getCurrentChain();
    }

    @Override
    public FilterChain getAllFiltersOrdered() {
        return FilterTopComponent.findInstance().getAllFiltersOrdered();
    }

    @Override
    public FilterChain createNewCustomFilterChain() {
        return FilterTopComponent.findInstance().createNewCustomFilterChain();
    }

    @Override
    public void selectFilterChain(FilterChain filterChain) {
        FilterTopComponent.findInstance().selectFilterChain(filterChain);
    }

    @Override
    public void setCustomFilterChain(FilterChain filterChain) {
        FilterTopComponent.findInstance().setCustomFilterChain(filterChain);
    }

    @Override
    public void setFilterChainSelectionChangedListener(ChangedListener<JComboBox<FilterChain>> listener) {
        FilterTopComponent.findInstance().setFilterChainSelectionChangedListener(listener);
    }
}
