using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Nancy;

namespace Communications.WebApi
{
    public class HomeModule : NancyModule
    {
        public HomeModule()
        {
            Get["/api/demo/say-hello", runAsync: true] = async (_, token) => "Hello....";
        }
    }
}
