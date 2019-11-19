using System;
using System.Collections.Generic;
using System.Text;
using CherryController.Communications;
using Common;
using Persistance;
using Persistance.Entities;
using CherryController.Utils;
using Monitoring;

namespace CherryController.Core
{
    public class DeviceController
    {
        public List<Device> _connectedDevices { get; }
        readonly object _syncLock = new object();

        public DeviceController()
        {
            _connectedDevices = new List<Device>();
            
        }

        public void AddNewPoppy(string sessionId, string poppyId, Action<string> responseAction)
        {
            lock (_syncLock)
            {
                _connectedDevices.Add(new Poppy(sessionId, poppyId, responseAction));
            }
        }

        public void AddNewApplication(string sessionId, string applicationId, Action<string> responseAction)
        {
            List<String> poppies = GetPoppies();
            lock (_syncLock)
            {
                _connectedDevices.Add(new Application(sessionId, applicationId, responseAction, poppies));
            }
        }

        public void SetApplicationPoppy(string applicationSessionId, string poppyId)
        {
            Application app = (Application) GetDeviceBySessionId(applicationSessionId);
            Poppy poppy = (Poppy) GetDeviceById(poppyId);
            app.Poppy = poppy;
            poppy.setApplication(app);
            app.SendBack(poppy.Id.ToString());
        }

        private Device GetDeviceBySessionId(string sessionId)
        {
            lock (_syncLock)
            {
                foreach (var device in _connectedDevices)
                {
                    if (device.SessionId.Equals(sessionId))
                    {
                        return device;
                    }
                }
            }
            return null;
        }

        private Device GetDeviceById(string sessionId)
        {
            lock (_syncLock)
            {
                foreach (var device in _connectedDevices)
                {
                    if (device.Id.Equals(sessionId))
                    {
                        return device;
                    }
                }
            }
            return null;
        }

        private List<String> GetPoppies()
        {
            List<String> poppies = new List<String>();
            lock (_syncLock)
            {
                foreach (var device in _connectedDevices)
                {
                    var poppy = device as Poppy;
                    if (poppy != null)
                    {
                        poppies.Add(poppy.Id);
                    }
                }
            }
            return poppies;
        }

        public void removeDevice(string sessionId)
        {
            lock (_syncLock)
            {
                foreach (var device in _connectedDevices)
                {
                    if (device.SessionId.Equals(sessionId))
                    {
                        _connectedDevices.Remove(device);
                        checkDeviceRemoval(device);
                        break;
                    }
                }
            }
        }

        private void checkDeviceRemoval(Device device)
        {
            if (device is Poppy)
            {
                Application app = (device as Poppy).getApplication();
                if (app != null)
                {
                    app.RemovePoppy();
                }
            }
        }

        public void Execute(string sessionId, dynamic someCommand)
        {
            Device device = GetDeviceBySessionId(sessionId);
            Application application = device as Application;
            Poppy poppy = device as Poppy;
            //string Command = someCommand.Command;
            switch ((string) someCommand.Command)
            {
                case "GetListPoppy":
                    List<String> poppyList = GetPoppies();
                    Log.Debug($"Retrieved Connected Poppy list: {string.Join("\n", poppyList)}");
                    application.ListPoppy(poppyList);
                    break;
                case "Recognize":
                    string data = someCommand.SoundData.ToString();
                    poppy?.Recognize(new RecognizeCommand(Encoding.ASCII.GetBytes(data)));
                    break;
                case "SetAppPoppy":
                    Log.Info($"Linking App {sessionId} with poppy {someCommand.PoppyId}");
                    SetApplicationPoppy(sessionId, (string) someCommand.PoppyId);
                    break;
                case "PlayMove":
                    application.Poppy.PlayMove((string) someCommand.MoveName);
                    Log.Debug($"Playing {someCommand.MoveName}");
                    break;
                case "PlayChoregraphy":
                    dynamic choregraphy = Database.getChoregraphy((string)someCommand.Name);
                    application.Poppy.PlayChoregraphy(choregraphy);
                    break;
                case "GetChoregraphies":
                    List<dynamic> choregraphiesList = Database.getChoregraphies();
                    application.SendBackChoregraphies(choregraphiesList);
                    break;
                case "AddChoregraphy":
                    Database.addChoregraphy((string) someCommand.Name, (List<string>) someCommand.Moves.ToObject(typeof(List<string>)), (string) someCommand.Music);
                    Log.Debug("Choregraphy added");
                    break;
                case "RemoveChoregraphy":
                    Database.removeChoregraphy((string) someCommand.Name);
                    Log.Debug("Choregraphy removed");
                    break;
                case "ListMovements":
                    List<Movement> movementList = Database.getMovements();
                    application.ListMovements(movementList);
                    break;
                case "GetJokes":
                    List<Joke> jokesList = Database.getJokes();
                    application.SendBackJokes(jokesList);
                    break;
                case "Speak":
                    application.Poppy.Speak((string) someCommand.Content);
                    Log.Debug($"Saying {someCommand.Content}");
                    break;
                case "GetCalculatorItems":
                    List<CalculatorItem> calculatorItemsList = Database.getCalculatorItems();
                    application.SendBackCalculatorItems(calculatorItemsList);
                    break;
                case "ChangeEmotion":
                    Log.Debug($"Changing emotion to {(EmotionEnum)someCommand.Emotion}");
                    application.Poppy.changeEmotion((EmotionEnum)someCommand.Emotion);
                    break;
                case "GetTales":
                    application.SendBackTales(Database.getTales());
                    break;
                case "ReviveDialog":
                    try
                    {
                        application.Poppy._isApplicationInactive = true;
                    } catch (Exception e)
                    {
                        Log.Warning("No Poppy linked yet");
                    }
                    break;
                case "TimerReset":
                    try
                    {
                        application.Poppy._isApplicationInactive = false;
                    }
                    catch (Exception e)
                    {
                        Log.Warning("No Poppy linked yet");
                    }
                    break;
                case "Test":
                    application.SendBackRedirectTales();
                    break;
                default:
                    Log.Warning($"Got an unknown command for {sessionId} : {someCommand.Command.ToString()}");
                    break;
            }
        }

        public void Recognize(string sessionId, byte[] data)
        {
            Poppy poppy = (Poppy) GetDeviceBySessionId(sessionId);
            poppy?.Recognize(new RecognizeCommand(data));
        }
    }

}
