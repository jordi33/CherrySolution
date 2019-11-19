using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CloudServices
{
    public delegate void GsttResultHandler(string text);
    public interface ISttService
    {
        event GsttResultHandler OnResult;
        void Transcribe(byte[] data);
    }
}
