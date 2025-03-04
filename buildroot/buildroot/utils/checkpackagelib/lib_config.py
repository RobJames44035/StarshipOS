#  StarshipOS Copyright (c) 2025. R.A. James

# See utils/checkpackagelib/readme.txt before editing this file.
# Kconfig generates errors if someone introduces a typo like "boool" instead of
# "bool", so below check functions don't need to check for things already
# checked by running "make menuconfig".

import re

from checkpackagelib.base import _CheckFunction
from checkpackagelib.lib import ConsecutiveEmptyLines  # noqa: F401
from checkpackagelib.lib import EmptyLastLine          # noqa: F401
from checkpackagelib.lib import NewlineAtEof           # noqa: F401
from checkpackagelib.lib import TrailingSpace          # noqa: F401
from checkpackagelib.tool import NotExecutable         # noqa: F401


def _empty_or_comment(text):
    line = text.strip()
    # ignore empty lines and comment lines indented or not
    return line == "" or line.startswith("#")


def _part_of_help_text(text):
    return text.startswith("\t  ")


# used in more than one check
entries_that_should_not_be_indented = [
    "choice", "comment", "config", "endchoice", "endif", "endmenu", "if",
    "menu", "menuconfig", "source"]


class AttributesOrder(_CheckFunction):
    attributes_order_convention = {
        "bool": 1, "prompt": 1, "string": 1, "default": 2, "depends": 3,
        "select": 4, "help": 5}

    def before(self):
        self.state = 0

    def check_line(self, lineno, text):
        if _empty_or_comment(text) or _part_of_help_text(text):
            return

        attribute = text.split()[0]

        if attribute in entries_that_should_not_be_indented:
            self.state = 0
            return
        if attribute not in self.attributes_order_convention.keys():
            return
        new_state = self.attributes_order_convention[attribute]
        wrong_order = self.state > new_state

        # save to process next line
        self.state = new_state

        if wrong_order:
            return ["{}:{}: attributes order: type, default, depends on,"
                    " select, help ({}#_config_files)"
                    .format(self.filename, lineno, self.url_to_manual),
                    text]


class CommentsMenusPackagesOrder(_CheckFunction):
    def before(self):
        self.level = 0
        self.menu_of_packages = ["The top level menu"]
        self.new_package = ""
        self.package = [""]
        self.print_package_warning = [True]
        self.state = ""

    def get_level(self):
        return len(self.state.split('-')) - 1

    def initialize_package_level_elements(self, text):
        try:
            self.menu_of_packages[self.level] = text[:-1]
            self.package[self.level] = ""
            self.print_package_warning[self.level] = True
        except IndexError:
            self.menu_of_packages.append(text[:-1])
            self.package.append("")
            self.print_package_warning.append(True)

    def initialize_level_elements(self, text):
        self.level = self.get_level()
        self.initialize_package_level_elements(text)

    def check_line(self, lineno, text):
        # We only want to force sorting for the top-level menus
        if self.filename not in ["fs/Config.in",
                                 "package/Config.in",
                                 "package/Config.in.host",
                                 "package/kodi/Config.in"]:
            return

        source_line = re.match(r'^\s*source ".*/([^/]*)/Config.in(.host)?"', text)

        if text.startswith("comment "):
            if not self.state.endswith("-comment"):
                self.state += "-comment"

            self.initialize_level_elements(text)

        elif text.startswith("if "):
            self.state += "-if"

            self.initialize_level_elements(text)

        elif text.startswith("menu "):
            if self.state.endswith("-comment"):
                self.state = self.state[:-8]

            self.state += "-menu"

            self.initialize_level_elements(text)

        elif text.startswith("endif") or text.startswith("endmenu"):
            if self.state.endswith("-comment"):
                self.state = self.state[:-8]

            if text.startswith("endif"):
                self.state = self.state[:-3]

            elif text.startswith("endmenu"):
                self.state = self.state[:-5]

            self.level = self.get_level()

        elif source_line:
            self.new_package = source_line.group(1)

            # We order _ before A, so replace it with .
            new_package_ord = self.new_package.replace('_', '.')

            if self.package[self.level] != "" and \
               self.print_package_warning[self.level] and \
               new_package_ord < self.package[self.level]:
                self.print_package_warning[self.level] = False
                prefix = "{}:{}: ".format(self.filename, lineno)
                spaces = " " * len(prefix)
                return ["{prefix}Packages in: {menu},\n"
                        "{spaces}are not alphabetically ordered;\n"
                        "{spaces}correct order: '-', '_', digits, capitals, lowercase;\n"
                        "{spaces}first incorrect package: {package}"
                        .format(prefix=prefix, spaces=spaces,
                                menu=self.menu_of_packages[self.level],
                                package=self.new_package),
                        text]

            self.package[self.level] = new_package_ord


