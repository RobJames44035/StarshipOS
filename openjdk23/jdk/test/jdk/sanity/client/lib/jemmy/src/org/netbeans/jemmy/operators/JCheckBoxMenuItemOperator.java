/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.operators;

import java.awt.Component;

import javax.swing.JCheckBoxMenuItem;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.JemmyProperties;

/**
 *
 * <BR><BR>Timeouts used: <BR>
 * JMenuItemOperator.PushMenuTimeout - time between button pressing and
 * releasing<BR>
 * ComponentOperator.WaitComponentTimeout - time to wait button displayed <BR>
 * ComponentOperator.WaitComponentEnabledTimeout - time to wait button enabled
 * <BR>.
 *
 * @see org.netbeans.jemmy.Timeouts
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 *
 */
public class JCheckBoxMenuItemOperator extends JMenuItemOperator {

    /**
     * Constructor.
     *
     * @param item a component.
     */
    public JCheckBoxMenuItemOperator(JCheckBoxMenuItem item) {
        super(item);
        setTimeouts(JemmyProperties.getProperties().getTimeouts());
        setOutput(JemmyProperties.getProperties().getOutput());
    }

    /**
     * Constructs a JCheckBoxMenuItemOperator object.
     *
     * @param cont a container
     * @param chooser a component chooser specifying searching criteria.
     * @param index an index between appropriate ones.
     */
    public JCheckBoxMenuItemOperator(ContainerOperator<?> cont, ComponentChooser chooser, int index) {
        this((JCheckBoxMenuItem) cont.
                waitSubComponent(new JCheckBoxMenuItemFinder(chooser),
                        index));
        copyEnvironment(cont);
    }

    /**
     * Constructs a JCheckBoxMenuItemOperator object.
     *
     * @param cont a container
     * @param chooser a component chooser specifying searching criteria.
     */
    public JCheckBoxMenuItemOperator(ContainerOperator<?> cont, ComponentChooser chooser) {
        this(cont, chooser, 0);
    }

    /**
     * Constructor. Waits component in container first. Uses cont's timeout and
     * output for waiting and to init operator.
     *
     * @param cont a container
     * @param text Button text.
     * @param index Ordinal component index.
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     */
    public JCheckBoxMenuItemOperator(ContainerOperator<?> cont, String text, int index) {
        this((JCheckBoxMenuItem) waitComponent(cont,
                new JCheckBoxMenuItemByLabelFinder(text,
                        cont.getComparator()),
                index));
        setTimeouts(cont.getTimeouts());
        setOutput(cont.getOutput());
    }

    /**
     * Constructor. Waits component in container first. Uses cont's timeout and
     * output for waiting and to init operator.
     *
     * @param cont a container
     * @param text Button text.
     * @see ComponentOperator#isCaptionEqual(String, String, boolean, boolean)
     */
    public JCheckBoxMenuItemOperator(ContainerOperator<?> cont, String text) {
        this(cont, text, 0);
    }

    /**
     * Constructor. Waits component in container first. Uses cont's timeout and
     * output for waiting and to init operator.
     *
     * @param cont a container
     * @param index Ordinal component index.
     */
    public JCheckBoxMenuItemOperator(ContainerOperator<?> cont, int index) {
        this((JCheckBoxMenuItem) waitComponent(cont,
                new JCheckBoxMenuItemFinder(),
                index));
        copyEnvironment(cont);
    }

    /**
     * Constructor. Waits component in container first. Uses cont's timeout and
     * output for waiting and to init operator.
     *
     * @param cont a container
     */
    public JCheckBoxMenuItemOperator(ContainerOperator<?> cont) {
        this(cont, 0);
    }

    ////////////////////////////////////////////////////////
    //Mapping                                             //
    /**
     * Maps {@code JCheckBoxMenuItem.getState()} through queue
     */
    public boolean getState() {
        return (runMapping(new MapBooleanAction("getState") {
            @Override
            public boolean map() {
                return ((JCheckBoxMenuItem) getSource()).getState();
            }
        }));
    }

    /**
     * Maps {@code JCheckBoxMenuItem.setState(boolean)} through queue
     */
    public void setState(final boolean b) {
        runMapping(new MapVoidAction("setState") {
            @Override
            public void map() {
                ((JCheckBoxMenuItem) getSource()).setState(b);
            }
        });
    }

    //End of mapping                                      //
    ////////////////////////////////////////////////////////
    /**
     * Allows to find component by text.
     */
    public static class JCheckBoxMenuItemByLabelFinder implements ComponentChooser {

        String label;
        StringComparator comparator;

        /**
         * Constructs JCheckBoxMenuItemByLabelFinder.
         *
         * @param lb a text pattern
         * @param comparator specifies string comparision algorithm.
         */
        public JCheckBoxMenuItemByLabelFinder(String lb, StringComparator comparator) {
            label = lb;
            this.comparator = comparator;
        }

        /**
         * Constructs JCheckBoxMenuItemByLabelFinder.
         *
         * @param lb a text pattern
         */
        public JCheckBoxMenuItemByLabelFinder(String lb) {
            this(lb, Operator.getDefaultStringComparator());
        }

        @Override
        public boolean checkComponent(Component comp) {
            if (comp instanceof JCheckBoxMenuItem) {
                if (((JCheckBoxMenuItem) comp).getText() != null) {
                    return (comparator.equals(((JCheckBoxMenuItem) comp).getText(),
                            label));
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "JCheckBoxMenuItem with text \"" + label + "\"";
        }

        @Override
        public String toString() {
            return "JCheckBoxMenuItemByLabelFinder{" + "label=" + label + ", comparator=" + comparator + '}';
        }
    }

    /**
     * Checks component type.
     */
    public static class JCheckBoxMenuItemFinder extends Finder {

        /**
         * Constructs JCheckBoxMenuItemFinder.
         *
         * @param sf other searching criteria.
         */
        public JCheckBoxMenuItemFinder(ComponentChooser sf) {
            super(JCheckBoxMenuItem.class, sf);
        }

        /**
         * Constructs JCheckBoxMenuItemFinder.
         */
        public JCheckBoxMenuItemFinder() {
            super(JCheckBoxMenuItem.class);
        }
    }
}
