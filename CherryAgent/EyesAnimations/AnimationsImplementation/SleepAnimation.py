from AnimationInterface import AnimationInterface
import time


class SleepAnimation(AnimationInterface):
    def __init__(self, refresh_function, frequency, image_folder, static_image_name):
        super(SleepAnimation, self).__init__(refresh_function, frequency, image_folder, static_image_name)
        new_images = [self.images[0], self.images[1], self.images[2], self.images[3],
                      self.images[4], self.images[5], self.images[6], self.images[7],
                      self.images[8], self.images[7], self.images[6], self.images[5],
                      self.images[6], self.images[7], self.images[8], self.images[9]]
        frequency_array = [0.07, 0.07, 0.07, 0.07,
                           0.07, 0.07, 0.07, 0.07,
                           0.25, 0.12, 0.12, 0.12,
                           0.12, 0.12, 0.12, 0.12]
        self.images = new_images
        self.frequency_array = frequency_array

    def toNeutral(self):
        for i in range(len(self.images)-1, -1, -1):
            self.refreshFunction(self.images[i])
            time.sleep(self.frequency_array[i])

    def fromNeutral(self):
        for i in range(len(self.images)):
            self.refreshFunction(self.images[i])
            time.sleep(self.frequency_array[i])
        self.refreshFunction(self.static_image)
