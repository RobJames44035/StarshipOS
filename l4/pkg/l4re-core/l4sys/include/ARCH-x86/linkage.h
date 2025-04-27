/**
 * \file
 * \brief   Linkage
 * \ingroup l4sys_defines
 */
/*
 * (c) 2008-2009 Adam Lackorzynski <adam@os.inf.tu-dresden.de>,
 *               Alexander Warg <warg@os.inf.tu-dresden.de>,
 *               Frank Mehnert <fm3@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#ifndef __L4__SYS__ARCH_X86__LINKAGE_H__
#define __L4__SYS__ARCH_X86__LINKAGE_H__

#ifdef __ASSEMBLY__

#ifndef ENTRY
#define ENTRY(name) \
  .globl name; \
  .p2align(2); \
  name:

#endif /* ! ENTRY */
#endif /* __ASSEMBLY__ */

#define L4_FASTCALL(x)	x __attribute__((regparm(3)))
#define l4_fastcall	__attribute__((regparm(3)))

/**
 * Define calling convention.
 * \ingroup l4sys_defines
 * \hideinitializer
 */
#define L4_CV		__attribute__((regparm(0)))

#endif /* ! __L4__SYS__ARCH_X86__LINKAGE_H__ */
