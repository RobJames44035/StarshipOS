/*
 * (c) 2010 Alexander Warg <warg@os.inf.tu-dresden.de>
 *     economic rights: Technische Universität Dresden (Germany)
 *
 * License: see LICENSE.spdx (in this directory or the directories above)
 */

#include "vproxy_dev.h"
#include "vbus_factory.h"

namespace Vi {

Proxy_dev::Proxy_dev(Hw::Device *d)
: _hwd(d)
{
  d->add_client(this);
  // suck features from real dev
  for (auto i: *d->features())
    {
      Dev_feature *vf = Feature_factory::match(i);
      if (vf)
        add_feature(vf);
    }

  // suck resources from our real dev
  for (auto i: *d->resources())
    {
      if (!i)
        continue;

      if (i->disabled() || i->internal())
        continue;

      Resource *vr = Resource_factory::match(i);
      if (!vr)
        vr = i;

      add_resource(vr);
    }
}

namespace {
  static Dev_factory_t<Proxy_dev, Hw::Device> __ghwdf;
}

}
