using System;
using System.Web;
using System.Web.Script.Serialization;
using CherryController;
using CherryController.Communications;
using CherryController.Core;
using Common;
using Monitoring;
using Newtonsoft.Json;

namespace Communications.Decoder
{
    public class ServerCommandBus
    {
        public DeviceController _controller { get; }

        public ServerCommandBus()
        {

            _controller = new DeviceController();

        }

        public void OnCreateSession(string sessionId, string sessionPath, Action<string> responseAction)
        {
            var url = new Uri("http://0.0.0.0" + sessionPath);
            var queryParams = HttpUtility.ParseQueryString(url.Query);
            var clientType = url.AbsolutePath.Replace("/", string.Empty);

            switch (clientType)
            {
                case "newapplication":
                    string applicationId = queryParams.Get("id");
                    _controller.AddNewApplication(sessionId, applicationId, responseAction);
                    Log.Info($"Created new Application with id = {applicationId}");
                    break;
                case "newpoppy":
                    string poppyId = queryParams.Get("id");
                    _controller.AddNewPoppy(sessionId, poppyId, responseAction);
                    Log.Info($"Created new Poppy device with id = {poppyId}");
                    break;
                default:
                    Log.Warning($"Connection failed : An unknown client tried to connect with {sessionPath}");
                    break;
            }
        }

        public void OnCloseSession(string sessionId, string value)
        {
            Log.Warning($"Session {sessionId} has disconnected for reason {value}");
            _controller.removeDevice(sessionId);
        }


        public void OnByteData(string sessionId, byte[] value)
        {
            _controller.Recognize(sessionId, value);
        }

        public void OnTextData(string sessionId, string value)
        {
            Log.Debug("ontextData");
            try
            {
                Log.Debug(value);
                dynamic data = JsonConvert.DeserializeObject(value, typeof(object));
                _controller.Execute(sessionId, data);
            }
            catch (Exception e)
            {
                Log.Error("Got incorrect data!", e);
            }
        }
    }
}
