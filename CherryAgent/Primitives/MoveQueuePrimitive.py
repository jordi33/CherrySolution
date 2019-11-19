import pypot.primitive
from pypot.primitive.move import Move, MovePlayer

class MoveQueuePrimitive(pypot.primitive.Primitive): 
    def __init__(self, robot, moveList, idleMotion):
        self.moveList = moveList
        self.robot = robot
        pypot.primitive.Primitive.__init__(self, robot)
        self.shouldStop = True
        self.idleMotion = idleMotion
        #self.movePlayer = CustomMovePlayer(poppy = self.robot, moveDirectory = "dance")

    def run(self):
        self.idleMotion.stop()
        while self.should_stop():
            for move in self.moveList:
                with open("/home/poppy/Documents/move_library/dance/" + move + ".move") as f:
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
                                      'r_elbow_y': 0.}, 1., wait=True)
            self.shouldStop = False

    def should_stop(self):
        return self.shouldStop

    def teardown(self): 
        self.idleMotion.start()
