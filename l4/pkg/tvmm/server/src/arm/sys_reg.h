/* SPDX-License-Identifier: MIT */
/*
 * Copyright (C) 2020, 2024 Kernkonzept GmbH.
 * Author(s): Alexander Warg <alexander.warg@kernkonzept.com>
 *
 */

#pragma once

#include "arm_hyp.h"
#include "vcpu_ptr.h"

namespace Vmm {
namespace Arm {

class Sys_reg
{
public:
  struct Key
  {
    l4_uint32_t k;

    enum
    {
      CP_64 = 1u << 24,
    };

    Key() = default;

    constexpr Key(unsigned cp, unsigned op0, unsigned op1,
                  unsigned crn , unsigned crm, unsigned op2, unsigned flags = 0)
    : k(  ((cp  & 0xf) << 18)
        | ((op0 & 0x3) << 16)
        | ((op1 & 0xf) << 12)
        | ((crn & 0xf) << 8)
        | ((crm & 0xf) << 4)
        | ((op2 & 0x7) << 0)
        | flags)
     {}

    static constexpr Key
    sr(unsigned op0, unsigned op1, unsigned crn, unsigned crm, unsigned op2)
    { return Key(0, op0, op1, crn, crm, op2); }

    static constexpr Key
    cp_r(unsigned cp, unsigned op1, unsigned crn, unsigned crm, unsigned op2)
    { return Key(cp, 0, op1, crn, crm, op2); }

    static constexpr Key
    cp_r_64(unsigned cp, unsigned op1, unsigned crm)
    { return Key(cp, 0, op1, 0, crm, 0, CP_64); }

    friend constexpr bool operator == (Key l, Key r)
    { return l.k == r.k; }

    constexpr bool operator<(Key const &other)
    { return k < other.k; }

    CXX_BITFIELD_MEMBER(12, 14, op1, k);
    CXX_BITFIELD_MEMBER( 8, 11, crn, k);
    CXX_BITFIELD_MEMBER( 4,  7, crm, k);
    CXX_BITFIELD_MEMBER( 0,  2, op2, k);
  };

  virtual l4_uint64_t read(Vmm::Vcpu_ptr cpu, Key reg) = 0;
  virtual void write(Vmm::Vcpu_ptr cpu, Key reg, l4_uint64_t val) = 0;
  virtual ~Sys_reg() = default;
};

class Sys_reg_ro : public Sys_reg
{
public:
  void write(Vmm::Vcpu_ptr, Key, l4_uint64_t) override
  {}
};

class Sys_reg_wo : public Sys_reg
{
public:
  l4_uint64_t read(Vmm::Vcpu_ptr, Key) override
  { return 0; }
};

template<l4_uint64_t VAL>
class Sys_reg_const : public Sys_reg_ro
{
public:
  l4_uint64_t read(Vmm::Vcpu_ptr, Key) override
  { return VAL; }
};

}
}
