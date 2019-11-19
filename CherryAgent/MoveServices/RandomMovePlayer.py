from MovePlayer import CustomMovePlayer
import os
import random

class RandomMovePlayer:
    def __init__(self, robot, moveDirectory = ""):
        self.movePath = "/home/odroid/Documents/move_library/" + moveDirectory
        self.moveList = os.listdir(self.movePath)
	self.movePlayer = CustomMovePlayer(robot, moveDirectory=moveDirectory)

    def playRandomMove(self):
        random_move = random.choice(self.moveList)
        random_move_name = os.path.splitext(random_move)[0]
        self.movePlayer.playMove(random_move_name)

