savedcmd_scripts/kconfig/conf.o := gcc -Wp,-MMD,scripts/kconfig/.conf.o.d -Wall -Wmissing-prototypes -Wstrict-prototypes -O2 -fomit-frame-pointer -std=gnu11   -I /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include   -I ./scripts/kconfig -c -o scripts/kconfig/conf.o /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/conf.c

source_scripts/kconfig/conf.o := /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/conf.c

deps_scripts/kconfig/conf.o := \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/internal.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/hashtable.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/array_size.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/list.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/list_types.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/lkc.h \
    $(wildcard include/config/prefix) \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/expr.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/lkc_proto.h \

scripts/kconfig/conf.o: $(deps_scripts/kconfig/conf.o)

$(deps_scripts/kconfig/conf.o):
