/* Copyright (C) 2002 Free Software Foundation, Inc.
   This file is part of the GNU C Library.
   Contributed by Jakub Jelinek <jakub@redhat.com>, 2002.

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

#include <locale.h>
#include <stdlib.h>
#include <string.h>
#ifdef USE_TLS
#include <tls.h>
#endif
#include "internals.h"


int __libc_multiple_threads attribute_hidden __attribute__((nocommon));


int * __libc_pthread_init (void)
{
#if !(USE_TLS && HAVE___THREAD)
  /* Initialize thread-locale current locale to point to the global one.
     With __thread support, the variable's initializer takes care of this.  */
  __uselocale (LC_GLOBAL_LOCALE);
#endif

  return &__libc_multiple_threads;
}
