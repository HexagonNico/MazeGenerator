package nico.maze;

import java.awt.*;
import java.util.HashSet;

public class Room {

    private final HashSet<Direction> paths;

    /**The room class is only used to hold a HashSet
     * This way we can use Room[][] instead of HashSet[][], which gives a warning
     */
    public Room() {
        this.paths = new HashSet<>();
    }

    /**Adds a path to this room
     * Will add the given direction to the stack
     */
    public void carvePath(Direction direction) {
        this.paths.add(direction);
    }

    /**Draw the room
     * Called in MazeGenerator#paintComponent
     */
    public void draw(Graphics graphics, int x, int y, int roomSize) {
        //Draw the white background
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, roomSize, roomSize);

        //Draw the black paths
        int pathSize = roomSize / 3;
        graphics.setColor(Color.BLACK);
        if(paths.contains(Direction.UP))
            graphics.fillRect(x + pathSize, y, pathSize, pathSize * 2);
        if(paths.contains(Direction.DOWN))
            graphics.fillRect(x + pathSize, y + pathSize, pathSize, pathSize * 2);
        if(paths.contains(Direction.LEFT))
            graphics.fillRect(x, y + pathSize, pathSize * 2, pathSize);
        if(paths.contains(Direction.RIGHT))
            graphics.fillRect(x + pathSize, y + pathSize, pathSize * 2, pathSize);
    }
}
