from threading import Thread

from Comm import Server

class ConnectionCoordinator:

    def __init__(self, on_message, on_error, on_close, on_open):
        self.is_connected = False
        self.__server = Server.ServerCnx()
        self.__server.init(on_message, on_error, on_close, on_open)

    def send_audio(self, data):
        if self.is_connected is True:
            self.__server.recognize_audio(data)

    def open(self):
        self.socket_thread = Thread(target=self.__server.connect, args=[], name="Socket").start()

    def close(self):
        self.__server.close()
        if self.socket_thread is not None:
            print "Closing server's websocket... (thread level)"
            self.socket_thread.join()
            self.socket_thread = None
            self.is_connected = False
            print "\t Done."
