This projects is intended to be running on an internet connected server. When running, Berit listens for HTTP requests. These requests will either trigger a script or generate human voice using Google Text-To-Speech API and output it to a connected speaker. Using IFTTT, tons of triggers can be used to trigger Berit to say things or execute commands automatically.

Berit requires Maven in order to start. JLayer is recommended for playing MP3 files, and is used by default by running the command "run" in the root folder of the project. On a Raspberry Pi, Omxplayer is used instead, and the application is started with the command "runpi" (requires Omxplayer to be installed). Berit then listens on port 8081 for incoming HTTP requests.

There are two type of packets supported:
  "exe_COMMAND", where COMMAND is the name of the script located in the scripts/ folder.
  "spk_TEXT", where TEXT is the phrase to be synthesized. It can also be used to play mp3 files located in the speech/ folder, where TEXT is the name of the mp3 file (exlucing ".mp3").
Make sure to forward port 8081 to the server running this software.
  
In order for the speech synthesis to work, the GOOGLE_APPLICATION_CREDENTIALS environment variable needs to be set to the local json key. See the following link: https://cloud.google.com/text-to-speech/docs/reference/libraries#client-libraries-install-java.
  
If Omxplayer doesn't work, try executing the command "sudo chmod 777 /dev/vchiq".