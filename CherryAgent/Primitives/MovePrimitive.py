import time

import pypot.primitive
from pypot.primitive.move import Move, MovePlayer

class MovePrimitive(pypot.primitive.Primitive):

    def __init__(self, robot, idleMotion, moveName, moveDirectory = ""):
        self.robot = robot
        self.moveName = moveName
        self.moveDirectory = moveDirectory
        if moveDirectory != "":
            self.moveDirectory = self.moveDirectory + "/"
        self.idleMotion = idleMotion
        pypot.primitive.Primitive.__init__(self, robot)
        self.idleMotion.stop()

    def run(self):
        with open("/home/poppy/Documents/move_library/"+self.moveDirectory+self.moveName+".move") as f:
            m = Move.load(f)
        move_player = MovePlayer(self.robot, m)
        move_player.start()
        move_player.wait_to_stop()
        self.robot.goto_position({'abs_z': 0.,
                     'bust_y': 0.,
                     'bust_x': 0.,
                     'head_z': 0.,
                     'head_y': 0.,
                     'l_shoulder_y': -20.,
                     'r_shoulder_y': -20.,
                     'l_shoulder_x': 0.,
                     'r_shoulder_x': 0.,
                     'l_arm_z': 0.,
                     'r_arm_z': 0.,
                     'l_elbow_y': 0.,
                     'r_elbow_y': 0.}, 2., wait=True)

    def teardown(self): 
        self.idleMotion.start()
