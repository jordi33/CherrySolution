# -*- coding: utf-8 -*-
 
from Tkinter import * 

root = Tk()
root.overrideredirect(True)
root.geometry("{0}x{1}+0+0".format(root.winfo_screenwidth(), root.winfo_screenheight()))


photo = PhotoImage(file="images/angry.png")

canvas = Canvas(root,width=root.winfo_screenwidth(), height=root.winfo_screenheight())
canvas.create_image(0, 0, anchor=NW, image=photo)
canvas.pack()
root.mainloop()
