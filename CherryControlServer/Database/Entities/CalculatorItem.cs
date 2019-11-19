using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MongoDB.Bson;
using Newtonsoft.Json;

namespace Persistance.Entities
{
    public class CalculatorItem
    {
        [JsonIgnore]
        public ObjectId Id { get; set; }
        public String Calculation { get; set; }
        public int Result {get; set;}
        public int Difficulty { get; set; }
    }
}
