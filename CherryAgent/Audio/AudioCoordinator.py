from threading import Thread

from Audio import Mic as m
from Audio import PoppySound as sp


class AudioCoordinator:

    def __init__(self):
        self.input = m.MicroProvider()
        self.output = sp.PSound(self.__output_terminated, self.input.resume)
        self.output_thread = None
        self.kill_output_bool = False
        self.is_playing = False

    def play_remote(self, path, duration):
        if not self.is_playing:
        # if self.output_thread is None:  #or not self.output_thread.isAlive():
            self.input.pause()
            self.output_thread = Thread(target=self.output.play_sound_from_server, args=[(lambda: self.kill_output_bool), path, duration])
            self.output_thread.start()
            self.is_playing = True
        else:
            print("Cannot play two sounds at the same time")

    def play_local(self, name, duration):
        if not self.is_playing:
        #if self.output_thread is None:  # and not self.output_thread.isAlive():
            self.input.pause()
            self.output_thread = Thread(target=self.output.play_local_sound, args=[(lambda: self.kill_output_bool), name, duration])
            self.output_thread.start()
            self.is_playing = True

    def __output_terminated(self):
        self.is_playing = False
        self.input.resume()
        if self.output_thread is not None:
            self.output_thread = None

    def start_mic_listenner(self, input_callback):
        self.input_thread = Thread(target=self.input.listen, args=[input_callback], name="Audio_IN").start()

    def kill_output(self):
        self.kill_output_bool = True
        print(self.output_thread is not None)
        if self.output_thread is not None:
            self.output_thread.join()
            self.output_thread = None
            print("Kill")
        self.kill_output_bool = False

    def close(self):
        self.input.close()
        if self.input_thread is not None:
            print "Closing audio input_thread..."
            self.input_thread.join()
            self.input_thread = None
            print "\t Done."
        if self.output_thread is not None:
            print "Closing audio output_thread..."
            self.output_thread.join()
            self.output_thread = None
            print "\t Done."
