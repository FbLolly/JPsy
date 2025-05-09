package mainPkg;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

import menusPkg.Menu;

public class JApp extends JPanel implements Runnable {
    private double drawInterval;
    private double nextDrawTime;
    private double remainingTime;

    private Thread thread;
    private boolean isWindows;

    public Game game;
    public Menu menu;

    public JApp() {
        this.isWindows();

        try {
            InputStream is = this.getClass().getResourceAsStream("fonts/CaskaydiaCove-Bold.ttf"); //im forced to put it in the same pkg
            Font f = Font.createFont(Font.TRUETYPE_FONT, is);
            Font sizedFont = f.deriveFont((float) (Defines.fontSize / Defines.zoom));
            Defines.font = sizedFont;
            this.setFont(Defines.font);
        } catch (IOException | FontFormatException e) {
            this.setFont(new Font("serif", Font.BOLD, 30));
        }

        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(Defines.width, Defines.height));
        this.setFocusable(true);

        this.setSize(new Dimension(Defines.width, Defines.height));
        this.setBounds(0, 0, Defines.width, Defines.height);
        this.setVisible(true);

        this.menu = new Menu(this);

        this.startGame();
    }

    private void startGame() {
        thread = new Thread(this);
        thread.start();
    }

    private long getNewTimeAndSleep() {
        drawInterval = 1000000000 / Defines.FPS; //recalculate drawInterval in case the fps changes

        try {
            remainingTime = nextDrawTime - System.nanoTime();
            remainingTime /= 1000000;

            if (remainingTime < 0)
                remainingTime = 0;

            Thread.sleep((long) remainingTime);
            nextDrawTime += drawInterval;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (long) remainingTime;
    }

    private void isWindows() {
        String osName;

        osName = System.getProperty("os.name");

        this.isWindows = false;

        if (osName.length() > 7 && osName.substring(0, 7).equals("windows")) {
            this.isWindows = true;
        }
    }

    @Override
    public void run() {
        drawInterval = 1000000000 / Defines.FPS;
        nextDrawTime = System.nanoTime() + drawInterval;
        remainingTime = 0;
        Defines.timer = 0;

        while (thread != null) {
            getNewTimeAndSleep();

            update();
            repaint();

            if (!(this.isWindows)) {
                Toolkit.getDefaultToolkit().sync();
            }

            if (Defines.timer > Defines.FPS)
                Defines.timer = 0;
            Defines.timer += 1;

            if (Defines.animationTimer > Defines.FPS * Defines.maxSlow) {
                Defines.animationTimer = 0;
            }
            Defines.animationTimer += 1;
        }
    }

    private void update() {
        if (this.game == null){
            this.menu.update();
            return;
        }

        this.game.update();
    }

    public void paintComponent(Graphics g) {
        if (this.game == null){
            this.menu.paintComponent(g);
            return;
        }

        if (this.game.paintComponent(g) == 1){
            this.game = null;
            this.menu = new Menu(this);
        }
    }
}
