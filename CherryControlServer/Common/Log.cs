using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using log4net.Config;

namespace Common
{
    public static class Log
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(Log));

        static Log()
        {
            XmlConfigurator.Configure();
        }

        public static void Info(string s)
        {
            log.Info(s);
        }

        public static void Error(string s, Exception e)
        {
            log.Error(s, e);
        }
        public static void Error(string s)
        {
            log.Error(s);
        }

        public static void Debug(string s)
        {
            log.Debug(s);
        }

        public static void Warning(string s, Exception e)
        {
            log.Warn(s, e);
        }
        public static void Warning(string s)
        {
            log.Warn(s);
        }

        public static void Fatal(string s, Exception e)
        {
            log.Fatal(s, e);
        }
        public static void Fatal(string s)
        {
            log.Fatal(s);
        }
    }
}
