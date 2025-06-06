/*
 * (c) 2010 Adam Lackorzynski <adam@os.inf.tu-dresden.de>,
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#pragma once

#include <l4/sys/types.h>

enum
{
  /**
   * Architecture specific version ID.
   *
   * This ID must match the version field in the l4_vcpu_state_t structure
   * after enabling vCPU mode or extended vCPU mode for a thread.
   */
  L4_VCPU_STATE_VERSION = 0x99,

  L4_VCPU_STATE_SIZE = 0x200,
  L4_VCPU_STATE_EXT_SIZE = L4_PAGESIZE,
};

/**
 * Offsets for vCPU state layouts
 * \ingroup l4_vcpu_api
 */
enum L4_vcpu_state_offset
{
  L4_VCPU_OFFSET_EXT_STATE = 0x400, ///< Offset where extended state begins
  L4_VCPU_OFFSET_EXT_INFOS = 0x200, ///< Offset where extended infos begin
};

/**
 * \brief vCPU registers.
 * \ingroup l4_vcpu_api
 */
typedef struct l4_vcpu_regs_t
{
  l4_umword_t pfa;
  l4_umword_t err;

  l4_umword_t r[28];
  l4_umword_t sp;
  l4_umword_t lr;
  l4_umword_t _dummy;
  l4_umword_t ip;
  l4_umword_t flags;

  /* And some more */
} l4_vcpu_regs_t;

typedef struct l4_vcpu_arch_state_t {} l4_vcpu_arch_state_t;

/**
 * \brief vCPU message registers.
 * \ingroup l4_vcpu_api
 */
typedef struct l4_vcpu_ipc_regs_t
{
  l4_msgtag_t tag;
  l4_umword_t _d1[3];
  l4_umword_t label;
  l4_umword_t _d2[8];
} l4_vcpu_ipc_regs_t;
