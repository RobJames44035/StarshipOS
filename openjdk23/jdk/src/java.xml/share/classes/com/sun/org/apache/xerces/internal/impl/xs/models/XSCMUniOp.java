/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.xs.models;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMNode;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMStateSet;
import com.sun.org.apache.xerces.internal.impl.xs.XSParticleDecl;

/**
 *
 * Content model Uni-Op node.
 *
 * @xerces.internal
 *
 * @author Neil Graham, IBM
 * @version $$
 */
public class XSCMUniOp extends CMNode {
    // -------------------------------------------------------------------
    //  Constructors
    // -------------------------------------------------------------------
    public XSCMUniOp(int type, CMNode childNode) {
        super(type);

        // Insure that its one of the types we require
        if ((type() != XSParticleDecl.PARTICLE_ZERO_OR_ONE)
        &&  (type() != XSParticleDecl.PARTICLE_ZERO_OR_MORE)
        &&  (type() != XSParticleDecl.PARTICLE_ONE_OR_MORE)) {
            throw new RuntimeException("ImplementationMessages.VAL_UST");
        }

        // Store the node and init any data that needs it
        fChild = childNode;
    }


    // -------------------------------------------------------------------
    //  Package, final methods
    // -------------------------------------------------------------------
    final CMNode getChild() {
        return fChild;
    }


    // -------------------------------------------------------------------
    //  Package, inherited methods
    // -------------------------------------------------------------------
    public boolean isNullable() {
        //
        //  For debugging purposes, make sure we got rid of all non '*'
        //  repetitions. Otherwise, '*' style nodes are always nullable.
        //
        if (type() == XSParticleDecl.PARTICLE_ONE_OR_MORE)
                return fChild.isNullable();
            else
                return true;
    }


    // -------------------------------------------------------------------
    //  Protected, inherited methods
    // -------------------------------------------------------------------
    protected void calcFirstPos(CMStateSet toSet) {
        // Its just based on our child node's first pos
        toSet.setTo(fChild.firstPos());
    }

    protected void calcLastPos(CMStateSet toSet) {
        // Its just based on our child node's last pos
        toSet.setTo(fChild.lastPos());
    }

    /**
     * Allows the user to set arbitrary data on this content model
     * node. This is used by the a{n,m} optimization that runs
     * in constant space. For convenience, set user data in
     * children node too.
     */
    @Override
    public void setUserData(Object userData) {
        super.setUserData(userData);
        fChild.setUserData(userData);
    }


    // -------------------------------------------------------------------
    //  Private data members
    //
    //  fChild
    //      This is the reference to the one child that we have for this
    //      unary operation.
    // -------------------------------------------------------------------
    private CMNode  fChild;
} // XSCMUniOp
