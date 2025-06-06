// Reference-counted COW string instantiations -*- C++ -*-

// Copyright (C) 2014-2025 Free Software Foundation, Inc.
//
// This file is part of the GNU ISO C++ Library.  This library is free
// software; you can redistribute it and/or modify it under the
// terms of the GNU General Public License as published by the
// Free Software Foundation; either version 3, or (at your option)
// any later version.

// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// Under Section 7 of GPL version 3, you are granted additional
// permissions described in the GCC Runtime Library Exception, version
// 3.1, as published by the Free Software Foundation.

// You should have received a copy of the GNU General Public License and
// a copy of the GCC Runtime Library Exception along with this program;
// see the files COPYING3 and COPYING.RUNTIME respectively.  If not, see
// <http://www.gnu.org/licenses/>.

//
// ISO C++ 14882: 21  Strings library
//

#define _GLIBCXX_USE_CXX11_ABI 0
#include "string-inst.cc"

#if ! _GLIBCXX_USE_DUAL_ABI
# error This file should not be compiled for this configuration.
#endif

#ifndef BID_VARIANT_FLAG_NOFPU
#include <random>

namespace std _GLIBCXX_VISIBILITY(default)
{
  void
  random_device::_M_init(const std::string& token)
  { _M_init(token.c_str(), token.length()); }

  void
  random_device::_M_init_pretr1(const std::string& token)
  { _M_init(token.c_str(), token.length()); }
} // namespace
#endif
