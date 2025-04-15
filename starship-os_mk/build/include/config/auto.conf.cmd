autoconfig := include/config/auto.conf

deps_config := \
	/home/rajames/IdeaProjects/StarshipOS/starship-os_mk/build/Kconfig.generated \
	Kconfig.generated.defines \
	./mk/arch/Kconfig.arm.inc \
	./mk/arch/Kconfig.arm64.inc \
	./mk/arch/Kconfig.ia32.inc \
	./mk/arch/Kconfig.mips.inc \
	./mk/arch/Kconfig.ppc32.inc \
	./mk/arch/Kconfig.riscv.inc \
	./mk/arch/Kconfig.sparc.inc \
	mk/arch/Kconfig.common.inc \
	Kconfig.generated.platform_types \
	Kconfig.generated.pkgs \

$(autoconfig): $(deps_config)
$(deps_config): ;
