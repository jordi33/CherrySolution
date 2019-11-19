from EmotionEnum import EmotionEnum
import pygame
import os
from threading import Timer
import time
from AnimationsImplementation import AngryAnimation, HappyAnimation, SadAnimation, SleepAnimation, SurpriseAnimation, NeutralAnimation, BlinkAnimation


class EmotionHandler:

    def __init__(self):
        self.baseDirectory = "/home/poppy/Documents/connectionServ"
        self.currentAnimation = NeutralAnimation.NeutralAnimation(self.refresh, 0, "", "neutral.jpg")
        self.window = pygame.display.set_mode((480, 272), pygame.FULLSCREEN)
        pygame.mouse.set_visible(False)
        static_image = pygame.image.load(self.baseDirectory + "/EyesAnimations/static/neutral.jpg").convert()
        static_image = pygame.transform.scale(static_image, (480, 272))
        self.refresh(static_image)
        self.timer = Timer(5, self.blink)
        self.timer.start()
        self.isChangingEmotion = False

    def changeEmotion(self, emotion):
        # if self.timer.is_alive():
        #     time.sleep(8)
        self.isChangingEmotion = True
        self.currentAnimation.toNeutral()
        newAnimation = self.getAnimation(emotion)
        newAnimation.fromNeutral()
        self.currentAnimation = newAnimation
        self.isChangingEmotion = False

    def refresh(self, backPicture):
        self.window.blit(backPicture, (0, -30))
        pygame.display.flip()

    def getAnimation(self, emotion):
        emotion = EmotionEnum(emotion)
        if emotion == EmotionEnum.ANGRY:
            return AngryAnimation.AngryAnimation(self.refresh, 0.05, self.baseDirectory + "/EyesAnimations/animation/angry_a", "angry.jpg")
        elif emotion == EmotionEnum.HAPPY:
            return HappyAnimation.HappyAnimation(self.refresh, 0.05, self.baseDirectory + "/EyesAnimations/animation/happy_a", "happy.jpg")
        elif emotion == EmotionEnum.NEUTRAL:
            return NeutralAnimation.NeutralAnimation(self.refresh, 0, "", "neutral.jpg")
        elif emotion == EmotionEnum.SAD:
            return SadAnimation.SadAnimation(self.refresh, 0.07, self.baseDirectory + "/EyesAnimations/animation/sad_a", "sad.jpg")
        elif emotion == EmotionEnum.SLEEP:
            return SleepAnimation.SleepAnimation(self.refresh, 0.05, self.baseDirectory + "/EyesAnimations/animation/sleepy_a", "sleepy.jpg")
        elif emotion == EmotionEnum.SURPRISED:
            return SurpriseAnimation.SurpriseAnimation(self.refresh, 0.05, self.baseDirectory + "/EyesAnimations/animation/surprise_a", "surprised.jpg")
        elif emotion == EmotionEnum.BLINK:
            return BlinkAnimation.BlinkAnimation(self.refresh, 0.02, self.baseDirectory + "/EyesAnimations/animation/sleepy_a", "sleepy.jpg")

    def blink(self):
        if not self.isChangingEmotion:
            runningEmotion = self.currentAnimation
            self.changeEmotion(EmotionEnum.BLINK)  # the sleep animation is the one allowing to blink
            self.currentAnimation.toNeutral()
            runningEmotion.fromNeutral()  # going back to the old animation
            self.currentAnimation = runningEmotion
        self.timer = Timer(5, self.blink)
        self.timer.start()

    def close(self):
        if self.isChangingEmotion:
            time.sleep(4)
        self.currentAnimation = None
        self.timer.cancel()
        self.timer.join()
        pygame.quit()
