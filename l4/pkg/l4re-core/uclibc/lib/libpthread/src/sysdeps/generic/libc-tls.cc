/* Initialization code for TLS in statically linked application.
   Copyright (C) 2002, 2003, 2004, 2005 Free Software Foundation, Inc.
   This file is part of the GNU C Library.

   The GNU C Library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   The GNU C Library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with the GNU C Library; if not, write to the Free
   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
   02111-1307 USA.  */

#include <errno.h>
#include <ldsodefs.h>
#include <tls.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/param.h>
#include <elf.h>
#include <link.h>
#include <string.h>
#include <stdlib.h>

#include <l4/re/env>
#include <l4/re/dataspace>
#include <l4/re/mem_alloc>
#include <l4/re/rm>

#ifdef SHARED
 #error makefile bug, this file is for static only
#endif

#if USE_TLS
extern ElfW(Phdr) *_dl_phdr;
extern size_t _dl_phnum;

extern "C"
__attribute__ ((weak))
void *__libc_alloc_initial_tls(unsigned long size) __THROW;

#ifdef SHARED
static
#endif
dtv_t static_dtv[2 + TLS_SLOTINFO_SURPLUS];


static struct
{
  struct dtv_slotinfo_list si;
  /* The dtv_slotinfo_list data structure does not include the actual
     information since it is defined as an array of size zero.  We define
     here the necessary entries.  Note that it is not important whether
     there is padding or not since we will always access the information
     through the 'si' element.  */
  dtv_slotinfo_list::dtv_slotinfo info[2 + TLS_SLOTINFO_SURPLUS];
} static_slotinfo;

/* Fake link map for the application.  */
static struct link_map static_map;


/* Highest dtv index currently needed.  */
size_t _dl_tls_max_dtv_idx;
/* Flag signalling whether there are gaps in the module ID allocation.  */
bool _dl_tls_dtv_gaps;
/* Information about the dtv slots.  */
struct dtv_slotinfo_list *_dl_tls_dtv_slotinfo_list;
/* Number of modules in the static TLS block.  */
size_t _dl_tls_static_nelem;
/* Size of the static TLS block.  */
size_t _dl_tls_static_size __attribute__((weak)) = TLS_STATIC_SURPLUS;
/* Size actually allocated in the static TLS block.  */
size_t _dl_tls_static_used;
/* Alignment requirement of the static TLS block.  */
size_t _dl_tls_static_align;

/* Generation counter for the dtv.  */
size_t _dl_tls_generation;


/* Additional definitions needed by TLS initialization.  */
#ifdef TLS_INIT_HELPER
TLS_INIT_HELPER
#endif

static inline void
init_slotinfo (void)
{
  /* Create the slotinfo list.  */
  static_slotinfo.si.len = (((char *) (&static_slotinfo + 1)
			     - (char *) &static_slotinfo.si.slotinfo[0])
			    / sizeof static_slotinfo.si.slotinfo[0]);
  // static_slotinfo.si.next = NULL;	already zero

  /* The slotinfo list.  Will be extended by the code doing dynamic
     linking.  */
  GL(dl_tls_max_dtv_idx) = 1;
  GL(dl_tls_dtv_slotinfo_list) = &static_slotinfo.si;
}

static inline void
init_static_tls (size_t memsz, size_t align)
{
  /* That is the size of the TLS memory for this object.  The initialized
     value of _dl_tls_static_size is provided by dl-open.c to request some
     surplus that permits dynamic loading of modules with IE-model TLS.  */
  GL(dl_tls_static_size) = roundup (memsz + GL(dl_tls_static_size),
				    TLS_TCB_ALIGN);
#if TLS_TCB_AT_TP
  GL(dl_tls_static_size) += TLS_TCB_SIZE;
#endif
  GL(dl_tls_static_used) = memsz;
  /* The alignment requirement for the static TLS block.  */
  GL(dl_tls_static_align) = align;
  /* Number of elements in the static TLS block.  */
  GL(dl_tls_static_nelem) = GL(dl_tls_max_dtv_idx);
}

__attribute__ ((weak))
void *__libc_alloc_initial_tls(unsigned long size) __THROW
{
  using namespace L4;
  using namespace L4Re;
  Cap<Dataspace> ds(Env::env()->first_free_cap() << L4_CAP_SHIFT);
  ::l4re_global_env->first_free_cap += 1;
  if (Env::env()->mem_alloc()->alloc(size, ds, 0) < 0)
    return NULL;

  void *addr = NULL;
  if(Env::env()->rm()->attach(&addr, size, Rm::F::Search_addr | Rm::F::RW,
                              L4::Ipc::make_cap_rw(ds), 0, 0) < 0)
    return NULL;

  return addr;
}

#if !defined(__FDPIC__) && !defined(SHARED) && defined(STATIC_PIE)
ElfW(Addr) _dl_load_base;
#endif

