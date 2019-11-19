using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Common;
using Nancy.Hosting.Self;

namespace Communications.Impl
{
    class CherryFileServer
    {
            private bool _isRunning;
            private Thread _webServerThread;

            public CherryFileServer()
            {
                _isRunning = false;
            }

            private void StartWebServerThread()
            {
                Log.Info("Starting Web Server");
                using (var nancyHost = new NancyHost(new Uri($"http://localhost:{Properties.Settings.Default.FileServPort}/")))
                {
                    nancyHost.Start();
                    while (_isRunning)
                    {
                        // let's be dirty here
                    }
                }
            }
            // TODO : this is not a robust isRunning implementation
            public bool StartServer()
            {
                try
                {
                    _isRunning = true;
                    _webServerThread = new Thread(StartWebServerThread);
                    _webServerThread.Start();
                    return true;
                }
                catch (Exception e)
                {
                    _isRunning = false;
                    Log.Error("Cannot Start Web Server: \n", e);
                }
                return false;
            }

            public bool StopServer()
            {
                try
                {
                    _isRunning = false;
                    _webServerThread.Abort();
                    _webServerThread = null;
                    return true;
                }
                catch (Exception e)
                {
                    Log.Error("Cannot Stop Web Server: \n", e);
                    return false;
                }
            }
        }
    }

