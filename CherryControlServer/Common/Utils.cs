using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common
{
    public static class Utils
    {
        private const string Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        private static readonly Random Random = new Random();
        public static string GetRandString()
        {
            return new string(Enumerable.Range(1, 5).Select(_ => Chars[Random.Next(Chars.Length)]).ToArray());
        }

        public static void RemoveFile(string filePath)
        {
            if (File.Exists(filePath))
                File.Delete(filePath);
        }
        public static void CleanDirectory(string directory)
        {
            string[] files = Directory.GetFiles(directory);
            foreach (string file in files)
            {
                File.Delete(file);
            }
        }
    }
}
