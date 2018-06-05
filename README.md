# Labyrint Spill / Maze Game

## Description

The game loads a maze from a text file with the specified format into a maze.
The goal of the game is to reach the exit.

## Background

The game started as a school exercise, for the class DAT100 at University of Stavanger.
We were working on making making GUI applications using JavaFX, and
reading from text files. 
However, I have since added several features and improvements.

## Features

### Blocks, Teleporters

The maze includes blocks, which the player pushes around in the maze.
You cannot push more than one block at a time, and cannot push
a block into a bush. If you push a block into a teleporter the block
disappears.

There are two types of teleporters. The ones with red stroke teleports the player
back to the start position. The ones with purple stroke teleports the player to another
location on the maze, specified in the maze text file, but unknown to the player.

![](https://christianfosli.github.io/img/Labyrint_020.jpg)

### Levels

I have created 5 maze text files. These are located in the levels directory of the repo,
and are automatically recognized as levels upon launch.

![](https://christianfosli.github.io/img/Labyrint_020_levels.jpg)

If you download the game as a .JAR file, the maze directory needs to be placed in the 
same directory as the .JAR file.

### Lights

The maze text file specifies whether lights should be on or off.
When lights are off, the maze becomes dark at all areas except around where the player has moved.

![](https://christianfosli.github.io/img/Labyrint_020_lights.jpg)

### Dynamic scaling

The game is resizable, and automatically scales according to window size.

![](https://christianfosli.github.io/img/Labyrint_020_scaling.jpg)
