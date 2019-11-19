import numpy
import pyaudio
import math
import audioop


# arecord -d 10 tt.wav -r 44100 -c 1 -D hw:1,0 -f S16_LE
class MicrophoneProvider():
    def __init__(self):
        self.chunk = 512
        self.rate = 44100
        self.channels = 1
        self.device_index = 2
        self.threshold = self.__measure_audio_intensity(100)
        self.silence_limit = 1
        self.started = False
        self.listening = False
        self.audio = numpy.empty((self.chunk), dtype="int16")

    def __measure_audio_intensity(self, num_samples):
        p = pyaudio.PyAudio()
        stream = p.open(format=pyaudio.paInt16,
                        channels=1,
                        rate=self.rate,
                        input=True,
                        frames_per_buffer=self.chunk,
                        input_device_index=self.device_index)
        values = [math.sqrt(abs(audioop.avg(stream.read(self.chunk), 4)))
                  for x in range(num_samples)]
        values = sorted(values, reverse=True)
        r = sum(values[:int(num_samples * 0.2)]) / int(num_samples * 0.2)
        print " Finished "
        print " Average Audio intensity is ", r
        stream.close()
        p.terminate()
        return r * 1.4

    def __mycallback(self, in_data, frame_count, time_info, status):
        self.audio = numpy.fromstring(in_data, dtype=numpy.int16)
        self.external_callback(in_data)
        return self.audio, pyaudio.paContinue

    def __listen_for_speech(self, callback, start_listening):
        self.external_callback = callback
        p = pyaudio.PyAudio()
        print "* Start listen_for_speech"
        stream = p.open(format=pyaudio.paInt16,
                        channels=self.channels,
                        rate=self.rate,
                        input=True,
                        frames_per_buffer=self.chunk,
                        input_device_index=self.device_index,
                        stream_callback=self.mycallback)
        print "* Listening mic. "
        self.started = True
        self.listening = start_listening
        #stream.start_stream()
        """
        cur_data = ''
        while self.started is True:
            cur_data = stream.read(self.chunk)
            print "hhh"
            if self.listening is True:
                callback(cur_data)
        """