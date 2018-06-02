# How to make mazes

Mazes must be saved as text files, and must follow the following syntax:

 First line: number of columns, 
 Second line: number of rows,
 Third line: "lights:on" or "lights:off",
 followed by characters 'making up' the maze.
	* '#'	wall
	* ' '	floor
	* '-'	exit
	* '*'	start
	* '+'	rock
	* 'x'	teleport to start
	* 't'	teleport from here
	* 'h'	teleport to here

	An example is shown below.
 
The files can then be loaded with 'File' -> 'Open labyrint',

Files placed in levels directory in the same folder as the .JAR file,
are loaded automatically as levels, given that they follow the naming
syntax "level" + levelNumber + ".txt", like "level1.txt".

Example of a maze .txt file:
 11
 7
 lights:off
 #####-####
 #*  #h#  #
 #+++#x#  #
 #x x    +#
 #   ++   #
 #       t#
 ##########
