# CherrySolution
Update version of the Cherry Architecture - Poppy Robot - Mobile App - Control Server

Cherry solution is composed of three sub projects:
<p align="center">
<img src="https://github.com/jordi33/CherrySolution/blob/master/ReadMePics/archi_cherry.PNG" alt="" width="600" height="500">
</p>



## Cherry Server 

#### C# Controller

This is a C# web service created to interact with Poppy Core and the mobile application through a websocket. 
It uses the Google Cloud Speech API for Text to Speech/Speech to Text and the DialogFlow application, to receive and return HTTP real-time responses.

__Installation__

At first, you need to setup the Google Cloud environment : activate the Speech Api and get your DialogFlow Token. Follow the Readme_GcloudEnvironment_Setup and the Server-installation documentation for details.

You can now launch the C# project on your IDE (Visual Studio for example). Your need to modify the DfToken and the ApiKeyPath in the Settings.settings file in the CloudService project :

https://github.com/jordi33/CherrySolution/blob/master/CherryControlServer/CloudServices/Properties/Settings.settings

Next, you need to modify the credentials to your MongoDB database. The config is in the Settings.settings file in the Database project :

https://github.com/jordi33/CherrySolution/blob/master/CherryControlServer/Database/Properties/Settings.settings

You can now launch the project, and create and executable file.

#### MongoDB Database

The server uses a MongoDB database, you can install it through the official documentation :

https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/. 

You need to create and administrator account, and to populate the database. Follow the Server-installation documentation for details.

## Cherry Mobile Application

The mobile application, written in Kotlin, allows to interact with the robot via the server with a websocket.

__Installation__

After importing the project to an Android IDE (Android Studio for example), you need to modify the ip address of the C# server. It's located in the ServerProps.properties file :

https://github.com/jordi33/CherrySolution/blob/master/MobileApp/app/src/main/assets/ServerProps.properties

You can now launch the project, and deploy it to an android device.

## Poppy Core Agent

The code inside the robot, driven by a UNIX OS card (raspberry for example) and written in python 2.7

__Installation__

Copy the code to the raspberry card. You need to modify the address to the C# server, to access it through websocket. This ip address is located in the conf.py file :

https://github.com/jordi33/CherrySolution/blob/master/CherryAgent/Core/conf.py

You can now launch the project through the Start.py file at root :

python Start.py

You may need to install some python packages.

## Setup a demo

After installing all the Cherry infrastructure components, you can start a demo.

All the components must be on the same network. Start the Cherry Server first, so that the Robot can connect to it, and finally the mobile app to connect to the robot.
