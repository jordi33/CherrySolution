using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Reflection;
using Common;
using Persistance.Entities;
using Monitoring;

namespace Persistance
{
    public class Database
    {
        private static readonly Lazy<Database> lazy = new Lazy<Database>(() => new Database());

        public static Database Instance { get { return lazy.Value; } }

        private string _connectionAddress;
        private IMongoDatabase db;
        static private IMongoCollection<Activity> activityCollection;
        static private IMongoCollection<Child> childCollection;
        static private IMongoCollection<Choregraphy> choregraphyCollection;
        static private IMongoCollection<Joke> jokeCollection;
        static private IMongoCollection<Movement> movementCollection;
        static private IMongoCollection<Robot> robotCollection;
        static private IMongoCollection<Tale> taleCollection;
        static private IMongoCollection<User> userCollection;
        static private IMongoCollection<CloudParams> cloudParamsCollection;
        static private IMongoCollection<CommunicationsParam> communicationsParamCollection;
        static private IMongoCollection<CalculatorItem> calculatorItemsCollection;

        private Database()
        {
            MongoCredential credentials = MongoCredential.CreateCredential("admin", Properties.Settings.Default.DatabaseUsername, Properties.Settings.Default.Password);
            MongoClientSettings settings = new MongoClientSettings
            {
                Credential = credentials
            };
            retrieveCollections(settings);
        }

        //public Database(string address, int port)
        //{
        //    _connectionAddress = address + ":" + port;
        //    retrieveCollections();
        //}

        private void retrieveCollections(MongoClientSettings settings)
        {
            var client = new MongoClient(settings);
            db = client.GetDatabase("cherryDb");
            activityCollection = db.GetCollection<Activity>("activities");
            childCollection = db.GetCollection<Child>("children");
            choregraphyCollection = db.GetCollection<Choregraphy>("choregraphies");
            jokeCollection = db.GetCollection<Joke>("jokes");
            movementCollection = db.GetCollection<Movement>("movements");
            robotCollection = db.GetCollection<Robot>("robots");
            taleCollection = db.GetCollection<Tale>("tales");
            userCollection = db.GetCollection<User>("users");
            cloudParamsCollection = db.GetCollection<CloudParams>("cloud_parameters");
            communicationsParamCollection = db.GetCollection<CommunicationsParam>("communications_parameters");
            calculatorItemsCollection = db.GetCollection<CalculatorItem>("calculator_items");
            Log.Info("Retrieved all collections from the database");
        }

