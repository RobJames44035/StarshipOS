#  StarshipOS Copyright (c) 2025. R.A. James

import os
import re
import sys
import tempfile
import subprocess
from urllib.request import urlopen
from urllib.error import HTTPError, URLError

ARTIFACTS_URL = "http://autobuild.buildroot.net/artefacts/"
BASE_DIR = os.path.realpath(os.path.join(os.path.dirname(__file__), "../../.."))


def log_file_path(builddir, stage, logtofile=True):
    """Return path to log file"""
    return "{}-{}.log".format(builddir, stage) if logtofile else None


def open_log_file(builddir, stage, logtofile=True):
    """
    Open a file for logging and return its handler.
    If logtofile is True, returns sys.stdout. Otherwise opens a file
    with a suitable name in the build directory.
    """
    return open(log_file_path(builddir, stage, logtofile), 'a+') if logtofile else sys.stdout


def basepath(relpath=""):
    """Return the absolute path for a file or directory relative to the Buildroot top directory."""
    return os.path.join(BASE_DIR, relpath)


def filepath(relpath):
    return os.path.join(BASE_DIR, "support/testing", relpath)


def download(dldir, filename):
    finalpath = os.path.join(dldir, filename)
    if os.path.exists(finalpath):
        return finalpath

    if not os.path.exists(dldir):
        os.makedirs(dldir)

    tmpfile = tempfile.mktemp(dir=dldir)
    print("Downloading to {}".format(tmpfile))

    try:
        url_fh = urlopen(os.path.join(ARTIFACTS_URL, filename))
        with open(tmpfile, "w+b") as tmpfile_fh:
            tmpfile_fh.write(url_fh.read())
    except (HTTPError, URLError) as err:
        os.unlink(tmpfile)
        raise err

    print("Renaming from {} to {}".format(tmpfile, finalpath))
    os.rename(tmpfile, finalpath)
    return finalpath


def run_cmd_on_host(builddir, cmd):
    """Call subprocess.check_output and return the text output."""
    out = subprocess.check_output(cmd,
                                  stderr=open(os.devnull, "w"),
                                  cwd=builddir,
                                  env={"LANG": "C"},
                                  universal_newlines=True)
    return out


def get_elf_arch_tag(builddir, prefix, fpath, tag):
    """
    Runs the cross readelf on 'fpath', then extracts the value of tag 'tag'.
    Example:
    >>> get_elf_arch_tag('output', 'arm-none-linux-gnueabi-',
                         'bin/busybox', 'Tag_CPU_arch')
    v5TEJ
    >>>
    """
    cmd = ["host/bin/{}-readelf".format(prefix),
           "-A", os.path.join("target", fpath)]
    out = run_cmd_on_host(builddir, cmd)
    regexp = re.compile(r"^  {}: (.*)$".format(tag))
    for line in out.splitlines():
        m = regexp.match(line)
        if not m:
            continue
        return m.group(1)
    return None


def get_file_arch(builddir, prefix, fpath):
    return get_elf_arch_tag(builddir, prefix, fpath, "Tag_CPU_arch")


def get_elf_prog_interpreter(builddir, prefix, fpath):
    """
    Runs the cross readelf on 'fpath' to extract the program interpreter
    name and returns it.
    Example:
    >>> get_elf_prog_interpreter('br-tests/TestExternalToolchainLinaroArm',
                                 'arm-linux-gnueabihf',
                                 'bin/busybox')
    /lib/ld-linux-armhf.so.3
    >>>
    """
    cmd = ["host/bin/{}-readelf".format(prefix),
           "-l", os.path.join("target", fpath)]
    out = run_cmd_on_host(builddir, cmd)
    regexp = re.compile(r"^ *\[Requesting program interpreter: (.*)\]$")
    for line in out.splitlines():
        m = regexp.match(line)
        if not m:
            continue
        return m.group(1)
    return None


def img_round_power2(img):
    """
    Rounds up the size of an image file to the next power of 2
    """
    sz = os.stat(img).st_size
    pow2 = 1
    while pow2 < sz:
        pow2 = pow2 << 1
    with open(img, 'ab') as f:
        f.truncate(pow2)
