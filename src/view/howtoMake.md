# How to make mazes

Mazes must be saved as text files, and must follow the following syntax:

 They must start with number of columns, then number of rows on the line below,
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
 #####-####
 #*  #h#  #
 #+++#x#  #
 #x x    +#
 #   ++   #
 #        #
 ##########