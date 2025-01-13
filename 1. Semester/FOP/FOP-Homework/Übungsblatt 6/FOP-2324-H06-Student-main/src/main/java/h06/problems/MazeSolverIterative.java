package h06.problems;

import h06.world.DirectionVector;
import h06.world.World;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An iterative implementation of a maze solver. The solver uses the left-hand rule to find a path from the start to
 * the end of the maze.
 *
 * @author Nhan Huynh
 */
@DoNotTouch
public class MazeSolverIterative implements MazeSolver {

    /**
     * Constructs an iterative maze solver.
     */
    @DoNotTouch
    public MazeSolverIterative() {
    }

    @StudentImplementationRequired
    @Override
    public DirectionVector nextStep(World world, Point p, DirectionVector d) {
        if(world.isBlocked(p, d.rotate270())){
            if(world.isBlocked(p, d)){
                if(world.isBlocked(p, d.rotate90())){
                    return d.rotate90().rotate90();
                }
                else{
                    return d.rotate90();
                }
            }
            else{
                return d;
            }
        }
        else {
            return d.rotate270();
        }
    }

    @StudentImplementationRequired
    @Override
    public int numberOfSteps(World world, Point s, Point e, DirectionVector d) {
        int numberOfSteps = 1;
        while(!(s.x == e.x && s.y == e.y)){
            numberOfSteps++;
            DirectionVector nextStep = nextStep(world, s, d);
            d=nextStep;
            s=nextStep.getMovement(s);
        }
        return numberOfSteps;
    }

    @StudentImplementationRequired
    @Override
    public Point[] solve(World world, Point s, Point e, DirectionVector d) {
        int numberOfSteps = numberOfSteps(world, s, e, d);
        Point[] steps = new Point[numberOfSteps];

        for (int i = 0; i < steps.length; i++) {
            DirectionVector nextStep = nextStep(world, s, d);
            steps[i]=s;
            d=nextStep;
            s=nextStep.getMovement(s);
        }

        return steps;
    }
}