class HelpText(_CheckFunction):
    HELP_TEXT_FORMAT = re.compile(r"^\t  .{,62}$")
    HELP_TEXT_FORMAT_1 = re.compile(r"^\t  \S.{,61}$")
    URL_ONLY = re.compile(r"^(http|https|git)://\S*$")

    def before(self):
        self.help_text = False

    def check_line(self, lineno, text):
        if _empty_or_comment(text):
            return

        entry = text.split()[0]

        if entry in entries_that_should_not_be_indented:
            self.help_text = False
            return
        if text.strip() == "help":
            self.help_text = True
            self.help_first_line = True
            return

        if not self.help_text:
            return

        if self.help_first_line:
            help_text_match = self.HELP_TEXT_FORMAT_1
            self.help_first_line = False
        else:
            help_text_match = self.HELP_TEXT_FORMAT
        if help_text_match.match(text.rstrip()):
            return
        if self.URL_ONLY.match(text.strip()):
            return
        return ["{}:{}: help text: <tab><2 spaces><62 chars>"
                " ({}#writing-rules-config-in)"
                .format(self.filename, lineno, self.url_to_manual),
                text,
                "\t  " + "123456789 " * 6 + "12"]


class Indent(_CheckFunction):
    ENDS_WITH_BACKSLASH = re.compile(r"^[^#].*\\$")
    entries_that_should_be_indented = [
        "bool", "default", "depends", "help", "prompt", "select", "string"]

    def before(self):
        self.backslash = False

    def check_line(self, lineno, text):
        if _empty_or_comment(text) or _part_of_help_text(text):
            self.backslash = False
            return

        entry = text.split()[0]

        last_line_ends_in_backslash = self.backslash

        # calculate for next line
        if self.ENDS_WITH_BACKSLASH.search(text):
            self.backslash = True
        else:
            self.backslash = False

        if last_line_ends_in_backslash:
            if text.startswith("\t"):
                return
            return ["{}:{}: continuation line should be indented using tabs"
                    .format(self.filename, lineno),
                    text]

        if entry in self.entries_that_should_be_indented:
            if not text.startswith("\t{}".format(entry)):
                return ["{}:{}: should be indented with one tab"
                        " ({}#_config_files)"
                        .format(self.filename, lineno, self.url_to_manual),
                        text]
        elif entry in entries_that_should_not_be_indented:
            if not text.startswith(entry):
                # four Config.in files have a special but legitimate indentation rule
                if self.filename in ["package/Config.in",
                                     "package/Config.in.host",
                                     "package/kodi/Config.in",
                                     "package/x11r7/Config.in"]:
                    return
                return ["{}:{}: should not be indented"
                        .format(self.filename, lineno),
                        text]


class RedefinedConfig(_CheckFunction):
    CONFIG = re.compile(r"^\s*(menu|)config\s+(BR2_\w+)\b")
    IF = re.compile(r"^\s*if\s+([^#]*)\b")
    ENDIF = re.compile(r"^\s*endif\b")

    def before(self):
        self.configs = {}
        self.conditional = []

    def check_line(self, lineno, text):
        if _empty_or_comment(text) or _part_of_help_text(text):
            return

        m = self.IF.search(text)
        if m is not None:
            condition = m.group(1)
            self.conditional.append(condition)
            return

        m = self.ENDIF.search(text)
        if m is not None:
            self.conditional.pop()
            return

        m = self.CONFIG.search(text)
        if m is None:
            return
        config = m.group(2)

        key = (config, ' AND '.join(self.conditional))
        if key in self.configs.keys():
            previous_line = self.configs[key]
            return ["{}:{}: config {} redeclared (previous line: {})"
                    .format(self.filename, lineno, config, previous_line),
                    text]
        self.configs[key] = lineno
