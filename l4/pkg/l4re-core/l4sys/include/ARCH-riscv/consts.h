/**
 * \file
 * \brief   Common L4 constants, RISC-V version
 * \ingroup l4_api
 */
/*
 * Copyright (C) 2021, 2024 Kernkonzept GmbH.
 * Author(s): Georg Kotheimer <georg.kotheimer@kernkonzept.com>
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#ifndef _L4_SYS_CONSTS_H
#define _L4_SYS_CONSTS_H

/* L4 includes */
#include <l4/sys/l4int.h>

/**
 * Sizeof a page in log2
 * \ingroup l4_memory_api
 */
#define L4_PAGESHIFT 12 // 4K pages

/**
 * Sizeof a large page in log2
 * \ingroup l4_memory_api
 */
#if __riscv_xlen == 32
#define L4_SUPERPAGESHIFT 22
#else
#define L4_SUPERPAGESHIFT 21
#endif

#include_next <l4/sys/consts.h>

#endif /* !_L4_SYS_CONSTS_H */
