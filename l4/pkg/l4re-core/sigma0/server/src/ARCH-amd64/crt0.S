/*
 * (c) 2008-2009 Adam Lackorzynski <adam@os.inf.tu-dresden.de>,
 *               Alexander Warg <warg@os.inf.tu-dresden.de>,
 *               Torsten Frenzel <frenzel@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#define __ASSEMBLY__
#include <l4/bid_config.h>
#include <l4/sys/compiler.h>
	
	.section .text.init

ENTRY(__crt_dummy__)
ENTRY(_start)
ENTRY(start)

	test	%rdi, %rdi
	jne	1f
	pop	%rdi		/* ptr to kernel info page is on init stack */
1:
	lea	_stack_top(%rip),%rsp

#ifdef CONFIG_BID_PIE
	movq %rdi, %r12                     /* save KIP ptr */
	lea  __ehdr_start(%rip), %rdi       /* "Calculate" load address... */
	call reloc_static_pie               /* relocate dynamic addrs */
	movq %r12, %rdi                     /* restore KIP ptr */
#endif

	/* The x86_64 System V ABI requires the stack pointer to be 16-byte
	 * aligned just before the call instruction is executed.
	 * 'push' + 'jmp' emulate 'call'. */
	lea	_exit(%rip),%rax
	pushq	%rax
	jmp	init@PLT

ENTRY(__main)
	ret
	
	.bss

	.balign	16
	.space	4096
_stack_top:
