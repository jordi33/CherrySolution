import websocket


class CmConnector():

    def __init__(self):
        self.cm_url = "ws://192.168.0.105:8002/newpoppy?=1"  # /connectpoppy

    def connect_to_cm(self, on_message_callback, on_error_callback, on_close_callback, on_open_callback):
        self.ws = websocket.WebSocketApp(self.cm_url, on_message=on_message_callback, on_error=on_error_callback,
                                         on_close=on_close_callback)
        self.ws.on_open = on_open_callback
        self.ws.run_forever()

    def send(self, message):
        self.ws.send("""{"Command":"Recognize",
                         "Priority":"Normal",
                         "Target": "1"
                         "SoundData":" 
                         """ + message + """ "} """)
