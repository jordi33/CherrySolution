using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace Persistance.Entities
{
    public class Tale
    {
        [JsonIgnore]
        public ObjectId Id { get; set; }
        public string Name { get; set; }
        public string Content { get; set; }
    }
}
