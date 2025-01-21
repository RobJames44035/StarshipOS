/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Verifies the JVMTI Heap Monitor Statistics using SerialGC
 * @build Frame HeapMonitor
 * @compile HeapMonitorGCTest.java
 * @requires vm.gc == "Serial" | vm.gc == "null"
 * @requires vm.jvmti
 * @run main/othervm/native -agentlib:HeapMonitorTest -XX:+UseSerialGC MyPackage.HeapMonitorGCTest
 */
