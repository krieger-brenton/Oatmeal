/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oatmeal;

import Robot.Button;
import java.util.List;
import Robot.GameControls;
import java.awt.AWTException;
import java.util.Set;

/**
 *
 * @author Brenton
 */
public class GameLogic {

    GameControls gc;

    public GameLogic() throws AWTException {
        gc = new GameControls();
    }

    public void start() {
//        gc.delay(1000);
//        gc.pressKey(Button.START);
//        gc.delay(1000);
        gc.pressKey(Button.RIGHT);
    }

    public void run(List doodads) {
        if (doodads == null) {
            return;
        }
        for (Object dd : doodads) {
            Doodad d = (Doodad) dd;
            if (d.center.y > 460 && d.center.y < 500) {
//                System.out.println("Y bounds correct.");
                if (d.center.x < 76) {
//                    System.out.println("X bounds correct.");
                    System.out.println(d.center.x + " " + d.center.y);
                    System.out.println("Jump.");
                    gc.tapKey(Button.JUMP, 200);
                }
            }
        }
    }
}
