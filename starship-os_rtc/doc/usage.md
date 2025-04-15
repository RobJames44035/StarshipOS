# RTC driver {#l4re_servers_rtc_driver}

 The RTC driver can drive various real-time clocks and provides an
 interface for other components, e.g., uvmm, to read and write the
 time.

 It needs access to the hardware, depending on the clock, either via a
 vbus or via an I2C device.

## Command Line Options

 There are no command line options.

## Environment

 Several capabilities can be used to interact with the environment:

 * 'rtc' (server)

   The capability that can be used from the outside to read and write the time.

 * 'vbus'

   The vbus used to access hardware for port-based clock on X86 and pl031 on arm.

   The vbus is also needed for receiving the inhibitor signal from IO.
