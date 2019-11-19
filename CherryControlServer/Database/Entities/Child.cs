using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistance.Entities
{
    public class Child
    {
        public ObjectId Id { get; set; }
        public string Name { get; set; }
        public string Surname { get; set; }
        public int Age { get; set; }
        public IEnumerable<ObjectId> Activities { get; set; }
        public IEnumerable<string> CentersOfInterest { get; set; }
    }
}
