using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistance.Entities
{
    public class Robot
    {
        public ObjectId Id { get; set; }
        public string WifiName { get; set; }
        public double? Version { get; set; }
    }
}
