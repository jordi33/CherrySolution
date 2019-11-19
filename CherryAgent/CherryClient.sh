#!/bin/bash
### BEGIN INIT INFO
# Provides: cherryclient
# Required-Start: $syslog
# Required-Stop: $syslog
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Short-Description: cherry client
# Description:
### END INIT INFO
case "$1" in
    "start")
        echo "cherry client is starting"
        # Starting Programm
        /home/poppy/miniconda/bin/python /home/poppy/Documents/connectionServ/Start.py --no-display
        ;;
    "stop")
        echo "cherry client stop not implemented"
        # Ending Programm
        ;;
    *)
        echo "Use: /etc/init.d/CherryClient {start|stop}"
        exit 1
        ;;
esac
exit 0
