using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using ApiAiSDK;
using ApiAiSDK.Model;
using Common;
using Monitoring;

namespace CloudServices.Impl
{
    public class DialogFlow : IDialogService
    {
        private readonly ApiAi _service;
        public event DfResultHandler OnResult;
        public DialogFlow()
        {
            var config = new AIConfiguration(Properties.Settings.Default.DfToken, SupportedLanguage.French);
            try
            {
                _service = new ApiAi(config);
            }
            catch (Exception e)
            {
                Log.Error("Cannot create DialogFlow client", e);
            }
        }

        public void Answer(string text)
        {
            try
            {
                Log.Info("Finding answer ...");
                var response = _service.TextRequest(text);
                OnResult?.Invoke(response.Result.Fulfillment.Speech);
            }
            catch (Exception e)
            {
                Log.Error("Cannot get answer from DialogFlow", e);
            }
        }
    }
}
