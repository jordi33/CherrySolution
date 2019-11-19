from pypot.primitive.move import Move, MovePlayer
from pypot.creatures import PoppyTorso

class CustomMovePlayer:
    def __init__(self, poppy = None, moveDirectory = ""):
	if poppy is None:
	    poppy = PoppyTorso()
	print("Constructor called")
	self.poppy = poppy
        self.movePath = "/home/poppy/Documents/move_library/" + moveDirectory
	if (moveDirectory != ""):
	    self.movePath = self.movePath +"/"
	#for m in self.poppy.motors:
        #    m.compliant = False
	print("constructor ended")

    def resetPosition(self):
        self.poppy.goto_position({'abs_z': 0.,
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

    def playMove(self, moveName):
	print("Playing move")
        self.resetPosition()
	print("Position reset")
        with open(self.movePath+moveName+".move") as f:
	    m = Move.load(f)
        move_player = MovePlayer(self.poppy, m)
        print("Starting move player")
	move_player.start()
        move_player.wait_to_stop()
        self.resetPosition()
