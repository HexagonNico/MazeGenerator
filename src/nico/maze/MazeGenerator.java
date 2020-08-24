package nico.maze;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

public class MazeGenerator extends JPanel implements ActionListener {

    public static final int MAZE_WIDTH = 80;
    public static final int MAZE_HEIGHT = 45;
    public static final int ROOM_SIZE = 12;

    private final Random random;

    //The actual maze, a 2D array of rooms
    private final Room[][] maze;
    private final boolean[][] generated;

    //The generator cursor's position
    private int posX;
    private int posY;

    public MazeGenerator() {
        this.setPreferredSize(new Dimension(MAZE_WIDTH * ROOM_SIZE, MAZE_HEIGHT * ROOM_SIZE));

        this.random = new Random();
        this.maze = new Room[MAZE_WIDTH][MAZE_HEIGHT];
        this.generated = new boolean[MAZE_WIDTH][MAZE_HEIGHT];

        this.initMaze();
        this.setRandomPoint();
    }

    /**Initializes the 2D array containing the rooms.
     * Called in this class constructor
     */
    private void initMaze() {
        for(int x = 0; x < MAZE_WIDTH; x++) {
            for(int y = 0; y < MAZE_HEIGHT; y++) {
                this.maze[x][y] = new Room();
                this.generated[x][y] = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        //This is called by the timer in MazeMain
        this.generate();
    }

    /**This can be used instead of the timer to generate the maze instantly.
     * When using this the Timer in MazeMain and the ActionListener in this class are not needed
     */
    public void generateInstantly() {
        while(!finished()) {
            this.generate();
        }
    }

    /**Maze generation algorithm
     * This can be called in actionPerformed or in generateInstantly
     */
    private void generate() {
        //First choose a random direction
        Direction direction = this.getRandomDirection();

        //If the room in that direction has not been generated yet a path is carved between the two
        if(!generated[posX + direction.dx][posY + direction.dy]) {
            this.maze[posX][posY].carvePath(direction);
            this.maze[posX + direction.dx][posY + direction.dy].carvePath(direction.opposite);
        }

        //Change the cursor's position
        this.posX += direction.dx;
        this.posY += direction.dy;

        //Mark that room as generated
        this.generated[posX][posY] = true;
    }

    /**Will place the cursor at a random position.
     * Called before generating the maze (in this class constructor)
     */
    private void setRandomPoint() {
        this.posX = random.nextInt(MAZE_WIDTH);
        this.posY = random.nextInt(MAZE_HEIGHT);
        this.generated[posX][posY] = true;
    }

    /**This chooses a random direction and check if that position is valid,
     * if its not, another direction is chosen
     */
    private Direction getRandomDirection() {
        Direction randomDirection = Direction.getRandom(random);
        if(positionIsValid(posX + randomDirection.dx, posY + randomDirection.dy))
            return randomDirection;
        else
            return getRandomDirection();
    }

    /**Checks if the given position is a valid position
     * (if it's not out of bounds)
     */
    private boolean positionIsValid(int x, int y) {
        return x >= 0 && y >= 0 && x < MAZE_WIDTH && y < MAZE_HEIGHT;
    }

    /**Checks if the maze is completed
     * Used in generateInstantly
     */
    private boolean finished() {
        for(int x = 0; x < MAZE_WIDTH; x++) {
            for(int y = 0; y < MAZE_HEIGHT; y++) {
                //If one room hasn't been generated yet, the maze is not finished
                if(!generated[x][y])
                    return false;
            }
        }
        //If all the rooms have been generated, the maze is finished
        return true;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        //Draw the maze
        for(int x = 0; x < MAZE_WIDTH; x++) {
            for(int y = 0; y < MAZE_HEIGHT; y++) {
                this.maze[x][y].draw(graphics, x * ROOM_SIZE, y * ROOM_SIZE, ROOM_SIZE);
            }
        }

        //Draw the cursor
        graphics.setColor(Color.RED);
        graphics.drawRect(posX * ROOM_SIZE, posY * ROOM_SIZE, ROOM_SIZE, ROOM_SIZE);

        super.repaint();
    }
}
