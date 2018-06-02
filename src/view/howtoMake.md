# How to make mazes

Mazes must be saved as text files, and must follow the following syntax:

 First line: number of columns, 
 Second line: number of rows,
 Third line: 'lights:on' or 'lights:off',
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
Otherwise, if they are saved in the same folder as this programs .JAR
they will be loaded automatically upon launch.

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
