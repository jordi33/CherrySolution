import pyaudio
import requests
import subprocess
import math
import time
import audioop
import logging



class PyAudioController:
    __instance = None
    __p = None

    @staticmethod
    def getInstance():
        """ Static access method. """
        if PyAudioController.__instance == None:
            PyAudioController()
        return PyAudioController.__instance

    def __init__(self):
        self.__chunk = 256  # 512
        self.__rate = 44100
        self.__channels = 1
        self.__device_index = 1
        self.__silence_limit = 1
        self.__is_listening = False
        self.__is_opened = False
        self.__consumer = None
        self.__silence = chr(0) * self.__chunk * self.__channels * 2


        print "start input stre"
        """ Virtually private constructor. """
        if PyAudioController.__instance != None:
            raise Exception("This class is a singleton!")
        else:
            PyAudioController.__instance = self
            self.p = pyaudio.PyAudio()
            self.streamInput = self.p.open(format=pyaudio.paInt16,
                                      channels=self.__channels,
                                      rate=self.__rate,
                                      input=True,
                                      frames_per_buffer=self.__chunk,
                                      input_device_index=self.__device_index,
                                      )
            self.streamInput.start_stream()

    def play_empty_sound(self):
        stream = self.p.open(format=2, channels=1, rate=13000, output=True)
        stream.write(self.__silence)

    def play_sound(self, kill_output, name):
        print "IN : play_sound"

        p = subprocess.Popen(["cvlc", name, "--play-and-exit", ">/dev/null 2>&1"])
        while p.poll() == None:
            if kill_output():
                p.terminate()
                break

        #stream = self.p.open(format=2, channels=1, rate=13000, output=True, frames_per_buffer=512)
        #with requests.get(name, stream=True) as r:
        #   r.raise_for_status()
        #    for chunk in r.iter_content(chunk_size=1024):
        #        if kill_output():
        #            break
        #        if chunk != None or chunk != '':
        #            stream.write(chunk, exception_on_underflow = False)
                    #print "Whiting chunk"


        print "OUT : play_sound"

    def audio_intensity(self, num_samples):
        stream = self.p.open(format=pyaudio.paInt16,
                             channels=self.__channels,
                             rate=self.__rate,
                             input=True,
                             frames_per_buffer=self.__chunk,
                             input_device_index=self.__device_index)

        values = [math.sqrt(abs(audioop.avg(stream.read(self.__chunk), 4)))
                  for x in range(num_samples)]
        values = sorted(values, reverse=True)
        r = sum(values[:int(num_samples * 0.2)]) / int(num_samples * 0.2)
        res = r * 20
        logging.debug("Average Audio intensity is: %d", res)
        stream.close()
        return res


    def start_listenning(self):
        print "Open input stream"
        pass

