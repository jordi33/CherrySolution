import json
import logging
from Core.conf import *
import websocket
from Comm import Commands
from json import JSONEncoder
import platform

class ServerCnx():

    def __init__(self):
        self.Connected = False
        self.cm_url = ws_server_ip + ":" + ws_server_port + poppy_connect_url + "?id=" + platform.uname()[1]

    def init(self, on_message_callback, on_error_callback, on_close_callback, on_open_callback):
        try:
            self.ws = websocket.WebSocketApp(self.cm_url, on_message=on_message_callback, on_error=on_error_callback,
                                             on_close=on_close_callback)
            self.ws.on_open = on_open_callback
        except Exception as ex:
            logging.warning("Error when creating web socket: %s ", ex)

    def connect(self):
        try:
            self.ws.run_forever()
        except Exception as ex:
            self.Connected = False
            logging.warning("Error when connecting to server: %s ", ex)

    def send(self, message):
        try:
            self.ws.send(message.encode("utf-8"))
        except Exception as ex:
            logging.warning("Error when sending data: %s ", ex)

    def recognize_audio(self, in_data):
        try:
            self.ws.send(in_data, 0x2)
        except Exception as ex:
            logging.warning("Error while sending recognize request: %s ", ex)

    def close(self):
        print "Closing server's websocket..."
        if self.ws is not None:
            self.ws.close()
        self.ws = None
        print "\t Done."
