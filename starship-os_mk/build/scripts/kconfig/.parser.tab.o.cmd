savedcmd_scripts/kconfig/parser.tab.o := gcc -Wp,-MMD,scripts/kconfig/.parser.tab.o.d -Wall -Wmissing-prototypes -Wstrict-prototypes -O2 -fomit-frame-pointer -std=gnu11   -I /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include  -I /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig -I ./scripts/kconfig -c -o scripts/kconfig/parser.tab.o scripts/kconfig/parser.tab.c

source_scripts/kconfig/parser.tab.o := scripts/kconfig/parser.tab.c

deps_scripts/kconfig/parser.tab.o := \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/xalloc.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/lkc.h \
    $(wildcard include/config/prefix) \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/expr.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/list_types.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/lkc_proto.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/internal.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/hashtable.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/array_size.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/include/list.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_mk/tool/kconfig/scripts/kconfig/preprocess.h \
  scripts/kconfig/parser.tab.h \

scripts/kconfig/parser.tab.o: $(deps_scripts/kconfig/parser.tab.o)

$(deps_scripts/kconfig/parser.tab.o):
