using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Communications
{
    public interface IWebSocketServer
    {
        bool StartServer();
        bool StopServer();
    }
}
