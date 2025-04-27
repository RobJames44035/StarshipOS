/**
 * \internal
 * \file
 * Kernel Interface Page (KIP).
 * \ingroup l4_kip_api
 */
/*
 * (c) 2008-2009 Adam Lackorzynski <adam@os.inf.tu-dresden.de>,
 *               Alexander Warg <warg@os.inf.tu-dresden.de>,
 *               Björn Döbel <doebel@os.inf.tu-dresden.de>,
 *               Frank Mehnert <fm3@os.inf.tu-dresden.de>,
 *               Torsten Frenzel <frenzel@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#pragma once

#include <l4/sys/types.h>

/**
 * L4 Kernel Interface Page.
 * \ingroup l4_kip_api
 */
typedef struct l4_kernel_info_t
{
  /* offset 0x00 */
  l4_uint64_t            magic;               /**< Kernel Info Page
					       **  identifier ("L4µK").
					       **/
  l4_uint64_t            version;             ///< Kernel version
  l4_uint8_t             offset_version_strings; ///< offset to version string
  l4_uint8_t             fill2[7];            ///< reserved \internal
  l4_uint8_t             kip_sys_calls;       ///< pointer to system calls
  l4_uint8_t             node;
  l4_uint8_t             fill3[6];            ///< reserved \internal

  /* the following stuff is undocumented; we assume that the kernel
     info page is located at offset 0x1000 into the L4 kernel boot
     image so that these declarations are consistent with section 2.9
     of the L4 Reference Manual */

  /* offset 0x20 */
  /* Kernel debugger */
  l4_umword_t            scheduler_granularity; ///< for rounding time slices
  l4_umword_t            _res00[3];           ///< default_kdebug_end

  /* offset 0x40 */
  /* Sigma0 */
  l4_umword_t            sigma0_esp;          ///< Sigma0 start stack pointer
  l4_umword_t            sigma0_eip;          ///< Sigma0 instruction pointer
  l4_umword_t            _res01[2];           ///< reserved \internal

  /* offset 0x60 */
  /* Sigma1 */
  l4_umword_t            sigma1_esp;          ///< Sigma1 start stack pointer
  l4_umword_t            sigma1_eip;          ///< Sigma1 instruction pointer
  l4_umword_t            _res02[2];           ///< reserved \internal

  /* offset 0x80 */
  /* Root task */
  l4_umword_t            root_esp;            ///< Root task stack pointer
  l4_umword_t            root_eip;            ///< Root task instruction pointer
  l4_umword_t            _res03[2];           ///< reserved \internal

  /* offset 0xA0 */
  /* L4 configuration */
  l4_umword_t            _res_a0[1];          ///< reserved \internal
  l4_umword_t            mem_info;            ///< memory information
  l4_umword_t            _res_b0[2];          ///< reserver \internal

  /* offset 0xC0 */
  l4_umword_t            _res04[16];          ///< reserved \internal

  /* offset 0x140 */
  volatile l4_cpu_time_t _clock_val;          ///< \internal
  l4_umword_t            _res05[1];           ///< reserved \internal
  l4_umword_t            frequency_cpu;       ///< CPU frequency in kHz
  l4_umword_t            frequency_bus;       ///< Bus frequency

  /* offset 0x160 */
  l4_umword_t            _res06[12];          ///< reserved \internal

  /* offset 0x1C0 */
  l4_umword_t		 user_ptr;            ///< user_ptr
  l4_umword_t            acpi_rsdp_addr;      ///< ACPI RSDP/XSDP
  l4_umword_t            _res07[2];

  /* offset 0x1E0 */
  struct l4_kip_platform_info    platform_info;
} l4_kernel_info_t;
