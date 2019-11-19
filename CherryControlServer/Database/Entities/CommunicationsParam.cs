using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistance.Entities
{
    public class CommunicationsParam
    {
        public ObjectId Id { get; set; }
        public int? WebSocketPort { get; set; }
        public string TmpDir { get; set; }
        public int? FileServPort { get; set; }
    }
}
