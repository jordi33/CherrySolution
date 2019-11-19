import threading
import time

import pyaudio
import requests

from Audio import PyAudioController as py
from Core.conf import *

class PSound:
    def __init__(self, callback, mic_resume):
        self.is_playing = False
        self.on_end_callback = callback
        self.mic_resume_callback = mic_resume

    def play_sound_from_server(self, kill_output, path, duration=0):
        print "IN : play_sound_from_server"
        remote_file = file_server_ip + ":" + file_server_port + get_file_url + path
        self.__play_sound(kill_output, remote_file, duration)
        print "OUT : play_sound_from_server"

    def play_local_sound(self, kill_output, name, duration=0):
        print "IN : play_local_sound"
        self.__play_sound(kill_output, "/home/poppy/Documents/musics/" + name + ".mp3", duration)
        print "OUT play_local_sound"


    def __play_sound(self, kill_output, name, duration=0):
        duration = duration - 2
        print "IN __play_sound"
        if self.is_playing:
            print "SoundPlayer : Cannot play two sounds at the same time."
            return
        else:
            try:
                self.is_playing = True
                py.PyAudioController.getInstance().play_sound(kill_output, name)

                self.mic_resume_callback()
                print "IN __play_sound - stop playing"
                self.is_playing = False
            except Exception as ex:
                print "Exception: SoundPlayer.__play_sound : " + ex.message
            finally:
                self.p = None
                self.is_playing = False
                print "OUT __play_sound before callback"
                self.on_end_callback()
                print "OUT __play_sound after callback"
