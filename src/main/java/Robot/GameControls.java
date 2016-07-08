package Robot;

import java.awt.AWTException;
import java.awt.Robot;

/**
 *
 * @author Brenton
 */
public class GameControls {

    Robot robot;


    public GameControls() throws AWTException {
        robot = new Robot();
        robot.setAutoDelay(40);
        robot.setAutoWaitForIdle(true);
    }
    
    public void delay(int time) {
        robot.delay(time);
    }
    
    public void pressKey(Button b) {
        robot.keyPress(b.code());
    }
    
    public void releaseKey(Button b) {
        robot.keyRelease(b.code());
    }
    
    public void tapKey(Button b, int time) {
        robot.keyPress(b.code());
        robot.delay(time);
        robot.keyRelease(b.code());
    }
}
