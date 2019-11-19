using System;
using System.IO;
using System.Threading.Tasks;
using Common;
using Monitoring;
using Nancy;
using HttpStatusCode = System.Net.HttpStatusCode;

namespace Communications.WebApi
{
    public class FileApi : NancyModule
    {

        public FileApi() : base("/api")
        {
            Get["/resource/{Name}"] = args =>
            {
                string name = args.Name;
                string filePath = $"{Properties.Settings.Default.TmpDir}\\{name}";
                var file = new FileStream(filePath, FileMode.Open);

                Response result = Response.FromStream(file, filePath); 
                result.WithHeader("Content-Disposition", "attachment;filename=\"" + name + "\"");
                result.WithHeader("Content-Type", "application/octet-stream");
                result.WithHeader("Content-Length", file.Length.ToString());
                result.WithHeader("Access-Control-Allow-Origin", "*");
                result.WithHeader("Access-Control-Allow-Headers", "Content-Type");
                Task.Delay(30000).ContinueWith(t => Utils.RemoveFile(filePath));
                return result;
            };
        }
    }

    public class GetFileArgs
    {
        public string FileId { get; }
        public HttpStatusCode Request { get; set; }

        public GetFileArgs() { }

        public GetFileArgs(string fileId)
        {
            FileId = fileId;
        }

        public GetFileArgs(string fileId, HttpStatusCode request)
        {
            FileId = fileId;
            Request = request;
        }
    }
}
