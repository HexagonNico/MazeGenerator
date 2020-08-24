package nico.maze;

import javax.swing.*;

public class MazeMain {

    private final JFrame frame;
    private final MazeGenerator mazeGenerator;
    private final Timer timer;

    private MazeMain() {
        this.frame = new JFrame("Maze generator");
        this.mazeGenerator = new MazeGenerator();
        this.timer = new Timer(20, mazeGenerator);
    }

    /**Start maze generator*/
    private void start() {
        this.createWindow();
        //Use one of the following two
//        this.mazeGenerator.generateInstantly(); //Generate the maze instantly
        this.timer.start(); //Show every step
    }

    /**Create window*/
    private void createWindow() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.add(mazeGenerator);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MazeMain().start();
    }
}
