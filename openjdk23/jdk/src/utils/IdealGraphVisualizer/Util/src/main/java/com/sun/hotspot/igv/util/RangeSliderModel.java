/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */
package com.sun.hotspot.igv.util;

import com.sun.hotspot.igv.data.ChangedEvent;
import com.sun.hotspot.igv.data.ChangedEventProvider;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Thomas Wuerthinger
 */
public class RangeSliderModel implements ChangedEventProvider<RangeSliderModel> {

    // Warning: Update setData method if fields are added
    private final ChangedEvent<RangeSliderModel> changedEvent;
    private final ChangedEvent<RangeSliderModel> colorChangedEvent;
    private List<String> positions;
    private int firstPosition;
    private int secondPosition;
    private List<Color> colors;

    public RangeSliderModel(RangeSliderModel model) {
        firstPosition = model.getFirstPosition();
        secondPosition = model.getSecondPosition();
        changedEvent = new ChangedEvent<>(this);
        colorChangedEvent = new ChangedEvent<>(this);
        positions = new ArrayList<>(model.getPositions());
        colors = new ArrayList<>(model.getColors());
    }

    public RangeSliderModel() {
        firstPosition = -1;
        secondPosition = -1;
        changedEvent = new ChangedEvent<>(this);
        colorChangedEvent = new ChangedEvent<>(this);
        positions = new ArrayList<>();
        colors = new ArrayList<>();
    }

    protected void setPositions(List<String> positions) {
        this.positions = positions;
        colors = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            colors.add(Color.black);
        }
        firstPosition = Math.min(firstPosition, positions.size() - 1);
        secondPosition = Math.min(secondPosition, positions.size() - 1);
        changedEvent.fire();
        colorChangedEvent.fire();
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
        colorChangedEvent.fire();
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<String> getPositions() {
        return Collections.unmodifiableList(positions);
    }

    public int getFirstPosition() {
        return firstPosition;
    }

    public int getSecondPosition() {
        return secondPosition;
    }

    public void setPositions(int fp, int sp) {
        assert fp >= 0 && fp < positions.size();
        assert sp >= 0 && sp < positions.size();
        if (firstPosition != fp || secondPosition != sp) {
            firstPosition = fp;
            secondPosition = sp;
            ensureOrder();
            changedEvent.fire();
        }
    }

    private void ensureOrder() {
        if (secondPosition < firstPosition) {
            int tmp = secondPosition;
            secondPosition = firstPosition;
            firstPosition = tmp;
        }
    }

    public ChangedEvent<RangeSliderModel> getColorChangedEvent() {
        return colorChangedEvent;
    }

    @Override
    public ChangedEvent<RangeSliderModel> getChangedEvent() {
        return changedEvent;
    }
}
