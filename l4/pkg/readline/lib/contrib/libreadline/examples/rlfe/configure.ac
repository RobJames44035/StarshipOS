dnl Process this file with autoconf to produce a configure script.
dnl last update: Fri May 10 10:45:29 EDT 2024

VERSION=0.4

AC_INIT(rlfe, 0.4)
AC_CONFIG_SRCDIR(rlfe.c)
AC_CONFIG_HEADERS(config.h)
AC_SUBST(VERSION)

dnl we don't do anything with this yet
AC_SUBST(INCDIR)

dnl
dnl Define some useful macros
dnl
AC_DEFUN([AC_PROGRAM_SOURCE],
[AC_REQUIRE([AC_PROG_CPP])AC_PROVIDE([$0])cat > conftest.c <<EOF
#include "confdefs.h"
[$1]
_CUT_HERE_
[$2]
EOF
eval "$ac_cpp conftest.c 2>&5 | sed -e '1,/_CUT_HERE_/d' -e 's/ //g' > conftest.out"
. ./conftest.out
rm -f conftest*
])dnl
dnl
define(AC_NOTE,
[echo "$1" 1>&AS_MESSAGE_FD
])dnl

old_CFLAGS="$CFLAGS"
AC_PROG_CC
AC_PROG_CPP

AC_PROG_MAKE_SET

AC_USE_SYSTEM_EXTENSIONS

AC_TRY_RUN(int main(){return (0);},,[
if test $CC != cc ; then
AC_NOTE(Your $CC failed - restarting with CC=cc)
AC_NOTE()
CC=cc
export CC
exec $0 $configure_args
fi
])

