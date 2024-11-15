
#ifndef CONSOLE_H
#define CONSOLE_H

#include "kernel/types.h"
#include "window.h"
#include "device.h"
#include "string.h"

/*
console_create_root creates the very first global console that
is used for kernel debug output.  The singleton "console_root"
must be statically allocated so that is usable at early
startup before memory allocation is available.
*/

extern struct console console_root;

struct console * console_create_root();

/*
Any number of other consoles can be created and manipulated
on top of existing windows.
*/

struct console * console_create( struct window *w );
struct console * console_addref( struct console *c );
void console_delete( struct console *c );

void console_reset( struct console *c );
int  console_post( struct console *c, const char *data, int length );
int  console_write( struct console *c, const char *data, int length );
int  console_read( struct console *c, char *data, int length );
int  console_read_nonblock( struct console *c, char *data, int length );
int  console_getchar( struct console *c );
void console_putchar( struct console *c, char ch );
void console_putstring( struct console *c, const char *str );
void console_heartbeat( struct console *c );
void console_size( struct console *c, int *xsize, int *ysize );

#endif
