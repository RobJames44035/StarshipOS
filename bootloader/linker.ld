OUTPUT_FORMAT(elf64-x86-64)
OUTPUT_ARCH(x86_64)
ENTRY(efi_main)

SECTIONS
{
  . = 0x100000;
  .text :
  {
    *(.text)
  }
  .data :
  {
    *(.data)
  }
  .bss :
  {
    *(.bss)
  }
}
