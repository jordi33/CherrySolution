using Newtonsoft.Json;

namespace CherryController.Communications
{
    public abstract class Command
    {
        protected Priority Priority { get; set; }
        protected string ClientID { get; set; }
        public string Action { get; set; }

        public Command()
        {
            Priority = Priority.Normal;
            ClientID = string.Empty;
        }
        public override string ToString()
        {
            return JsonConvert.SerializeObject(this);
        }

    }

    public enum Priority
    {
        High = 3,
        Normal = 2,
        Low = 1
    }

}
