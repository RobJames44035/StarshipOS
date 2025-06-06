/*
 * Copyright (C) 2017, 2020, 2024 Kernkonzept GmbH.
 * Author: Jakub Jermar <jakub.jermar@kernkonzept.com>
 *
 * This file is distributed under the terms of the GNU General Public
 * License, version 2. Please see the COPYING-GPL-2 file for details.
 */

// This file contains code that enables multiboot2 boots. With many other parts
// of the system depending on multiboot (v1) data structures and with the goal
// of making a low-impact change, the method chosen is to extract essential
// information from the multiboot2 info structure and convert it into a v1
// structure as soon as possible so that the rest of the bootstrap code remains
// untouched.

#include <l4/util/mb_info.h>
#include <l4/sys/l4int.h>
#include <l4/sys/consts.h>
#include <l4/cxx/minmax>

#include <string.h>

#include <assert.h>
#include <stddef.h>

// This class can be used to convert a multiboot2 information buffer into a
// multiboot information structure and associated data structures. It works in
// place and does not allocate additional memory except temporary stack space.
//
// The space for the multiboot info structure comes from the multiboot2 tag
// headers. There must be enough tags (luckily this is under our control) to
// create enough space. Memory for the other objects, such as strings, module
// arrays and memory maps comes from the multiboot2 tag payloads (i.e.  the
// multiboot2 payloads are rotated to the end of the buffer and the buffer is
// shrunk accordingly).
class Mbi2_convertible_buffer
{
public:
  Mbi2_convertible_buffer(l4util_mb2_info_t *mbi2)
  : _buf(reinterpret_cast<char *>(mbi2)),
    _size(mbi2->total_size),
    _total_size(mbi2->total_size),
    _mbi({})
  {}

  l4util_mb2_tag_t *begin()
  {
    return reinterpret_cast<l4util_mb2_tag_t *>(_buf
                                                + sizeof(l4util_mb2_info_t));
  }

  void process_cmdline(l4util_mb2_tag_t *tag)
  {
    auto dst_tag = rotate_to_end(tag);
    reserve_from_end(dst_tag->size - offsetof(l4util_mb2_tag_t, cmdline));

    _mbi.flags |= L4UTIL_MB_CMDLINE;
    _mbi.cmdline = reinterpret_cast<l4_uint32_t>(dst_tag->cmdline.string);
  }

  // Module tags must appear in a consecutive block (they may not be interleaved
  // by some other tag type)
  void process_modules(l4util_mb2_tag_t *tag)
  {
    size_t cnt = 0;

    while (tag->type == L4UTIL_MB2_MODULE_INFO_TAG)
      {
        cnt++;
        auto dst_tag = rotate_to_end(tag);

        // Reserve only the command line
        reserve_from_end(dst_tag->size
                         - (offsetof(l4util_mb2_tag_t, module)
                            + offsetof(l4util_mb2_module_tag_t, string)));

        // Convert to v1 entry
        // Both versions happen to be the same size without the module name
        l4util_mb_mod_t mod = {.mod_start = dst_tag->module.mod_start,
                               .mod_end = dst_tag->module.mod_end,
                               .cmdline = reinterpret_cast<l4_uint32_t>(
                                 dst_tag->module.string),
                               .pad = 0};
        *reinterpret_cast<l4util_mb_mod_t *>(dst_tag) = mod;
      }

    if (cnt)
      {
        // Reserve the converted v1 structures
        // They happen to be adjacent at the end of the buffer
        reserve_from_end(sizeof(l4util_mb_mod_t) * cnt);

        _mbi.flags |= L4UTIL_MB_MODS;
        _mbi.mods_count = cnt;
        _mbi.mods_addr = reinterpret_cast<l4_uint32_t>(end());
      }
  }

  void process_memmap(l4util_mb2_tag_t *tag)
  {
    auto dst_tag = rotate_to_end(tag);
    auto size = dst_tag->size - (offsetof(l4util_mb2_tag_t, memmap)
                                 + offsetof(l4util_mb2_memmap_tag_t, entries));

    auto entry_size = dst_tag->memmap.entry_size;

    // Convert to v1 entries
    for (auto entry = dst_tag->memmap.entries;
         reinterpret_cast<l4_addr_t>(entry)
         < reinterpret_cast<l4_addr_t>(dst_tag) + dst_tag->size;)
      {
        auto next = reinterpret_cast<decltype(entry)>(
           reinterpret_cast<l4_addr_t>(entry) + entry_size);

        // Each v1 entry has the same length as the corresponding v2 entry
        l4util_mb_addr_range_t range = {.struct_size =
                                          entry_size - sizeof(range.struct_size),
                                        .addr = entry->base_addr,
                                        .size = entry->length,
                                        .type = entry->type};

        *reinterpret_cast<decltype(range) *>(entry) = range;
        entry = next;
      }

    reserve_from_end(size);

    _mbi.flags |= L4UTIL_MB_MEM_MAP;
    _mbi.mmap_length = size;
    _mbi.mmap_addr = reinterpret_cast<l4_addr_t>(end());
  }

