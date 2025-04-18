/*
 * (c) 2008-2009 Adam Lackorzynski <adam@os.inf.tu-dresden.de>,
 *               Alexander Warg <warg@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#include "ioports.h"
#include "mem_man.h"
#include "globals.h"

#include <l4/sys/types.h>
#include <l4/sys/ipc.h>

#include <l4/cxx/iostream>

enum { PORT_SHIFT = 12 };

static Mem_man io_ports;

void init_io_ports()
{
  io_ports.add_free(Region::kr(0, (64 * 1024) << PORT_SHIFT, 0, L4_FPAGE_RW));
}

void dump_io_ports()
{
  L4::cout << "IO PORTS--------------------------\n";
  io_ports.dump();
}

void handle_io_page_fault(l4_umword_t t, l4_utcb_t *utcb, Answer *a)
{
  unsigned long port, size;
  l4_fpage_t fp = (l4_fpage_t&)l4_utcb_mr_u(utcb)->mr[0];
  port = l4_fpage_ioport(fp) << PORT_SHIFT;
  size = l4_fpage_size(fp) + PORT_SHIFT;

  unsigned long i =
    io_ports.alloc(Region::bs(port, 1UL << size, t, L4_FPAGE_RW));
  if (i == port)
    a->snd_fpage(l4_iofpage(port >> PORT_SHIFT, size - PORT_SHIFT));
  else
    a->error(L4_ENOMEM);
}
