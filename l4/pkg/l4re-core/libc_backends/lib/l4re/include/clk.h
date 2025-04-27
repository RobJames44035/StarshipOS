/*
 * (c) 2009 Adam Lackorzynski <adam@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#pragma once

#include <time.h>
#include <inttypes.h>

extern uint64_t __libc_l4_rt_clock_offset;

__BEGIN_DECLS

int libc_backend_rt_clock_gettime(struct timespec *tp);

__END_DECLS