AC_TRY_RUN(int main(){return(0);},,
exec 5>&2
eval $ac_link
AC_NOTE(CC=$CC; CFLAGS=$CFLAGS; LIBS=$LIBS;)
AC_NOTE($ac_compile)
AC_MSG_ERROR(Can't run the compiler - sorry))

AC_TRY_RUN([
int main()
{
  int __something_strange_();
  __something_strange_(0);
}
],AC_MSG_ERROR(Your compiler does not set the exit status - sorry))

AC_PROG_AWK

if test -f etc/toolcheck; then
AC_MSG_CHECKING(for buggy tools)
sh etc/toolcheck 1>&AS_MESSAGE_FD
fi

dnl
dnl    ****  special unix variants  ****
dnl

AC_MSG_CHECKING(for System V)
AC_TRY_COMPILE(
[#include <sys/types.h>
#include <signal.h>
#include <fcntl.h>], [int x = SIGCHLD | FNDELAY;], , AC_DEFINE(SYSV))
AC_MSG_RESULT(done)

AC_MSG_CHECKING(for Solaris 2.x)
AC_EGREP_CPP(yes,
[#if defined(SVR4) && defined(sun)
  yes
#endif
], LIBS="$LIBS -lsocket -lnsl -lkstat")

AC_MSG_CHECKING(for POSIX)
AC_TRY_RUN([
#include <unistd.h>
#include <stdlib.h>

int
main()
{
#ifdef _POSIX_VERSION
  return (0);
#else
  return (1);
#endif
}
], posix=yes, , posix=yes)
if test -n "$posix"; then
AC_DEFINE(POSIX)
AC_MSG_RESULT(yes)
else
AC_MSG_RESULT(no)
fi

dnl
dnl libc
dnl

dnl system calls
AC_CHECK_FUNCS(seteuid setreuid _exit)

dnl library functions
AC_CHECK_FUNCS(getcwd memcpy memmove mkfifo strerror strftime utimes)

dnl locale
AC_CHECK_FUNCS(setlocale nl_langinfo)

dnl
dnl    ****  select()  ****
dnl

AC_MSG_CHECKING(select)
AC_CHECK_HEADERS(sys/select.h)
AC_CHECK_FUNCS(select)

if test X$av_cv_func_select = Xno; then
LIBS="$LIBS -lnet -lnsl"
AC_MSG_CHECKING(select with $LIBS)
AC_TRY_LINK(
[
    #include <sys/select.h>
],
[ 
    select(0, 0, 0, 0, 0);
],
AC_MSG_RESULT(yes), 
AC_MSG_ERROR(!!! no select - no screen)
)
fi

dnl
dnl    ****  check the select implementation ****
dnl

AC_MSG_CHECKING(select return value)
AC_TRY_RUN([
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>

char *nam = "/tmp/conftest$$";

#ifdef NAMEDPIPE

#ifndef O_NONBLOCK
#define O_NONBLOCK O_NDELAY
#endif
#ifndef S_IFIFO
#define S_IFIFO 0010000
#endif


int
main()
{
#ifdef FD_SET
  fd_set f;
#else
  int f;
#endif

#ifdef __FreeBSD__
/* From Andrew A. Chernov (ache@astral.msk.su):
 * opening RDWR fifo fails in BSD 4.4, but select return values are
 * right.
 */
  exit(0);
#endif
  (void)alarm(5);
#ifdef POSIX
  if (mkfifo(nam, 0777))
#else
  if (mknod(nam, S_IFIFO|0777, 0))
#endif
    exit(1);
  close(0);
  if (open(nam, O_RDWR | O_NONBLOCK))
    exit(1);
  if (write(0, "TEST", 4) == -1)
    exit(1);

#else

#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

int
main()
{
  int s1, s2, l;
  struct sockaddr_un a;
#ifdef FD_SET
  fd_set f;
#else
  int f;
#endif

  (void)alarm(5);
  if ((s1 = socket(AF_UNIX, SOCK_STREAM, 0)) == -1)
    exit(1);
  a.sun_family = AF_UNIX;
  strcpy(a.sun_path, nam);
  (void) unlink(nam);
  if (bind(s1, (struct sockaddr *) &a, strlen(nam)+2) == -1)
    exit(1);
  if (listen(s1, 2))
    exit(1);
  if (fork() == 0)
    {
      if ((s2 = socket(AF_UNIX, SOCK_STREAM, 0)) == -1)
	kill(getppid(), 3);
      (void)connect(s2, (struct sockaddr *)&a, strlen(nam) + 2);
      if (write(s2, "HELLO", 5) == -1)
	kill(getppid(), 3);
      exit(0);
    }
  l = sizeof(a);
  close(0);
  if (accept(s1, (struct sockaddr *)&a, &l))
    exit(1);
#endif


#ifdef FD_SET
  FD_SET(0, &f);
#else
  f = 1;
#endif
  if (select(1, &f, 0, 0, 0) == -1)
    exit(1);
  if (select(1, &f, &f, 0, 0) != 2)
    exit(1);
  exit(0);
}
],AC_NOTE(- select is ok),
AC_NOTE(- select can't count) AC_DEFINE(SELECT_BROKEN))

dnl
dnl    ****  termcap or terminfo  ****
dnl
AC_MSG_CHECKING(for tgetent)
AC_TRY_LINK([extern char tgetent(void);],tgetent();,,
olibs="$LIBS"
LIBS="-lcurses $olibs"
AC_MSG_CHECKING(libcurses)
AC_TRY_LINK([extern char tgetent(void);],[
#ifdef __hpux
__sorry_hpux_libcurses_is_totally_broken_in_10_10();
#else
tgetent();
#endif
],,
LIBS="-ltermcap $olibs"
AC_MSG_CHECKING(libtermcap)
AC_TRY_LINK([extern char tgetent(void);],tgetent();,,
LIBS="-ltermlib $olibs"
AC_MSG_CHECKING(libtermlib)
AC_TRY_LINK([extern char tgetent(void);],tgetent();,,
LIBS="-lncurses $olibs"
AC_MSG_CHECKING(libncurses)
AC_TRY_LINK([extern char tgetent(void);],tgetent();,,
AC_MSG_ERROR(!!! no tgetent - no screen))))))

AC_TRY_RUN([
#include <string.h>
extern char *tgoto();
int
main()
{
 return(strcmp(tgoto("%p1%d", 0, 1), "1") ? 0 : 1);
}], AC_NOTE(- you use the termcap database),
AC_NOTE(- you use the terminfo database) AC_DEFINE(TERMINFO))
AC_MSG_CHECKING(ospeed)
AC_TRY_LINK([extern short ospeed;],[ospeed=5;],have_ospeed=yes,have_ospeed=no)
AC_MSG_RESULT($have_ospeed)
if test $have_ospeed = yes; then
	:
else
AC_DEFINE(NEED_OSPEED)
fi

dnl
dnl    ****  PTY specific things  ****
dnl
AC_MSG_CHECKING(for /dev/ptc)
if test -r /dev/ptc; then
AC_DEFINE(HAVE_DEV_PTC)
AC_MSG_RESULT(yes)
else
AC_MSG_RESULT(no)
fi

AC_MSG_CHECKING(for SVR4 ptys)
sysvr4ptys=
if test -c /dev/ptmx ; then
AC_TRY_LINK([#include <stdlib.h>],[ptsname(0);grantpt(0);unlockpt(0);],[AC_DEFINE(HAVE_SVR4_PTYS)
sysvr4ptys=1])
fi
if test -n "$sysvr4ptys"; then
AC_MSG_RESULT(yes)
else
AC_MSG_RESULT(no)
fi

AC_CHECK_FUNCS(getpt)

dnl check for openpty()
if test -z "$sysvr4ptys"; then
AC_CHECK_FUNCS(openpty,,
[AC_CHECK_LIB(util,openpty, [AC_DEFINE(HAVE_OPENPTY)] [LIBS="$LIBS -lutil"])])
fi

AC_MSG_CHECKING(for ptyranges)
if test -d /dev/ptym ; then
pdir='/dev/ptym'
else
pdir='/dev'
fi
dnl SCO uses ptyp%d
AC_EGREP_CPP(yes,
[#ifdef M_UNIX
   yes;
#endif
], ptys=`echo /dev/ptyp??`, ptys=`echo $pdir/pty??`)
dnl if test -c /dev/ptyp19; then
dnl ptys=`echo /dev/ptyp??`
dnl else
dnl ptys=`echo $pdir/pty??`
dnl fi
if test "$ptys" != "$pdir/pty??" ; then
p0=`echo $ptys | tr ' ' '\012' | sed -e 's/^.*\(.\).$/\1/g' | sort -u | tr -d '\012'`
p1=`echo $ptys | tr ' ' '\012' | sed -e 's/^.*\(.\)$/\1/g'  | sort -u | tr -d '\012'`
AC_DEFINE_UNQUOTED(PTYRANGE0,"$p0")
AC_DEFINE_UNQUOTED(PTYRANGE1,"$p1")
fi
AC_MSG_RESULT(done)

dnl    ****  pty mode/group handling ****
dnl
dnl support provided by Luke Mewburn <lm@rmit.edu.au>, 931222
AC_ARG_WITH(pty-mode, [  --with-pty-mode=mode    default mode for ptys], [ ptymode="${withval}" ])
AC_ARG_WITH(pty-group, [  --with-pty-group=group  default group for ptys], [ ptygrp="${withval}" ])
test -n "$ptymode" || ptymode=0620
if test -n "$ptygrp" ; then
AC_DEFINE_UNQUOTED(PTYMODE, $ptymode)
AC_DEFINE_UNQUOTED(PTYGROUP,$ptygrp)
else

AC_MSG_CHECKING(default tty permissions/group)
rm -f conftest_grp
AC_TRY_RUN([
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
int
main()
{
  struct stat sb;
  char *x,*ttyname();
  int om, m;
  FILE *fp;

  if (!(x = ttyname(0))) exit(1);
  if (stat(x, &sb)) exit(1);
  om = sb.st_mode;
  if (om & 002) exit(0);
  m = system("mesg y");
  if (m == -1 || m == 127) exit(1);
  if (stat(x, &sb)) exit(1);
  m = sb.st_mode;
  if (chmod(x, om)) exit(1);
  if (m & 002) exit(0);
  if (sb.st_gid == getgid()) exit(1);
  if (!(fp=fopen("conftest_grp", "w")))
    exit(1);
  fprintf(fp, "%d\n", sb.st_gid);
  fclose(fp);
  exit(0);
}
],[
    if test -f conftest_grp; then
	ptygrp=`cat conftest_grp`
	AC_NOTE([- pty mode: $ptymode, group: $ptygrp])
	AC_DEFINE_UNQUOTED(PTYMODE, $ptymode)
	AC_DEFINE_UNQUOTED(PTYGROUP,$ptygrp)
    else
	AC_NOTE(- ptys are world accessable)
    fi
],[
    WRITEPATH=''
    XTERMPATH=''
    AC_PATH_PROG(WRITEPATH, write)
    AC_PATH_PROG(XTERMPATH, xterm)
    found=
    if test -n "$WRITEPATH$XTERMPATH"; then
      findfollow=
      lsfollow=
      found=`find $WRITEPATH $XTERMPATH -follow -print 2>/dev/null`
      if test -n "$found"; then
	findfollow=-follow
	lsfollow=L
      fi
      if test -n "$XTERMPATH"; then
	ptygrpn=`ls -l$lsfollow $XTERMPATH | sed -n -e 1p | $AWK '{print $4}'`
	if test tty != "$ptygrpn"; then
	  XTERMPATH=
	fi
      fi
    fi
    if test -n "$WRITEPATH$XTERMPATH"; then
      found=`find $WRITEPATH $XTERMPATH $findfollow -perm -2000 -print` 
      if test -n "$found"; then
	ptygrp=`ls -ln$lsfollow $found | sed -n -e 1p | $AWK '{print $4}'`
	AC_NOTE([- pty mode: $ptymode, group: $ptygrp])
	AC_DEFINE_UNQUOTED(PTYMODE, $ptymode)
	AC_DEFINE_UNQUOTED(PTYGROUP,$ptygrp)
      else
	AC_NOTE(- ptys are world accessable)
      fi
    else
      AC_NOTE(- can't determine - assume ptys are world accessable)
    fi
  ]
)
rm -f conftest_grp
fi

dnl
dnl    ****  signal handling  ****
dnl
if test -n "$posix" ; then

dnl POSIX has reliable signals with void return type.
AC_NOTE(assuming posix signal definition)
AC_DEFINE(SIGVOID)
AC_DEFINE(HAVE_POSIX_SIGNALS)
else

AC_MSG_CHECKING(return type of signal handlers)
AC_TRY_COMPILE(
[#include <sys/types.h>
#include <signal.h>
#ifdef signal
#undef signal
#endif
extern void (*signal ()) ();], [int i;], AC_DEFINE(SIGVOID))
AC_MSG_CHECKING(sigset)
AC_TRY_LINK([
#include <sys/types.h>
#include <signal.h>
],[
#ifdef SIGVOID
sigset(0, (void (*)())0);
#else
sigset(0, (int (*)())0);
#endif
], AC_DEFINE(USESIGSET))
AC_MSG_CHECKING(signal implementation)
AC_TRY_RUN([
#include <sys/types.h>
#include <signal.h>

#ifndef SIGCLD
#define SIGCLD SIGCHLD
#endif
#ifdef USESIGSET
#define signal sigset
#endif

int got;

#ifdef SIGVOID
void
#endif
hand()
{
  got++;
}

int
main()
{
  /* on hpux we use sigvec to get bsd signals */
#ifdef __hpux
  (void)signal(SIGCLD, hand);
  kill(getpid(), SIGCLD);
  kill(getpid(), SIGCLD);
  if (got < 2)
    return(1);
#endif
  return(0);
}
],,AC_DEFINE(SYSVSIGS))

fi

AC_CHECK_HEADERS(sys/stropts.h sys/wait.h sgtty.h sys/select.h)
AC_CHECK_HEADERS(term.h)

AC_CONFIG_FILES(Makefile)
AC_OUTPUT
