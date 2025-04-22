package mainPkg;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JHorror");

        frame.setResizable(false);
        frame.setBounds(0, 0, Defines.width, Defines.height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JApp());
        frame.setVisible(true);
        frame.pack();
    }
}
//Copyright 2024 FbLolly
