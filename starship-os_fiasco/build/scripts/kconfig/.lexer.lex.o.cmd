savedcmd_scripts/kconfig/lexer.lex.o := gcc -Wp,-MMD,scripts/kconfig/.lexer.lex.o.d -Wall -Wmissing-prototypes -Wstrict-prototypes -O2 -fomit-frame-pointer -std=gnu11   -I /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include  -I /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig -I ./scripts/kconfig -c -o scripts/kconfig/lexer.lex.o scripts/kconfig/lexer.lex.c

source_scripts/kconfig/lexer.lex.o := scripts/kconfig/lexer.lex.c

deps_scripts/kconfig/lexer.lex.o := \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/xalloc.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/lkc.h \
    $(wildcard include/config/prefix) \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/expr.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/include/list_types.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/lkc_proto.h \
  /home/rajames/IdeaProjects/StarshipOS/starship-os_fiasco/tool/kconfig/scripts/kconfig/preprocess.h \
  scripts/kconfig/parser.tab.h \

scripts/kconfig/lexer.lex.o: $(deps_scripts/kconfig/lexer.lex.o)

$(deps_scripts/kconfig/lexer.lex.o):
