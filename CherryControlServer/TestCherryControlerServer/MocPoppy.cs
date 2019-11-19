using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.WebSockets;
using System.Threading;


namespace TestCherryControlerServer
{
    class MocPoppy
    {
        ClientWebSocket ws;

        public MocPoppy()
        {
            this.ws = new ClientWebSocket();
            
        }

        public Task connect()
        {
            return this.ws.ConnectAsync(new Uri("ws://127.0.0.1/newpoppy?id=\"42\""), new CancellationToken(true));
        }
    }
}
