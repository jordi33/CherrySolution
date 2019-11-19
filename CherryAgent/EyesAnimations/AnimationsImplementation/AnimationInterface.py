from pypot.primitive import Primitive
import time
import pygame
from pygame.locals import *
from os import listdir, path


class AnimationInterface(object):

    def __init__(self, refresh_function, frequency, animation_image_folder, static_image_name):
        self.goingToNeutral = True
        self.refreshFunction = refresh_function
        self.frequency = frequency
        images = []
        folderContent = listdir(animation_image_folder)
        folderContent.sort()
        for image in folderContent:
            unscaled_image = pygame.image.load(path.join(animation_image_folder, image)).convert_alpha()
            images.append(pygame.transform.scale(unscaled_image, (480, 272)))
        static_image = pygame.image.load("/home/poppy/Documents/connectionServ/EyesAnimations/static/{}".format(static_image_name)).convert()
        self.static_image = pygame.transform.scale(static_image, (480, 272))
        self.images = images

    def toNeutral(self):
        for i in range(len(self.images)-1, -1, -1):
            self.refreshFunction(self.images[i])
            time.sleep(self.frequency)

    def fromNeutral(self):
        for i in range(len(self.images)):
            self.refreshFunction(self.images[i])
            time.sleep(self.frequency)
        self.refreshFunction(self.static_image)


