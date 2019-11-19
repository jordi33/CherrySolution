from Tkinter import * 

class GraphicInterface: 
    def __init__(self, blocking): 
        self.root = Tk()
        if blocking: 
            self.root.overrideredirect(True)
            self.root.geometry("{0}x{1}+0+0".format(self.root.winfo_screenwidth(), self.root.winfo_screenheight()))
        else: 
            pad=3
            self._geom='200x200+0+0'
            self.root.geometry("{0}x{1}+0+0".format(
            self.root.winfo_screenwidth()-pad, self.root.winfo_screenheight()-pad))
            self.root.bind('<Escape>',self.toggle_geom)  
            # photo = PhotoImage(file="images/" + imageName)
            # photo = PhotoImage(file=imageName)
            photo = PhotoImage(file="images/angry.png")
            canvas = Canvas(self.root,width=self.root.winfo_screenwidth(), height=self.root.winfo_screenheight())
            canvas.create_image(0, 0, anchor=NW, image=photo)
            canvas.pack()

    def addImage(self, imageName):
        pass

    def toggle_geom(self,event):
        geom=self.root.winfo_geometry()
        print(geom,self._geom)
        self.root.geometry(self._geom)
        self._geom=geom

    def run(self): 
        self.root.mainloop()

interface = GraphicInterface(blocking = False)
interface.addImage("angry.png")
interface.run()