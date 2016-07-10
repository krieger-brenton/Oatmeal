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

/**
 *
 * @author Brenton
 */
public class GameLogic {

    GameControls gc;
    static int timer;
    boolean gap;
    boolean baddie;

    public GameLogic() throws AWTException {
        gc = new GameControls();
        timer = 0;
        gap = false;
    }

    public void start() {
//        gc.delay(1000);
//        gc.pressKey(Button.START);
//        gc.delay(1000);
//        gc.pressKey(Button.RUN);
        gc.pressKey(Button.RIGHT);

    }

    public void run(List<Doodad> doodads, int[] pix, int[] pix2) throws AWTException {
        if (doodads == null) {
            return;
        }
        gap = true;
        baddie = false;

        for (Doodad d : doodads) {

            if (d.center.y > 260 && d.center.y < 300 && d.xMin < 65) {
                baddie = true;

            }
            if (d.center.y > 300 && d.center.x < 40) {
                gap = false;
            }
        }

        if (pix[0] == ImageControls.BUSH || pix[1] == ImageControls.BUSH || pix[2] == ImageControls.BUSH || pix[3] == ImageControls.BUSH) {
            gc.tapKey(Button.JUMP, 350);
        } else if (gap) {
            gc.pressKey(Button.RUN);
            gc.tapKey(Button.JUMP, 3);
            gc.releaseKey(Button.RUN);
        } else if (pix2[0] == ImageControls.GOOMBABELLY || pix2[1] == ImageControls.GOOMBABELLY 
                || pix2[2] == ImageControls.GOOMBABELLY || pix2[3] == ImageControls.GOOMBABELLY) {
            gc.pressKey(Button.RUN);
            gc.tapKey(Button.JUMP, 350);
            gc.releaseKey(Button.RUN);
        } else if (baddie) {
            gc.pressKey(Button.RUN);
            gc.tapKey(Button.JUMP, 3);
//            gc.delay(100);
            gc.releaseKey(Button.RUN);
        }

    }

//        class Butts implements Runnable {
//
//            List<Doodad> doodads;
//            GameControls gc1;
//
//            Butts(List<Doodad> d) throws AWTException {
//                doodads = d;
//                gc1 = new GameControls();
//            }
//
//            @Override
//            public void run() {
////                System.out.println(timer);
//                for (Doodad d : doodads) {
//                    if (d.center.y > 460 && d.center.y < 500) {
//                        if (d.center.x < 80) {
//                            gc1.tapKey(Button.JUMP, 1);
//                            timer = 0;
//                        }
//                    }
//                    if (timer > 35) {
//                        gc1.tapKey(Button.JUMP, 400);
//                        timer = 0;
//                    }
//                }
//            }
//        }
//        Thread t = new Thread(new Butts(doodads));
//        t.start();
}
