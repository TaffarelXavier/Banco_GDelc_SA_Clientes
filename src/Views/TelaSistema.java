/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Taffarel <taffarel_deus@hotmail.com>
 */
public class TelaSistema {

    private static Frame jFrame;

    public static void main(String[] args) {
        setFrame();
    }

    /**
     *
     */
    public static void setFrame() {
        
        jFrame = new Frame("Login");

        jFrame.setSize(300, 300);
        //jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

        jFrame.setVisible(true);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // do whatever else
            }
        });
        new TelaLogin(jFrame, true).setVisible(true);
    }

    /**
     *
     * @return
     */
    public static Frame getFrame() {
        return jFrame;
    }

}
