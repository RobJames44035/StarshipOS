savedcmd_scripts/basic/fixdep := gcc -Wp,-MMD,scripts/basic/.fixdep.d -Wall -Wmissing-prototypes -Wstrict-prototypes -O2 -fomit-frame-pointer -std=gnu11   -I /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include   -I ./scripts/basic   -o scripts/basic/fixdep /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/basic/fixdep.c   

source_scripts/basic/fixdep := /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/basic/fixdep.c

deps_scripts/basic/fixdep := \
    $(wildcard include/config/HIS_DRIVER) \
    $(wildcard include/config/MY_OPTION) \
    $(wildcard include/config/FOO) \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/xalloc.h \

scripts/basic/fixdep: $(deps_scripts/basic/fixdep)

$(deps_scripts/basic/fixdep):
