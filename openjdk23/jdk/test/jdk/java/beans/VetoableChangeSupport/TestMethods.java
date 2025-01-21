/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 5004188
 * @summary Tests sequence of methods
 * @author Sergey Malenkov
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

enum Fire {
    PropertyChangeEvent,
    PropertyObject,
    PropertyInteger,
    PropertyBoolean,
}

public class TestMethods extends VetoableChangeSupport implements VetoableChangeListener {
    private static final String NAME = "property";

    public static void main(String[] args) throws PropertyVetoException {
        Object source = new Object();
        new TestMethods(source).fireVetoableChange(new PropertyChangeEvent(source, NAME, null, null));
        new TestMethods(source).fireVetoableChange(NAME, null, null);
        new TestMethods(source).fireVetoableChange(NAME, 0, 1);
        new TestMethods(source).fireVetoableChange(NAME, true, false);
    }

    private Fire state;

    public TestMethods(Object source) {
        super(source);
        addVetoableChangeListener(this);
    }

    public void vetoableChange(PropertyChangeEvent event) {
        if (this.state != Fire.PropertyChangeEvent)
            throw new Error("Illegal state: " + this.state);
    }

    @Override
    public void fireVetoableChange(String property, Object oldValue, Object newValue) throws PropertyVetoException {
        if ((this.state != null) && (this.state != Fire.PropertyBoolean) && (this.state != Fire.PropertyInteger))
            throw new Error("Illegal state: " + this.state);

        this.state = Fire.PropertyObject;
        super.fireVetoableChange(property, oldValue, newValue);
    }

    @Override
    public void fireVetoableChange(String property, int oldValue, int newValue) throws PropertyVetoException {
        if (this.state != null)
            throw new Error("Illegal state: " + this.state);

        this.state = Fire.PropertyInteger;
        super.fireVetoableChange(property, oldValue, newValue);
    }

    @Override
    public void fireVetoableChange(String property, boolean oldValue, boolean newValue) throws PropertyVetoException {
        if (this.state != null)
            throw new Error("Illegal state: " + this.state);

        this.state = Fire.PropertyBoolean;
        super.fireVetoableChange(property, oldValue, newValue);
    }

    @Override
    public void fireVetoableChange(PropertyChangeEvent event) throws PropertyVetoException {
        if ((this.state != null) && (this.state != Fire.PropertyObject))
            throw new Error("Illegal state: " + this.state);

        this.state = Fire.PropertyChangeEvent;
        super.fireVetoableChange(event);
    }
}
