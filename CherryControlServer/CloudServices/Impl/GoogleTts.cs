using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Common;
using Google.Cloud.TextToSpeech.V1;
using Monitoring;
using NAudio.Wave;


namespace CloudServices.Impl
{
    public class GoogleTts : ITtsService
    {
        private readonly TextToSpeechClient _service;
        private readonly VoiceSelectionParams _voice;
        private AudioConfig _audioConfig;
        public event GSttResultHandler OnResult;
        private double _gain = Properties.Settings.Default.TtsGain;
        public double Gain
        {
            get => _gain;
            set
            {
                _gain = value;
                InitAudioConfig();
            }
        }

        public GoogleTts()
        {

            /*
            if (Environment.GetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS") == null)
            {
                Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", Properties.Settings.Default.ApiKeyPath, EnvironmentVariableTarget.Machine);
            }
            */

            Utils.CleanDirectory(Properties.Settings.Default.TmpDir);
            _service = TextToSpeechClient.Create();
            _voice = new VoiceSelectionParams()
            {
                LanguageCode = Properties.Settings.Default.TtsLangCode,
                SsmlGender = SsmlVoiceGender.Female,
                Name = Properties.Settings.Default.TtsName,
            };
            InitAudioConfig();
        }

        private void InitAudioConfig()
        {
            _audioConfig = new AudioConfig()
            {
                AudioEncoding = AudioEncoding.Linear16,
                Pitch = Properties.Settings.Default.TtsPitch,
                SpeakingRate = Properties.Settings.Default.TtsRate,
                VolumeGainDb = Gain
            };
        }


        public void Pronounce(string input)
        {
            SynthesizeSpeechResponse response = null;
            try
            {
                Log.Info("Creating response audio file ...");
                response = _service.SynthesizeSpeech(new SynthesizeSpeechRequest
                {
                    Input = new SynthesisInput() { Ssml = EncodeSsml(input) },
                    Voice = _voice,
                    AudioConfig = _audioConfig,
                });
            }
            catch (Exception e)
            {
                Log.Error("Ah! Cannon get response from TTS. :[", e);
            }
            Log.Info("Audio file created");

            if (response != null) WriteResponseToDrive(response);
        }

        private string EncodeSsml(string input)
        {
            return $"<speak>{input}</speak>";
        }

        private void WriteResponseToDrive(SynthesizeSpeechResponse response)
        {
            try
            {
                Log.Info("Write Audio to drive ...");
                string name = $"{Utils.GetRandString()}.wav";
                string filePath = $"{Properties.Settings.Default.TmpDir}\\{name}";
                Log.Debug("FILEPATH" + filePath);
                using (Stream output = File.Create(filePath))
                {
                    response.AudioContent.WriteTo(output);
                    int duration = (int)Math.Ceiling(response.AudioContent.Length / 3200d);
                    OnResult?.Invoke(name, duration);
                }
            }
            catch (Exception e)
            {
                Log.Error("Ah! Cannon write TTS answer to file. :[", e);
            }
        }
    }
}
