package ui;

import javax.swing.*;
import java.awt.*;

public class Modal extends JDialog {


    public Modal(JFrame parent, String title, String message, int width, int height) {
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        label.setFont(new Font("Calibre", Font.PLAIN, 18));
        panel.add(label);
        getContentPane().add(panel);
        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

