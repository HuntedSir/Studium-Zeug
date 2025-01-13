package h06;

import h06.problems.MazeSolverIterative;
import h06.problems.MazeSolverRecursive;
import h06.ui.MazeVisualizer;
import h06.world.DirectionVector;
import h06.world.World;

import java.awt.*;
import java.awt.geom.Point2D;


/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        World world = new World(5, 5);
        world.placeWall(0,0,false);
        world.placeWall(0,1,false);
        world.placeWall(0,2,false);
        world.placeWall(0,3,false);
        world.placeWall(2,0,false);
        world.placeWall(2,0,true);
        world.placeWall(1,1,true);
        world.placeWall(2,1,true);
        world.placeWall(3,1,true);
        world.placeWall(3,1,false);
        world.placeWall(1,4,false);
        world.placeWall(1,3,false);
        world.placeWall(2,2,true);
        world.placeWall(3,3,true);
        world.placeWall(3,3,false);
        world.placeWall(4,2,true);

        MazeVisualizer visualizer = new MazeVisualizer();
        visualizer.init(world);
        visualizer.show(true);

        MazeSolverRecursive recursive = new MazeSolverRecursive();
        MazeSolverIterative iterative = new MazeSolverIterative();
        Point s = new Point(2, 0);
        Point e = new Point(2, 4);
        visualizer.run(recursive, s, e, DirectionVector.UP);

    }
}
