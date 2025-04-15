autoconfig := include/config/auto.conf

deps_config := \
	Kconfig \

$(autoconfig): $(deps_config)
$(deps_config): ;

ifneq "$(INCLUDE_PPC32)" ""
$(autoconfig): FORCE
endif

ifneq "$(INCLUDE_SPARC)" ""
$(autoconfig): FORCE
endif
