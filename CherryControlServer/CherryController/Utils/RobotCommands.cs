using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CherryController.Communications;
using CherryController.Core;
using Common;
using Persistance;
using Newtonsoft.Json;
using Monitoring;

namespace CherryController.Utils
{
    public static class RobotCommands
    {
        public class MoveCommand : Command
        {
            public string MoveName { get; set; }
            public string MoveDirectory { get; set; }
            public MoveCommand(string name = "", string action = "Move")
            {
                if (name != "Die")
                {
                    MoveDirectory = Persistance.Database.getMovementDirectory(name.ToLower());
                }
                MoveName = name;
                Action = action;
            }
        }

        public class ReadAudioCommand : Command
        {
            public ReadAudioCommand(string path = "", string ext = "mp3", int len = 0, string action = "ReadAudioFile")
            {
                Path = path;
                Ext = ext;
                Len = len;
                Action = action;
            }

            public int Len { get; set; }
            public string Ext { get; set; }
            public string Path { get; set; }


        }

        public class ChangeEmotionCommand: Command
        {
            public EmotionEnum Emotion { get; set; }

            public ChangeEmotionCommand(EmotionEnum emotion, string action = "ChangeEmotion")
            {
                Emotion = emotion;
                Action = action;
            }
        }

        public class PlayChoregraphyCommand: Command
        {
            public List<string> Movements { get; set; }
            public String Name { get; set; }
            public String Music { get; set; }

            public PlayChoregraphyCommand(dynamic choregraphy, string action = "PlayChoregraphy")
            {
                Action = action;
                Name = (string) choregraphy.Name;
                Movements = (List<string>) choregraphy.Movements;
                Music = (string)choregraphy.Music;
            }

        }

        public class CommParser
        {
            public string ParseCommand(string s)
            {
                s = "{" + s + "}";
                dynamic d = JsonConvert.DeserializeObject(s, typeof(object));
                switch ((string) d.Command)
                {
                    case "Move":
                        return new MoveCommand((string) d.MoveType).ToString();
                    case "ChangeEmotion":
                        return new ChangeEmotionCommand((EmotionEnum)d.Emotion).ToString();
                    default:
                        Log.Error($"Gon unknown command from DF response: {d.ToString()}");
                        return String.Empty;
                }
            }

        }
    }
}
