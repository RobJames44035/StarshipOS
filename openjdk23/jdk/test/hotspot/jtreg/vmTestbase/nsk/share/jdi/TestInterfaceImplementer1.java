/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */
package nsk.share.jdi;

interface Interface1 {

}

interface Interface2 {

}

interface Interface3 {

}

interface Interface4 {

}

interface Interface5 {

}

/*
 * Empty class, used to be sure that there are no instances of this class in target VM
 * Used in tests where interface implementer is needed
 */
public class TestInterfaceImplementer1 implements Interface1, Interface2, Interface3, Interface4, Interface5 {

}
