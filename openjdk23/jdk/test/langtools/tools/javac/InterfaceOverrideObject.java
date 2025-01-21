/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @summary check proper behavior when an interface overrides a method from Object
 * @author gafter
 *
 * @compile InterfaceOverrideObject.java
 */

interface GSSNameSpi {
    public boolean equals(GSSNameSpi name);
}
class InterfaceOverrideObject {
    private GSSNameSpi mechElement = null;
    {
        mechElement.equals(mechElement);
    }
}
