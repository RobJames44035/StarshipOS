#  StarshipOS Copyright (c) 2025. R.A. James

import distro

assert(distro.name() == 'Buildroot')
assert(distro.id() == 'buildroot')
