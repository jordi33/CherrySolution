using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CloudServices
{
    public delegate void GSttResultHandler(string text, int duration);
    public interface ITtsService
    {
        double Gain { get; set; }
        event GSttResultHandler OnResult;
        void Pronounce(string data);
    }
}
