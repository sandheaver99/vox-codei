# vox-codei
internet coding challenge

From codingame.com : 

London, September 27th 1989, 6pm, Museum of Shadows...  
 
Destruction, then cold, and a void... Within this void, revenge has settled, warming me. It has twisted itself across my mind, and has kept me company during all these years. The moment to unleash it has now arrived...
 
My infiltration work has paid off. I have obtained the security codes for Fate, the government's super-computer. Without it, the Eye shall be blind, and the Ear shall be deaf. Deprived of its surveillance organs, the fascist regime will be at its most vulnerable. My vengeance shall smash down upon the vile, and the sound of my victory will vibrate across the vast expanse of villainy - verily.
 
The one thing left to do is to break down Fate's defenses. Child's play for a developer...
 
Help V destroy Fate's firewall!
 
 
THE PROGRAM :
You access the central computer's firewall interface via V's vPad. The firewall is comprised of surveillance nodes.

You must destroy these nodes by overloading them with the help of fork-bombs.

The interface is presented by a rectangular grid, of variable width and height. It is provided at the beginning of each game in the shape of a table of characters.

Details:
Several surveillance nodes (@) are positioned on the grid.
Indestructible and passive nodes (#) can also be present on the grid.
You have a fixed number of bombs to use at the start of the game.
Each game turn, you may either place a bomb on the grid or wait.
The fork-bombs explode 3 turns after having been placed.
Bombs explode in a cross shape. They have a range of 3 cells (see schema further down).
The bombs will destroy every node the explosion comes into contact with. Explosions do not go through passive nodes.
A fork-bomb can explode before the 3 turns if it is triggered by the explosion of another fork-bomb.
You cannot place a bomb on a node. You must place them on empty cells (.) of the grid.
You have a limited amount of turns in which to destroy all of the surveillance nodes, or you will be detected and lose the game.
You will win once all of the surveillance nodes have been destroyed in the allotted amount of turns.
 
Example for the explosion of a bomb placed at (3, 3) in the following interface grid:
...@...
...#...
.......
.....@@
.......
.......
.......

1rst round after placing the fork-bomb	
2nd round, the fork-bomb is charging	
3rd round, the bomb explodes
The two nodes to the right are destroyed by the blast. The top node is protected by the indestructible passive node.  
The program must first read the initialization data from standard input. Then, within an infinite loop, read the contextual data (number of bombs and remaining turns) from the standard input and provide the next instruction to standard output. The protocol is detailed further down.

Don’t forget to run the tests by launching them from the “Test cases” window. You do not have to pass all tests to enter the leaderboard. Each test you pass will get you some points (for example 10%).

The tests provided are similar to the validation tests used to compute the final score but are slightly different.


INITIALIZATION INPUT:
Line 1 : 2 integers width and height. They correspond to the size of the grid.
height next lines: 1 string mapRow of length width. Each line corresponds to a row in the grid. The . represents an empty cell, @ represents surveillance node and # represents a passive node.

INPUT FOR ONE GAME TURN:
Line 1 : 2 integers rounds and bombs. They correspond to the number of turns remaining before detection and the number of bombs still available to you.

OUTPUT FOR ONE GAME TURN:
A single line (including newline character) to indicate which action to take:
Either the keyword WAIT to do nothing
or the (x, y) coordinates in the grid of where you want to place a fork-bomb (2 integers separated by a space). The (0,0) coordinate is located in the upper-left corner of the grid


CONSTRAINTS:
2 < width, height < 20
3 < rounds < 20
0 < bombs < 10
0 <= x < width
0 <= y < height
Duration of one game turn: 100 ms
