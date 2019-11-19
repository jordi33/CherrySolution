using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CherryController.Core
{
    public abstract class Device
    {
        protected readonly Action<string> _sendBack;
        public string Id { get; }
        public string SessionId { get; set; }

        public Device(string id, string sessionId, Action<string> sendBack)
        {
            Id = id;
            SessionId = sessionId;
            _sendBack = sendBack;
        }
    }
}
