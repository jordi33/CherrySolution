using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace Persistance.Entities
{
    public class Choregraphy
    {
        [JsonIgnore]
        public ObjectId Id { get; set; }
        public String Name { get; set; }
        public IEnumerable<ObjectId> Movements { get; set; }
        public String Music { get; set; }
    }
}
