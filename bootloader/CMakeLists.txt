cmake_minimum_required(VERSION 3.16)
project(StarshipBootloader LANGUAGES C)

# Define paths
set(GNU_EFI_PATH "${CMAKE_SOURCE_DIR}/tools/efi-toolchain/toolchain-src/gnu-efi-3.0.18")
set(TOOLCHAIN_SCRIPT "${CMAKE_SOURCE_DIR}/tools/efi-toolchain/toolchain/build-toolchain.sh")

# Ensure the toolchain script exists
if(NOT EXISTS ${TOOLCHAIN_SCRIPT})
    message(FATAL_ERROR "Toolchain script does not exist: ${TOOLCHAIN_SCRIPT}")
endif()

# Build gnu-efi toolchain using the shell script
add_custom_target(build_toolchain ALL
        COMMAND chmod +x ${TOOLCHAIN_SCRIPT}
        COMMAND ${TOOLCHAIN_SCRIPT}
        WORKING_DIRECTORY ${CMAKE_SOURCE_DIR}/tools/efi-toolchain
        COMMENT "Building gnu-efi toolchain using ${TOOLCHAIN_SCRIPT}..."
)

# Set cross-compiler and linker explicitly for UEFI builds
set(CMAKE_C_COMPILER x86_64-linux-gnu-gcc)    # Replace with `x86_64-elf-gcc` if needed
set(CMAKE_ASM_COMPILER x86_64-linux-gnu-gcc)  # Replace with `x86_64-elf-gcc` if available
set(CMAKE_LINKER "${GNU_EFI_PATH}/x86_64/bin/ld") # Use GNU EFI's linker if available

# Include paths for EFI headers
include_directories(
        ${GNU_EFI_PATH}/inc                   # Path for EFI base headers (efi.h, efiprot.h, etc.)
        ${GNU_EFI_PATH}/x86_64/include        # Path for architecture-specific EFI headers
)

# Add the main EFI bootloader executable
add_executable(bootloader.efi src/main.c)
add_dependencies(bootloader.efi build_toolchain)

# Link with the gnu-efi libraries
target_link_libraries(bootloader.efi
        ${GNU_EFI_PATH}/x86_64/lib/libefi.a         # Path to `libefi.a`
        ${GNU_EFI_PATH}/x86_64/gnuefi/libgnuefi.a  # Fixed path for `libgnuefi.a`
)

# Compiler options for UEFI-compatible code
target_compile_options(bootloader.efi PRIVATE
        -ffreestanding        # Build for a freestanding environment
        -fshort-wchar         # Ensure `wchar_t` is 16 bits, as required by UEFI
        -mno-red-zone         # Disable red zone for better stack usage in EFI
        -nostdlib             # Avoid using system standard libraries
        -g                    # Include debug information
        -fdiagnostics-color=always # Enable colored diagnostics
)

# Linker options for UEFI (producing a PE/COFF format binary)
set(LINKER_SCRIPT "${CMAKE_SOURCE_DIR}/linker.ld")
target_link_options(bootloader.efi PRIVATE
        -nostdlib
        -Wl,--build-id=none        # Disable creation of a build ID
        -Wl,-T,${LINKER_SCRIPT}    # Use custom linker script for PE/COFF output
        -Wl,--entry,efi_main       # Specify the entry point as `efi_main`
        -Wl,-znocombreloc          # Disable combining of relocation entries
        -Wl,-m,i386:x86-64pep      # Produce a PE/COFF EFI binary
)

# Specify the output EFI file extension
set_target_properties(bootloader.efi PROPERTIES SUFFIX ".efi")
