using System;
using System.IO;
using System.Media;
using CherryController.Communications;
using CherryController.Utils;
using CloudServices;
using CloudServices.Impl;
using Common;
using Newtonsoft.Json;
using System.Threading;
using Monitoring;

namespace CherryController.Core
{
    class Poppy: Device
    {
        private bool _isFistConnection;
        private Application _connectedApplication;
        private Timer _isActiveTimer;
        public bool _isApplicationInactive { get; set; }

        // Add incoming and out-coming queue

        private ISttService _sttService;
        private ITtsService _ttsService;
        private IDialogService _dialogService;


        public Poppy(string sessionId, string poppyId, Action<string> sendBackAction): base(poppyId, sessionId, sendBackAction)
        {
            _connectedApplication = null;
            _isFistConnection = true;
            _isActiveTimer = new Timer(TimerCallback, this, 15 * 1000, Timeout.Infinite);
            _isApplicationInactive = false;
            InitializeCloudServices();
            OnFistConnection();
        }

        public void TimerCallback(object info)
        {
            this.ReviveDialog();
        }

        private void OnFistConnection()
        {
            if (_isFistConnection)
            {
                _dialogService.Answer("Salut");
                _isFistConnection = false;
            }
        }

        private void InitializeCloudServices()
        {
            Log.Info($"Creating Cloud services for {SessionId}");
            _sttService = new GoogleStt();
            _sttService.OnResult += SttCallback;

            _ttsService = new GoogleTts();
            _ttsService.OnResult += TtsCallback;

            _dialogService = new DialogFlow();
            _dialogService.OnResult += DialogCallback;
        }

        private void DialogCallback(string text)
        {
            Log.Info("Answer found");
            ResetTimer();
            Log.Debug($"<-- {text}");
            var actions = text.Split('*');
            if (actions.Length == 2)
            {
                _sendBack(new RobotCommands.CommParser().ParseCommand(actions[0]));
                _ttsService.Pronounce(actions[1]);
            }
            else
            {
                _ttsService.Pronounce(actions[0]);
            }
        }

        private void TtsCallback(string text, int duration)
        {
            ResetTimer();
            _sendBack(new RobotCommands.ReadAudioCommand(path: text, len: duration).ToString());
            Log.Info("Audio writen");

        }

        private void SttCallback(string message)
        {
            ResetTimer();
            switch (message.ToLower())
            {
                case "baisse le volume":
                    _ttsService.Gain = _ttsService.Gain - 1;
                    Log.Debug($"New tts gain : {_ttsService.Gain}");
                    break;
                case "monte le volume":
                    _ttsService.Gain = _ttsService.Gain + 1;
                    Log.Debug($"New tts gain : {_ttsService.Gain}");
                    break;
                default:
                    Log.Debug($"--> {message}");
                    _dialogService.Answer(message);
                    break;
            }
        }

        public void Recognize(RecognizeCommand recognizeCommand)
        {
            _sttService.Transcribe(recognizeCommand.SoundData);
        }

        public void PlayMove(string moveName)
        {
            ResetTimer();
            _sendBack(new RobotCommands.MoveCommand(moveName).ToString());
        }

        public void Speak(string speech)
        {
            ResetTimer();
            _ttsService.Pronounce(speech);
        }

        public void setApplication(Application app)
        {
            ResetTimer();
            this._connectedApplication = app;
        }

        public Application getApplication()
        {
            ResetTimer();
            return this._connectedApplication;
        }

        public bool isApplication(Application app)
        {
            ResetTimer();
            return app.Equals(this._connectedApplication);
        }

        public void changeEmotion(EmotionEnum emotion)
        {
            ResetTimer();
            _sendBack(new RobotCommands.ChangeEmotionCommand(emotion).ToString());
        }

        public void PlayChoregraphy(dynamic choregraphy)
        {
            ResetTimer();
            _sendBack(new RobotCommands.PlayChoregraphyCommand(choregraphy).ToString());
        }

        private void ReviveDialog()
        {
            if (this._isApplicationInactive)
            {
                Log.Info("Reviving the dialog");
                _dialogService.Answer("Relance"); // Intent revive_dialog
                this._isApplicationInactive = false;
            }
            ResetTimer();
        }

        private void ResetTimer()
        {
            this._isActiveTimer.Change(15 * 1000, Timeout.Infinite);
        }
    }
}