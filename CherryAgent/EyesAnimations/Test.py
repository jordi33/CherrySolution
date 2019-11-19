import time
from EmotionEnum import EmotionEnum
import EmotionHandler
import sys

handler = EmotionHandler.EmotionHandler()

time.sleep(10)
handler.changeEmotion(EmotionEnum.HAPPY)

time.sleep(10)
handler.changeEmotion(EmotionEnum.SAD)
time.sleep(5)

handler.quit()
sys.exit(0)
