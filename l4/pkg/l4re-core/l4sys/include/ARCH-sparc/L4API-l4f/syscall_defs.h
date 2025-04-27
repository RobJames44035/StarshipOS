/**
 * \file
 * \brief  Syscall entry definitions.
 */
/*
 * (c) 2010 Adam Lackorzynski <adam@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#ifndef __L4SYS__ARCH_ARM__L4API_L4F__SYSCALL_DEFS_H__
#define __L4SYS__ARCH_ARM__L4API_L4F__SYSCALL_DEFS_H__

#ifndef L4_SYSCALL_MAGIC_OFFSET
#  define L4_SYSCALL_MAGIC_OFFSET	8
#endif
#define L4_SYSCALL_INVOKE		(-0x00000004-L4_SYSCALL_MAGIC_OFFSET)
#define L4_SYSCALL_MEM_OP               (-0x00000008-L4_SYSCALL_MAGIC_OFFSET)

#endif /* __L4SYS__ARCH_ARM__L4API_L4F__SYSCALL_DEFS_H__ */
