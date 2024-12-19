package org.starship.init

import org.starship.sys.PanicException

class Init2 {

    void main(String[] args) {

        println "Hello from Init!"
        throw new PanicException("FOO")
    }
}
