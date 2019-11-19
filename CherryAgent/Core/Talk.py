import pyaudio

class Talk:
    def __init__(self):
        self.chunk = 1024
        self.p = pyaudio.PyAudio()


    