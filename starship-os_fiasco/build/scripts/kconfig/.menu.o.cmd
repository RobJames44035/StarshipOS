savedcmd_scripts/kconfig/menu.o := gcc -Wp,-MMD,scripts/kconfig/.menu.o.d -Wall -Wmissing-prototypes -Wstrict-prototypes -O2 -fomit-frame-pointer -std=gnu11   -I /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include   -I ./scripts/kconfig -c -o scripts/kconfig/menu.o /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/menu.c

source_scripts/kconfig/menu.o := /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/menu.c

deps_scripts/kconfig/menu.o := \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/list.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/list_types.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/xalloc.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/lkc.h \
    $(wildcard include/config/prefix) \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/expr.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/lkc_proto.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/internal.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/hashtable.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/array_size.h \

scripts/kconfig/menu.o: $(deps_scripts/kconfig/menu.o)

$(deps_scripts/kconfig/menu.o):
