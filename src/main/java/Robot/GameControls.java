package Robot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Brenton
 */
public class GameControls {

    Robot robot;
    int START;
    int SELECT;
    int LEFT;
    int RIGHT;
    int JUMP;
    int SHOOT;

    public GameControls() throws AWTException {
        robot = new Robot();
        START = KeyEvent.VK_G;
        LEFT = KeyEvent.VK_A;
        RIGHT = KeyEvent.VK_D;
        JUMP = KeyEvent.VK_X;
    }

    public void testRobotControls() {
        robot.setAutoDelay(40);
        robot.setAutoWaitForIdle(true);

        robot.keyPress(START);
        robot.keyRelease(START);

        robot.delay(4000); // starting delay time

        robot.keyPress(RIGHT);
        robot.delay(2000);
        robot.keyRelease(RIGHT);

        robot.keyPress(LEFT);
        robot.delay(2000);
        robot.keyRelease(LEFT);

        robot.keyPress(RIGHT);
        robot.delay(100);
        robot.keyRelease(RIGHT);

        robot.keyPress(JUMP);
        robot.keyRelease(JUMP);
        robot.delay(2000);
        robot.keyPress(JUMP);
        robot.delay(500);
        robot.keyRelease(JUMP);

    }
}
