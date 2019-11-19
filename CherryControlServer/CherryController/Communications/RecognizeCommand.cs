using Newtonsoft.Json;

namespace CherryController.Communications
{
    public class RecognizeCommand : Command
    {
        public byte[] SoundData { get; set; }

        public RecognizeCommand(byte[] data, Priority priority = Priority.Normal)
        {
            SoundData = data;
            Priority = priority;
        }
    }
}
