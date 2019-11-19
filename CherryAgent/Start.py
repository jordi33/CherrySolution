#!/home/poppy/miniconda/bin/python
import json
import time
from Audio import AudioCoordinator as auc
import Comm.Commands as comm
from Primitives import PrimitiveProvider as pp
from Comm import ConnectionCoordinator as cco
import signal
import sys
from EyesAnimations import EmotionHandler
from Audio import PyAudioController as py



class Main:

    def __init__(self, display_eyes):
        self.init_move = "hello"
        self.connectionRefused = False
        self.Audio = auc.AudioCoordinator()
        py.PyAudioController.getInstance().play_empty_sound()
        self.PrimitiveProvider = pp.PrimitiveProvider()
        self.Server = cco.ConnectionCoordinator(self.on_message, self.on_error, self.on_close, self.on_open)
        self.Server.open()
        self.Audio.start_mic_listenner(self.on_sound_available)

        if display_eyes:
            self.EmotionHandler = None
        else:
            self.EmotionHandler = EmotionHandler.EmotionHandler()

    def on_sound_available(self, in_data):
        if self.Server is not None:
            self.Server.send_audio(in_data)

    def on_error(self, data):
        if data[0] == 111:
            self.connectionRefused = True
            self.init_move = "init_error"
            self.start_move_thread()
            self.Audio.play_local("init_error", 10)
        pass

    def on_close(self):
        self.Server.is_connected = False
        self.close()
        pass

    def on_open(self):
        self.Server.is_connected = True
        self.init_move = "hello"
        prim = self.PrimitiveProvider.getMovePrim("hello")
        prim.start()
        pass

    def on_message(self, data):
        # It's durty! fuuuuuu
        message = json.loads(data)
        print message
        if message['Action'] == "ReadAudioFile":
            command = comm.ReadAudioFile(message["Action"],
                                         message["Len"],
                                         message["Ext"],
                                         message["Path"], )
            self.Audio.play_remote(command.Path, command.Len)
        elif message['Action'] == "Move":
            if message["MoveName"] == "Dance":
               prim = self.PrimitiveProvider.getMovePrim("twist", "dance")
               prim.start()
            if message["MoveName"] == "Die":
                self.close()
            else:
                prim = self.PrimitiveProvider.getMovePrim(message["MoveName"], message["MoveDirectory"])
                prim.start()
                pass
        elif message["Action"] == "ChangeEmotion" and self.EmotionHandler is not None:
            self.EmotionHandler.changeEmotion(message["Emotion"])
        elif message["Action"] == "PlayChoregraphy":
            prim = self.PrimitiveProvider.getQueuePrim(message["Movements"])
            prim.start()
            self.Audio.play_local(message["Music"], len(message["Movements"])*10)
        elif message["Action"] == "Reset":
            print("Resetting program")
            self.Audio.kill_output()
            pass

    def start_move_thread(self):
        moveThread = self.PrimitiveProvider.getMovePrim(self.init_move)
        moveThread.start()
        pass

    def close(self):
        self.Server.close()
        self.Audio.close()
        self.PrimitiveProvider.close()
        if self.EmotionHandler is not None:
            self.EmotionHandler.close()
        sys.exit(1)


def exit_gracefully(signum, frame):
    signal.signal(signal.SIGINT, original_sigint)
    try:
        print "Trying to close Poppy agent"
        poppy.close()
        # sys.exit(1)
    except KeyboardInterrupt:
        print("Error, quitting unexpectedly")
        sys.exit(1)


def run_poppy():
    while True:
        time.sleep(1)
    print ">Exiting Main<"


if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] == "--no-display":
        poppy = Main(True)
    else:
        poppy = Main(False)
    original_sigint = signal.getsignal(signal.SIGINT)
    signal.signal(signal.SIGINT, exit_gracefully)
    run_poppy()
