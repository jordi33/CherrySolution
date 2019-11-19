using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Common;
using SuperSocket.SocketBase.Command;
using SuperSocket.SocketBase.Config;
using SuperSocket.SocketBase.Logging;
using SuperSocket.SocketEngine;
using SuperSocket.WebSocket;
using SuperSocket.WebSocket.Protocol;

namespace Communications.Impl
{
    public class WebSockImpl : WebSocketServer<WebSocketSession>, IWebSocketServer
    {
        public bool StartServer()
        {
            if (this.Setup(new RootConfig(),
                new ServerConfig()
                {
                    Port = Properties.Settings.Default.WebSocketPort,
                    MaxRequestLength = 8048
                },
                new SocketServerFactory(),
                ReceiveFilterFactory,
                new Log4NetLogFactory(),
                ConnectionFilters,
                new ICommandLoader<ICommand<WebSocketSession, IWebSocketFragment>>[1]))
            {
                this.Start();
                Log.Info("Web Socket Server is up and running :)");
                return true;
            }
            else
            {
                return false;
            }
        }

        public bool StopServer()
        {
            try
            {
                this.Stop();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
}
