using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistance.Entities
{
    public class Movement
    {
        public ObjectId Id { get; set; }
        public string Name { get; set; }
        public string Directory { get; set; }
        public string Image { get; set; }
    }
}
