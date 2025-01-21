/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
   @bug 4093817 4189594
   @summary Test for an illegalargumentexception on loadFactor
*/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * This class tests to see if creating a hash table with an
 * illegal value of loadFactor results in an IllegalArgumentException
 */
public class IllegalLoadFactor {

    public static void main(String[] args) throws Exception {
        boolean testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            Hashtable bad1 = new Hashtable(100, -3);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("Hashtable, negative load factor");

        testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            Hashtable bad1 = new Hashtable(100, Float.NaN);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("Hashtable, NaN load factor");

        testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            HashMap bad1 = new HashMap(100, -3);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("HashMap, negative load factor");

        testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            HashMap bad1 = new HashMap(100, Float.NaN);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("HashMap, NaN load factor");


        testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            HashSet bad1 = new HashSet(100, -3);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("HashSet, negative load factor");

        testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            HashSet bad1 = new HashSet(100, Float.NaN);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("HashSet, NaN load factor");

        testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            WeakHashMap bad1 = new WeakHashMap(100, -3);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("WeakHashMap, negative load factor");

        testSucceeded = false;
        try {
            // this should generate an IllegalArgumentException
            WeakHashMap bad1 = new WeakHashMap(100, Float.NaN);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded = true;
        }
        if (!testSucceeded)
            throw new Exception("WeakHashMap, NaN load factor");

        // Make sure that legal creates don't throw exceptions
        Map goodMap = new Hashtable(100, .69f);
        goodMap = new HashMap(100, .69f);
        Set goodSet = new HashSet(100, .69f);
        goodMap = new WeakHashMap(100, .69f);
    }
}