extern "C"
void
__libc_setup_tls (size_t tcbsize, size_t tcbalign)
{
  void *tlsblock;
  size_t memsz = 0;
  size_t filesz = 0;
  char *initimage = NULL;
  size_t align = 0;
  size_t max_align = tcbalign;
  size_t tcb_offset;
  ElfW(Phdr) *phdr;
  /* Look through the TLS segment if there is any.  */
  if (_dl_phdr != NULL)
    for (phdr = _dl_phdr; phdr < &_dl_phdr[_dl_phnum]; ++phdr)
      if (phdr->p_type == PT_TLS)
	{
	  /* Remember the values we need.  */
	  memsz = phdr->p_memsz;
	  filesz = phdr->p_filesz;
	  initimage = (char *) phdr->p_vaddr;
#if !defined(SHARED) && defined(STATIC_PIE)
	  initimage += _dl_load_base;
#endif
	  align = phdr->p_align;
	  if (phdr->p_align > max_align)
	    max_align = phdr->p_align;
	  break;
	}
  /* We have to set up the TCB block which also (possibly) contains
     'errno'.  Therefore we avoid 'malloc' which might touch 'errno'.
     Instead we use 'sbrk' which would only uses 'errno' if it fails.
     In this case we are right away out of memory and the user gets
     what she/he deserves.

     The initialized value of _dl_tls_static_size is provided by dl-open.c
     to request some surplus that permits dynamic loading of modules with
     IE-model TLS.  */
# if defined(TLS_TCB_AT_TP)
  tcb_offset = roundup (memsz + GL(dl_tls_static_size), max_align);
  tlsblock = __libc_alloc_initial_tls(tcb_offset + tcbsize + max_align);
# elif defined(TLS_DTV_AT_TP)
  tcb_offset = roundup (tcbsize, align ?: 1);
  tlsblock = __libc_alloc_initial_tls(tcb_offset + memsz + max_align
		     + TLS_PRE_TCB_SIZE + GL(dl_tls_static_size));
  //tlsblock += TLS_PRE_TCB_SIZE;
  tlsblock = (char *)tlsblock + TLS_PRE_TCB_SIZE;
# else
  /* In case a model with a different layout for the TCB and DTV
     is defined add another #elif here and in the following #ifs.  */
#  error "Either TLS_TCB_AT_TP or TLS_DTV_AT_TP must be defined"
# endif

  /* Align the TLS block.  */
  tlsblock = (void *) (((uintptr_t) tlsblock + max_align - 1)
		       & ~(max_align - 1));

  /* Initialize the dtv.  [0] is the length, [1] the generation counter.  */
  static_dtv[0].counter = (sizeof (static_dtv) / sizeof (static_dtv[0])) - 2;
  // static_dtv[1].counter = 0;		would be needed if not already done

  /* Initialize the TLS block.  */
# if defined(TLS_TCB_AT_TP)
  static_dtv[2].pointer.val = ((char *) tlsblock + tcb_offset
			       - roundup (memsz, align ?: 1));
  static_map.l_tls_offset = roundup (memsz, align ?: 1);
# elif defined(TLS_DTV_AT_TP)
  static_dtv[2].pointer.val = (char *) tlsblock + tcb_offset;
  static_map.l_tls_offset = tcb_offset;
# else
#  error "Either TLS_TCB_AT_TP or TLS_DTV_AT_TP must be defined"
# endif
  static_dtv[2].pointer.is_static = true;
  /* sbrk gives us zero'd memory, so we don't need to clear the remainder.  */
  memcpy (static_dtv[2].pointer.val, initimage, filesz);

  /* Install the pointer to the dtv.  */

  /* Initialize the thread pointer.  */
# if defined(TLS_TCB_AT_TP)
  INSTALL_DTV ((char *) tlsblock + tcb_offset, static_dtv);
  const char *lossage = TLS_INIT_TP ((char *) tlsblock + tcb_offset, 0);
# elif defined(TLS_DTV_AT_TP)
  INSTALL_DTV (tlsblock, static_dtv);
  const char *lossage = (char *)TLS_INIT_TP (tlsblock, 0);
# else
#  error "Either TLS_TCB_AT_TP or TLS_DTV_AT_TP must be defined"
# endif
  if (__builtin_expect (lossage != NULL, 0))
    abort();

  /* We have to create a fake link map which normally would be created
     by the dynamic linker.  It just has to have enough information to
     make the TLS routines happy.  */
  static_map.l_tls_align = align;
  static_map.l_tls_blocksize = memsz;
  static_map.l_tls_initimage = initimage;
  static_map.l_tls_initimage_size = filesz;
  static_map.l_tls_modid = 1;

  init_slotinfo ();
  // static_slotinfo.si.slotinfo[1].gen = 0; already zero
# pragma GCC diagnostic push
# pragma GCC diagnostic ignored "-Wpragmas"
# pragma GCC diagnostic ignored "-Wunknown-warning-option"
# pragma GCC diagnostic ignored "-Warray-bounds"
# pragma GCC diagnostic ignored "-Wzero-length-bounds"
  /* The slotinfo_list in static_slotinfo is guaranteed to have at least two
     entries (see the declaration of static_slotinfo), thus this array access
     is not out of bounds. */
  static_slotinfo.si.slotinfo[1].map = &static_map;
# pragma GCC diagnostic pop

  memsz = roundup (memsz, align ?: 1);

# if defined(TLS_TCB_AT_TP)
  memsz += tcbsize;
# elif defined(TLS_DTV_AT_TP)
  memsz += tcb_offset;
# endif

  init_static_tls (memsz, MAX (TLS_TCB_ALIGN, max_align));
}

/* This is called only when the data structure setup was skipped at startup,
   when there was no need for it then.  Now we have dynamically loaded
   something needing TLS, or libpthread needs it.  */
int
internal_function
_dl_tls_setup (void)
{
  init_slotinfo ();
  init_static_tls (
# if defined(TLS_TCB_AT_TP)
		   TLS_TCB_SIZE,
# else
		   0,
# endif
		   TLS_TCB_ALIGN);
  return 0;
}


/* This is the minimal initialization function used when libpthread is
   not used.  */
extern "C"
void
__attribute__ ((weak))
__pthread_initialize_minimal (void)
{
  __libc_setup_tls (TLS_INIT_TCB_SIZE, TLS_INIT_TCB_ALIGN);
}

#endif
