#! /usr/bin/env tclsh

#
# StarshipOS Copyright (c) 2025. R.A. James
#

proc factorial {n} {
   set f 1
   for {set i 1} {$i <= $n} {incr i} {
      set f [expr {$f * $i}]
   }
   return $f
}

puts [factorial [lindex $argv 0]]
