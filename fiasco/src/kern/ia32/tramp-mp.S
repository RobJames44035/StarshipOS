/* -*- c -*- */
#include "config_gdt.h"
#include "tcboffset.h"
#include "linking.h"
#include "tramp-realmode.h"

#ifdef CONFIG_AMD64
# define CX rcx
# define SP rsp
#else
# define CX ecx
# define SP esp
#endif

REALMODE_SECTION
	.code16
	.align 16
	.global _tramp_mp_entry
_tramp_mp_entry:

    /* Basic setup of the processor (stack not yet needed) */
	cli
	cld
	RM_LOAD_SEGMENT_BASE

	ENTER_PROTECTED_MODE
	/* 32bit code follows */

	ENABLE_PAGING cr0=_tramp_mp_startup_cr0 cr3=_realmode_startup_pdbr cr4=_tramp_mp_startup_cr4 gdt=_tramp_mp_startup_gdt_pdesc

    /* Acquire spinlock */
    // Copy of Spin_lock::lock_arch(), we have no stack yet, so we cannot call it.
    // {
1:	cmpl $0, _tramp_mp_spinlock
	je 2f
	pause
	jmp 1b
2:	mov $2, %CX
	xchg _tramp_mp_spinlock, %CX
	cmp $0, %CX
	jne 1b
    // }

	/* we've the lock, can run on the init_stack */
	mov $_tramp_mp_init_stack_top, %SP
	mov %edi, %eax /* IA32: cpu-num in %eax, AMD64: %rdi */
	jmp BOOT_AP_CPU


	.align 8
.global _tramp_mp_startup_cr0
.global _tramp_mp_startup_cr4
.global _tramp_mp_startup_gdt_pdesc
_tramp_mp_startup_cr0:      	.quad 0x00000000
_tramp_mp_startup_cr4:      	.quad 0x00000000
_tramp_mp_startup_gdt_pdesc:	.quad 0
                            	.quad 0
.global _tramp_mp_spinlock
_tramp_mp_spinlock:
	.quad 0

	.align 16
_tramp_mp_init_stack:
	.space 2048
_tramp_mp_init_stack_top:

