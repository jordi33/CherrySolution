import pypot
import time
from pypot.creatures import PoppyTorso 
from pypot.primitive.move import MoveRecorder #primitive used for recording motion

class MoveCreator: 
    blockedMotors = ["bust_y", "abs_z"]
    poppy = PoppyTorso()
    record = MoveRecorder(poppy, 50, poppy.motors)  #record a move on the whole robot

    def init(self): 
        #enregistrer un mouvement
        for m in self.poppy.motors:
            if m.name in self.blockedMotors: 
                m.compliant = False
            else:
                m.compliant = True

    def startRecord(self, recordName, recordPath, recordTime):
        self.init()
        # Give you time to get ready
        print('Get ready to record a move...')
        time.sleep(5)

        # Start the record
        self.record.start()
        print('Now recording !')

        # Wait for 10s so you can record what you want
        time.sleep(recordTime)

        # Stop the record
        print('The record is over!')
        self.record.stop() 

        path = recordPath + "/" + recordName + ".move"
        with open(path, 'w') as f:
            self.record.move.save(f)

        for m in self.poppy.motors:
            m.compliant = True
