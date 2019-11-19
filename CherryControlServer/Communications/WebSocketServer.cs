using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Common;
using Communications.Decoder;
using Communications.Impl;

namespace Communications
{
    public class WebSocketServer : IWebSocketServer
    {

        private readonly WebSockImpl _sockImpl;
        public ServerCommandBus _commandBus { get; } 
        private CherryFileServer _cherryFileServer = new CherryFileServer();

        public WebSocketServer()
        {
            _commandBus = new ServerCommandBus();
            _sockImpl = new WebSockImpl();
            _sockImpl.NewSessionConnected += session => { _commandBus.OnCreateSession(session.SessionID, session.Path, session.Send); };
            _sockImpl.SessionClosed += (session, value) => { _commandBus.OnCloseSession(session.SessionID, value.ToString()); };
            _sockImpl.NewDataReceived += (session, value) => { _commandBus.OnByteData(session.SessionID, value); };
            _sockImpl.NewMessageReceived += (session, value) => { Log.Debug("Receive"); _commandBus.OnTextData(session.SessionID, value); };
            StartFileServer();
        }


 
        public bool StartServer()
        {
            try
            {
                return _sockImpl.StartServer();
            }
            catch (Exception e)
            {
                Log.Fatal("Failed to Start Web Socket Server! ", e);
                return false;
            }
        }
        public bool StartFileServer()
        {
            if (_cherryFileServer == null) return false;
            try
            {
                return _cherryFileServer.StartServer();
            }
            catch (System.Exception e)
            {
                Log.Error("Cannot Start File Server", e);
                throw;
            }
        }

        public bool StopServer()
        {
            try
            {
                return _sockImpl.StopServer();
            }
            catch (Exception e)
            {
                Log.Fatal("Failed to Stop Web Socket Server! ", e);
                return false;
            }
        }
    }
}
