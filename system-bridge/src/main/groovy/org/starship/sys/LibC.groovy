package org.starship.sys

import com.sun.jna.Library

// Define the LibC interface
interface LibC extends Library {
    void sync() // Maps the native sync() method

    int reboot(int magic) // Maps the native reboot() method, accepts an integer as the parameter
}
