game
====

This project implements the game of Breakout.

Name: Ryan Culhane

### Timeline

Start Date: 1/13/2019

Finish Date: 1/20/2019

Hours Spent: 30

### Resources Used
* JavaFX Documentation
* https://www.tutorialspoint.com/Reading-a-Text-file-in-java
* https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm

### Running the Program

Main class: GamePlayer

Data files needed: 
* resources/brick1.gif
* resources/brick2.gif
* resources/brick3.gif
* resources/brick4.gif
* resources/brick10.gif
* resources/level_one_layout.txt
* resources/level_two_layout.txt
* resources/level_three_layout.txt
* paddle.gif
* ball.gif

Key/Mouse inputs:
* BACKSPACE - return to the start menu
* '1' - begin level 1 (PauseScreen Only)
* '2' - begin level 2 (PauseScreen Only)
* '3' - begin level 3 (PauseScreen Only)
* 'B' - change to beginner mode (PauseScreen Only)
* 'I' - change to intermediate mode (PauseScreen Only)
* 'A' - change to advanced mode (PauseScreen Only)
* Left and Right arrows decrease and increase paddle velocity
* SPACE pauses game and displays PauseScreen/Splash Screen
* ENTER sets ball in motion
* 'B' - initiate BouncerDropper power-up (In-Game Only)
* 'E' - initiate ExtraPaddle power-up (In-Game Only)
* 'L' - initiate LongPaddle power-up (In-Game Only)
* 'D' - initiate SearchAndDestroy power-up (In-Game Only)


Cheat keys:
* 'L' - add another life (accessible only from PauseScreen/Splash Screen)
* 'R' - reset the level (accessible only from PauseScreen/Splash Screen)
* 'T' - give (nearly) infinite time (accessible only from PauseScreen/Splash Screen)
* '# keys - Jump to level of that number (options: 1,2,3) (accessible only from PauseScreen/Splash Screen)
* 'P' - gain access to all power-ups (accessible in game)

Known Bugs:
* If Bouncer hits the corner of a brick it will count as two collisions
and sometime destroy bricks on only "one" hit.
* Power-up animations are not included in Tutorial which would have been a 
nice touch.
* If you hit 'B' to activate another BouncerDrop before the previous one
completed, the first one does not finish and the Bouncers get stuck on scene.


Extra credit: (Interpreted as extra feature we were supposed to implement)
* Tutorial that introduces the player to the game and the various objects
they may encounter and the keys they need to get used to.  Tutorial also allows
player to practice moving the paddle.


### Notes


### Impressions

