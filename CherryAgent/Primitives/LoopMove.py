from pypot.creatures import PoppyTorso
import numpy
import pypot.primitive
import time
from RandomMovePlayer import RandomMovePlayer

class LoopMovePrimitive(pypot.primitive.LoopPrimitive):
    
    def __init__(self, robot, refresh_freq, moveDirectory):
        self.robot = robot
        self.moveDirectory=moveDirectory
        pypot.primitive.LoopPrimitive.__init__(self, robot, refresh_freq)

    def update(self):
        fake_motors = []
        for m in self.robot.motors:
          fake_motors.append(self.get_mockup_motor(m))
	#randomMovePlayer = RandomMovePlayer(self.robot, "congratulations")
        randomMovePlayer = RandomMovePlayer(self.robot, self.moveDirectory)
        randomMovePlayer.playRandomMove()
    
    
