cmake_minimum_required(VERSION 3.10)
project(EFI_Toolchain)

# Set the directory for downloading tarballs
set(DOWNLOAD_DIR ${CMAKE_CURRENT_SOURCE_DIR}/dl)
set(TOOLCHAIN_TARBALL ${DOWNLOAD_DIR}/gnu-efi.tar.gz)
set(TOOLCHAIN_SRC_DIR ${CMAKE_CURRENT_BINARY_DIR}/toolchain-src)
set(TOOLCHAIN_BUILD_DIR ${CMAKE_CURRENT_BINARY_DIR}/toolchain)

# Create the dl directory if it doesn't exist
file(MAKE_DIRECTORY ${DOWNLOAD_DIR})

# If the tarball is not present, download it
if(NOT EXISTS ${TOOLCHAIN_TARBALL})
    message(STATUS "Downloading toolchain tarball...")
    file(DOWNLOAD
            "https://github.com/rnkoaa/gnu-efi/archive/refs/tags/v3.0.18.tar.gz"
            ${TOOLCHAIN_TARBALL}
    )
endif()

# Extract the tarball if it hasn't been extracted already
if(NOT EXISTS ${TOOLCHAIN_SRC_DIR})
    message(STATUS "Extracting toolchain...")
    execute_process(
            COMMAND ${CMAKE_COMMAND} -E tar xzvf ${TOOLCHAIN_TARBALL} -C ${CMAKE_CURRENT_BINARY_DIR}
    )
endif()

# Set up the build environment
file(MAKE_DIRECTORY ${TOOLCHAIN_BUILD_DIR})
set(CMAKE_C_FLAGS "-ffreestanding -fno-stack-protector -fshort-wchar -mno-red-zone")
set(CMAKE_CXX_FLAGS "${CMAKE_C_FLAGS}")

# Build the toolchain
add_subdirectory(${TOOLCHAIN_SRC_DIR} ${TOOLCHAIN_BUILD_DIR})
