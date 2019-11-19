#!/usr/bin/python
from Tkinter import *
import time
gui = Tk()
screen_height = gui.winfo_screenheight()
screen_width = gui.winfo_screenwidth()

# gui.geometry(str(screen_width) + "x" + str(screen_height))
# c = Canvas(gui ,width=screen_width ,height=screen_height)
# c.pack()
# oval = c.create_oval(5,5,60,60,fill='pink')
# xd = 5
# yd = 10

frames = [PhotoImage(file='rick.gif',format = 'gif -index %i' %(i)) for i in range(61)]

def update(ind):
   if ind == 61:
      ind = 0
   frame = frames[ind]
   ind += 1
   label.configure(image=frame)
   gui.after(100, update, ind)
label = Label(gui)
label.pack()
gui.after(0, update, 0)

# while True:
#   c.move(oval,xd,yd)
#   p=c.coords(oval)
#   if p[3] >= screen_height or p[1] <=0:
#      yd = -yd
#   if p[2] >= screen_width or p[0] <=0:
#      xd = -xd
#   gui.update()
#   time.sleep(0.025) 
gui.title("First title")
gui.mainloop()