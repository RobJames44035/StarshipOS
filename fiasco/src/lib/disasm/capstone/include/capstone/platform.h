/* Capstone Disassembly Engine */
/* By Axel Souchet & Nguyen Anh Quynh, 2014 */

#ifndef CAPSTONE_PLATFORM_H
#define CAPSTONE_PLATFORM_H


// handle C99 issue (for pre-2013 VisualStudio)
#if !defined(__CYGWIN__) && !defined(__MINGW32__) && !defined(__MINGW64__) && (defined (WIN32) || defined (WIN64) || defined (_WIN32) || defined (_WIN64))
// MSVC

// stdbool.h
#if (_MSC_VER < 1800) || defined(_KERNEL_MODE)
// this system does not have stdbool.h
#ifndef __cplusplus
typedef unsigned char bool;
#define false 0
#define true 1
#endif  // __cplusplus

#else
// VisualStudio 2013+ -> C99 is supported
#include <stdbool.h>
#endif  // (_MSC_VER < 1800) || defined(_KERNEL_MODE)

#else
// not MSVC -> C99 is supported
#include <stdbool.h>
#endif  // !defined(__CYGWIN__) && !defined(__MINGW32__) && !defined(__MINGW64__) && (defined (WIN32) || defined (WIN64) || defined (_WIN32) || defined (_WIN64))


// handle inttypes.h / stdint.h compatibility
#if defined(_WIN32_WCE) && (_WIN32_WCE < 0x800)
#include "windowsce/stdint.h"
#endif  // defined(_WIN32_WCE) && (_WIN32_WCE < 0x800)

#if defined(CAPSTONE_HAS_OSXKERNEL) || (defined(_MSC_VER) && (_MSC_VER <= 1700 || defined(_KERNEL_MODE))) || 1 // Fiasco
// this system does not have inttypes.h

#if defined(_MSC_VER) && (_MSC_VER <= 1600 || defined(_KERNEL_MODE))
// this system does not have stdint.h
typedef signed char  int8_t;
typedef signed short int16_t;
typedef signed int   int32_t;
typedef unsigned char  uint8_t;
typedef unsigned short uint16_t;
typedef unsigned int   uint32_t;
typedef signed long long   int64_t;
typedef unsigned long long uint64_t;
#endif  // defined(_MSC_VER) && (_MSC_VER <= 1600 || defined(_KERNEL_MODE))

#include <stdint.h>          // Fiasco
#include "globalconfig.h"    // Fiasco

#if defined(_MSC_VER) && (_MSC_VER < 1600 || defined(_KERNEL_MODE))
#define INT8_MIN         (-127 - 1)
#define INT16_MIN        (-32767 - 1)
#define INT32_MIN        (-2147483647 - 1)
#define INT64_MIN        (-9223372036854775807ll - 1)
#define INT8_MAX         127
#define INT16_MAX        32767
#define INT32_MAX        2147483647
#define INT64_MAX        9223372036854775807
#define UINT8_MAX        0xff
#define UINT16_MAX       0xffff
#define UINT32_MAX       0xfffffffful
#define UINT64_MAX       0xffffffffffffffffull
#endif  // defined(_MSC_VER) && (_MSC_VER < 1600 || defined(_KERNEL_MODE))

#ifdef CAPSTONE_HAS_OSXKERNEL
// this system has stdint.h
#include <stdint.h>
#endif

#define __PRI_8_LENGTH_MODIFIER__ "hh"
#define __PRI_64_LENGTH_MODIFIER__ "ll"

#define PRId8         __PRI_8_LENGTH_MODIFIER__ "d"
#define PRIi8         __PRI_8_LENGTH_MODIFIER__ "i"
#define PRIo8         __PRI_8_LENGTH_MODIFIER__ "o"
#define PRIu8         __PRI_8_LENGTH_MODIFIER__ "u"
#define PRIx8         __PRI_8_LENGTH_MODIFIER__ "x"
#define PRIX8         __PRI_8_LENGTH_MODIFIER__ "X"

#define PRId16        "hd"
#define PRIi16        "hi"
#define PRIo16        "ho"
#define PRIu16        "hu"
#define PRIx16        "hx"
#define PRIX16        "hX"

#if defined(_MSC_VER) && _MSC_VER <= 1700
#define PRId32        "ld"
#define PRIi32        "li"
#define PRIo32        "lo"
#define PRIu32        "lu"
#define PRIx32        "lx"
#define PRIX32        "lX"
#else	// OSX
#define PRId32        "d"
#define PRIi32        "i"
#define PRIo32        "o"
#define PRIu32        "u"
#define PRIx32        "x"
#define PRIX32        "X"
#endif  // defined(_MSC_VER) && _MSC_VER <= 1700

#if defined(_MSC_VER) && _MSC_VER <= 1700
// redefine functions from inttypes.h used in cstool
#define strtoull _strtoui64
#endif

#define PRId64        __PRI_64_LENGTH_MODIFIER__ "d"
#define PRIi64        __PRI_64_LENGTH_MODIFIER__ "i"
#define PRIo64        __PRI_64_LENGTH_MODIFIER__ "o"
#define PRIu64        __PRI_64_LENGTH_MODIFIER__ "u"
#define PRIx64        __PRI_64_LENGTH_MODIFIER__ "x"
#define PRIX64        __PRI_64_LENGTH_MODIFIER__ "X"

#else
// this system has inttypes.h by default
#include <inttypes.h>
#endif  // defined(CAPSTONE_HAS_OSXKERNEL) || (defined(_MSC_VER) && (_MSC_VER <= 1700 || defined(_KERNEL_MODE)))

#endif
