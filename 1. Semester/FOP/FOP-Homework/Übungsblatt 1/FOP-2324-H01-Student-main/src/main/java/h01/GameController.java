package h01;

import fopbot.Robot;
import fopbot.World;
import h01.template.GameControllerBase;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

/**
 * A {@link GameController} controls the game loop and the {@link Robot}s and checks the win condition.
 */
public class GameController extends GameControllerBase {

    /**
     * Creates a new {@link GameControllerBase}.
     */
    public GameController() {
        setup();
    }

    @Override
    public void checkWinCondition() {
        Robot cleaner = getCleaningRobot();
        Robot contaminant1 = getContaminant1();
        Robot contaminant2 = getContaminant2();


        float fieldsWithCoins = 0;
        float numberOfFields = World.getHeight() * World.getWidth();

        for (int i = 0; i < World.getWidth(); i++) {
            for (int n = 0; n < World.getHeight(); n++){
                if (Utils.getCoinAmount(i, n) > 0) {
                    fieldsWithCoins++;
                }
            }
        }

        if ((contaminant1.isTurnedOff() && contaminant2.isTurnedOff()) || Utils.getCoinAmount(0 ,World.getHeight() - 1) >= 200) {
            if((contaminant1.isTurnedOff())==false) {
                contaminant1.turnOff();
            }
            if((contaminant2.isTurnedOff())==false) {
                contaminant2.turnOff();
            }
            System.out.println("Cleaning robot won!");
            stopGame();
        }
        float coinFieldDifferential = fieldsWithCoins / numberOfFields;

        if (coinFieldDifferential >= 0.5) {
            System.out.println("Contaminants won!");
            cleaner.turnOff();
            stopGame();
        }

    }
}
