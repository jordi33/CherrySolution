using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistance.Entities
{
    public class User
    {
        public ObjectId Id { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
    }
}
