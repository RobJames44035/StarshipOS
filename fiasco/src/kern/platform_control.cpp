INTERFACE:

#include "types.h"

class Platform_control
{
public:
  static void init(Cpu_number);
  static bool cpu_shutdown_available();
  static int cpu_allow_shutdown(Cpu_number cpu, bool allow);

  /**
   * Initate a system suspend.
   *
   * Blocks the calling thread until the system resumes from the suspend.
   *
   * \pre Must only be called from thread on the boot CPU, which must not be
   *      migrated to a different CPU during the suspend, otherwise the system
   *      might get stuck.
   *
   * \param extra  Platform-specific additional information to pass to the
   *               system suspend implementation.
   *
   * \return 0 on success, otherwise error code.
   */
  static int system_suspend(Mword extra);
  static void system_off();
  static void system_reboot();
};

// ------------------------------------------------------------------------
IMPLEMENTATION:

#include "l4_types.h"
#include "reset.h"

IMPLEMENT_DEFAULT inline
void
Platform_control::init(Cpu_number)
{}

IMPLEMENT_DEFAULT inline NEEDS["l4_types.h"]
int
Platform_control::system_suspend(Mword)
{ return -L4_err::EBusy; }

IMPLEMENT_DEFAULT inline
bool
Platform_control::cpu_shutdown_available()
{ return false; }

IMPLEMENT_DEFAULT inline NEEDS["l4_types.h"]
int
Platform_control::cpu_allow_shutdown(Cpu_number, bool)
{ return -L4_err::ENodev; }

IMPLEMENT_DEFAULT inline
void
Platform_control::system_off()
{}

IMPLEMENT_DEFAULT inline NEEDS["reset.h"]
void
Platform_control::system_reboot()
{
  platform_reset();
}
