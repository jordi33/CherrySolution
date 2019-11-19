import datetime
import logging
from collections import deque

import pyaudio
import math
import time
import audioop
import PyAudioController as py


class MicroProvider:

    def __init__(self):
        self.__chunk = 1024
        self.__rate = 44100
        self.__channels = 1
        self.__device_index = 1
        self.__silence_limit = 1
        self.__is_listening = False
        self.__is_opened = False
        self.__consumer = None
        #self.__threshold = self.__measure_audio_intensity(100)
        self.tmpListenLastState = 0
        self.tmpListenAndSendLastState = 0

    def __measure_audio_intensity(self, num_samples):
        logging.debug("Measuring optimal micrpphone intensity")

        return py.PyAudioController.getInstance().audio_intensity(num_samples)
    """
    def __start_listenning(self):
        #stream = py.PyAudioController.getInstance().start_listenning()
        print "IN : __start_listenning"
        while self.__is_opened:
            if self.tmpListenLastState == 0:
                print "Start Listenning at : " + str(datetime.datetime.now())
                self.tmpListenLastState = 1
            cur_data = py.PyAudioController.getInstance().streamInput.read(
                self.__chunk, exception_on_overflow=False)
            #print "Listenning"
            if self.__is_listening:
                if self.tmpListenAndSendLastState == 0:
                    print "Start Sending at : " + str(datetime.datetime.now())
                    self.tmpListenAndSendLastState = 1
                self.__consumer(cur_data)
                #print "Listenning & Sending"
            else:
                if self.tmpListenAndSendLastState == 1:
                    print "Stop Sending at : " + str(datetime.datetime.now())
                    self.tmpListenAndSendLastState = 0
                pass

        print "\tClosing stream..."
        #stream.close()
        print "\t Done."
        stream = None
    """

    def __start_listenning(self):
        #stream = py.PyAudioController.getInstance().start_listenning()
        print "IN : __start_listenning"
        while self.__is_opened:
            cur_data = py.PyAudioController.getInstance().streamInput.read(
                self.__chunk, exception_on_overflow=False)
            if self.__is_listening:
                self.__consumer(cur_data)
            else:
                pass
        print "\tClosing stream..."
        #stream.close()
        print "\t Done."
        stream = None

    def pause(self):
        self.__is_listening = False
        print(" ------ Mic OFF ------ ")

    def resume(self):
        self.__is_listening = True
        print("------ Mic ON ------ ")

    def listen(self, consumer=None):
        self.__consumer = consumer
        try:
            self.__is_opened = True
            self.__is_listening = True
            self.__start_listenning()
        except Exception as ex:
            logging.error("Error when trying to open input sound stream : %s", ex)

    def stop_listen(self):
        if self.__is_opened:
            try:
                self.__is_opened = False
            except Exception as ex:
                logging.error("Error when trying to close input sound stream : %s", ex)
        else:
            logging.debug("Cannot close input audio stream - already closed")

    def close(self):
        print "Closing audio listener..."
        self.__is_opened = False
        self.__is_listening = False
        print "\t Done."
