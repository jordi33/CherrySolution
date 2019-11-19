using Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistance
{
    public class DatabaseFiller
    {
        public static void fillDatabase()
        {
            Database db = Database.Instance;

            // Activities
            Database.addActivity("Play", "Just casually playing");
            Database.addActivity("Laugh", "Poppy is so funny");

            // Children 
            Database.addChild("Toto", 15, new List<string> { "Football", "Games" });
            Database.addChild("Tata", 5, new List<string> { "Pokemon", "Music" });

            // Cloud Parameters
            Database.addCloudParams();

            // Communications Parameters
            Database.addCommunicationsParam();

            // Jokes 
            Database.addJoke("Qu'est ce qui est jaune et qui attend? Jonathan");
            Database.addJoke("C'est l'histoire d'une blague vaseuse. Mets tes bottes.");
            Database.addJoke("C'est quoi une chauve souris avec une perruque ? Une souris");

            //Movements 
            Database.addMovement("dab", "dance", "image");
            Database.addMovement("shaking", "dance", "image");
            Database.addMovement("twist", "dance", "image");
            Database.addMovement("balancing", "dance", "image");
            Database.addMovement("clap_left_right", "dance", "image");
            Database.addMovement("l_arm_bwd", "dance", "image");
            Database.addMovement("l_arm_fwd", "dance", "image");
            Database.addMovement("l_elbow_up", "dance", "image");
            Database.addMovement("r_arm_bwd", "dance", "image");
            Database.addMovement("r_arm_fwd", "dance", "image");
            Database.addMovement("r_elbow_up", "dance", "image");
            Database.addMovement("hello", "", "image");
            Database.addMovement("goodbye", "", "image");
            Database.addMovement("congratulations_1", "congratulations", "image");

            // Robots
            Database.addRobot("PoleInno", 1.0);

            // Tales
            Database.addTale("Cinderella", "Her evil stepmother and stepsisters would not let her go to the ball, but her fairy godmother made it happen with magic. She danced with the prince and they fell in love. Since she had to leave by midnight, she ran and lost one slipper. The prince found the slipper and searched for her. After he found her, they were married and lived happily ever after");
            Database.addTale("Elves and the Shoemaker", "A shoemaker and his wife were very poor. One day they ran out of leather <break time='200ms'/> so they went to bed. In the morning, they found a pair of shoes and a passerby bought them. The next night, another pair of shoes appeared. The third night they hid and saw two elves making shoes. In gratitude, they made clothes for the elves, as winter was approaching. The elves were very happy and went on to help someone else.");

            // Users
            Database.addUser("admin", "admin");
            Database.addUser("nonadmin", "nonadmin");

            // Choregraphies 
            Database.addChoregraphy("Chore 1", new List<string> { "dab", "dab", "dab" }, "pop");
            Database.addChoregraphy("Chore 2", new List<string> { "shaking", "balancing" }, "rock");

            Database.addCalculatorItem("1+1", 2, 1);
            Database.addCalculatorItem("1+2", 3, 1);
            Database.addCalculatorItem("35-33", 2, 2);
            Database.addCalculatorItem("22-14", 8, 2);
            Database.addCalculatorItem("43 - 16", 27, 2);
            Database.addCalculatorItem("1+5", 6, 1);
            Database.addCalculatorItem("1*8", 8, 3);
            Database.addCalculatorItem("3*8", 24, 3);
            Database.addCalculatorItem("4*8", 32, 3);
            Database.addCalculatorItem("1+5", 6, 1);

            Log.Debug("Database filled");

        }
    }
}
