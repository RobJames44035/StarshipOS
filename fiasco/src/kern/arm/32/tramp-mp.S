#include "globalconfig.h"
#include "tcboffset.h"
#include "asm_arm.h"

#include TRAMP_MP_ASM_INCLUDE

#ifndef HAVE_MACRO_BSP_EARLY_INIT
.macro bsp_early_init tmp1, tmp2
.endm
#endif

.macro defvar name
	.global \name
	.type   \name,%object
	.p2align 2
\name:	.long 0
	.size \name, . - \name
.endm

.macro refvar name
.LC\name:
	.long \name - .LCanchor
.endm

	.section .mp_tramp_text, "ax"
	.p2align 2

#if defined(CONFIG_ARM_V7) && !defined(CONFIG_ARM_PSCI)
/* Similar to B2.2.7 but only consider L1 and use DCISW instead of DCCSW.
 * Must be executed _before_ the cache is enabled!
 *
 * We would like to be able to invalidate all levels of non-shared caches.
 * Unfortunately there is no information available regarding the layout of
 * shared / non-shared caches in the system. As only non-PSCI platforms need to
 * be considered (PSCI CPU_ON performs invalidation of caches on boot), assume
 * that these only have L1 cache or they use bsp_early_init to perform further
 * initialization.
 */
cache_invalidate_l1_v7:
	mrc     p15, 1, r0, c0, c0, 1   @ read CLIDR
	tst     r0, #0x07000000         @ CLIDR.LoC == 0?
	beq     finished
	and     r1, r0, #7              @ CLIDR.ctype0
	cmp     r1, #2
	blt     skip                    @ skip on no cache or I cache only
	mov     r0, #0
	mcr     p15, 2, r0, c0, c0, 0   @ CSSELR = 0 (L1)
	isb
	mrc     p15, 1, r1, c0, c0, 0   @ read CCSIDR
	and     r2, r1, #7              @ CCSIDR.LineSize
	add     r2, r2, #4
	movw    r4, #0x3ff
	ands    r4, r4, r1, lsr #3      @ CCSIDR.Associativity
	clz     r5, r4
	mov     r9, r4
loop1:
	movw    r7, #0x7fff
	ands    r7, r7, r1, lsr #13     @ CCSIDR.NumSets
loop2:
	orr     r11, r0, r9, lsl r5
	orr     r11, r11, r7, lsl r2
	mcr     p15, 0, r11, c7, c6, 2  @ DCISW
	subs    r7, r7, #1
	bge     loop2
	subs    r9, r9, #1
	bge     loop1
skip:
	dsb
finished:
	mov     r0, #0
	mcr     p15, 2, r0, c0, c0, 0   @ reset CSSELR
	dsb
	isb
	mov     pc, lr
#endif

	.global _tramp_mp_entry
_tramp_mp_entry:
        bsp_early_init r0, r1
        mrs   r0, cpsr
        and   r1, r0, #0x1f
        cmp   r1, #0x1a // hyp mode
#ifndef CONFIG_CPU_VIRT
        bne   2f

	// IRQs off, SVC
        mov   r0, #0xd3
        orr   r0, #0x100
        msr   spsr_cxsf, r0
        adr   r1, 2f
        .inst 0xe12ef300 | 1   @ msr elr_hyp, r1
        .inst 0xe160006e       @ eret
#else
	beq   2f
	switch_to_hyp
	mrs   r0, cpsr
#endif
2:
        orr   r0, r0, #0x1c0 // disable irq's etc
        msr   cpsr, r0

	// enable SMP
#ifndef CONFIG_ARM_EM_NS
#if defined(CONFIG_ARM_MPCORE) || defined(CONFIG_ARM_CORTEX_A9) || defined(CONFIG_ARM_CORTEX_A5)
	adr   r0, .Lmpcore_phys_base
	ldr   r0, [r0]
	ldr   r1, [r0]
	orr   r1, #1
	str   r1, [r0]
#endif

	mcr	p15, 0, r0, c7, c11, 1 /* Clean the data cache by MVA*/
	dsb	sy

#endif

#if defined(CONFIG_ARM_V7) && !defined(CONFIG_ARM_PSCI)
	bl    cache_invalidate_l1_v7
#endif
	mov   r0, #0
	mcr   p15, 0, r0, c7, c5, 0 // ICIALLU
	mcr   p15, 0, r0, c7, c5, 6 // BPIALL
	isb   sy
	dsb   sy
#ifdef CONFIG_ARM_V6
	mcr   p15, 0, r0, c7, c7, 0  // inv both
#endif

#ifdef CONFIG_ARM_V7
	// ACTRL is implementation defined
	mrc   p15, 0, r0, c0, c0, 0  // read MIDR
	adr   r3, .Lactrl_cpuid      // load addr
	ldm   r3, {r1, r2}           // load mask + val
	and   r3, r0, r1             // apply mask
	teq   r3, r2                 // check value
	bne   2f                     // only do mcr on this CPU
#endif

	mrc   p15, 0, r1, c1, c0, 1
#ifdef CONFIG_ARM_V7
	tst   r1, #0x40
	bne   2f
	lsr   r0, r0, #8
	and   r0, r0, #7
	cmp   r0, #7
	orrne r1, r1, #0x41
	orreq r1, r1, #0x40
#else
	orr   r1, r1, #0x20
