from pypot.creatures import PoppyTorso
import numpy
import pypot.primitive
import time
from RandomMovePlayer import RandomMovePlayer

class LoopDancePrimitive(pypot.primitive.LoopPrimitive):
    def __init__(self, robot, refresh_freq):
        self.robot = robot
        pypot.primitive.LoopPrimitive.__init__(self, robot, refresh_freq)

    def update(self):
	fake_motors = []
	for m in self.robot.motors:
	    if m.name == "l_arm_z" or m.name == "r_arm_z":
                fake_motors.append(self.get_mockup_motor(m))
	randomMovePlayer = RandomMovePlayer(self.robot, "congratulations")
	randomMovePlayer.playRandomMove()
