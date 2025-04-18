/*
 * Try to define the minimal empty init/ctor/dtor/fini_arrays so building with
 * older or out-of-tree linker scripts will still work.
 */
/*
 * Note that these aren't the using the GNU "CONSTRUCTOR" output section
 * command, so they don't start with a size.  Because of p2align and the
 * end/END definitions, and the fact that they're mergeable, they can also
 * have NULLs which aren't guaranteed to be at the end.
 */
#ifndef L4 /* L4Re provides the symbols through bootstrap's linker script */
	.section .init_array,"aw",%init_array
	.p2align 4, 0
	.globl __init_array_start
__init_array_start:
	.globl __init_array_end
__init_array_end:
#endif /* L4 */
	.section .ctors,"aw",%progbits
	.p2align 4, 0
	.globl __CTOR_LIST__
__CTOR_LIST__:
	.globl __CTOR_END__
__CTOR_END__:
	.section .dtors,"aw",%progbits
	.p2align 4, 0
	.globl __DTOR_LIST__
__DTOR_LIST__:
	.globl __DTOR_END__
__DTOR_END__:
	.section .fini_array,"aw",%fini_array
	.p2align 4, 0
	.globl __fini_array_start
__fini_array_start:
	.globl __fini_array_end
__fini_array_end:

#if defined(__ELF__) && defined(__linux__)
	.section .note.GNU-stack,"",%progbits
#endif