  void process_fb(l4util_mb2_tag_t *tag)
  {
    auto dst_tag = rotate_to_end(tag);

    // We only support type 1 currently
    if (dst_tag->fb.framebuffer_type != 1)
      return;

    reserve_from_end(sizeof(_vbe_mode) + sizeof(_vbe_ctrl));

    memset(&_vbe_ctrl, 0, sizeof(_vbe_ctrl));
    memset(&_vbe_mode, 0, sizeof(_vbe_mode));

    _vbe_mode.phys_base = dst_tag->fb.framebuffer_addr;
    _vbe_mode.reserved1 = dst_tag->fb.framebuffer_addr >> 32;

    _vbe_mode.x_resolution       = dst_tag->fb.framebuffer_width;
    _vbe_mode.y_resolution       = dst_tag->fb.framebuffer_height;
    _vbe_mode.bits_per_pixel     = dst_tag->fb.framebuffer_bpp;
    _vbe_mode.bytes_per_scanline = dst_tag->fb.framebuffer_pitch;

    _vbe_mode.red_mask_size        = dst_tag->fb.color_info_rgb.framebuffer_red_mask_size;
    _vbe_mode.red_field_position   = dst_tag->fb.color_info_rgb.framebuffer_red_field_position;
    _vbe_mode.green_mask_size      = dst_tag->fb.color_info_rgb.framebuffer_green_mask_size;
    _vbe_mode.green_field_position = dst_tag->fb.color_info_rgb.framebuffer_green_field_position;
    _vbe_mode.blue_mask_size       = dst_tag->fb.color_info_rgb.framebuffer_blue_mask_size;
    _vbe_mode.blue_field_position  = dst_tag->fb.color_info_rgb.framebuffer_blue_field_position;

    size_t video_fb_size = _vbe_mode.bytes_per_scanline * _vbe_mode.y_resolution;
    _vbe_ctrl.total_memory = (video_fb_size + 64 * 1024 - 1) / (64 * 1024); // in 64k chunks

    _mbi.flags |= L4UTIL_MB_VIDEO_INFO;
  }

  void process_rsdp(l4util_mb2_tag_t *tag, void **rsdp_start,
                    l4_uint32_t *rsdp_size)
  {
    auto dst_tag = rotate_to_end(tag);
    auto size = dst_tag->size - offsetof(l4util_mb2_tag_t, rsdp);

    reserve_from_end(size);

    *rsdp_start = end();
    *rsdp_size = size;
  }

  // Finalize conversion from multiboot2 to multiboot info buffer
  l4_addr_t finalize()
  {
    assert(sizeof(_mbi) <= _size);
    size_t s = 0;
    if (_mbi.flags & L4UTIL_MB_VIDEO_INFO)
      {
        assert(sizeof(_mbi) + sizeof(_vbe_mode) + sizeof(_vbe_ctrl) <= _size);

        memcpy(_buf + s, &_vbe_mode, sizeof(_vbe_mode));
        _mbi.vbe_mode_info = reinterpret_cast<l4_uint32_t>(_buf + s);
        s += sizeof(_vbe_mode);

        memcpy(_buf + s, &_vbe_ctrl, sizeof(_vbe_ctrl));
        _mbi.vbe_ctrl_info = reinterpret_cast<l4_uint32_t>(_buf + s);
        s += sizeof(_vbe_ctrl);
      }
    void *mbi_addr = _buf + s;
    memcpy(mbi_addr, &_mbi, sizeof(_mbi));
    s += sizeof(_mbi);
    memset(_buf + s, 0, _size - s);

    return reinterpret_cast<l4_addr_t>(mbi_addr);
  }

private:
  char *end() { return _buf + _size; }

  l4util_mb2_tag_t *rotate_to_end(l4util_mb2_tag_t *tag)
  {
    char buf[1024];

    size_t size = l4_round_size(tag->size, L4UTIL_MB2_TAG_ALIGN_SHIFT);
    l4util_mb2_tag_t *dst_tag =
      reinterpret_cast<l4util_mb2_tag_t *>(end() - size);
    char *_src = reinterpret_cast<char *>(tag);

    while (size)
      {
        size_t copied = cxx::min(sizeof(buf), size);
        char *_dst = end() - copied;
        memcpy(buf, _src, copied);
        memmove(_src, _src + copied, (end() - _src) - copied);
        memcpy(_dst, buf, copied);
        size -= copied;
      }

    return dst_tag;
  }

  void reserve_from_end(size_t size)
  {
    size = l4_round_size(size, L4UTIL_MB2_TAG_ALIGN_SHIFT);
    assert(_size >= size);
    _size -= size;
  }

  char *_buf;
  size_t _size;
  const size_t _total_size;

  l4util_mb_info_t _mbi;
  l4util_mb_vbe_mode_t _vbe_mode;
  l4util_mb_vbe_ctrl_t _vbe_ctrl;
};

extern void *rsdp_start;
extern l4_uint32_t rsdp_size;

extern "C" l4_addr_t _multiboot2_to_multiboot(l4util_mb2_info_t *);

l4_addr_t _multiboot2_to_multiboot(l4util_mb2_info_t *mbi2)
{
  Mbi2_convertible_buffer buf(mbi2);

  l4util_mb2_tag_t *tag = buf.begin();

  while (tag->type != L4UTIL_MB2_TERMINATOR_INFO_TAG)
    {
      switch (tag->type)
        {
        case L4UTIL_MB2_BOOT_CMDLINE_INFO_TAG:
          buf.process_cmdline(tag);
          break;
        case L4UTIL_MB2_MODULE_INFO_TAG:
          buf.process_modules(tag);
          break;
        case L4UTIL_MB2_MEMORY_MAP_INFO_TAG:
          buf.process_memmap(tag);
          break;
        case L4UTIL_MB2_FRAMEBUFFER_INFO_TAG:
          buf.process_fb(tag);
          break;
        case L4UTIL_MB2_RSDP_OLD_INFO_TAG:
        case L4UTIL_MB2_RSDP_NEW_INFO_TAG:
          buf.process_rsdp(tag, &rsdp_start, &rsdp_size);
          break;
        default:
          tag = reinterpret_cast<l4util_mb2_tag_t *>(
            reinterpret_cast<char *>(tag)
            + l4_round_size(tag->size, L4UTIL_MB2_TAG_ALIGN_SHIFT));
          break;
        }
    }

  return buf.finalize();
}