#endif
	mcr   p15, 0, r1, c1, c0, 1

2:
.LCanchor:
	adr r12, .LCanchor

#ifdef CONFIG_CPU_VIRT
        // TLB flush
        mcr p15, 4, r0, c8, c7, 0 /* TLBIALLH */
        mcr p15, 4, r0, c8, c7, 4 /* TLBIALLNSNH */
        dsb sy

        ldr r0, .LC_tramp_mp_startup_ttbcr
        ldr r1, [r0, r12]
        mcr p15, 4, r1, c2, c0, 2   // HTCR

        ldr r0, .LC_tramp_mp_startup_mair0
        ldr r0, [r0, r12]
        mcr p15, 4, r0, c10, c2, 0  // HMAIR0

        ldr r0, .LC_tramp_mp_startup_pdbr
        ldr r0, [r0, r12]
        mov r1, #0
        mcrr p15, 4, r0, r1, c2     // HTTBR

        // Ensure paging configuration is committed before MMU and caches are
        // enabled.
        isb sy

        movw r0, #(1 | 4 | 32 | 0x1000)
        mcr  p15, 4, r0, c1, c0     // HSCTLR

#else
	// TLB flush
	mcr p15, 0, r0, c8, c7, 0 // TLBIALL
	dsb sy

	ldr r0, .LC_tramp_mp_startup_dcr
	ldr r0, [r0, r12]
	mcr p15, 0, r0, c3, c0	  // DACR

	// reset ASID and PROCID
	mov r0, #0
	mcr p15, 0, r0, c13, c0, 1  // CONTEXTIDR

	// init TTBCR
	ldr r0, .LC_tramp_mp_startup_ttbcr
	ldr r1, [r0, r12]
	mcr p15, 0, r1, c2, c0, 2   // TTBCR

	ldr r0, .LC_tramp_mp_startup_mair0
	ldr r0, [r0, r12]
	mcr p15, 0, r0, c10, c2, 0  // MAIR0 / PRRR
	ldr r0, .LC_tramp_mp_startup_mair1
	ldr r0, [r0, r12]
	mcr p15, 0, r0, c10, c2, 1  // MAIR1 / NMRR

	ldr r0, .LC_tramp_mp_startup_pdbr
	ldr r0, [r0, r12]
	tst r1, #(1 << 31)
	beq 1f
	mov r1, #0
	mcrr p15, 0, r0, r1, c2 // TTBR0
	b 2f
1:
	mcr p15, 0, r0, c2, c0  // TTBR0
2:

	// Ensure paging configuration is committed before MMU and caches are
	// enabled.
	isb sy
#endif

	ldr r0, .LC_tramp_mp_startup_cp15_c1
	ldr r0, [r0, r12]
	mcr p15, 0, r0, c1, c0  // SCTLR

	isb sy

	ldr r0, 1f
	mov pc, r0
1:
	.long .LC_tramp_mp_virt

#if defined(CONFIG_ARM_MPCORE) || defined(CONFIG_ARM_CORTEX_A9) || defined(CONFIG_ARM_CORTEX_A5)
.Lmpcore_phys_base:
	.long MPCORE_PHYS_BASE
#endif

.Lactrl_cpuid:
	.long 0xff0fff00
	.long 0x410fc000

refvar _tramp_mp_startup_cp15_c1
refvar _tramp_mp_startup_pdbr
refvar _tramp_mp_startup_dcr
refvar _tramp_mp_startup_ttbcr
refvar _tramp_mp_startup_mair0
refvar _tramp_mp_startup_mair1

	// we run paged now
.LC_tramp_mp_virt:
	// spinlock on cpu-init
	ldr	r0, =_tramp_mp_spinlock
	// Copy of Spin_lock::lock_arch(), we have no stack yet, so we cannot call it.
	// Always use the non-optimized variant of lock_arch(), which is also
	// compatible with versions of the ARM architecture prior to ARMv8.
	// {
1:	ldrex	r1, [r0]
	tst	r1, #2
	wfene
	orreq	r2, r1, #2
	strexeq	r1, r2, [r0]
	teqeq	r1, #0
	bne	1b
#ifdef CONFIG_ARM_V7PLUS
	// acquire barrier
	dmb
#else
        // CP15DMB
	mcr p15, 0, r0, c7, c10, 5
#endif
	// }

	// TLB flush
	mcr p15, 0, r0, c8, c7, 0

	ldr	sp, =.LC_tramp_mp_init_stack
	add	sp, #(.LC_tramp_mp_init_stack_top-.LC_tramp_mp_init_stack)
	nop
	ldr	pc, [pc, #-4]

	.long BOOT_AP_CPU

	.section .mp_tramp_data, "aw"
	.p2align 12

.long 0
defvar _tramp_mp_spinlock

.global _tramp_mp_startup_data_begin
_tramp_mp_startup_data_begin:
defvar _tramp_mp_startup_cp15_c1
defvar _tramp_mp_startup_pdbr
defvar _tramp_mp_startup_dcr
defvar _tramp_mp_startup_ttbcr
defvar _tramp_mp_startup_mair0
defvar _tramp_mp_startup_mair1
.global _tramp_mp_startup_data_end
_tramp_mp_startup_data_end:

	.p2align 3
.LC_tramp_mp_init_stack:
	.space 1024
.LC_tramp_mp_init_stack_top:

