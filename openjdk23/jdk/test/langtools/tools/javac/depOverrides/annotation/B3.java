/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

// combinations of methods defined in an interface
// and overridden in subtypes

// class overrides deprecated mthods as shown, but should not give warnings by
// virtue of being deprecated itself

@Deprecated
abstract class B3 extends A implements I {
    @Deprecated public void iDep_aDep_bDep() { }
                public void iDep_aDep_bUnd() { } // potential warning x 2, suppressed
    //          public void iDep_aDep_bInh() { }
    @Deprecated public void iDep_aUnd_bDep() { }
                public void iDep_aUnd_bUnd() { } // potential warning, suppressed
    //          public void iDep_aUnd_bInh() { } // potential warning, suppressed
    @Deprecated public void iDep_aInh_bDep() { }
                public void iDep_aInh_bUnd() { } // potential warning, suppressed
    //          public void iDep_aInh_bInh() { }
    @Deprecated public void iUnd_aDep_bDep() { }
                public void iUnd_aDep_bUnd() { } // potential warning, suppressed
    //          public void iUnd_aDep_bInh() { }
    @Deprecated public void iUnd_aUnd_bDep() { }
                public void iUnd_aUnd_bUnd() { }
    //          public void iUnd_aUnd_bInh() { }
    @Deprecated public void iUnd_aInh_bDep() { }
                public void iUnd_aInh_bUnd() { }
    //          public void iUnd_aInh_bInh() { }
}
