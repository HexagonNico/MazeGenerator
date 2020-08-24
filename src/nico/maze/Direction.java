package nico.maze;

import java.util.Random;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public int dx;
    public int dy;
    public Direction opposite;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
    }

    public static Direction getRandom(Random random) {
        Direction[] values = Direction.values();
        return values[random.nextInt(values.length)];
    }
}