        // Function comparing the hashed password in the db with the one provided in the app for the authentication
        public static bool checkPassword(string login, string hashedPassword)
        {
            string filter = "{Login: '" + login + "'}";
            var results = userCollection.Find(user => user.Login == login && user.Password == hashedPassword).Limit(1).ToList();
            if (results.Count == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }


        public static void addActivity(string name, string description)
        {
            Activity newActivity = new Activity
            {
                Name = name,
                Description = description
            };
            activityCollection.InsertOne(newActivity);
        }

        public static void addChild(string name, int age, IEnumerable<string> centersOfInterest)
        {
            Child newChild = new Child
            {
                Name = name,
                Age = age,
                Activities = new List<ObjectId>(),
                CentersOfInterest = centersOfInterest
            };
            childCollection.InsertOne(newChild);
        }

        public static List<dynamic> getChoregraphies()
        {

            List<Choregraphy> choregraphies = choregraphyCollection.Find(choregraphy => true).ToList();
            List<object> customChoregraphyList = new List<object>();
            foreach (Choregraphy choregraphy in choregraphies)
            {
                List<String> choregraphiesMovements = new List<string>();
                foreach (ObjectId moveId in choregraphy.Movements)
                {
                    Movement move = movementCollection.Find(movement => movement.Id == moveId).Limit(1).ToList<Movement>().FirstOrDefault();
                    choregraphiesMovements.Add(move.Name);
                }
                var customChoregraphy = new { Name = choregraphy.Name, Movements = choregraphiesMovements, Music = choregraphy.Music };
                customChoregraphyList.Add(customChoregraphy);
            }
            return customChoregraphyList;
        }


        public static dynamic getChoregraphy(string name)
        {
            Choregraphy toPlay = choregraphyCollection.Find(chore => chore.Name == name).Limit(1).ToList().FirstOrDefault<Choregraphy>();
            List<string> choregraphyMovements = new List<string>();
            foreach (ObjectId moveId in toPlay.Movements)
            {
                Movement move = movementCollection.Find(movement => movement.Id == moveId).Limit(1).ToList<Movement>().FirstOrDefault();
                choregraphyMovements.Add(move.Name);
            }
            dynamic returnChore = new System.Dynamic.ExpandoObject();
            returnChore.Name = toPlay.Name;
            returnChore.Movements = choregraphyMovements;
            returnChore.Music = toPlay.Music;
            return returnChore;
        }

        public static void addChoregraphy(String name, List<string> movements, String music)
        {
            List<Movement> movementList = new List<Movement>();
            foreach(string moveName in movements)
            {
                movementList.Add(movementCollection.Find(mvmt => mvmt.Name == moveName).Limit(1).ToList().FirstOrDefault<Movement>());
            }
            List<ObjectId> idList = new List<ObjectId>();
            foreach (Movement mvmt in movementList)
            {
               idList.Add(mvmt.Id);
            }
            Choregraphy newChoregraphy = new Choregraphy
            {
                Name = name,
                Movements = idList,
                Music = music
            };
            choregraphyCollection.InsertOne(newChoregraphy);      
        }

        public static void removeChoregraphy(String name)
        {
            choregraphyCollection.DeleteOne(choregraphy => choregraphy.Name == name);
        }

        public static void addCloudParams()
        {
            CloudParams param = new CloudParams
            {
                SttLangCode = "fr-FR", 
                SttSampleRate = 44100,
                SttInterimResults = true,
                SttSingleUtterance = true, 
                SttResetTime = 60000, 
                ApiKeyPath = "..\\..\\..\\Acadroid-aa3b5737c66d.json",
                TtsLangCode = "fr-FR",
                TtsName = "fr-FR-Wavenet-A", 
                TtsPich = 2.5, 
                TtsRate = 0.85, 
                TtsGain = 4, 
                TmpDir = "..\\..\\..\\tmp\\", 
                DfToken = "0ede100dc6f7428eb26566450aa5ada3"
            };
            cloudParamsCollection.InsertOne(param);
        }

        // Usage: give in parameter a CloudParams instance with all fields set to "null" except for the ones that are meant to change 
        public static void updateCloudParams(CloudParams newParams)
        {
            // This update variable is a Set BsonDocument (required to change a database document)
            // We parse the properties, exclude the Id and the null values 
            // Then we create a selection for the values of the newParams given in parameter that are not null
            var update = new BsonDocument("$set",
                new BsonDocument(typeof(CloudParams).GetProperties().Where(p => p.Name != "Id" && typeof(CloudParams).GetProperty(p.Name).GetValue(newParams, null) != null)
                .Select(p => new KeyValuePair<string, object>(p.Name, typeof(CloudParams).GetProperty(p.Name).GetValue(newParams, null))))
            );
            // Here we update One param (since there is only one in the db) with a filter set to always true and the update created above
            cloudParamsCollection.UpdateOne<CloudParams>(param => true, update);
        }

        public static void addCommunicationsParam()
        {
            CommunicationsParam param = new CommunicationsParam
            {
                WebSocketPort = 21,
                TmpDir = "C:\\Deploy\\CherryControlServer2019\\tmp\\",
                FileServPort = 22
            };
            communicationsParamCollection.InsertOne(param);
        }

        public static void updateCommunicationsParams(CommunicationsParam newParams)
        {
            var update = new BsonDocument("$set",
                new BsonDocument(typeof(CommunicationsParam).GetProperties().Where(p => p.Name != "Id" && typeof(CommunicationsParam).GetProperty(p.Name).GetValue(newParams, null) != null)
                .Select(p => new KeyValuePair<string, object>(p.Name, typeof(CommunicationsParam).GetProperty(p.Name).GetValue(newParams, null))))
            );
            // Here we update One param (since there is only one in the db) with a filter set to always true and the update created above
            communicationsParamCollection.UpdateOne<CommunicationsParam>(param => true, update);
        }

        public static void addJoke(string content)
        {
            Joke newJoke = new Joke
            {
                Content = content
            };
            jokeCollection.InsertOne(newJoke);
        }

        public static List<Joke> getJokes()
        {
            return jokeCollection.Find(_ => true).Project<Joke>("{Content: 1,_id: 0}").ToList();
        }

        public static void addMovement(string name, string directory, string image)
        {
            Movement newMovement = new Movement
            {
                Name = name, 
                Directory = directory,
                Image = image
            };
            movementCollection.InsertOne(newMovement);
        }

        public static List<Movement> getMovements()
        {
            return movementCollection.Find(_ => true).ToList<Movement>();
        }

        public static string getMovementDirectory(string name)
        {
            Movement move = movementCollection.Find<Movement>(movement => movement.Name == name).Limit(1).ToList()[0];
            return move.Directory;
        }

        public static void addRobot(string wifiName, double version)
        {
            Robot newRobot = new Robot
            {
                WifiName = wifiName,
                Version = version
            };
            robotCollection.InsertOne(newRobot);
        }

        // Usage: give in parameter a robot instance with all fields set to "null" except for the ones that are meant to change 
        public static void updateRobot(Robot newRobot, string robotId)
        {
            ObjectId Id = new ObjectId(robotId);
            // This update variable is a Set BsonDocument (required to change a database document)
            // We parse the properties, exclude the Id and the null values 
            // Then we create a selection for the values of the newRobot given in parameter that are not null
            var update = new BsonDocument("$set",
                new BsonDocument(typeof(Robot).GetProperties().Where(p => p.Name != "Id" && typeof(Robot).GetProperty(p.Name).GetValue(newRobot, null) != null)
                .Select(p => new KeyValuePair<string, object>(p.Name, typeof(Robot).GetProperty(p.Name).GetValue(newRobot, null))))
            );
            // Here we update One robot with a filter set to always true at the moment and the update
            robotCollection.UpdateOne<Robot>(robot => robot.Id == Id, update);
        }

        public static void addTale(string name, string content)
        {
            Tale newTale = new Tale
            {
                Name = name,
                Content = content
            };
            taleCollection.InsertOne(newTale);
        }

        public static void addUser(string login, string password)
        {
            User newUser = new User
            {
                Login = login,
                Password = password
            };
            userCollection.InsertOne(newUser);
        }

        public static void addCalculatorItem(string calculation, int result, int difficulty)
        {
            CalculatorItem newItem = new CalculatorItem
            {
                Calculation = calculation,
                Result = result,
                Difficulty = difficulty
            };
            calculatorItemsCollection.InsertOne(newItem);
        }

        public static List<CalculatorItem> getCalculatorItems()
        {
            return calculatorItemsCollection.Find(_ => true).Project<CalculatorItem>("{_id: 0}").ToList();
        }

        public static List<Tale> getTales()
        {
            return taleCollection.Find(_ => true).ToList<Tale>();
        }
    }
}
