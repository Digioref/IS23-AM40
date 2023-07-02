# MyShelfie (group IS23-AM40)
## Members 
- 10776567 Francesco Di Giore [@Digioref](https://github.com/Digioref) -- 
  francesco.digiore@mail.polimi.it
- 10743207 Filippo Maria Bertossi [@filippobertossi](https://github.com/filippobertossi) -- 
  filippomaria.bertossi@mail.polimi.it
- 10730677 Marco Brambillasca [@MarcoBrambillasca](https://github.com/MarcoBrambillasca) -- 
  marcoandrea.brambillasca@mail.polimi.it
- 10668044 Daniele Di Giorgio [@DanieleDiGiorgio](https://github.com/DanieleDiGiorgio) -- 
  daniele2.digiorgio@mail.polimi.it

## Project Status

| Project Module | Status | Current | Notes |
| -------------- | ------ | ------- | ------- |
| UML | 🟢 | 100 % |
| Basic Rules | 🟢 | 100 % |
| Complete Rules | 🟢 | 100 % |
| CLI | 🟢 | 100 % |
| GUI | 🟢 | 100 % |
| RMI | 🟢 | 100 % |
| Socket | 🟢 | 100 % |

| Advanced Features | Status | Current | Notes |
| -------------- | ------ | ------- | ------- |
| Multiple Matches | 🟢 | 100 % |
| Connections' Resilience | 🟢 | 100 % |
| Chat | 🟢 | 100 % |

| Test Module | Status | Current | Notes |
| -------------- | ------ | ------- | ------- |
| Test Model | 🟢 | 100 % |
| Test Controller | 🟢 | 100 % |

# How to install

The game consists of a single jar file by the name AM40.jar. It can be found in /shade directory after building the project with Maven.
This file can launch both the Server and the Client (CLI or GUI can be selected in the starting phase)

To run the server, use the command:
java -jar AM40.jar --server

To run the client, use the command:
java -jar AM40.jar --client

from the command line in the jar's folder. 
You can't use the windows' jar generated file in linux and viceversa. The jar must be regenerated.

Supported in Windows and Linux.

For macOS you have to change the settings of the screen, to do it just follow the instructions in the comments in the method [newScene](https://github.com/Digioref/IS23-AM40/blob/main/src/main/java/it/polimi/ingsw/am40/GUI/Viewer.java) starting at line 378.


#How to play

After launching a client, you have to choose how to play
If you want to play using a Command Line Interface (CLI) press C
If you want to play using a Graphic User Interface (GUI) press G

## CLI
1. If you have chosen CLI, the newxt step is chooing the type of connection: press S for Socket or R of RMI
2. Then you will be asked to enter the IP of the server (press L for localhost, the default is localhost)
3. REMEMBER! In any moment you can write the command "help" to print a list of the available commands and their functionalities
4. Write the command "**login nickname**" (substitute nickname with the name you want to use in game, if your nickname is already used the system will provide you some suggestions of similar nicknames!)
5. The first player sets the number of players of the game, use the command **setPlayers [int]**
6. When the number of players has been reached, the game starts! when it's written "your turn" you can use in sequence the following commands:

Small rules recap: you have to complete your bookshelf picking the tiles on the board
1. **select [int, int]** (the " , " represents the coordinate of a tile)
2. You can choose a maximum of 3 tiles that has to be adjacent and with one side free
3. **remove** command removes the tiles selected
4. **pick** sets the tiles selected as picked (cannot be undone)
5. **order [] [] []** set the order of the tiles (the first [] represents the first tile selected, the second [] the second and so on) example order 2 1 3
6. **insert []** to insert the picked tiles in the choosen column 

6. **getboard** prints the board
7. **getbookshelf** prints your bookshelf
8. **getbookall** prints all the bookshelves

9. **getcommgoals** prints the common goals
10. **getpersonalgoals** prints the personal goals

11. **getcurrent** returns the name of the current player
12. **getplayers** returns the players' names

13. **getcurscore** returns your current score
14. **gethiddenscore** returns your current score plus the points obtained by your secret personal goal

15. **pong** to see if the server is still online
16. **chat** to enter the chat -> write "**to [player] message**", if you don't specify the player the message will be broadcasted
17. **viewchat** to see the previous messages in the chat

The CLI tutorial ends here, enjoy playing MyShelfie!!

## GUI
Follow these steps:

1. Select the type of connection
2. Enter your nickname in game
3. The first player needs to set the number of the players in the game
The game starts!

1. **Select** a maximum of three adjacent tiles with at least one free side each (the tiles that can be selected have dotted borders)
2. To clear the selection press the **red cross**
3. To pick the selected tiles press the **orange arrow**
4. **Order** the tiles clicking on them
5. Choose the **column** you want to insert the tiles with the arrows
6. Use the **chat** (notifications shows you if you have new messages!)
7. On the top right you can find the **scores**
Have fun!

