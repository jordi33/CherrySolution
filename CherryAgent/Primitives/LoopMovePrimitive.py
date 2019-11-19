import pypot.primitive
#from RandomMovePlayer import RandomMovePlayer
import os
import random
from pypot.primitive.move import Move, MovePlayer

class LoopMovePrimitive(pypot.primitive.LoopPrimitive):
    def __init__(self, robot, refresh_freq, idleMotion, moveDirectory = ""):
        self.robot = robot
        self.moveDirectory=moveDirectory
        self.movePath = "/home/poppy/Documents/move_library/" + moveDirectory
        self.moveList = os.listdir(self.movePath)
        if moveDirectory != "":
	      self.movePath = self.movePath + "/"
        pypot.primitive.LoopPrimitive.__init__(self, robot, refresh_freq)
        self.idleMotion = idleMotion
        self.idleMotion.stop()

    def update(self):
        random_move = random.choice(self.moveList)
        random_move_name = os.path.splitext(random_move)[0]
	print("playing " + random_move_name)
	self.playMove(random_move_name)

    def playMove(self, moveName):
        with open(self.movePath+moveName+".move") as f:
            m = Move.load(f)
        move_player = MovePlayer(self.robot, m)
        move_player.start()
        move_player.wait_to_stop()
    
    def teardown(self): 
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
        self.idleMotion.start()
