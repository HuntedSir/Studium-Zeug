package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

/**
 * Synchronises the robots, by moving all the robots
 * that were given upon constructing this class to the same spot
 * and making them look in the same direction.
 * */
public class RobotSynchronizer {
    private int X = -1;
    private int Y = -1;
    private Direction direction = null;
    private static Robot[] robots;
    /**
     * Creates a new RobotSynchroniser with following parameters:
     * @param robots The robots that will get synchronised.
     */
    public RobotSynchronizer (Robot[] robots) {
        this.robots = robots;
    }
    /**
     * Sets the X to the coordinate that will get synced to.
     * @param x The x coordinate that the robots will have to move to.
     */
    public void setX(int x) {
        if (x >= 0 && x < World.getWidth()){
            this.X = x;
        }
    }
    /**
     * Sets the Y to the coordinate that will get synced to.
     * @param y The y coordinate that the robots will have to move to.
     */
    public void setY (int y) {
        if (y >= 0 && y < World.getHeight()){
            this.Y = y;
        }
    }

    /**
     * Sets the direction that the robots will have to face
     * @param direction The direction that the robots will have to face.
     */
    public void setDirection (Direction direction) {
        if (direction != null) {
            this.direction = direction;
        }
    }

    /**
     * Synchronises the robots, by moving all the robots
     * that were given upon constructing this class to the same spot
     * and making them look in the same direction.
     */
    public void sync() {
        for (int i = 0; i < this.robots.length; i++) {
            // Align the robot to the set Y coordinate
            int yVariable = this.robots[i].getY();
            if (this.Y >= 0 && this.Y < World.getHeight()){
                yVariable = this.Y;
            }
            while (this.robots[i].getY() != yVariable) {
                if (this.robots[i].getY() < yVariable) {
                    while (this.robots[i].getDirection() != Direction.UP){
                        this.robots[i].turnLeft();
                    }
                    this.robots[i].move();
                }
                else {
                    while (this.robots[i].getDirection() != Direction.DOWN){
                        this.robots[i].turnLeft();
                    }
                    this.robots[i].move();
                }
            }

            // Align the robot to the set X coordinate
            int xVariable = this.robots[i].getX();
            if (this.X >= 0 && this.X < World.getHeight()){

                xVariable = this.X;
            }
            while (this.robots[i].getX() != xVariable) {
                if (this.robots[i].getX() < xVariable) {
                    while (this.robots[i].getDirection() != Direction.RIGHT){
                        this.robots[i].turnLeft();
                    }
                    this.robots[i].move();
                }
                else {
                    while (this.robots[i].getDirection() != Direction.LEFT){
                        this.robots[i].turnLeft();
                    }
                    this.robots[i].move();
                }
            }

            // Turn the bot to the set direction
            while (this.robots[i].getDirection() != this.direction){
                this.robots[i].turnLeft();
            }

        }
    }
}
