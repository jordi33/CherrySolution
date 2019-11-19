using System;
using System.Diagnostics;
using System.IO;
using System.Runtime.Remoting.Channels;
using System.Threading;
using System.Threading.Tasks;
using Common;
using Google.Cloud.Speech.V1;
using Google.Protobuf;
using Monitoring;
using Timer = System.Timers.Timer;

namespace CloudServices.Impl
{
    public class GoogleStt : ISttService
    {
        private SpeechClient _speech;
        private SpeechClient.StreamingRecognizeStream _streamingCall;
        private readonly Task _collectResponses;
        public event GsttResultHandler OnResult;


        private readonly object _writeLock = new object();
        private bool _writeMore = false;
        private readonly Timer _timer;
        private readonly Timer _last_write_counter;
        private readonly Action _taskOnAudioReceived;
        private Task _taskAction;
        private bool _transcribing;
        private bool _testIsSpeaking = false;
        private LastModeWrite _lastWrite = LastModeWrite.Last;

        private long timerSinceData = 0;

        public GoogleStt()
        {
            _last_write_counter = new Timer(3000);
            _last_write_counter.Elapsed += (sender, e) =>
            {
                //WriteCompleteAsync();
            };
            _timer = new Timer(60000);
            _timer.Elapsed += (sender, e) =>
            {
                WriteCompleteAsync();
            };

            var timer2 = new Timer(3000);
            timer2.Elapsed += (sender, e) => {
                WriteCompleteAsync();
            };

            _taskOnAudioReceived = async () =>
            {
                try
                {
                    Log.Info("Transcription Received");
                    while (_transcribing && await _streamingCall.ResponseStream.MoveNext(default(CancellationToken)))
                    {
                        if (_streamingCall == null)
                        {
                            WriteCompleteAsync();
                        }
                        else
                        {
                            if (_streamingCall.ResponseStream.Current != null &&
                                _streamingCall.ResponseStream.Current.Results.Count == 0 &&
                                _streamingCall.ResponseStream.Current.SpeechEventType == StreamingRecognizeResponse.Types.SpeechEventType.EndOfSingleUtterance)
                            {
                                if (_testIsSpeaking == false)
                                {
                                    WriteCompleteAsync();
                                }
                                else
                                {
                                    timer2.Start();
                                }
                            }
                            else
                            {
                                if (_streamingCall.ResponseStream.Current.Results.Count != 0)
                                {
                                    _testIsSpeaking = true;
                                }
                                timer2.Stop();
                                foreach (var result in _streamingCall.ResponseStream.Current.Results)
                                {
                                    Log.Debug($"__{result.Alternatives[0].Transcript}");
                                    if (result.IsFinal || _streamingCall.ResponseStream.Current.SpeechEventType == StreamingRecognizeResponse.Types.SpeechEventType.EndOfSingleUtterance)
                                    {
                                        Log.Info("Final Transcription");
                                        var usertext = result.Alternatives[0].Transcript;
                                        OnResult?.Invoke(usertext);
                                        WriteCompleteAsync();
                                    }
                                }
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.Warning($"Ouops, gstt got a minor error in _taskOnAudioReceived(-_-`) \n {e.Message}");
                    WriteCompleteAsync();
                }
            };
        }

        public void Init()
        {
            /*
            if (Environment.GetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS") == null)
            {
                Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", Properties.Settings.Default.ApiKeyPath, EnvironmentVariableTarget.Machine);
            }
            */
            if (_speech == null || _streamingCall == null)
            {
                _speech = SpeechClient.Create();
                _streamingCall = _speech.StreamingRecognize();
                _timer.Start();
                _transcribing = true;
            }
        }

        public async void FirstWriteAsync()
        {
            Init();
            _writeMore = true;
            await _streamingCall.WriteAsync(
                new StreamingRecognizeRequest
                {
                    StreamingConfig = new StreamingRecognitionConfig
                    {
                        Config = new RecognitionConfig
                        {
                            Encoding = RecognitionConfig.Types.AudioEncoding.Linear16,
                            SampleRateHertz
                                = Properties.Settings.Default.SttSampleRate,
                            LanguageCode
                                = Properties.Settings.Default.SttLangCode
                        },
                        InterimResults = Properties.Settings.Default.SttInterimResults,
                        SingleUtterance = true
                    }
                });

            _lastWrite = LastModeWrite.First;
            _taskAction = Task.Run(_taskOnAudioReceived);
        }

        public async void WriteCompleteAsync()
        {
            try
            {
                if (_lastWrite != LastModeWrite.Audio) return;

                lock (_writeLock) _writeMore = false;

                _testIsSpeaking = false;
                await _streamingCall.WriteCompleteAsync();
                await _taskAction;

                _timer.Stop();
                _last_write_counter.Stop();
                _speech = null;
                _streamingCall = null;
                _transcribing = false;
                _lastWrite = LastModeWrite.Last;

            }
            catch (Exception e)
            {
                Log.Warning($"Ouops, gstt got a minor error in WriteCompleteAsync (-_-`) \n {e.Message}");
            }
        }

        public void Transcribe(byte[] bytes)
        {
            try
            {
                long current = new DateTimeOffset(DateTime.UtcNow).ToUnixTimeMilliseconds();
                if (timerSinceData + 2000 < current)
                    Log.Info("Start Receiving data ...");
                timerSinceData = current;
                


                //_last_write_counter.Stop();
                lock (_writeLock)
                {
                    if (_lastWrite == LastModeWrite.Last) FirstWriteAsync();

                    if (!_writeMore) return;
                    _streamingCall.WriteAsync(new StreamingRecognizeRequest
                    {
                        AudioContent = ByteString.CopyFrom(bytes, 0, bytes.Length)
                    }).Wait();
                    _lastWrite = LastModeWrite.Audio;
                }
                //_last_write_counter.Start();
            }
            catch (Exception e)
            {
                Log.Warning($"Ouops, gstt got a minor error in Transcribe(-_-`) \n {e.Message}");
                WriteCompleteAsync();
            }
        }
        
        public enum LastModeWrite
        {
            First,
            Audio,
            Last
        }
    }
}
