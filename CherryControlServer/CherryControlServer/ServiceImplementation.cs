using CherryControlServer.Framework;
using System.ServiceProcess;
using Communications;
using Persistance;
using Communications.Impl;


namespace CherryControlServer
{
    /// <summary>
    /// The actual implementation of the windows service goes here...
    /// </summary>
    [WindowsService("CherryControlServer",
        DisplayName = "CherryControlServer",
        Description = "Service hosting a control server for Cherry project",
        EventLogSource = "CherryControlServer",
        StartMode = ServiceStartMode.Automatic)]
    public class ServiceImplementation : IWindowsService
    {
        public WebSocketServer SocketServer { get; set; }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        /// <filterpriority>2</filterpriority>
        public void Dispose()
        {
        }

        /// <summary>
        /// This method is called when the service gets a request to start.
        /// </summary>
        /// <param name="args">Any command line arguments</param>
        public void OnStart(string[] args)
        {
            Database db = Database.Instance; 
            SocketServer = new WebSocketServer();
            SocketServer.StartServer();
        }

        /// <summary>
        /// This method is called when the service gets a request to stop.
        /// </summary>
        public void OnStop()
        {
            SocketServer.StopServer();
        }

        /// <summary>
        /// This method is called when a service gets a request to pause,
        /// but not stop completely.
        /// </summary>
        public void OnPause()
        {
        }

        /// <summary>
        /// This method is called when a service gets a request to resume 
        /// after a pause is issued.
        /// </summary>
        public void OnContinue()
        {
        }

        /// <summary>
        /// This method is called when the machine the service is running on
        /// is being shutdown.
        /// </summary>
        public void OnShutdown()
        {
        }

        /// <summary>
        /// This method is called when a custom command is issued to the service.
        /// </summary>
        /// <param name="command">The command identifier to execute.</param >
        public void OnCustomCommand(int command)
        {
        }
    }
}
