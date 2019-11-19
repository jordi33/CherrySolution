using System;
using System.Collections.Generic;
using System.IO;
using System.Media;
using CherryController.Communications;
using CherryController.Utils;
using CloudServices;
using CloudServices.Impl;
using Common;
using Persistance.Entities;
using Newtonsoft.Json;


namespace CherryController.Core
{
    class Application : Device
    {
        public Poppy Poppy { get; set; }

        public Application(string sessionId, string applicationId, Action<string> sendBackAction, List<String> connectedPoppies): base(applicationId, sessionId, sendBackAction)
        {
            Poppy = null;
            var json = JsonConvert.SerializeObject(connectedPoppies);
            Log.Debug($"Retrieved Connected Poppy list: {string.Join("\n", json)}");
            _sendBack("{ 'ConnectedPoppies': " + json + "}");
        }

        public void ListPoppy(List<string> list)
        {
            var json = JsonConvert.SerializeObject(list);
            _sendBack(json);
        }

        public void ListMovements(List<Movement> list)
        {
            var json = JsonConvert.SerializeObject(list);
            _sendBack(json);
        }

        public void SendBackJokes(List<Joke> list)
        {
            List<String> contentList = new List<string>();
            foreach (Joke joke in list)
            {
                contentList.Add(joke.Content);
            }
            var json = JsonConvert.SerializeObject(contentList);
            _sendBack("{ 'Jokes': " + json + "}");
        }

        public void SendBackCalculatorItems(List<CalculatorItem> list)
        {
            var json = JsonConvert.SerializeObject(list);
            _sendBack("{ 'CalculatorItems': " + json + "}");
        }

        public void SendBackChoregraphies(List<dynamic> list)
        {
            var json = JsonConvert.SerializeObject(list);
            _sendBack("{'Choregraphies': " + json + "}");
        }

        public void SendBack(string message)
        {
            var json = JsonConvert.SerializeObject(message);
            _sendBack("{'PoppyId': " + json + "}");
        }

        public void RemovePoppy()
        {
            this.Poppy = null;
            _sendBack("{'PoppyState': 'Disconnected'}");
        }

        public void SendBackTales(List<Tale> taleList)
        {
            var json = JsonConvert.SerializeObject(taleList);
            _sendBack("{'Tales': " + json + "}");
        }

        public void SendBackRedirectTales()
        {
            _sendBack("{'RedirectToTales': ''}");
        }
    }
}
