/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot;

import java.awt.event.KeyEvent;

/**
 *
 * @author Brenton
 */
public enum Button {
    START(KeyEvent.VK_G),
    LEFT(KeyEvent.VK_A),
    RIGHT(KeyEvent.VK_D),
    DOWN(KeyEvent.VK_S),
    JUMP(KeyEvent.VK_X),
    RUN(KeyEvent.VK_Z);

    private final int code;

    Button(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
