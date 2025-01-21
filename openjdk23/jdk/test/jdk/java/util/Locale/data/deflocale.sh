#!/bin/sh
#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#
cat /etc/*release
uname -a
echo "Testing all available locales"
/usr/bin/locale -a | while read line; do
    echo ""
    echo "OS Locale: " $line
    env LC_ALL= LC_CTYPE= LC_MESSAGES= LANG=$line $1 $2 $3 $4 $5 $6 $7 $8 $9 PrintDefaultLocale
done

echo ""
echo "Testing some typical combinations"
echo ""
while read lcctype lcmessages; do
    if [ "$lcctype" = "#" -o "$lcctype" = "" ]; then
        continue
    fi
    echo ""
    echo "OS Locale (LC_CTYPE: "$lcctype", LC_MESSAGES: "$lcmessages")"
    env LC_ALL= LC_CTYPE=$lcctype LC_MESSAGES=$lcmessages $1 $2 $3 $4 $5 $6 $7 $8 $9 PrintDefaultLocale
done < deflocale.input
