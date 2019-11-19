from pypot.creatures import PoppyTorso
from MovePrimitive import MovePrimitive
from MoveQueuePrimitive import MoveQueuePrimitive
from LoopMovePrimitive import LoopMovePrimitive
import time


class PrimitiveProvider():
    def __init__(self):
        self.poppy = PoppyTorso()
        self.idleMotion = self.poppy.upper_body_idle_motion
        self.idleMotion.start()
        self.current_primitive = None

    def getMovePrim(self, moveName, moveDirectory=""):
        prim = MovePrimitive(self.poppy, self.idleMotion, moveDirectory + "/" + moveName)
        self.current_primitive = prim
        return prim

    def getQueuePrim(self, moveList):
        prim = MoveQueuePrimitive(self.poppy, moveList, self.idleMotion)
        self.current_primitive = prim
        return prim

    def releaseMotors(self):
        self.idleMotion.stop()
        for m in self.poppy.motors:
            m.compliant = True
            time.sleep(0.2)

    def getLoopPrim(self, refresh_freq, moveDirectory=""):
        prim = LoopMovePrimitive(self.poppy, refresh_freq, self.idleMotion, moveDirectory)
        self.current_primitive = prim
        return prim

    def killPrimitive(self):
        if self.current_primitive.is_alive():
            self.current_primitive.stop()

    def close(self):
        if self.poppy is not None:
            print "Closing Primitive Provider..."
            gbye_thread = self.getMovePrim("goodbye")
            gbye_thread.start()
            gbye_thread.join()
            self.releaseMotors()
            self.poppy = None
            print "\t Done."
