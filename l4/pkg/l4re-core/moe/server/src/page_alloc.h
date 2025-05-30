/*
 * (c) 2008-2009 Alexander Warg <warg@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#pragma once

#include <l4/cxx/exceptions>

class Dbg;

/**
 * Base page allocator.
 *
 * Manages the physical memory pages available to Moe.
 */
class Single_page_alloc_base
{
public:
  enum Nothrow { nothrow };

  struct Config
  {
    l4_addr_t physmin;
    l4_addr_t physmax;

    constexpr Config() : physmin(0), physmax(~0UL) {}

    explicit Config(l4_addr_t physmin, l4_addr_t physmax)
    : physmin(physmin), physmax(physmax) {}
  };

  static Config default_mem_cfg;

protected:
  Single_page_alloc_base();

public:
  static void *_alloc_max(unsigned long min, unsigned long *max,
                          unsigned align, unsigned granularity,
                          Config cfg);
  static void *_alloc(Nothrow, unsigned long size, unsigned long align = 0,
                      Config cfg = default_mem_cfg);
  static void *_alloc(unsigned long size, unsigned long align = 0,
                      Config cfg = default_mem_cfg)
  {
    void *r = _alloc(nothrow, size, align, cfg);
    if (!r)
      throw L4::Out_of_memory();
    return r;
  }
  static void _free(void *p, unsigned long size, bool initial_mem = false);
  static unsigned long _avail();

#ifndef NDEBUG
  static void _dump_free(Dbg &dbg);
#endif

  static bool can_free;
};

class Single_page_unique_ptr
{
private:
  void *_p = 0;
  unsigned long _s;

public:
  void *release()
  {
    void *p = _p;
    _p = 0;
    return p;
  }

  void reset(void *n = 0, unsigned long size = 0)
  {
    if (n == _p)
      return;

    void *p = _p;
    unsigned long s = _s;

    _p = n;
    if (n)
      _s = size;

    if (p)
      Single_page_alloc_base::_free(p, s);
  }

  unsigned long size() const { return _s; }

  void *get() const { return _p; }
  void *operator * () const { return _p; }

  Single_page_unique_ptr() = default;
  Single_page_unique_ptr(void *p, unsigned long size) : _p(p), _s(size) {}

  ~Single_page_unique_ptr()
  { reset(); }

  Single_page_unique_ptr &operator = (Single_page_unique_ptr &&o)
  {
    if (this == &o)
      return *this;

    reset(o._p, o._s);
    o._p = 0;
    return *this;
  }
};
