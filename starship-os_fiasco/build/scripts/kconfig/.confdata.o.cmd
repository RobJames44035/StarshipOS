savedcmd_scripts/kconfig/confdata.o := gcc -Wp,-MMD,scripts/kconfig/.confdata.o.d -Wall -Wmissing-prototypes -Wstrict-prototypes -O2 -fomit-frame-pointer -std=gnu11   -I /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include   -I ./scripts/kconfig -c -o scripts/kconfig/confdata.o /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/confdata.c

source_scripts/kconfig/confdata.o := /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/confdata.c

deps_scripts/kconfig/confdata.o := \
    $(wildcard include/config/FOO) \
    $(wildcard include/config/X) \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/xalloc.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/internal.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/hashtable.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/array_size.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/list.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/list_types.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/lkc.h \
    $(wildcard include/config/prefix) \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/expr.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/lkc_proto.h \

scripts/kconfig/confdata.o: $(deps_scripts/kconfig/confdata.o)

$(deps_scripts/kconfig/confdata.o):
