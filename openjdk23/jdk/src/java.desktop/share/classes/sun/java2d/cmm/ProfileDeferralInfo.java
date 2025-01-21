/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package sun.java2d.cmm;

/**
 * A class to pass information about a profile to be loaded from a file to the
 * static getInstance(int cspace) method of ICC_Profile. Loading of the profile
 * data and initialization of the CMM is to be deferred as long as possible.
 */
public final class ProfileDeferralInfo {

    /**
     * Need to have this info for ICC_ColorSpace without causing a deferred
     * profile to be loaded.
     */
    public final int colorSpaceType, numComponents, profileClass;

    /**
     * The profile file name, such as "CIEXYZ.pf", "sRGB.pf", etc.
     */
    public final String filename;

    public ProfileDeferralInfo(String fn, int type, int ncomp, int pclass) {
        filename = fn;
        colorSpaceType = type;
        numComponents = ncomp;
        profileClass = pclass;
    }
}
