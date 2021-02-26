package ui;

import javax.swing.*;
import java.awt.*;

public class Modal extends JDialog {

    /**
     *Used to display messages during the game.
     */
    public Modal(JFrame parent, String title, String message, int width, int height) {
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        label.setFont(new Font("Calibri", Font.PLAIN, 18));
        panel.add(label);
        this.getContentPane().add(panel);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
