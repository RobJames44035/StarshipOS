/*
 * Copyright (C) 2020, 2024 Kernkonzept GmbH.
 * Author(s): Adam Lackorzynski <adam.lackorzynski@kernkonzept.com>
 *            Sarah Hoffmann <sarah.hoffmann@kernkonzept.com>
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */
#include "lua.h"

#include <l4/util/util.h>

namespace Lua { namespace {

class Lib_sleep : public Lib
{
public:
  static int sleep(lua_State *l)
  {
    if (lua_gettop(l) >= 1 && !lua_isnil(l, 1)
        && lua_isnumber(l, 1))
      {
        int s = lua_tointeger(l, 1);
        printf("Ned: Sleeping %ds\n", s);
        l4_sleep(s * 1000);
      }
    return 0;
  }

  static int msleep(lua_State *l)
  {
    if (lua_gettop(l) >= 1 && !lua_isnil(l, 1)
        && lua_isnumber(l, 1))
      {
        int s = lua_tointeger(l, 1);
        printf("Ned: Sleeping %dms\n", s);
        l4_sleep(s);
      }
    return 0;
  }


  Lib_sleep() : Lib(P_env) {}

  void init(lua_State *l)
  {
    lua_require_module(l, package);

    // L4.sleep()
    lua_pushcfunction(l, sleep);
    lua_setfield(l, -2, "sleep");

    // L4.msleep()
    lua_pushcfunction(l, msleep);
    lua_setfield(l, -2, "msleep");

  }
};
static Lib_sleep __libsleep;

}}
